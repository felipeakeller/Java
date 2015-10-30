package com.unisinos.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.unisinos.Log;

public class PropertiesUtil {

	private Properties properties = new Properties();
	
	private Boolean dayOfWeek;
	private Boolean hour;
	private Boolean openedTime;
	private Boolean isWeekend;
	private Boolean idScreen;
	private Boolean rxKB;
	private Boolean txKB;
	private Boolean appsCount;
	private Boolean appsFlow;
	private Boolean timeScreenHour;
	
	private int minimumTestSize;
	private double percentRelation;
	
	public PropertiesUtil() {
		try {
			String path = System.getProperty("user.dir") + "\\config.properties";
			Log.show(path);
			InputStream input = new FileInputStream(path);
			properties.load(input);

			dayOfWeek = properties.getProperty("dayOfWeek").equals("true");
			hour = properties.getProperty("hour").equals("true");
			openedTime = properties.getProperty("openedTime").equals("true");
			isWeekend = properties.getProperty("isWeekend").equals("true");
			idScreen = properties.getProperty("idScreen").equals("true");
			rxKB = properties.getProperty("rxKB").equals("true");
			txKB = properties.getProperty("txKB").equals("true");
			appsCount = properties.getProperty("appsCount").equals("true");
			appsFlow = properties.getProperty("appsFlow").equals("true");
			timeScreenHour = properties.getProperty("timeScreenHour").equals("true");
			
			minimumTestSize = Integer.valueOf(properties.getProperty("minimumTestSize"));
			percentRelation = Double.valueOf(properties.getProperty("percentRelation"));
			
			AppsUtil.setFilterApps(properties.getProperty("filterApps").equals("true"));
			AppsUtil.accepedtApps(properties.getProperty("accpetedApps"));
			
			Log.show("Arquivo Configurações Carregado");
		} catch (Exception e) {
			Log.show("Erro:" + e.toString());
		}
	}

	public String dataBaseFolder() {
		return properties.getProperty("dataBaseFolder");
	}

	public String trainFolder() {
		return properties.getProperty("trainFolder");
	}

	public String testFolder() {
		return properties.getProperty("testFolder");
	}

	public Boolean dayOfWeek() {
		return dayOfWeek;
	}
	public Boolean hour() {
		return hour;
	}
	public Boolean openedTime() {
		return openedTime;
	}
	public Boolean isWeekend() {
		return isWeekend;
	}
	public Boolean idScreen() {
		return idScreen;
	}
	public Boolean rxKB() {
		return rxKB;
	}
	public Boolean txKB() {
		return txKB;
	}
	public Boolean appsCount() {
		return appsCount;
	}
	public Boolean appsFlow() {
		return appsFlow;
	}
	public boolean timeScreenHour() {
		return timeScreenHour;
	}
	public int minimumTestSize() {
		return minimumTestSize;
	}
	public double percentRelation() {
		return percentRelation;
	}
	
}
