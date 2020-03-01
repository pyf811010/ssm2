/*
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
 
@Controller
@RequestMapping(value = "/manage")
public class ManageAction{
    *//**
	 * 通过流把文件传到前台下载  
	 * @param request
	 * @param response
	 * @param id  第几个文件  （因为有多个文件  用;号隔开的）
	 * @param tzggid   对应的通知公告id
	 *//*
       @RequestMapping(value = "/findfile")
	@ResponseBody
	public void findfile(HttpServletRequest request,HttpServletResponse response,@RequestParam("id") String id,@RequestParam("tzggid") String tzggid) throws IOException {
		ServletOutputStream out = null;  
	        FileInputStream ips = null;  
	        List<Map<String, Object>> list = null;  //此处为业务需要
			list = jdbcTemplate.queryForList("select fjaddress,fjname from tb_tzgg where id = ?",tzggid);  //此处为业务需要
			if(list.size() > 0){
		    try {  
			   String url = String.valueOf(list.get(0).get("fjaddress")).split(";")[Integer.valueOf(id)];  //此处为业务需要  如果是测试可以指定路径
		            //获取文件存放的路径
		            File file = new File(url);
		            String fileName=file.getName();
                            //获取到文字 数据库里对应的附件名字加上老的文件名字：filename 截取到后面的文件类型 例：txt  组成一个新的文件名字：newFileName
			    String newFileName = String.valueOf(list.get(0).get("fjname")).split(";")[Integer.parseInt(id)]+"."+fileName.substring(fileName.lastIndexOf(".")+1);
		            if(!file.exists()) {
		            	//如果文件不存在就跳出
		                return;
		            }
		            ips = new FileInputStream(file);  
		            response.setContentType("multipart/form-data"); 
                            //为文件重新设置名字，采用数据库内存储的文件名称 
		            response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(newFileName.getBytes("UTF-8"),"ISO8859-1") + "\"");
		            out = response.getOutputStream();  
		            //读取文件流  
		            int len = 0;  
		            byte[] buffer = new byte[1024 * 10];  
		            while ((len = ips.read(buffer)) != -1){  
		                out.write(buffer,0,len);  
		            }  
		            out.flush();  
		        }catch (Exception e){  
		            e.printStackTrace();  
		        }finally {  
		            try {
						out.close();
						ips.close();  
					} catch (IOException e) {
						System.out.println("关闭流出现异常");
						e.printStackTrace();
					}  
		        }  
			}
	        return ;  
	}
}
*/