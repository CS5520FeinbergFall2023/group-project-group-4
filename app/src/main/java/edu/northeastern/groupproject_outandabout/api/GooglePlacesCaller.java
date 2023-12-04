package edu.northeastern.groupproject_outandabout.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import edu.northeastern.groupproject_outandabout.BuildConfig;
import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

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

    /**
     * Parses json formatted response from Google Places API and puts the point of interest data
     * into ActivityOption objects.
     * @param response JSON formatted string containing a Google Places API response.
     * @param type The type of points of interests contained in the response (Restaurant, Entertainment, etc.)
     * @return ArrayList of ActivityOption objects for each point of interest in API response.
     */
    public ArrayList<ActivityOption> parseApiResponse(String response, String type) {
        ArrayList<ActivityOption> activityOptions = new ArrayList<>();

        JSONArray jsonResponse;
        try {
            // Convert response to JSON
            jsonResponse = new JSONArray(response);

            // Populate arraylist with POI ActivityOption objects
            for(int i = 0; i < jsonResponse.length(); i++) {
                JSONObject pointOfInterest = jsonResponse.getJSONObject(i);

                //TODO Figure out and update api response formatting to get these fields
                String name = "";
                String address = "";
                float rating = 0f;

                ActivityOption option = new ActivityOption(name, "", address, "", rating, type);
                activityOptions.add(option);
            }
        }
        catch (JSONException e) {
            Log.d("GOOGLE_PLACES_CALLER_ERROR", "parseApiResponse: JSONException");
        }

        return activityOptions;
    }

    private static String convertStreamToString(InputStream inputStream) {
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
}
