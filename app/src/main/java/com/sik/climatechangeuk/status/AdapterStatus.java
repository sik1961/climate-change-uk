package com.sik.climatechangeuk.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class AdapterStatus {
    private static final int HOURS_IN_DAY = 24;
    private static final int MINUTES_IN_HOUR = 60;
    private LocalDateTime startDate;
    private long addressRequestCount;
    private long locationsFoundCount;

    public AdapterStatus() {
        startDate = LocalDateTime.now();
        addressRequestCount = 0;
        locationsFoundCount = 0;
    }

    public String getStartedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return startDate.format(formatter);
    }

    public String getUptime() {
        LocalDateTime now = LocalDateTime.now();
        long days = ChronoUnit.DAYS.between(startDate, now);
        long daysAsHours = days * HOURS_IN_DAY;
        long hours = ChronoUnit.HOURS.between(startDate, now);
        long hoursAsMinutes = hours * MINUTES_IN_HOUR;
        long minutes = ChronoUnit.MINUTES.between(startDate, now);

        return days + " days, " + (hours - daysAsHours) + " hours, " + (minutes - hoursAsMinutes) + " minutes";
    }

    public long getAddressRequestCount() {
        return addressRequestCount;
    }

    public void incrementAddressRequestCount() {
        addressRequestCount++;
    }

    public long getLocationsFoundCount() {
        return locationsFoundCount;
    }

    public void incrementLocationsFoundCount() {
        locationsFoundCount++;
    }
}
