package com.unisinos;
import java.io.File;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.unisinos.dao.DaoManager;
import com.unisinos.domain.AppInfoDto;
import com.unisinos.domain.AppInfoExtractor;
import com.unisinos.domain.AppInfoSpliter;
import com.unisinos.domain.TestUserRandomSplit;
import com.unisinos.util.FileManager;
import com.unisinos.util.FileUtil;
import com.unisinos.util.PropertiesUtil;

public class Main {
	
	public static void main(String[] args) {
		
		Log.show("Iniciando");
		PropertiesUtil propertiesUtil = new PropertiesUtil();
		
		File dataBaseDir = new File(propertiesUtil.dataBaseFolder());
		AppInfoExtractor builder = new AppInfoExtractor();
				
		List<AppInfoDto> trainList = new LinkedList<>();
		List<AppInfoDto> testList = new LinkedList<>();
		
		if(dataBaseDir.canRead() && dataBaseDir.isDirectory()) {
			for (String bdName : dataBaseDir.list((dir, name) -> name.endsWith(".db")) ) {
				String bdPath = "jdbc:sqlite:" + dataBaseDir.getAbsolutePath() + "\\" + bdName;
				DaoManager dao = DaoManager.getInstance().openConnection(bdPath);
				try {
					StringBuilder sql = new StringBuilder();
					sql.append(" select process_name, state, tx_bytes, rx_bytes, date, screen_action_id, screen_actions.dt_start, screen_actions.dt_stop ");
					sql.append(" from app_infos ");
					sql.append(" inner join screen_actions on screen_action_id = screen_actions.id ");
					sql.append(" order by app_infos.id ");
					
					ResultSet resultSet = dao.query(sql.toString());
					String userName = bdName.substring(0, bdName.indexOf("."));
					
					List<AppInfoDto> result = builder.build(userName, resultSet);
					trainList.addAll(AppInfoSpliter.train(result));
					testList.addAll(AppInfoSpliter.test(result));
					
					Log.show("DB: " + bdPath);
				} catch (Exception e) {
					Log.show("Erro: " + e.toString());
				}
			}
		}
		
		FileUtil fileUtil = new FileUtil(propertiesUtil);		
		String resultCsv = fileUtil.createCSVData(trainList);
		String resultArff = fileUtil.createArffData(trainList, "train");
		FileManager.writeFiles(resultCsv, propertiesUtil.trainFolder(), "train.csv");
		FileManager.writeFiles(resultArff, propertiesUtil.trainFolder(), "train.arff");
		
		String testCsv = fileUtil.createCSVData(testList);
		String testArff = fileUtil.createArffData(testList, "test");
		FileManager.writeFiles(testCsv, propertiesUtil.testFolder(), "test.csv");
		FileManager.writeFiles(testArff, propertiesUtil.testFolder(), "test.arff");
		
		TestUserRandomSplit testUserRandomSplit = new TestUserRandomSplit(trainList, propertiesUtil);
		
		Map<String, List<AppInfoDto>> testResultByHour = testUserRandomSplit.split(testList);
		for (String nameFile : testResultByHour.keySet()) {
			String arrfDataTest = fileUtil.createArffData(testResultByHour.get(nameFile), trainList, nameFile);
			FileManager.writeFiles(arrfDataTest, propertiesUtil.testFolder(), nameFile +".arff");
		}
		
		Log.show("Arquivos Criados");
	}

}
