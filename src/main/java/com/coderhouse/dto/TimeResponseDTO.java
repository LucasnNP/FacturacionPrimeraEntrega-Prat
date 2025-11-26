package com.coderhouse.dto;

import lombok.Data;

@Data
public class TimeResponseDTO {
	
	private String currentDateTime;
	private String dayOfWeek;
	private boolean isDayLightSavingsTime;
	private String timeZoneName;
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int seconds;

}
