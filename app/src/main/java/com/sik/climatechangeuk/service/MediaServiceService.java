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

}
