package cn.tycoding.util;

import java.util.UUID;

public class ShortUUID {
	private String[] chars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public String generateUUID() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int j = 0; j < 8; j++) {
			String str = uuid.substring(j * 4, j * 4 + 4);
			int par = Integer.parseInt(str, 16);
			shortBuffer.append(chars[par % 36]);
		}
		return shortBuffer.toString();
	}

}
