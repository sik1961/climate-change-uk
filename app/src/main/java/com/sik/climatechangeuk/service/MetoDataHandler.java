
package com.sik.climatechangeuk.service;

import com.sik.climatechangeuk.model.MonthlyWeatherData;
import com.sik.climatechangeuk.model.YearlyAverageWeatherData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.*;

import static com.sik.climatechangeuk.model.MonthlyWeatherData.*;


@Component
public class MetoDataHandler {

	private static final Logger LOG = LoggerFactory.getLogger(MetoDataHandler.class);

	private static final String IMPORT_FILE = "/weather-station-data.dat";
	private static final String EQ = "=";
	private static final String SPACES = " +";

	MetoDataUtilities util = new MetoDataUtilities();

	private Map<String, String> urlMap = new HashMap<>();

	public Set<MonthlyWeatherData> getMonthlyData() throws IOException {
		Set<MonthlyWeatherData> monthlyData = new TreeSet<>();

		this.urlMap = new MetOfficeUrlMap().getUrlMap();

		for (String weatherStation : urlMap.keySet()) {
			monthlyData.addAll(readMonthlyDataFromUrl(weatherStation));
		}

		LOG.info("records=" + monthlyData.size());

		return monthlyData;

	}

	private Set<MonthlyWeatherData> readMonthlyDataFromUrl(String weatherStation) throws IOException {
		LOG.info("Getting: " + urlMap.get(weatherStation));
		Set<MonthlyWeatherData> monthlyWeatherDataSet = new TreeSet<>();
		int linesInFile=0;
		String location = "n/a";
		URL page = new URL(urlMap.get(weatherStation));
		BufferedReader br =  new BufferedReader(new InputStreamReader(page.openStream()));
		try {

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				linesInFile++;

				if (util.isLocationData(inputLine)) {
					location = inputLine;
				}

				String[] fields = inputLine.split(SPACES);

				MonthlyWeatherData monthData = null;
				if (util.isMonthlyData(fields)) {
					if (fields.length >= 8) {
						monthData = MonthlyWeatherData.builder()
								.stationName(weatherStation)
								.stationLocation(location)
								.monthStartDate(util.getMonthStartDate(util.getInt(fields[I_YEAR]), util.getInt(fields[I_MNTH])))
								.tempMaxC(util.getFloat(fields[I_TMAX]))
								.tempMedC(util.median(util.getFloat(fields[I_TMAX]), util.getFloat(fields[I_TMIN])))
								.tempMinC(util.getFloat(fields[I_TMIN]))
								.afDays(util.getInt(fields[I_AFDY]))
								.rainfallMm(util.getFloat(fields[I_RNMM]))
								.sunHours(util.getFloat(fields[I_SUNH])).build();
					} else if (fields.length == 7) {
						monthData = MonthlyWeatherData.builder()
								.stationName(weatherStation)
								.stationLocation(location)
								.monthStartDate(util.getMonthStartDate(util.getInt(fields[I_YEAR]), util.getInt(fields[I_MNTH])))
								.tempMaxC(util.getFloat(fields[I_TMAX]))
								.tempMedC(util.median(util.getFloat(fields[I_TMAX]), util.getFloat(fields[I_TMIN])))
								.tempMinC(util.getFloat(fields[I_TMIN]))
								.afDays(util.getInt(fields[I_AFDY]))
								.rainfallMm(util.getFloat(fields[I_RNMM])).build();
					} else {
						LOG.info("Warn: " + inputLine + " #fields=" + fields.length);
					}
				}
				if (monthData != null) {
					monthlyWeatherDataSet.add(monthData);
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LOG.info("lines=" + linesInFile + " records=" + monthlyWeatherDataSet.size());
		br.close();
		return monthlyWeatherDataSet;
	}

	public Map<Integer, YearlyAverageWeatherData> buildYearlyAvarageWeatherDataMap(final Set<MonthlyWeatherData> monthlyWeatherData) {
		Map<Integer, YearlyAverageWeatherData> yearlyAverageDataMap = new TreeMap<>();
		for (MonthlyWeatherData month: monthlyWeatherData) {
			YearlyAverageWeatherData existing = yearlyAverageDataMap.get(month.getMonthStartDate().getYear());
			if (existing == null) {
				yearlyAverageDataMap.put(month.getMonthStartDate().getYear(), createYearAverageData(month));
			} else {
				yearlyAverageDataMap.put(existing.getYearStartDate().getYear(), updateYearAverageData(month, existing));
			}
		}
		return yearlyAverageDataMap;
	}

	public YearlyAverageWeatherData createYearAverageData(MonthlyWeatherData monthlyWeatherData) {
		return YearlyAverageWeatherData.builder()
				.yearStartDate(util.getYearStartDate(monthlyWeatherData.getMonthStartDate().getYear()))
				.countTempMaxC(monthlyWeatherData.getTempMaxC()!=null?1:0)
				.totTempMaxC(monthlyWeatherData.getTempMaxC()!=null?monthlyWeatherData.getTempMaxC():0)
				.countTempMedC(monthlyWeatherData.getTempMedC()!=null?1:0)
				.totTempMedC(monthlyWeatherData.getTempMedC()!=null?monthlyWeatherData.getTempMedC():0)
				.countTempMinC(monthlyWeatherData.getTempMinC()!=null?1:0)
				.totTempMinC(monthlyWeatherData.getTempMinC()!=null?monthlyWeatherData.getTempMinC():0)
				.countAfDays(monthlyWeatherData.getAfDays()!=null?1:0)
				.totAfDays(monthlyWeatherData.getAfDays()!=null?util.intToFloat(monthlyWeatherData.getAfDays()):0)
				.countRainfallMm(monthlyWeatherData.getRainfallMm()!=null?1:0)
				.totRainfallMm(monthlyWeatherData.getRainfallMm()!=null?monthlyWeatherData.getRainfallMm():0)
				.countSunHours(monthlyWeatherData.getSunHours()!=null?1:0)
				.totSunHours(monthlyWeatherData.getSunHours()!=null?monthlyWeatherData.getSunHours():0)
				.build();

	}

	public YearlyAverageWeatherData updateYearAverageData(MonthlyWeatherData monthlyWeatherData,
														  YearlyAverageWeatherData existing) {
		return YearlyAverageWeatherData.builder()
				.yearStartDate(existing.getYearStartDate())
				.countTempMaxC(existing.getCountTempMaxC() + (monthlyWeatherData.getTempMaxC()!=null?1:0))
				.totTempMaxC(existing.getTotTempMaxC() + (monthlyWeatherData.getTempMaxC()!=null?monthlyWeatherData.getTempMaxC():0))
				.countTempMedC(existing.getCountTempMedC() + (monthlyWeatherData.getTempMedC()!=null?1:0))
				.totTempMedC(existing.getTotTempMedC() + (monthlyWeatherData.getTempMedC()!=null?monthlyWeatherData.getTempMedC():0))
				.countTempMinC(existing.getCountTempMinC() + (monthlyWeatherData.getTempMinC()!=null?1:0))
				.totTempMinC(existing.getTotTempMinC() + (monthlyWeatherData.getTempMinC()!=null?monthlyWeatherData.getTempMinC():0))
				.countAfDays(existing.getCountAfDays() + (monthlyWeatherData.getAfDays()!=null?1:0))
				.totAfDays(existing.getTotAfDays() + (monthlyWeatherData.getAfDays()!=null?util.intToFloat(monthlyWeatherData.getAfDays()):0))
				.countRainfallMm(existing.getCountRainfallMm() + (monthlyWeatherData.getRainfallMm()!=null?1:0))
				.totRainfallMm(existing.getTotRainfallMm() + (monthlyWeatherData.getRainfallMm()!=null?monthlyWeatherData.getRainfallMm():0))
				.countSunHours(existing.getCountSunHours() + (monthlyWeatherData.getSunHours()!=null?1:0))
				.totSunHours(existing.getTotSunHours() + (monthlyWeatherData.getSunHours()!=null?monthlyWeatherData.getSunHours():0))
				.build();
	}

}
