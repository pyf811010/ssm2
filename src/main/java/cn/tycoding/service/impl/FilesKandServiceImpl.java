package cn.tycoding.service.impl;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tycoding.mapper.AdminMapper;
import cn.tycoding.mapper.EgContrastMapper;
import cn.tycoding.mapper.FilesElectromyographyMapper;
import cn.tycoding.mapper.FilesKandMapper;
import cn.tycoding.mapper.PreecMapper;
import cn.tycoding.pojo.Admin;
import cn.tycoding.pojo.EgContrast;
import cn.tycoding.pojo.FilesElectromyography;
import cn.tycoding.pojo.FilesKand;
import cn.tycoding.pojo.Machine;
import cn.tycoding.pojo.ObjectQuery;
import cn.tycoding.pojo.State;
import cn.tycoding.service.FilesEleService;
import cn.tycoding.service.FilesKandService;
import cn.tycoding.util.DataFormatUtil;
import cn.tycoding.util.ExceptionUtil;
import cn.tycoding.util.QueryCondition;
import cn.tycoding.util.QueryUtil;
import cn.tycoding.util.SqlJointUtil;


@Service
public class FilesKandServiceImpl implements FilesKandService {
    /**
     * 注入service层
     */
    @Autowired
    private FilesKandMapper filesKandMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private PreecMapper preecMapper;

    private FilesKand selectByPrimaryKey(Integer m_id) {
		return filesKandMapper.selectByPrimaryKey(m_id);
	}

	@Override
	public ObjectQuery findByPage(Boolean _search, String filters, int page, int rows) {
		 if (!_search) {
	            return findByPage(page, rows);
	        } else {
	        	// 按filters中的条件查找
	            QueryCondition queryCondition = null;
	            try {
	                queryCondition = new ObjectMapper().readValue(filters, QueryCondition.class);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	            String sql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, false, "files_kand");

	            List list = filesKandMapper.findByFilters(sql);
	            String getSumSql = SqlJointUtil.getSqlByFilters(queryCondition, (page - 1) * rows, rows, true, "files_kand");
	            int records = filesKandMapper.findByFiltersSum(getSumSql);
	            int total = QueryUtil.getTotalPage(records, rows);
	            ObjectQuery sq = new ObjectQuery(page, total, records, list);
	            return sq;
	        }
	}

	private ObjectQuery findByPage(int page, int rows) {
		// 本次操作不是搜索，而是按条件进行查询
        // 查询全部
        // page 当前所处页数 rows 每页显示的条数
        List list = filesKandMapper.findByPage((page - 1) * rows, rows);
        // 获得总记录数
        int records = filesKandMapper.getSum();
        // 获得总页数
        int total = QueryUtil.getTotalPage(records, rows);
        // 第一个参数为当前页数，第二个为总页数，第三个参数为总记录数，第四个参数为模型对象
        ObjectQuery sq = new ObjectQuery(page, total, records, list);
        return sq;
	}

	@Override
	public List find() {
		
		List list = filesKandMapper.find();
		
		return list;
	}

	@Override
    public String handle(String oper, FilesKand filesKand, String id[]) {
		filesKand = DataFormatUtil.checkNull(filesKand);
        // oper有三种操作 add,del,edit,
        switch (oper) {
            case "edit":
                // 按st_id进行更改学生数据
                if (id != null) {
//                    student.setSt_id(id[0]);
                	filesKand.setExpid(Integer.valueOf(id[0]));
                }
                try {
                    int editAffectedRow = filesKandMapper.edit(filesKand);
                    if (editAffectedRow == 1) {
                        return "success";
                    }
                } catch (Exception e) {
                    return ExceptionUtil.HandleDataException(e,"files_kand");
                }
                break;
            case "del":

                // 会按st_id来删除，考虑到存在多选，此时主键id是数组
                int count = 0;
                for (int i = 0; i < id.length; i++) {
                    int expid = Integer.parseInt(id[i]);
                	String url = filesKandMapper.getPathByExpid(expid);
                	File file = new File(url);
                	boolean result = file.delete();
                    int tryCount = 0;
                    while (!result && tryCount++ < 10) {
                        System.gc();    //回收资源
                        result = file.delete();
                    }
                    filesKandMapper.del(id[i]);
                    count++;
                }
                String str = count + "条成功删除" + (id.length - count) + "条删除失败";
                System.out.println(str);
                return "success";
            case "add":
                // 新增对象
                System.out.println(filesKand.toString());
                try {
                    int addAffectedRow = filesKandMapper.add(filesKand);
                    if (addAffectedRow == 1) {
                        return "success";
                    }
                } catch (Exception e) {
                    return ExceptionUtil.HandleDataException(e,"files_kand");
                }
        }
        return "success";
    }

