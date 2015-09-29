package com.unisinos.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FileManager {

	public static void writeFiles(List<HSSFWorkbook> wb, String path) {
		try {
			for (int i = 0; i < wb.size(); i++) {
				FileOutputStream out = new FileOutputStream(path + "appInfo" + i + ".xls");
				wb.get(i).write(out);
				out.close();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

}
