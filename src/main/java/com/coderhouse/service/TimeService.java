package com.coderhouse.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.coderhouse.dto.TimeResponseDTO;

@Service
public class TimeService {
	
	@Autowired
	private RestTemplate rt;
	
	private static final String URL = "http://worldclockapi.com/api/json/utc/now";
	
	public TimeResponseDTO obtenerFechaYHoraActual() {
		
		try {
			TimeResponseDTO response = rt.getForObject(URL, TimeResponseDTO.class);
			
			if (response == null || response.getCurrentDateTime() == null) {
				 throw new IllegalStateException("La API no devolvió datos válidos.");
			}
			
			// Formato esperado: 2025-01-19T17:45:52Z
			String fechaHora = response.getCurrentDateTime();
			
			response.setYear(Integer.parseInt(fechaHora.substring(0, 4)));
			response.setMonth(Integer.parseInt(fechaHora.substring(5, 7)));
			response.setDay(Integer.parseInt(fechaHora.substring(8, 10)));
			response.setHour(Integer.parseInt(fechaHora.substring(11, 13)));
			response.setMinute(Integer.parseInt(fechaHora.substring(14, 16)));
			response.setSeconds(Integer.parseInt(fechaHora.substring(17, 19)));
			
			return response;
			
		} catch (Exception e) {
			System.err.println("Error conectando a WorldClockAPI: " + e.getMessage());
			
			// → fallback seguro:
	        TimeResponseDTO fallback = new TimeResponseDTO();
	        LocalDateTime now = LocalDateTime.now();

	        fallback.setYear(now.getYear());
	        fallback.setMonth(now.getMonthValue());
	        fallback.setDay(now.getDayOfMonth());
	        fallback.setHour(now.getHour());
	        fallback.setMinute(now.getMinute());
	        fallback.setSeconds(now.getSecond());
	        fallback.setCurrentDateTime(now.toString());

	        return fallback;
		}
	}

}
