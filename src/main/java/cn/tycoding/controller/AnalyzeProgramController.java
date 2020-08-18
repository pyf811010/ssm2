package cn.tycoding.controller;

import cn.tycoding.pojo.State;
import cn.tycoding.service.FilesKandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Arrays;

@Controller
@RequestMapping(value = "/AnalyzeProgram")
public class AnalyzeProgramController {
    @RequestMapping(value = "/MLR_DataAnalysis")
    public void open1() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process exec = runtime.exec("cmd /k start D:\\Program\\MLR_Program\\main.exe");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Autowired
    private FilesKandService filesKandService;

    /**
     * hmm模型
     *
     * @param id        excel文件id
     * @param numStates 模型隐状态数量
     * @param numMix    混合高斯模型的分模型数
     * @param indexs    文件中哪些列的数据进行训练 例如："2,4,6"
     * @author fdd
     */
    @ResponseBody
    @RequestMapping("/hmmAnalysis")
    public State openHmm(@RequestParam(value = "pythonId") int id,
                         @RequestParam(value = "indexs[]") int[] indexs,
                         @RequestParam(value = "numStates", defaultValue = "6") Integer numStates,
                         @RequestParam(value = "numMix", defaultValue = "1") Integer numMix) {
        //获取文件的地址
        String url = filesKandService.getUrl(id);
        Arrays.sort(indexs);
        StringBuffer indexsList = new StringBuffer();
        for (int i = 0; i < indexs.length; i++) {
            if (i != indexs.length - 1){
                indexsList.append(indexs[i]);
                indexsList.append(",");
            }else {
                indexsList.append(indexs[i]);
            }
        }
        String n_state = String.valueOf(numStates);
        String n_mix = String.valueOf(numMix);
        try {
            // python参数
            String[] arg = new String[]{"python", "D:/Program/pythonHMM.py",
                    url, n_state, n_mix, indexsList.toString()};
            System.out.println("开始运行python");
            Process process = Runtime.getRuntime().exec(arg);
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new State(1, "运行结束,点击\"查看结果\"打开文件");
    }


    /**
     * 获取数据excel表中的列信息
     *
     * @param pythonId 文件ID
     * @return excel表的列信息（列名）
     * @author fdd
     */
    @ResponseBody
    @RequestMapping("/findInfo")
    public String[] getTableInfo(@RequestParam int pythonId) {
        System.out.println("查询表头信息");
        return filesKandService.getFileInfo(pythonId);
    }

    @RequestMapping("/openExcel")
    public void openExcel() {
        String url = "D:\\ProgrammData\\kend_train_result.xlsx";
        try {
            Runtime.getRuntime().exec("cmd /c start " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}