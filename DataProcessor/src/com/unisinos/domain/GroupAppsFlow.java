package com.unisinos.domain;

public class GroupAppsFlow {
	
	int count = 0;
	String appsFlow = "";
	
	int lastIdScreen = 0;
	long timeScreenOn = 0;
	
	public GroupAppsFlow(AppInfoDto app) {
		int lastIndex = app.getProcessName().lastIndexOf(".");
		appsFlow = app.getProcessName().substring(++lastIndex);
		count++;
		
		timeScreenOn += app.timeScreenOn();
		lastIdScreen = app.getIdScreen();
	}
	
	public void add(AppInfoDto app) {
		String processName = app.getProcessName();
		int lastIndex = processName.lastIndexOf(".");
		appsFlow = appsFlow.concat("-").concat(processName.substring(++lastIndex));
		count++;
		
		if(lastIdScreen != app.getIdScreen()) {
			timeScreenOn += app.timeScreenOn();
			lastIdScreen = app.getIdScreen();
		}
		
	}

}
