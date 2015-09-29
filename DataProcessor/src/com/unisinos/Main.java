package com.unisinos;
import java.io.File;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.unisinos.dao.DaoManager;
import com.unisinos.domain.AppInfoBuilder;
import com.unisinos.domain.AppInfoDto;
import com.unisinos.util.ExcelUtil;
import com.unisinos.util.FileManager;

public class Main {
	
	public static void main(String[] args) {
		
		File folder = new File("C:\\Users\\Felipe\\Desktop\\TCC2\\bancos");
		AppInfoBuilder builder = new AppInfoBuilder();
				
		List<AppInfoDto> result = new LinkedList<>();
		
		if(folder.canRead() && folder.isDirectory()) {
			for (String bdName : folder.list()) {
				String bdPath = "jdbc:sqlite:" + folder.getAbsolutePath() + "\\" + bdName;
				DaoManager dao = DaoManager.getInstance().openConnection(bdPath);
				
				try {
					ResultSet resultSet = dao.query(" select * from app_infos order by id ");
					String userName = bdName.substring(0, bdName.indexOf("."));
					result.addAll(builder.build(userName, resultSet));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		
		ExcelUtil excelUtil = new ExcelUtil();
		List<HSSFWorkbook> resultWb = excelUtil.createXls(result);
		
		FileManager.writeFiles(resultWb, "C:\\Users\\Felipe\\Desktop\\TCC2\\bancos\\");
		
		System.out.println("Arquivos criados");
	}

}
