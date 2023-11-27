package edu.northeastern.groupproject_outandabout.api;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Utility class used to call the OpenStreetMap Overpass API and retrieve point of interest data.
 */
public class OpenStreetMapCaller {

    final static String API_URL = "https://z.overpass-api.de/api/interpreter";

    /**
     * Sends a POST request to OpenStreetMap Overpass API using the given Overpass QL query
     * as the request body. ***DO NOT CALL IN MAIN THREAD***
     *
     * @param query Overpass QL formatted String containing the body of the API call.
     * @return JSON formatted String containing the search response and point of interest data.
     */
    public static String fetchPoiData(String query) {
        URL url = null;
        try {
            url = new URL(API_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String response = "none"; // Initialize default value
        try {
            // Open connection for POST request
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Add request body (search query)
            OutputStream os = conn.getOutputStream();
            os.write(query.getBytes(StandardCharsets.UTF_8));

            // Get request response
            try {
                InputStream inputStream = conn.getInputStream();
                response = convertStreamToString(inputStream);
            }
            catch (FileNotFoundException e) {
                String errorMessage = convertStreamToString(conn.getErrorStream());
                Log.d("OSM_CALLER_ERROR", "fetchPoiData:\n" + errorMessage);
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private static String convertStreamToString(InputStream inputStream) {
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
}
