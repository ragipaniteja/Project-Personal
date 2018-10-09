package com.practical.springbootpractical.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class HelloWorldController    {

    static final String APIKEY = "nT9wWEOvZPBWxWSxpaUIcIN72QVCeWmF";

    @RequestMapping("/hello")
    public String sayHello(@RequestParam(value = "name") String name){
        return "Hello" + name + "!";
    }

    @RequestMapping("/temperature")
    public StringBuffer showTemperture(@RequestParam(value = "city") String city) throws Exception{

        final String CITY_SEARCH_URI = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=";

        //creating a request
        String urlString = CITY_SEARCH_URI+APIKEY+"&q="+city;
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        //setting request headers
        con.setRequestProperty("Content-Type", "application/json");

        //configuring timeouts
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while((inputLine = in.readLine()) != null){
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content;

    }
}
