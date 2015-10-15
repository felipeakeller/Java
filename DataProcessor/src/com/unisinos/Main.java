package com.unisinos;
import java.io.File;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.unisinos.dao.DaoManager;
import com.unisinos.domain.AppInfoDto;
import com.unisinos.domain.AppInfoExtractor;
import com.unisinos.domain.AppInfoSpliter;
import com.unisinos.util.FileManager;
import com.unisinos.util.FileUtil;

public class Main {
	
	public static void main(String[] args) {
		
		File folder = new File("C:\\Users\\Felipe\\Desktop\\TCC2\\bancos");
		AppInfoExtractor builder = new AppInfoExtractor();
				
		List<AppInfoDto> trainList = new LinkedList<>();
		List<AppInfoDto> testList = new LinkedList<>();
		
		if(folder.canRead() && folder.isDirectory()) {
			for (String bdName : folder.list((dir, name) -> name.endsWith(".db")) ) {
				String bdPath = "jdbc:sqlite:" + folder.getAbsolutePath() + "\\" + bdName;
				DaoManager dao = DaoManager.getInstance().openConnection(bdPath);
				try {
					ResultSet resultSet = dao.query(" select * from app_infos order by id ");
					String userName = bdName.substring(0, bdName.indexOf("."));
					
					List<AppInfoDto> result = builder.build(userName, resultSet);
					trainList.addAll(AppInfoSpliter.train(result));
					testList.addAll(AppInfoSpliter.test(result));
					
					System.out.println("DB: " + bdPath);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		FileUtil fileUtil = new FileUtil();		
		String resultCsv = fileUtil.createCSVData(trainList);
		String resultArff = fileUtil.createArffData(trainList, "train");
		FileManager.writeFiles(resultCsv, "C:\\Users\\Felipe\\Desktop\\TCC2\\bancos\\train\\train.csv");
		FileManager.writeFiles(resultArff, "C:\\Users\\Felipe\\Desktop\\TCC2\\bancos\\train\\train.arff");
		
		String testCsv = fileUtil.createCSVData(trainList);
		String testArff = fileUtil.createArffData(trainList, "test");
		FileManager.writeFiles(testCsv, "C:\\Users\\Felipe\\Desktop\\TCC2\\bancos\\test\\test.csv");
		FileManager.writeFiles(testArff, "C:\\Users\\Felipe\\Desktop\\TCC2\\bancos\\test\\test.arff");
		
		System.out.println("Arquivos criados");
	}

}
