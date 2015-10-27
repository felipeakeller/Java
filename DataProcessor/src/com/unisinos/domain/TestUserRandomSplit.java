package com.unisinos.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.unisinos.util.PropertiesUtil;

public class TestUserRandomSplit {

	int minimumTestSize;
	
	public TestUserRandomSplit(PropertiesUtil propertiesUtil) {
		this.minimumTestSize = propertiesUtil.minimumTestSize();
	}

	public Map<String, List<AppInfoDto>> split(List<AppInfoDto> testList) {
		Map<String, List<AppInfoDto>> result = new HashMap<>();
		
		List<String> usuarios = testList.stream()
									.map(app -> app.getUserName())
									.distinct()
									.collect(Collectors.toList());
		
		usuarios.forEach(user -> {
			for (int i = 0; i < 7; i++) {
				List<AppInfoDto> appsInfo = testList.stream().filter(test -> test.getUserName().equals(user)).collect(Collectors.toList());
				LocalDateTime date = appsInfo.get(0).getLocalDateTime().plusDays(i);
				Map<Integer, List<AppInfoDto>> hours = appsInfo.stream().filter(app -> app.getLocalDateTime().getDayOfMonth() == date.getDayOfMonth())
															   			.collect(Collectors.groupingBy(AppInfoDto::getHour));
				
				Map<Integer, List<AppInfoDto>> resultHours = new HashMap<>();
				for (Integer hour : hours.keySet()) {
					List<AppInfoDto> list = hours.get(hour);
					if(list.size() > minimumTestSize) {
						resultHours.put(hour, list);
					}
				}
				if(!resultHours.isEmpty()) {
					Integer anyHour = StreamSupport.stream(resultHours.keySet().spliterator(), false).findAny().get();
					result.put(user + "_" + (i + 1), resultHours.get(anyHour));
				}
			}
		});
		
		return result;
	}

}
