package com.unisinos;
import java.io.File;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.unisinos.dao.DaoManager;
import com.unisinos.domain.AppInfoDto;
import com.unisinos.domain.AppInfoExtractor;
import com.unisinos.util.FileManager;
import com.unisinos.util.FileUtil;

public class Main {
	
	public static void main(String[] args) {
		
		File folder = new File("C:\\Users\\Felipe\\Desktop\\TCC2\\bancos");
		AppInfoExtractor builder = new AppInfoExtractor();
				
		List<AppInfoDto> result = new LinkedList<>();
		
		if(folder.canRead() && folder.isDirectory()) {
			for (String bdName : folder.list((dir, name) -> name.endsWith(".db")) ) {
				String bdPath = "jdbc:sqlite:" + folder.getAbsolutePath() + "\\" + bdName;
				DaoManager dao = DaoManager.getInstance().openConnection(bdPath);
				try {
					ResultSet resultSet = dao.query(" select * from app_infos order by id ");
					String userName = bdName.substring(0, bdName.indexOf("."));
					result.addAll(builder.build(userName, resultSet));
					System.out.println("DB: " + bdPath);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		
		FileUtil fileUtil = new FileUtil();
		String resultCsv = fileUtil.createCSVData(result);
		String resultArff = fileUtil.createArffData(result, "train");
		
		FileManager.writeFiles(resultCsv, "C:\\Users\\Felipe\\Desktop\\TCC2\\bancos\\train.csv");
		FileManager.writeFiles(resultArff, "C:\\Users\\Felipe\\Desktop\\TCC2\\bancos\\train.arff");
		
		System.out.println("Arquivos criados");
	}

}
