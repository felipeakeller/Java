package com.unisinos.domain;

public class GroupAppsFlow {
	
	int count = 0;
	String appsFlow = "";
	
	public GroupAppsFlow(AppInfoDto app) {
		int lastIndex = app.getProcessName().lastIndexOf(".");
		appsFlow = app.getProcessName().substring(++lastIndex);
		count++;
	}
	public void add(String processName) {
		int lastIndex = processName.lastIndexOf(".");
		appsFlow = appsFlow.concat("-").concat(processName.substring(++lastIndex));
		count++;
	}

}
