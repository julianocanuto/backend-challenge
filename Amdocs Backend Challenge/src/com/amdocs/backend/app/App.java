package com.amdocs.backend.app;

import java.time.LocalDateTime;

import com.amdocs.backend.SLAAnalyzer;

public class App {

	public static void main(String[] args) {
		
		SLAAnalyzer slaAnalyzer = new SLAAnalyzer();
		
		int SLA = 15;
		LocalDateTime openingDate = LocalDateTime.of(2019, 8, 10, 17, 0, 58, 587);
		System.out.println("nanoseconds: " + openingDate.getNano());
		System.out.println(slaAnalyzer.calculateSLA(openingDate, 0));
		LocalDateTime closingDate = slaAnalyzer.calculateSLA(openingDate, SLA);
		System.out.println(closingDate);
		System.out.println("closing date day of week: " + closingDate.getDayOfWeek());

	}

}
