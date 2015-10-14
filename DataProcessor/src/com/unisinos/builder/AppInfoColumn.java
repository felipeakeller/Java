package com.unisinos.builder;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.unisinos.domain.AppInfoDto;

public class AppInfoColumn {
	private static final int NOMINAL = 0;
	private static final int NUMERIC = 1;
	
	String header;
	int type;
	private Function<AppInfoDto, String> extractor;
	
	public AppInfoColumn withHeader(String header) {
		this.header = header;
		return this;
	}
	
	public AppInfoColumn withExtractor(Function<AppInfoDto, String> extractor) {
		this.extractor = extractor;
		return this;
	}
	
	public String rowData(AppInfoDto app) {
		return extractor.apply(app);
	}

	public AppInfoColumn nominal() {
		this.type = NOMINAL;
		return this;
	}
	
	public AppInfoColumn numeric() {
		this.type = NUMERIC;
		return this;
	}

	public String extractType(List<AppInfoDto> result) {
		if(type == NUMERIC) {
			return "numeric";
		} else {
			StringBuilder nominalHeader = new StringBuilder();
			Set<String> nominalSet = result.stream().map(extractor).distinct().collect(Collectors.toSet());
			nominalSet.stream().forEach(header -> {
				if(nominalHeader.length() > 0) {
					nominalHeader.append(",");
				}
				nominalHeader.append(header);
			});
			return "{" + nominalHeader + "}";
		}
	}

}
