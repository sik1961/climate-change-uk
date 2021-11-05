package com.sik.climatechangeuk.rest;

import com.sik.climatechangeuk.model.MonthlyWeatherData;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
@Controller
public class DemoController
{
    @RequestMapping("/")
    public String index()
    {
        return"index";
    }
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public ModelAndView save(@ModelAttribute MonthlyWeatherData data)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("weather-data");
        modelAndView.addObject("data", data);
        return modelAndView;
    }
}