	@Override
	public void download(int expid, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
        State state = new State();
        String path = filesKandMapper.getPathByExpid(expid);
        String filename = filesKandMapper.getFile_name(expid);
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        String fileName = URLEncoder.encode(filename, "utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        byte[] buffer = new byte[8192];
        while ((len = bis.read(buffer, 0, 8192)) != -1) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.close();
		
	}

	@Override
	public void open(int expid) throws IOException {
		// TODO Auto-generated method stub
        State state = new State();
        String path = filesKandMapper.getPathByExpid(expid);
        String filename = filesKandMapper.getFile_name(expid);
        File file = new File(path);
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String authorityTemp(String oper, FilesKand filesKand, String[] id, HttpServletRequest request) throws UnsupportedEncodingException {
		String user_name = (String) request.getSession().getAttribute("user_name");
		String type = adminMapper.findTypeByUserName(user_name);
		if(type.equals("管理员")){
			System.out.println("用户为管理员");
			if(oper.equals("add")) filesKand.setUser_name(user_name);
			return URLEncoder.encode(handle(oper, filesKand, id), "UTF-8");
		}
        System.out.println("session中的user_name -----> " + user_name);
        if(id == null){
        	//点击页面下方编辑按钮会传入后台id，而直接在修改行提交修改则不会直接传入后台id
        	String machineUser_name2 = filesKand.getUser_name();
        	System.out.println(machineUser_name2);
            if (!user_name.equals(machineUser_name2)) {
                System.out.println("无权限修改");
                return URLEncoder.encode("fail", "UTF-8");
            }
            String temp = handle(oper, filesKand, id);
            System.out.println(temp);
            // 对传回的中文进行编码
            return URLEncoder.encode(temp, "UTF-8");
        }else{
        	//增加受试者信息的判断(增加受试者信息时，id为_empty，需要数据库自增)
        	 if(id[0].equals("_empty")){
        		 filesKand.setUser_name(user_name);
                 String temp = handle(oper, filesKand, id);
                 System.out.println(temp);
                 // 对传回的中文进行编码
                 return URLEncoder.encode(temp, "UTF-8");
             }
            for (int i = 0; i < id.length; i++) {
                String machineUser_name = (selectByPrimaryKey(Integer.valueOf(id[i]))).getUser_name();
                System.out.println("数据库中的user_name -----> " + machineUser_name);
                if (!user_name.equals(machineUser_name)) {
                    System.out.println("无权限修改");
                    return URLEncoder.encode("fail", "UTF-8");
                }
            }
            String temp = handle(oper, filesKand, id);
            System.out.println(temp);
            // 对传回的中文进行编码
            return URLEncoder.encode(temp, "UTF-8");
        }
	}
	

	@Override
	public State sign(int expid,HttpServletRequest request) throws IOException {
		State state = new State();
		String user_name = (String) request.getSession().getAttribute("user_name");
		String type = adminMapper.findTypeByUserName(user_name);
		if(type.equals("管理员")){
			System.out.println("用户为管理员");
			filesKandMapper.sign(expid);
			state.setInfo("标记成功");
			state.setSuccess(1);
			return state;
		}else{
			String FilesUser_name = (selectByPrimaryKey(expid)).getUser_name();
            System.out.println("数据库中的user_name -----> " + FilesUser_name);
            if (!user_name.equals(FilesUser_name)) {
                System.out.println("无权限修改");
    			state.setInfo("无标记权限");
    			state.setSuccess(0);//0代表失败
    			return state;
            }else{
            	filesKandMapper.sign(expid);
            	state.setInfo("标记成功");
    			state.setSuccess(1);
    			return state;
            }
		}
		
	}

	@Override
	public State cancelSign(int expid,HttpServletRequest request) throws IOException {
		State state = new State();
		String user_name = (String) request.getSession().getAttribute("user_name");
		String type = adminMapper.findTypeByUserName(user_name);
		if(type.equals("管理员")){
			System.out.println("用户为管理员");
			filesKandMapper.cancelSign(expid);
			state.setInfo("标记成功");
			state.setSuccess(1);
			return state;
		}else{
			String FilesUser_name = (selectByPrimaryKey(expid)).getUser_name();
            System.out.println("数据库中的user_name -----> " + FilesUser_name);
            if (!user_name.equals(FilesUser_name)) {
                System.out.println("无权限修改");
    			state.setInfo("无标记权限");
    			state.setSuccess(0);//0代表失败
    			return state;
            }else{
            	filesKandMapper.cancelSign(expid);
            	state.setInfo("标记成功");
    			state.setSuccess(1);
    			return state;
            }
		}
	}
	
	/** 
     * 解压文件到指定目录 
     * 解压后的文件名，和之前一致 
     * @param zipFile   待解压的zip文件 
     * @param descDir   指定目录 
     */  
    @SuppressWarnings("rawtypes")  
    public static void unZipFiles(File zipFile, String descDir) throws IOException {  
          
        ZipFile zip = new ZipFile(zipFile,Charset.forName("GBK"));//解决中文文件夹乱码  
        String name = zip.getName().substring(zip.getName().lastIndexOf('\\')+1, zip.getName().lastIndexOf('.'));  
          
        File pathFile = new File(descDir+name);  
        if (!pathFile.exists()) {  
            pathFile.mkdirs();  
        }  
          
        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {  
            ZipEntry entry = (ZipEntry) entries.nextElement();  
            String zipEntryName = entry.getName();  
            InputStream in = zip.getInputStream(entry);  
            String outPath = (descDir + name +"/"+ zipEntryName).replaceAll("\\*", "/");  
              
            // 判断路径是否存在,不存在则创建文件路径  
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
            if (!file.exists()) {  
                file.mkdirs();  
            }  
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
            if (new File(outPath).isDirectory()) {  
                continue;  
            }  
            // 输出文件路径信息  
//          System.out.println(outPath);  
  
            FileOutputStream out = new FileOutputStream(outPath);  
            byte[] buf1 = new byte[1024];  
            int len;  
            while ((len = in.read(buf1)) > 0) {  
                out.write(buf1, 0, len);  
            }  
            in.close();  
            out.close();  
        }  
        System.out.println("******************解压完毕********************");  
        return;  
    }  
	

	@Override
	public State batchDownload(String fi_info) throws IOException {
		State state = new State();
		List<Integer> ids = new ArrayList<Integer>();
		//根据实验方式查询到对应的id号，放到List集合中
		ids = preecMapper.getIdByAdvance(fi_info);
		if(ids.isEmpty()){
			//集合为空，说明没有对应的文件
			state.setSuccess(0);
			return state;
		}else{
			//需要压缩的文件--包括文件地址和文件名
			ArrayList<String> path = new ArrayList<String>();
			ArrayList<String> file_names = new ArrayList<String>();
		    for (int i = 0; i < ids.size(); i++) {
			 String url = filesKandMapper.getPathByExpid(ids.get(i));
			 String file_name = filesKandMapper.getFile_name(ids.get(i));
//			 String filename = filesKandMapper.getFile_name(i);
			 path.add(i, url);
			 file_names.add(i, file_name);
		 }
	        // 要生成的压缩文件地址和文件名称
		    String basePath = "D:\\MLR_Data";
		    String desPath = "D:\\MLR_Data\\"+fi_info+".zip";
		    File baseFile = new File(basePath);
		    if (!baseFile.exists()) {
		    	baseFile.mkdirs();
		    }
	        File zipFile = new File(desPath);
	        ZipOutputStream zipStream = null;
	        FileInputStream zipSource = null;
	        BufferedInputStream bufferStream = null;
	        try {
	            //构造最终压缩包的输出流
	            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
	            for(int i =0;i<path.size();i++){
	                File file = new File(path.get(i));
	                //将需要压缩的文件格式化为输入流
	                zipSource = new FileInputStream(file);
	                //压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样
	                ZipEntry zipEntry = new ZipEntry(file_names.get(i));
	                //定位该压缩条目位置，开始写入文件到压缩包中

	                zipStream.putNextEntry(zipEntry);

	                //输入缓冲流
	                bufferStream = new BufferedInputStream(zipSource, 1024 * 10);
	                int read = 0;
	                //创建读写缓冲区
	                byte[] buf = new byte[1024 * 10];
	                while((read = bufferStream.read(buf, 0, 1024 * 10)) != -1)
	                {
	                    zipStream.write(buf, 0, read);
	                }
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            //关闭流
	            try {
	                if(null != bufferStream) bufferStream.close();
	                if(null != zipStream) zipStream.close();
	                if(null != zipSource) zipSource.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        //解压
	        try {  
	            unZipFiles(new File("D:/MLR_Data/"+fi_info+".zip"), "D:/MLR_Data/");  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        //删除压缩包
        	File file = new File("D:/MLR_Data/"+fi_info+".zip");
        	boolean result = file.delete();
        	int tryCount = 0;
        	while (!result && tryCount++ < 10) {
        		System.gc();    //回收资源
        		result = file.delete();
        	}
        	if(result) System.out.println("压缩文件删除成功");else System.out.println("压缩文件删除失败");
	        state.setSuccess(1);
	        return state;
		}
	}

}
