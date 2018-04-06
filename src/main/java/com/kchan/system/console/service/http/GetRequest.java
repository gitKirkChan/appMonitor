package com.kchan.system.console.service.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class GetRequest {

    private final static Logger LOGGER = LogManager.getLogger();

    public HttpResponse call(String target) {

        LOGGER.info(String.format("Pinging %s", target));

        String responseCode = "";
        StringBuilder responseBody = new StringBuilder();

        try {

            URL url = new URL(target);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("GET");
            huc.setRequestProperty("Accept", "application/json");
            huc.setConnectTimeout(100);

            // Grab the message code and message
            responseCode = Integer.toString(huc.getResponseCode());

            BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
            String line;
            while((line = br.readLine()) != null)  {
                responseBody.append(line);
            }
            huc.disconnect();

            if(!huc.getContentType().equals("application/json;charset=UTF-8")) {
                LOGGER.warn(String.format("Irregular content type returned: ['content type': %s, 'message': %s]", huc.getContentType(), responseBody.toString()));
            }

        } catch (MalformedURLException e) {
            LOGGER.error(String.format("Bad URL: %s", target));
            throw new HttpError(responseCode, responseBody.toString());

        } catch (IOException e) {
            LOGGER.error(String.format("Network error: %s", e.toString()));
            responseCode = "503";
            responseBody.append("SERVICE DOWN");
        }

        return new HttpResponse(responseCode, responseBody.toString());
    }
}
