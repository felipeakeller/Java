package com.unisinos.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.unisinos.util.PropertiesUtil;

public class TestUserRandomSplit {

	private List<AppInfoDto> trainList;
	int minimumTestSize;
	double percentRelation;
	int instancesOfTest;
	
	private Random randomGenerator = new Random();
	
	public TestUserRandomSplit(List<AppInfoDto> trainList, PropertiesUtil propertiesUtil) {
		this.trainList = trainList;
		this.minimumTestSize = propertiesUtil.minimumTestSize();
		this.percentRelation = propertiesUtil.percentRelation();
		this.instancesOfTest = propertiesUtil.instancesOfTest();
	}

	public Map<String, List<AppInfoDto>> split(List<AppInfoDto> testList) {
		Map<String, List<AppInfoDto>> result = new HashMap<>();
		
		List<String> usuarios = testList.stream()
									.map(app -> app.getUserName())
									.distinct()
									.collect(Collectors.toList());
		
		usuarios.forEach(user -> {
			
			List<List<AppInfoDto>> resultHoursFiltered = new ArrayList<>();
			
			for (int i = 0; i < 7; i++) {
				List<AppInfoDto> appsInfo = testList.stream().filter(test -> test.getUserName().equals(user)).collect(Collectors.toList());
				LocalDateTime date = appsInfo.get(0).getLocalDateTime().plusDays(i);
				Map<Integer, List<AppInfoDto>> hours = appsInfo.stream().filter(app -> app.getLocalDateTime().getDayOfMonth() == date.getDayOfMonth())
															   			.collect(Collectors.groupingBy(AppInfoDto::getHour));
				
				Map<Integer, List<AppInfoDto>> resultHours = new HashMap<>();
				for (Integer hour : hours.keySet()) {
					List<AppInfoDto> list = hours.get(hour);
					if(minimumTestSize == 0 || list.size() > minimumTestSize) {
						resultHours.put(hour, list);
					}
				}
				resultHoursFiltered.addAll(relationFilter(user, resultHours).values()); 
			}
			
			result.putAll(getInstancesOfWeek(resultHoursFiltered, user));
			result.putAll(getInstancesOfWeekand(resultHoursFiltered, user));
		});
		
		return result;
	}

	private Map<String, List<AppInfoDto>> getInstancesOfWeekand(List<List<AppInfoDto>> resultHoursFiltered, String user) {
		List<List<AppInfoDto>> resultWeekand = resultHoursFiltered.stream()
																.filter(list -> list.stream()
																.findAny().get().getIsWeekend())
																.collect(Collectors.toList());
		return selectRandomItens(resultWeekand, user, "weekand");
	}
	
	private Map<String, List<AppInfoDto>> getInstancesOfWeek(List<List<AppInfoDto>> resultHoursFiltered, String user) {
		List<List<AppInfoDto>> resultWeek = resultHoursFiltered.stream()
																.filter(list -> list.stream()
																.findAny().get().getIsWeekend() == false)
																.collect(Collectors.toList());
		return selectRandomItens(resultWeek, user, "week");
	}
	
	private Map<String, List<AppInfoDto>> selectRandomItens(List<List<AppInfoDto>> resultHoursFiltered, String prefix, String sufix) {
		Map<String, List<AppInfoDto>> result = new HashMap<>();
		List<Integer> indexesInUse = new ArrayList<>();
		for (int i = 0; i < instancesOfTest && i < resultHoursFiltered.size(); i++) {
			int randomIndex = findRandomIndex(resultHoursFiltered, indexesInUse);
			indexesInUse.add(randomIndex);
			result.put(prefix + "_" + sufix + "_" + (i+1), resultHoursFiltered.get(randomIndex));
		}
		return result;
	}

	private int findRandomIndex(List<List<AppInfoDto>> resultHoursFiltered, List<Integer> indexesInUse) {
		int randomIndex = randomGenerator.nextInt(resultHoursFiltered.size());
		if(indexesInUse.contains(randomIndex)) {
			return findRandomIndex(resultHoursFiltered, indexesInUse);
		}
		return randomIndex;
	}
	
	private Map<Integer, List<AppInfoDto>> relationFilter(String user, Map<Integer, List<AppInfoDto>> resultHours) {
		if(percentRelation == 0) {
			return resultHours;
		}
		Map<Integer, List<AppInfoDto>> resultFiltered = new HashMap<>();
		List<AppInfoDto> trainListByUser = trainList.stream().filter(t -> t.getUserName().equals(user))
															 .collect(Collectors.toList());
		resultHours.forEach((hour, list) -> {
			long totalAppsHour = trainListByUser.stream()
												.filter(t -> t.getHour() == hour)
												.count();
			
			long mediaAppsHour = totalAppsHour / 14;
			double valorRelacao = ((mediaAppsHour * percentRelation) / 100);
			double valorMaximo = mediaAppsHour + valorRelacao;
			double valorMinimo = mediaAppsHour - valorRelacao;
			
			if(list.size() >= valorMinimo && list.size() <= valorMaximo) {
				resultFiltered.put(hour, list);
			}
		});
		return resultFiltered;
	}

}
