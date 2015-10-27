package com.unisinos.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.unisinos.Log;
import com.unisinos.util.AppsUtil;

public class AppInfoExtractor {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public List<AppInfoDto> build(String userName, ResultSet rs) throws SQLException, ParseException {
		List<AppInfoDto> appInfoResult = new LinkedList<>();
		HashMap<String, AppInfoDto> apps = new HashMap<>();
		while ( rs.next() ) {
			
			AppInfoDto appInfoDTO = createAppInfo(rs);
			
			if(!apps.isEmpty() && screenActionChange(appInfoDTO, apps)) {
				
				apps.values().stream().forEach(appOpened -> {
					appOpened.updateSecondsOpen(appOpened.getDtScreenStop());
					addToList(appOpened, userName, app -> appInfoResult.add(app));					
				});
				apps.clear();
				
			}
			
			if(AppsUtil.acceptApp(appInfoDTO.getProcessName())) {
				
				if(apps.containsKey(appInfoDTO.getProcessName()) && "BACKGROUND".equals(appInfoDTO.getState())) {
					
					AppInfoDto appOpened = apps.get(appInfoDTO.getProcessName());
					appOpened.sumTxRx(appInfoDTO);
					appOpened.updateSecondsOpen(appInfoDTO.getDate());
					addToList(appOpened, userName, app -> appInfoResult.add(app));
					apps.remove(appInfoDTO.getProcessName());
					
				} else if("FOREGROUND".equals(appInfoDTO.getState())){
					
					apps.put(appInfoDTO.getProcessName(), appInfoDTO);
					
				}
				
			}

		}

		updateTotalColumns(appInfoResult);
		
		return appInfoResult;
	}
	
	private void addToList(AppInfoDto appOpened, String userName, Consumer<AppInfoDto> addToListConsumer) {
		appOpened.setUserName(userName);
		if(appOpened.getOpenedTime() >= 0 && appOpened.getOpenedTime() < 3600) {
			addToListConsumer.accept(appOpened);
		}
	}
	
	private boolean screenActionChange(AppInfoDto appInfoDTO, HashMap<String, AppInfoDto> apps) {
		if(apps.isEmpty()) return false;
		return appInfoDTO.getIdScreen() != apps.values().stream().findAny().get().getIdScreen();
	}

	private void updateTotalColumns(List<AppInfoDto> appInfoResult) {
		Map<LocalDateTime, GroupAppsFlow> groupsApps = new HashMap<>();
		appInfoResult.forEach(app -> {
			GroupAppsFlow groupApp = groupsApps.get(app.getLocalDateTime());
			if(groupApp != null) {
				groupApp.add(app);
				app.setGroupApps(groupApp);
			} else {
				groupApp = new GroupAppsFlow(app);
				app.setGroupApps(groupApp);
				groupsApps.put(app.getLocalDateTime(), groupApp);
			}
		});
	}

	private AppInfoDto createAppInfo(ResultSet rs) throws SQLException {
		AppInfoDto appInfoDto = new AppInfoDto();
		appInfoDto.setProcessName(rs.getString("process_name"));
		appInfoDto.setRxBytes(rs.getLong("rx_bytes"));
		appInfoDto.setTxBytes(rs.getLong("tx_bytes"));
		appInfoDto.setIdScreen(rs.getInt("screen_action_id"));
		appInfoDto.setState(rs.getString("state"));
		try {
			appInfoDto.setDate(sdf.parse(rs.getString("date")));
			
			String dtStopStr = rs.getString("dt_stop");
			if(dtStopStr == null) {
				appInfoDto.setDtScreenStop(appInfoDto.getDate());
			} else {
				appInfoDto.setDtScreenStop(sdf.parse(dtStopStr));				
			}
			appInfoDto.setDtScreenStart(sdf.parse(rs.getString("dt_start")));
						
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(appInfoDto.getDate());
			appInfoDto.setHour(calendar.get(Calendar.HOUR_OF_DAY));
			appInfoDto.setMinutes(calendar.get(Calendar.MINUTE));
			appInfoDto.setSeconds(calendar.get(Calendar.SECOND));
			appInfoDto.setDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
			appInfoDto.setLocalDateTime(calendar.get(Calendar.YEAR)
									, calendar.get(Calendar.MONTH) + 1
									, calendar.get(Calendar.DAY_OF_MONTH)
									, calendar.get(Calendar.HOUR_OF_DAY));
			
		} catch (Exception e) {
			Log.show("Erro: " + e.getMessage());
		}
		return appInfoDto;
	}
	
}
