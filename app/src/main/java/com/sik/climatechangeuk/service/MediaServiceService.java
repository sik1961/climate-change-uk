package com.sik.climatechangeuk.service;

import com.sik.climatechangeuk.model.MonthlyWeatherData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class MediaServiceService {

//    private final MediaServiceClient mediaServiceClient;
//
//    public void replaceGuidWithImageUrl(MyPlaceAddress myPlaceAddress)  {
//        myPlaceAddress.getAddressWaypoints().getAddressWaypoint().forEach(waypoint -> {
//            //SVOC sends an imageGUID which we replace with an imageURL from Media Service.
//            String imageGUID = waypoint.getImageUrl();
//            Optional<String> imageUrl = Optional.empty();
//            if(StringUtils.isNotEmpty(imageGUID)) {
//                imageUrl = mediaServiceClient.findImageUrl(imageGUID);
//            }
//            waypoint.setImageUrl(imageUrl.orElse(null));
//        });
//    }

    @Component
    public static class MetoDataScheduler {

        @Scheduled(cron = "0 0/1 * * * *")
        public void doStuff() throws IOException {
            MetoDataHandler manager = new MetoDataHandler();
            MetoDataUtilities utilities = new MetoDataUtilities();
            MetoExcelWriter excelWriter = new MetoExcelWriter();

            Set<MonthlyWeatherData> weatherData = manager.getMonthlyData();

            //Set<MonthlyWeatherData> locationSpecificData = utilities.filterByStation(weatherData, "Paisley");

            Map<String,Set<MonthlyWeatherData>> dataByLocation = weatherData.stream()
                    .sorted()
                    .collect(Collectors.groupingBy(MonthlyWeatherData::getStationName,Collectors.toSet()));

            LinkedHashMap<String, Set<MonthlyWeatherData>> sortedMap = new LinkedHashMap<>();

            dataByLocation.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
                    .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

            excelWriter.writeHistoricWorkbook(sortedMap);

            excelWriter.writeAveragesWorkbook(sortedMap);

            excelWriter.writeExtremesWorkbook(utilities.buildExtremesMap(weatherData));

        }


    }
}
