package com.unisinos.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.unisinos.domain.AppInfoDto;

public class AppInfoArffBuilder implements AppInfoBuilder {

	private String name;
	private List<AppInfoColumn> appInfoColumns = new ArrayList<>();
	
	@Override
	public String buildHeaders(List<AppInfoDto> result) { 
		StringBuilder builder = new StringBuilder();
		builder.append("@relation ").append(name).append("\r\n\r\n");
		appInfoColumns.forEach(app -> {
			builder.append("@attribute ").append(app.header).append(" ");
			builder.append(app.extractType(result));
			builder.append("\r\n");
		});
		builder.append("\r\n");
		builder.append("@data").append("\r\n");
		return builder.toString();
	}	

	@Override
	public String buildRow(AppInfoDto appInfoDto) {
		StringBuilder builder = new StringBuilder();		
		appInfoColumns.forEach(app -> {
			if(builder.length() > 0) {
				builder.append(",");
			}
			builder.append(app.rowData(appInfoDto));
		});
		builder.append("\r\n");
		return builder.toString();
	}

	@Override
	public AppInfoBuilder addColumn(Consumer<AppInfoColumn> consumer) {
		AppInfoColumn appInfoColumn = new AppInfoColumn();
		consumer.accept(appInfoColumn);
		appInfoColumns.add(appInfoColumn);
		return this;
	}

	public static AppInfoBuilder create(String name) {
		AppInfoArffBuilder appInfoArrfBuilder = new AppInfoArffBuilder();
		appInfoArrfBuilder.name = name;
		return appInfoArrfBuilder;
	}

}
