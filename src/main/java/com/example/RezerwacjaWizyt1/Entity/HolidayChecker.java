package com.example.RezerwacjaWizyt1.Entity;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
public class HolidayChecker{
    private final static String country_name ="PL";
    private final static String API_URL ="https://date.nager.at/api/v3/PublicHolidays";

    private String date=null;
    private String localName=null;
    public HolidayChecker(String date, String localName) {
        this.date = date;
        this.localName = localName;
    }

    public HolidayChecker(LocalDate localDate) {

    }

    public String toString() {
        return date + " - " + localName;
    }
    /** data =  rok-miesiąc-dzien
     *
     *
     */

    public static String checkIfHoliday(LocalDate date) throws IOException {
        int year = date.getYear();
        String urlStr = API_URL + "/" + year + "/" + country_name;
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int status = connection.getResponseCode();
        if (status != 200) {
            throw new IOException("Failed to get holidays from API. HTTP status: " + status);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        connection.disconnect();

        // Parse JSON response using Jackson
        ObjectMapper mapper = new ObjectMapper();
        JsonNode holidays = mapper.readTree(content.toString());

        for (JsonNode holiday : holidays) {
            String dateStr = holiday.get("date").asText();  // format: yyyy-MM-dd
            LocalDate holidayDate = LocalDate.parse(dateStr);
            if (holidayDate.equals(date)) {
                return holiday.get("localName").asText();
            }
        }
        return null;  // Not a holiday
    }
}
