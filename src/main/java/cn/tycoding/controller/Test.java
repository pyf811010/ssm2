package cn.tycoding.controller;

import java.io.File;

public class Test {
	public static void main(String[] args) {
		String dir = "D:\\其他\\谷歌浏览器\\ssm2\\";
		File file = new File(dir,"肌电数据传感器对照表.xlsx");
		file.delete();
	}

}
