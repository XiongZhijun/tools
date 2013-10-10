/*
 * Copyright © 2013-2014 Xiong Zhijun, All Rights Reserved.
 */
package org.herod.alarmclock;

/**
 * 闹钟定义模型
 * 
 * @author Xiong Zhijun
 * @email hust.xzj@gmail.com
 */
public class AlarmClock {
	private long id;
	/** 响铃时间 */
	private String alarmTime;
	/** 重复周期，周一、周五等。 */
	private int repetitionPeriod;
	/** 标签 */
	private String tag;
	/** 铃声URL */
	private String ring;
	/** 是否振动 */
	private boolean vibration;
	/** 是否渐强 */
	private boolean volumeCrescendo;
	/** 重复间隔 */
	private int repeatInterval;
	/** 是否启用 */
	private boolean enabled;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public int getRepetitionPeriod() {
		return repetitionPeriod;
	}

	public void setRepetitionPeriod(int repetitionPeriod) {
		this.repetitionPeriod = repetitionPeriod;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getRing() {
		return ring;
	}

	public void setRing(String ring) {
		this.ring = ring;
	}

	public boolean isVibration() {
		return vibration;
	}

	public void setVibration(boolean vibration) {
		this.vibration = vibration;
	}

	public boolean isVolumeCrescendo() {
		return volumeCrescendo;
	}

	public void setVolumeCrescendo(boolean volumeCrescendo) {
		this.volumeCrescendo = volumeCrescendo;
	}

	public int getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
