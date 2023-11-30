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

import edu.northeastern.groupproject_outandabout.BuildConfig;

/**
 * Utility class used to call the Google Places API and retrieve point of interest data.
 */
public class GooglePlacesCaller {

    final static String API_URL = "https://places.googleapis.com/v1/places:searchNearby";
    final static String API_KEY = BuildConfig.GOOGLE_API_KEY;
    final static String FIELD_MASKS = "places.displayName";

    /**
     * Handles POST request to Google Places API Nearby Search (new) using the given parameters
     * as the request body. ***DO NOT CALL IN MAIN THREAD***
     *
     * @param parameters JSON formatted string containing the body of API call.
     * @return JSON formatted String containing the search response and point of interest data.
     */
    public static String fetchPoiData(String parameters) {

        String apiKey = "ENTER_API_KEY_HERE"; // Replace with API_KEY to use. USE SPARINGLY WHILE TESTING

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

            // Add required headers/field masks
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-Goog-Api-Key", apiKey);
            conn.setRequestProperty("X-Goog-FieldMask", FIELD_MASKS);

            // Add request body (search parameters)
            OutputStream os = conn.getOutputStream();
            os.write(parameters.getBytes(StandardCharsets.UTF_8));

            // Get request response
            try {
                InputStream inputStream = conn.getInputStream();
                response = convertStreamToString(inputStream);
            }
            catch (FileNotFoundException e) {
                String errorMessage = convertStreamToString(conn.getErrorStream());
                Log.d("GOOGLE_PLACES_CALLER_ERROR", "fetchPoiData:\n" + errorMessage);
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
