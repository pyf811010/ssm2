package cn.tycoding.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/AnalyzeProgram")
public class AnalyzeProgramController {
	@RequestMapping(value = "/MLR_DataAnalysis")
	public void open1() {
    	Runtime runtime = Runtime.getRuntime();  
    	try {
    		Process exec = runtime.exec("cmd /c D:\\Softwares\\【电脑】黑科下载器-3.5\\HeikeTest.exe");
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}  
    }
}
