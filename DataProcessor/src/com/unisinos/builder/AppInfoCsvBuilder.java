package com.unisinos.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.unisinos.domain.AppInfoDto;

public class AppInfoCsvBuilder implements AppInfoBuilder {

	private List<AppInfoColumn> appInfoColumns = new ArrayList<>();
	
	@Override
	public String buildHeaders(List<AppInfoDto> result) {
		StringBuilder builder = new StringBuilder();
		appInfoColumns.forEach(app -> {
			if(builder.length() > 0) {
				builder.append(";");
			}
			builder.append(app.header);
		});
		builder.append("\r\n");
		return builder.toString();
	}

	@Override
	public String buildRow(AppInfoDto appInfoDto) {
		StringBuilder builder = new StringBuilder();
		appInfoColumns.forEach(app -> {
			if(builder.length() > 0) {
				builder.append(";");
			}
			builder.append(app.rowData(appInfoDto));
		});
		builder.append("\r\n");
		return builder.toString();
	}

	public static AppInfoCsvBuilder create() {
		return new AppInfoCsvBuilder();
	}

	public AppInfoBuilder addColumn(Consumer<AppInfoColumn> consumer) {
		AppInfoColumn appInfoColumn = new AppInfoColumn();
		consumer.accept(appInfoColumn);
		appInfoColumns.add(appInfoColumn);
		return this;
	}

}
