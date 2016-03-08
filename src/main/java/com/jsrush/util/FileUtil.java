package com.jsrush.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	
	public static String copyFile(InputStream uploadFile,String destPath, String filename) throws Exception {
		String time = "(" + DateUtil.formatTimestampToStringByFmt(DateUtil.getNowTimestamp(), DateUtil.ADC_MSG_SID) + ")";
		String newName = filename.substring(0, filename.lastIndexOf(".")) + time + ".xls" ;
		File file = new File(destPath + newName);
		int available = uploadFile.available();
		byte[] b = new byte[available];
		FileOutputStream foutput = new FileOutputStream(file);
		uploadFile.read(b);
		foutput.write(b);
		foutput.flush();
		foutput.close();
		uploadFile.close();
		return newName;
	}
	
	/**
	 * 读取文件内容文本信息
	 * @param filePath	项目中相对路径
	 * @return
	 */
	public static String readFileText(String filePath) {
		StringBuilder text = new StringBuilder();
		byte[] buff = new byte[1024];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(SystemUtil.readProjectPath() + filePath);
			int eof = 0;
			while ((eof = fis.read(buff)) > -1) {
				text.append(new String(buff, 0, eof));
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return text.toString();
	}
	
	
}
