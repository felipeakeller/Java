package com.unisinos.domain;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class AppInfoDto {
	
	private String userName;

	private int seq;
	private String processName;
	private int dayOfWeek;
	
	private int hour;
	private int minutes; 
	private int seconds;
	private long openedTime;
	private int idScreen;
	private long tx;
	private long rx;
	private Date dtScreenStart;
	private Date dtScreenStop;
	
	private String state;
	private Date date;
	private LocalDateTime localDateTime;
	private GroupAppsFlow groupApps;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	public long getOpenedTime() {
		return openedTime;
	}
	public void setOpenedTime(long openedTime) {
		this.openedTime = openedTime;
	}
	public int getIdScreen() {
		return idScreen;
	}
	public void setIdScreen(int idScreen) {
		this.idScreen = idScreen;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getTx() {
		return tx;
	}
	public void setTxBytes(long tx) {
		this.tx = tx > 0 ? tx/1000 : tx;
	}
	public long getRx() {
		return rx;
	}
	public void setRxBytes(long rx) {
		this.rx = rx > 0 ? rx/1000 : rx;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getAppsCount() {
		return groupApps.count;
	}
	public int getAppsFlow() {
		return groupApps.appsFlow.length();
	}
	
	public boolean getIsWeekend() {
		return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
	}

	public void updateSecondsOpen(Date dateClosed) {
		long secondsOpen = (dateClosed.getTime() - getDate().getTime()) / 1000;
		setOpenedTime(secondsOpen);
	}

	public void setLocalDateTime(int year, int month, int dayOfMonth, int hour) {
		this.localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, 0);
	}
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	public void setGroupApps(GroupAppsFlow groupApps) {
		this.groupApps = groupApps;
	}
	public GroupAppsFlow getGroupApps() {
		return groupApps;
	}
	public Date getDtScreenStop() {
		return dtScreenStop;
	}
	public void setDtScreenStop(Date dtScreenStop) {
		this.dtScreenStop = dtScreenStop;
	}
	public Date getDtScreenStart() {
		return dtScreenStart;
	}
	public void setDtScreenStart(Date dtScreenStart) {
		this.dtScreenStart = dtScreenStart;
	}
	
	public String getDayOfWeekStr() {
		return String.valueOf(dayOfWeek);
	}
	public String getHourStr() {
		return String.valueOf(hour);
	}
	public String getOpenedTimeStr() {
		return String.valueOf(openedTime);
	}
	public String getIsWeekendStr() {
		return Boolean.valueOf(getIsWeekend()).toString();
	}
	public String getIdScreenStr() {
		return String.valueOf(idScreen);
	}
	public void sumTxRx(AppInfoDto appInfoDTO) {
		this.tx = getTx() + appInfoDTO.getTx();
		this.rx = getRx() + appInfoDTO.getRx();
	}
	
	public long timeScreenOn() {
		return (dtScreenStop.getTime() - dtScreenStart.getTime()) / 1000;
	}
	public long timeScreenHour() {
		return groupApps.timeScreenOn;
	}
	public String timeScreenHourStr() {
		return String.valueOf(timeScreenHour());
	}
	
}
