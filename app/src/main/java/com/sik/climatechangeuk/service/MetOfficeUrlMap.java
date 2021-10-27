package com.sik.climatechangeuk.service;

import java.util.HashMap;
import java.util.Map;

public class MetOfficeUrlMap {

    private Map<String,String> urlMap;
    
    public MetOfficeUrlMap() {
        this.urlMap = new HashMap<>();
        this.initMap();
    }

    public Map<String,String> getUrlMap() {
        return this.urlMap;
    }

    private void initMap() {
        urlMap.put("Aberporth","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/aberporthdata.txt");
        urlMap.put("Armargh","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/armaghdata.txt");
        urlMap.put("Ballypatrick_Forest","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/ballypatrickdata.txt");
        urlMap.put("Bradford","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/bradforddata.txt");
        urlMap.put("Braemar","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/braemardata.txt");
        urlMap.put("Camborne","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/cambornedata.txt");
        urlMap.put("Cambridge_NIAB","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/cambridgedata.txt");
        urlMap.put("Cardiff_Bute_Park","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/cardiffdata.txt");
        urlMap.put("Chivenor","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/chivenordata.txt");
        urlMap.put("Cymstwyth","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/cwmystwythdata.txt");
        urlMap.put("Dunstaffnage","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/dunstaffnagedata.txt");
        urlMap.put("Durham","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/durhamdata.txt");
        urlMap.put("Eastbourne","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/eastbournedata.txt");
        urlMap.put("Eskdalemuir","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/eskdalemuirdata.txt");
        urlMap.put("Heathrow","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/heathrowdata.txt");
        urlMap.put("Hurn","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/hurndata.txt");
        urlMap.put("Lerwick","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/lerwickdata.txt");
        urlMap.put("Leuchars","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/leucharsdata.txt");
        urlMap.put("Lowestoft","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/lowestoftdata.txt");
        urlMap.put("Manston","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/manstondata.txt");
        urlMap.put("Nairn","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/nairndata.txt");
        urlMap.put("Newton_Rigg","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/newtonriggdata.txt");
        urlMap.put("Oxford","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/oxforddata.txt");
        urlMap.put("Paisley","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/paisleydata.txt");
        urlMap.put("Ringway","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/ringwaydata.txt");
        urlMap.put("Ross-on-Wye","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/rossonwyedata.txt");
        urlMap.put("Shawbury","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/shawburydata.txt");
        urlMap.put("Sheffield","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/sheffielddata.txt");
        urlMap.put("Southampton","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/southamptondata.txt");
        urlMap.put("Stornoway_Airport","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/stornowaydata.txt");
        urlMap.put("Sutton_Bonington","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/suttonboningtondata.txt");
        urlMap.put("Tiree","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/tireedata.txt");
        urlMap.put("Valley","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/valleydata.txt");
        urlMap.put("Waddington","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/waddingtondata.txt");
        urlMap.put("Whitby","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/whitbydata.txt");
        urlMap.put("Wick_Airport","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/wickairportdata.txt");
        urlMap.put("Yeovilton","https://www.metoffice.gov.uk/pub/data/weather/uk/climate/stationdata/yeoviltondata.txt");
    }
}
