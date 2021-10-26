package com.sik.climatechangeuk.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
