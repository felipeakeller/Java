package com.unisinos.util;

import java.util.ArrayList;
import java.util.List;

import com.unisinos.builder.AppInfoArffBuilder;
import com.unisinos.builder.AppInfoBuilder;
import com.unisinos.builder.AppInfoCsvBuilder;
import com.unisinos.domain.AppInfoDto;

public class FileUtil {

	private List<AppInfoDto> originalList;
	private PropertiesUtil propertiesUtil;

	public FileUtil(PropertiesUtil propertiesUtil) {
		this.propertiesUtil = propertiesUtil;
	}

	public String createCSVData(List<AppInfoDto> result) {
		AppInfoBuilder csvBuilder = AppInfoCsvBuilder.create();
		addColumns(csvBuilder);						
		return buildData(result, csvBuilder);
	}
	
	public String createArffData(List<AppInfoDto> result, String name) {
		return createArffData(result, result, name);
	}
	
	public String createArffData(List<AppInfoDto> result, List<AppInfoDto> originalList, String name) {
		this.originalList = originalList == null ? new ArrayList<>() : originalList;
		AppInfoBuilder csvBuilder = AppInfoArffBuilder.create(name);
		addColumns(csvBuilder);						
		return buildData(result, csvBuilder);
	}
	
	private void addColumns(AppInfoBuilder csvBuilder) {
		csvBuilder.addColumn(col -> col.withHeader("processName").nominal().withExtractor(app -> app.getProcessName()) );
		if(propertiesUtil.dayOfWeek()) {
			csvBuilder.addColumn(col -> col.withHeader("dayOfWeek").numeric().withExtractor(app -> app.getDayOfWeekStr()) );			
		}
		if(propertiesUtil.hour()) {
			csvBuilder.addColumn(col -> col.withHeader("hour").numeric().withExtractor(app -> app.getHourStr()) );
		}
		if(propertiesUtil.openedTime()) {
			csvBuilder.addColumn(col -> col.withHeader("openedTime").numeric().withExtractor(app -> app.getOpenedTimeStr()) );
		}
		if(propertiesUtil.timeScreenHour()) {
			csvBuilder.addColumn(col -> col.withHeader("timeScreenHour").numeric().withExtractor(app -> app.timeScreenHourStr()) );
		}
		if(propertiesUtil.isWeekend()) {
			csvBuilder.addColumn(col -> col.withHeader("isWeekend").nominal().withExtractor(app -> app.getIsWeekendStr()) );
		}
		if(propertiesUtil.idScreen()) {
			csvBuilder.addColumn(col -> col.withHeader("idScreen").numeric().withExtractor(app -> app.getIdScreenStr()) );
		}
		if(propertiesUtil.rxKB()) {
			csvBuilder.addColumn(col -> col.withHeader("rx_KB").numeric().withExtractor(app -> String.valueOf(app.getRx())) );
		}
		if(propertiesUtil.txKB()) {
			csvBuilder.addColumn(col -> col.withHeader("tx_KB").numeric().withExtractor(app -> String.valueOf(app.getTx())) );
		}
		if(propertiesUtil.appsCount()) {
			csvBuilder.addColumn(col -> col.withHeader("appsCount").numeric().withExtractor(app -> String.valueOf(app.getAppsCount())) );
		}
		if(propertiesUtil.appsFlow()) {
			csvBuilder.addColumn(col -> col.withHeader("appsFlow").numeric().withExtractor(app -> String.valueOf(app.getAppsFlow())) );
		}
		csvBuilder.addColumn(col -> col.withHeader("userName").nominal().withExtractor(app -> app.getUserName()) );
	}	
	
	private String buildData(List<AppInfoDto> result, AppInfoBuilder builder) {
		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder.append(builder.buildHeaders(originalList));
		
		result.forEach(app -> {
			resultBuilder.append(builder.buildRow(app));
		});
		
		return resultBuilder.toString();
	}

}
