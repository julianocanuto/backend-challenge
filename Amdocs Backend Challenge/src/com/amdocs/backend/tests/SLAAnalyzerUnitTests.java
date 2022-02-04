package com.amdocs.backend.tests;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.amdocs.backend.SLAAnalyzer;


public class SLAAnalyzerUnitTests {

	
	@Test
	@DisplayName(value = "Problem opening date as August 1st at 2AM with SLA = 8 should return closing date equal to August 1st, 4PM or 16h")
	public void baseCase() {

		int SLA = 8;
		LocalDateTime openingDate = LocalDateTime.of(2019, 8, 1, 2, 0);
		LocalDateTime expectedDateTime = LocalDateTime.of(2019, 8, 1, 16, 0);
		
		LocalDateTime closingDate = SLAAnalyzer.calculateSLA(openingDate, SLA);
		
		Assertions.assertEquals(expectedDateTime.getYear(), closingDate.getYear());
		Assertions.assertEquals(expectedDateTime.getMonth(), closingDate.getMonth());
		Assertions.assertEquals(expectedDateTime.getDayOfMonth(), closingDate.getDayOfMonth());
		Assertions.assertEquals(expectedDateTime.getHour(), closingDate.getHour());
		Assertions.assertEquals(expectedDateTime.getMinute(), closingDate.getMinute());
		Assertions.assertEquals(expectedDateTime.getSecond(), closingDate.getSecond());
		Assertions.assertEquals(expectedDateTime.getNano(), closingDate.getNano());

	}
	
	@Test
	@DisplayName(value = "Closing date should not be on SATURDAY")
	public void outOfSaturday() {
		
		int SLA = 15;
		LocalDateTime openingDate = LocalDateTime.of(2019, 8, 10, 4, 0);
		
		LocalDateTime closingDate = SLAAnalyzer.calculateSLA(openingDate, SLA);
		
		Assertions.assertNotEquals("SATURDAY", closingDate.getDayOfWeek().toString());

	}
	
	@Test
	@DisplayName(value = "Closing date should not be on SUNDAY")
	public void outOfSunday() {
		
		int SLA = 2;
		LocalDateTime openingDate = LocalDateTime.of(2019, 8, 18, 4, 0);
		
		LocalDateTime closingDate = SLAAnalyzer.calculateSLA(openingDate, SLA);
		
		Assertions.assertNotEquals("SUNDAY", closingDate.getDayOfWeek().toString());

	}
	
	@Test
	@DisplayName(value = "Closing date should not be on Holiday")
	public void outOfHoliday() {
		
		Set<LocalDate> holidays = new HashSet<>();
		// Define 2019-8-15 as holiday
		holidays.add(LocalDate.of(2019, 8, 15));		
		LocalDateTime openingDate = LocalDateTime.of(2019, 8, 15, 4, 0);
		int SLA = 2;
		
		LocalDateTime closingDate = SLAAnalyzer.calculateSLA(openingDate, SLA);
		
		Assertions.assertEquals(16, closingDate.getDayOfMonth());

	}
	
	@Test
	@DisplayName(value = "Closing date should begin at 8AM of next business day if opening date and time is out of business hours")
	public void beginAt8AM() {
		
		int SLA = 2;
		LocalDateTime openingDate = LocalDateTime.of(2019, 8, 6, 4, 0);
		LocalDateTime closingDate; 
		
		closingDate = SLAAnalyzer.calculateSLA(openingDate, SLA);
		
		Assertions.assertEquals(LocalDateTime.of(2019, 8, 6, 8, 0), closingDate.minusHours(SLA));

	}
	
	@Test
	@DisplayName(value = "Opening date should be equal to or greater than 01/08/2019")
	public void openingBiggerOrEqualThan1stAugust2019() {
		
		int SLA = 2;
		LocalDateTime openingDate = LocalDateTime.of(2019, 7, 31, 2, 0);
		LocalDateTime closingDate; 
		
		closingDate = SLAAnalyzer.calculateSLA(openingDate, SLA);
		
		Assertions.assertEquals(LocalDateTime.of(2019, 8, 1, 10, 0), closingDate);
		
	}
	
	@Test
	@DisplayName(value = "Closing date should be lower than or equal to 31/08/2019")
	public void ClosingLowerOrEqualThan31August2019() {
		
		int SLA = 30;
		LocalDateTime openingDate = LocalDateTime.of(2019, 8, 31, 2, 0);
		LocalDateTime closingDate; 
		
		closingDate = SLAAnalyzer.calculateSLA(openingDate, SLA);
		
		Assertions.assertEquals(LocalDateTime.of(2019, 8, 31, 17, 00, 00, 000), closingDate);
		
	}	
	
	@Test
	@DisplayName(value = "SLA greater than one business day should ends in the next business day")
	public void slaGreaterThanOneBusinessDay() {
		
		int SLA = 10;
		LocalDateTime openingDate = LocalDateTime.of(2019, 8, 6, 2, 0);
		LocalDateTime closingDate; 
		
		closingDate = SLAAnalyzer.calculateSLA(openingDate, SLA);
		
		Assertions.assertEquals(LocalDateTime.of(2019, 8, 7, 9, 00), closingDate);
		
	}
	
	@Test
	@DisplayName(value = "SLA greater than two business days should add business days forward")
	public void slaGreaterThanTwoBusinessDay() {
		
		int SLA = 10;
		LocalDateTime openingDate = LocalDateTime.of(2019, 8, 6, 2, 0);
		LocalDateTime closingDate; 
		
		closingDate = SLAAnalyzer.calculateSLA(openingDate, SLA);
		
		Assertions.assertEquals(LocalDateTime.of(2019, 8, 7, 9, 00), closingDate);
		
	}
}
