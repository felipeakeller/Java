package com.unisinos.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppInfoSpliter {

	/**
	 * Remove o primeiro dia de cada usuario e adiciona os proximos 14 dias
	 */
	public static List<AppInfoDto> train(List<AppInfoDto> result) {
		LocalDateTime firstDateIn = firstDate(result.get(0).getLocalDateTime());
		LocalDateTime lastDateIn = lastDate(result.get(0).getLocalDateTime());
		
		return result.stream()
				.filter(app -> app.getLocalDateTime().isAfter(firstDateIn) && app.getLocalDateTime().isBefore(lastDateIn))
				.collect(Collectors.toList());
	}
	
	/**
	 * Todos os registros que possuem data posterior a ultima data de treinamento
	 */
	public static List<AppInfoDto> test(List<AppInfoDto> result) {
		LocalDateTime lastDateIn = lastDate(result.get(0).getLocalDateTime());
		
		return result.stream()
				.filter(app -> app.getLocalDateTime().isAfter(lastDateIn))
				.collect(Collectors.toList());
	}
	
	private static LocalDateTime firstDate(LocalDateTime date) {
		return LocalDateTime.of(date.toLocalDate().plusDays(1), LocalTime.of(0, 0, 0));
	}
	private static LocalDateTime lastDate(LocalDateTime date) {
		return LocalDateTime.of(date.toLocalDate().plusDays(15), LocalTime.of(0, 0, 0));
	}

}