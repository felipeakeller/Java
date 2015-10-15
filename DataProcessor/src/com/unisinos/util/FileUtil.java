package com.unisinos.util;

import java.util.List;

import com.unisinos.builder.AppInfoArffBuilder;
import com.unisinos.builder.AppInfoBuilder;
import com.unisinos.builder.AppInfoCsvBuilder;
import com.unisinos.domain.AppInfoDto;

public class FileUtil {

	public String createCSVData(List<AppInfoDto> result) {
		AppInfoBuilder csvBuilder = AppInfoCsvBuilder.create();
		addColumns(csvBuilder);						
		return buildData(result, csvBuilder);
	}
	
	public String createArffData(List<AppInfoDto> result, String name) {
		AppInfoBuilder csvBuilder = AppInfoArffBuilder.create(name);
		addColumns(csvBuilder);						
		return buildData(result, csvBuilder);
	}
	
	private void addColumns(AppInfoBuilder csvBuilder) {
		csvBuilder.addColumn(col -> col.withHeader("processName").nominal().withExtractor(app -> app.getProcessName()) )
//			.addColumn(col -> col.withHeader("dayOfWeek").numeric().withExtractor(app -> app.getDayOfWeekStr()) )
			.addColumn(col -> col.withHeader("hour").numeric().withExtractor(app -> app.getHourStr()) )
			.addColumn(col -> col.withHeader("openedTime").numeric().withExtractor(app -> app.getOpenedTimeStr()) )
			.addColumn(col -> col.withHeader("isWeekend").nominal().withExtractor(app -> app.getIsWeekendStr()) )
			.addColumn(col -> col.withHeader("idScreen").numeric().withExtractor(app -> app.getIdScreenStr()) )
			.addColumn(col -> col.withHeader("rx_KB").numeric().withExtractor(app -> String.valueOf(app.getRx())) )
			.addColumn(col -> col.withHeader("tx_KB").numeric().withExtractor(app -> String.valueOf(app.getTx())) )
			.addColumn(col -> col.withHeader("appsCount").numeric().withExtractor(app -> String.valueOf(app.getAppsCount())) )
			.addColumn(col -> col.withHeader("appsFlow").numeric().withExtractor(app -> String.valueOf(app.getAppsFlow())) )
			.addColumn(col -> col.withHeader("userName").nominal().withExtractor(app -> app.getUserName()) );
	}	
	
	private String buildData(List<AppInfoDto> result, AppInfoBuilder builder) {
		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder.append(builder.buildHeaders(result));
		
		result.forEach(app -> {
			resultBuilder.append(builder.buildRow(app));
		});
		
		return resultBuilder.toString();
	}

}
