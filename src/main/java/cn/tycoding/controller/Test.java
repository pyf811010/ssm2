package cn.tycoding.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Test {
	
	public static void openFile(String filePath){
		File file = new File(filePath);
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
//		String dir = "D:\\其他\\谷歌浏览器\\ssm2\\";
//		File file = new File(dir,"肌电数据传感器对照表.xlsx");
//		file.delete();
		File file = new File("C:\\Users\\pyf\\Desktop\\杂例\\证件照、证书照、简历\\1.jpg");
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Test.openFile("C:\\Users\\pyf\\Desktop\\杂例\\证件照、证书照、简历\\1.jpg");
	}

}
