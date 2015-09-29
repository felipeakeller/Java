package com.unisinos.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AppInfoBuilder {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public List<AppInfoDto> build(String userName, ResultSet rs) throws SQLException, ParseException {
		List<AppInfoDto> appInfoResult = new LinkedList<>();
		HashMap<String, AppInfoDto> apps = new HashMap<>();
		while ( rs.next() ) {
			AppInfoDto appInfoDTO = createAppInfo(rs);
			if(apps.containsKey(appInfoDTO.getProcessName())) {
				
				AppInfoDto appOpened = apps.get(appInfoDTO.getProcessName());
				appOpened.updateSecondsOpen(appInfoDTO);
				appOpened.setUserName(userName);
				appInfoResult.add(appOpened);
				apps.remove(appInfoDTO.getProcessName());
				
			} else if("FOREGROUND".equals(appInfoDTO.getState())){
				
				apps.put(appInfoDTO.getProcessName(), appInfoDTO);
				
			}
	      }
		
		return appInfoResult;
	}
	
	private AppInfoDto createAppInfo(ResultSet rs) throws SQLException {
		AppInfoDto appInfoDto = new AppInfoDto();
		appInfoDto.setProcessName(rs.getString("process_name"));
		appInfoDto.setRx(rs.getLong("rx_bytes"));
		appInfoDto.setTx(rs.getLong("tx_bytes"));
		appInfoDto.setIdScreen(rs.getInt("screen_action_id"));
		appInfoDto.setState(rs.getString("state"));
		try {
			appInfoDto.setDate(sdf.parse(rs.getString("date")));
						
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(appInfoDto.getDate());
			appInfoDto.setHour(calendar.get(Calendar.HOUR_OF_DAY));
			appInfoDto.setMinutes(calendar.get(Calendar.MINUTE));
			appInfoDto.setSeconds(calendar.get(Calendar.SECOND));
			appInfoDto.setDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appInfoDto;
	}

}
