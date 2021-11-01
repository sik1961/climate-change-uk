package com.sik.climatechangeuk.service;

import com.sik.climatechangeuk.model.MonthlyWeatherData;
import com.sik.climatechangeuk.model.YearlyAverageWeatherData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MetoDataService {

    private Set<MonthlyWeatherData> weatherData;
    private Map<Integer, YearlyAverageWeatherData> yearlyAverageWeatherData;

    @Scheduled(cron = "0 0/1 * * * *")
    public void updateWeatherData() throws IOException {
        MetoDataHandler modHandler = new MetoDataHandler();
        MetoDataUtilities modUtils = new MetoDataUtilities();
        MetoExcelWriter excelWriter = new MetoExcelWriter();

        this.weatherData = modHandler.getMonthlyData();
        this.yearlyAverageWeatherData = modHandler.buildYearlyAvarageWeatherDataMap(this.weatherData);


        //Set<MonthlyWeatherData> locationSpecificData = modUtils.filterByStation(weatherData, "Paisley");

        Map<String,Set<MonthlyWeatherData>> dataByLocation = weatherData.stream()
                .sorted()
                .collect(Collectors.groupingBy(MonthlyWeatherData::getStationName,Collectors.toSet()));

        LinkedHashMap<String, Set<MonthlyWeatherData>> sortedMap = new LinkedHashMap<>();

        dataByLocation.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

//        for(String key :sortedMap.keySet()) {
//            for (MonthlyWeatherData x: sortedMap.get(key)) {
//                log.info(">>>>>> " + x);
//            }
//
//        }


        excelWriter.writeHistoricWorkbook(sortedMap);

        excelWriter.writeAveragesWorkbook(sortedMap);

        excelWriter.writeExtremesWorkbook(modUtils.buildExtremesMap(weatherData));

    }


}
