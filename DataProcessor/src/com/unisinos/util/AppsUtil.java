package com.unisinos.util;

import java.util.ArrayList;
import java.util.List;

public class AppsUtil {
	
	private static  Boolean filterApps;
	
	public static final List<String> acceptApps = new ArrayList<>();
	
//	public static final List<String> acceptApps = new ArrayList<>(Arrays.asList(
//		"com.whatsapp"
//		,"com.android.settings"
//		,"com.google.android.deskclock"
//		,"com.google.android.calendar"
//		,"com.android.mms"
//		,"com.adobe.reader"	 // 54
//		,"com.facebook.katana"
//		,"com.google.android.apps.docs"
//		,"com.google.android.dialer"
//		,"com.google.android.contacts" // 51
//		,"com.android.chrome"
//		,"com.google.android.apps.photos"
//		,"com.instagram.android"	//959
//		,"com.google.android.youtube"	//1013
//		,"com.motorola.camera"	//243
//		,"com.android.dialer"	//2105
//		,"com.google.android.music"	//422
//		,"com.tinder"	//254
//		,"br.com.bb.android"	//351
//		,"com.mercadolibre"	//1033
//		,"com.schibsted.bomnegocio.androidApp"	//535
//		,"com.waze"	//88
//		,"hands.android.webmotors"	//680
//		,"com.google.android.apps.maps"	//53
//	));
	
	public static boolean acceptApp(String processName) {
		if(filterApps) {
			return acceptApps.contains(processName);
		}
		return true;
	}
	
	public static void setFilterApps(Boolean filterApps) {
		AppsUtil.filterApps = filterApps;
	}

	public static void accepedtApps(String accpetedApps) {
		String[] apps = accpetedApps.split(",");
		for (String app : apps) {
			AppsUtil.acceptApps.add(app);
		}
	}

}
