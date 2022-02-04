package com.amdocs.backend;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class SLAAnalyzer 
{
    /**
    * Method will receive a particular problem opening date and amount of working hours (business hours) it should be solved and return the maximum date and time it should be solved.
    * It should be considered:
    * 	- Business hours are from 8AM to 5PM excluding weekends and holidays
    *  - Logic should consider only month of August 2019, Sao Carlos location
    *  - Method signature cannot be changed
    *  
    * @param iOpeningDateTime - Problem opening date
    * @param iSLA - Quantity of hours to solve the problem
    * @return Maximum date and time that problem should be solved
    */
    
    public static LocalDateTime calculateSLA(LocalDateTime iOpeningDateTime, Integer iSLA)
    {
    	
    	Set<LocalDate> holidays = new HashSet<>();
    	holidays.add(LocalDate.of(2019, 8, 15));
    	
    	// Opening date before 01/08/2019
    	if(iOpeningDateTime.isBefore(LocalDateTime.of(2019, 8, 1, 0, 0))) {
    		iOpeningDateTime = LocalDateTime.of(2019, 8, 1, 8, 0);
    	}
    	
    	// SLA greater than 1 or 2 business day of 9 hours
    	if (iSLA > 9) {
    		BigDecimal divisionSlaPerBusinessHours = new BigDecimal(iSLA/9);
    		iOpeningDateTime = iOpeningDateTime.plusDays(divisionSlaPerBusinessHours.intValue());
    		iSLA -= 9;
    	}

    	// Opening date out of business hours
    	if (iOpeningDateTime.getHour() < 8 && iOpeningDateTime.getHour() > 0) {
    		iOpeningDateTime = LocalDateTime.of(iOpeningDateTime.getYear(), iOpeningDateTime.getMonth(), iOpeningDateTime.getDayOfMonth(), 8, iOpeningDateTime.getSecond()); 
    	}
    	
    	LocalDateTime closingDate = iOpeningDateTime.plusHours(iSLA);
    	
    	// Skip SATURDAY    	
    	if(closingDate.getDayOfWeek().toString().equals("SATURDAY")) {
    		closingDate = closingDate.plusDays(2);
    	}
    	// Skip SUNDAY    	
    	if(closingDate.getDayOfWeek().toString().equals("SUNDAY")) {
    		closingDate = closingDate.plusDays(1);
    	}
    	
    	// Skip Holiday    	
    	if(holidays.contains(LocalDate.of(closingDate.getYear(), closingDate.getMonth(), closingDate.getDayOfMonth()))) {
    		closingDate = closingDate.plusDays(1);
    	}
    	
    	// Maximum closing date is 31/08/2019
    	if(closingDate.isAfter(LocalDateTime.of(2019, 8, 31, 17, 00, 00, 000))) {
    		closingDate = LocalDateTime.of(2019, 8, 31, 17, 00, 00, 000);
    	}
    	
        return closingDate;
    }
    
      
}
