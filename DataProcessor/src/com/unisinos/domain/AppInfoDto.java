package com.unisinos.domain;

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
	
	private String state;
	private Date date;

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
	public void setTx(long tx) {
		this.tx = tx;
	}
	public long getRx() {
		return rx;
	}
	public void setRx(long rx) {
		this.rx = rx;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public void updateSecondsOpen(AppInfoDto appInfoCloseDTO) {
		Date dateClosed = appInfoCloseDTO.getDate();
		long secondsOpen = (dateClosed.getTime() - getDate().getTime()) / 1000;
		setOpenedTime(secondsOpen);
	}
	
}
