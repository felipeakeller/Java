package com.unisinos.builder;

import java.util.List;
import java.util.function.Consumer;

import com.unisinos.domain.AppInfoDto;

public interface AppInfoBuilder {
	
	public String buildHeaders(List<AppInfoDto> result);
	public String buildRow(AppInfoDto appInfoDto);
	public AppInfoBuilder addColumn(Consumer<AppInfoColumn> consumer);

}
