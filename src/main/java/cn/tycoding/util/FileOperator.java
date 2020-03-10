package cn.tycoding.util;

import java.io.File;

public class FileOperator {
	/**
	 * 删除文件
	 * 
	 * @param pathname
	 * @return
	 * @throws IOException
	 */
	public static boolean deleteFile(String pathname){
		boolean result = false;
		File file = new File(pathname);
		if (file.exists()) {
			file.delete();
			result = true;
			System.out.println("文件已经被成功删除");
		}
		return result;
	}

}
