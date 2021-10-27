package com.sik.climatechangeuk.service;

import com.sik.climatechangeuk.model.MonthlyWeatherData;
import com.sik.climatechangeuk.model.WeatherExtremesData;
import com.sik.climatechangeuk.model.YearlyAverageWeatherData;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static com.sik.climatechangeuk.model.MonthlyWeatherData.I_MNTH;
import static com.sik.climatechangeuk.model.MonthlyWeatherData.I_YEAR;

@Component
public class MetoDataUtilities {

    //private static final Logger LOG = LoggerFactory.getLogger(MetoDataUtilities.class);
    private static final String EQ = "=";
    private static final String AX = "\\*";
    private static final String HASH = "#";
    private static final String DOLLAR = "$";
    private static final String MT = "";
    private String LOC_TIME_FORMAT = "%s %s";
    private static final DateTimeFormatter YYYY_MM = DateTimeFormatter.ofPattern("yyyy/MM");

    public Set<MonthlyWeatherData> filterByDates(final Set<MonthlyWeatherData> monthlyWeatherData, final LocalDate start, final LocalDate end) {
        return monthlyWeatherData.stream()
                .filter(w -> (w.getMonthStartDate().isEqual(start) || w.getMonthStartDate().isAfter(start)) &&
                        w.getMonthStartDate().isBefore(end))
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<MonthlyWeatherData> filterByStation(final Set<MonthlyWeatherData> monthlyWeatherData, final String stationName) {
        return monthlyWeatherData.stream()
                .filter(w -> (w.getStationName().equals(stationName)))
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<MonthlyWeatherData> filterByYear(final Set<MonthlyWeatherData> monthlyWeatherData, final int year) {
        return monthlyWeatherData.stream()
                .filter(w -> (w.getMonthStartDate().getYear()==year))
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }



    public Map<String, WeatherExtremesData> buildExtremesMap(Set<MonthlyWeatherData> weatherData) {
        Map<String, WeatherExtremesData> extremes = new TreeMap<>();
        extremes.put("*All*", this.getExtremes(weatherData));

        Map<String,Set<MonthlyWeatherData>> dataByLocation = weatherData.stream()
                .sorted()
                .collect(Collectors.groupingBy(MonthlyWeatherData::getStationName,Collectors.toSet()));
        for (String location: dataByLocation.keySet()) {
            extremes.put(location, this.getExtremes(dataByLocation.get(location)));
        }
        return extremes;
    }

    private WeatherExtremesData getExtremes(Set<MonthlyWeatherData> weatherData) {
        WeatherExtremesData extremes = new WeatherExtremesData();
        for (MonthlyWeatherData month: weatherData) {
            extremes = this.updateExtremes(extremes, month);
        }
        return extremes;
    }

    private WeatherExtremesData updateExtremes(WeatherExtremesData extremes, MonthlyWeatherData monthData) {

        if (monthData.getTempMinC() != null && monthData.getTempMinC() < extremes.getMinTemp()) {
            extremes.setMinTemp(monthData.getTempMinC());
            extremes.setMinTempLocTime(String.format(LOC_TIME_FORMAT,
                    monthData.getStationName(),
                    monthData.getMonthStartDate().format(YYYY_MM)));
        }
        if (monthData.getTempMaxC() != null &&monthData.getTempMaxC() > extremes.getMaxTemp()) {
            extremes.setMaxTemp(monthData.getTempMaxC());
            extremes.setMaxTempLocTime(String.format(LOC_TIME_FORMAT,
                    monthData.getStationName(),
                    monthData.getMonthStartDate().format(YYYY_MM)));
        }
        if (monthData.getAfDays() != null && monthData.getAfDays() > extremes.getMaxAfDays()) {
            extremes.setMaxAfDays(monthData.getAfDays());
            extremes.setMaxAfDaysLocTime(String.format(LOC_TIME_FORMAT,
                    monthData.getStationName(),
                    monthData.getMonthStartDate().format(YYYY_MM)));
        }
        if (monthData.getRainfallMm() != null && monthData.getRainfallMm() > extremes.getMaxRainfallMm()) {
            extremes.setMaxRainfallMm(monthData.getRainfallMm());
            extremes.setMaxRainfallMmLocTime(String.format(LOC_TIME_FORMAT,
                    monthData.getStationName(),
                    monthData.getMonthStartDate().format(YYYY_MM)));
        }
        if (monthData.getSunHours() != null && monthData.getSunHours() > extremes.getMaxSunHours()) {
            extremes.setMaxSunHours(monthData.getSunHours());
            extremes.setMaxSunHoursLocTime(String.format(LOC_TIME_FORMAT,
                    monthData.getStationName(),
                    monthData.getMonthStartDate().format(YYYY_MM)));
        }
        return extremes;
    }

    public Float intToFloat(Integer i) {
        return Float.parseFloat(i.toString());
    }

    public LocalDate getMonthStartDate(final int year, final int month) {
        return LocalDate.of(year, month, 1);
    }

    public LocalDate getYearStartDate(final int year) {
        return LocalDate.of(year, 1, 1);
    }

    public boolean isValidUrlProperty(String s) {
        return s.indexOf(EQ) > 1 && s.split(EQ).length == 2;
    }

    public boolean isMonthlyData(String[] fields) {
        return fields != null &&
                fields.length >= 6 &&
                isNumeric(fields[I_YEAR]) &&
                isNumeric(fields[I_MNTH]);
    }

    public boolean isLocationData(String s) {
        return s != null &&
                s.contains(MonthlyWeatherData.LOC) &&
                s.contains(MonthlyWeatherData.LAT) &&
                s.contains(MonthlyWeatherData.LON);
    }

    public Integer getInt(String i) {
        try {
            return Integer.parseInt(strip(i));
        }
        catch(NumberFormatException nfe) {
            return null;
        }
    }

    public Float getFloat(String i) {
        try {
            return Float.parseFloat(strip(i));
        }
        catch(NumberFormatException nfe) {
            return null;
        }
    }

    public boolean isNumeric(String i) {
        try {
            Float.parseFloat(strip(i));
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public String strip(String s) {
        return s.replaceAll(AX, MT)
                .replaceAll(HASH,MT)
                .replaceAll(DOLLAR,MT);
    }

    public Float median(Float f1, Float f2) {
        if (f1==null || f2==null) {
            return null;
        } else {
            return (f1 + f2)/2;
        }
    }


}
