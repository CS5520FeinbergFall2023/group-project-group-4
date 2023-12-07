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

import edu.northeastern.groupproject_outandabout.ActivityType;
import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

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

    /**
     * Parses json formatted response from OpenStreetMap Overpass API and puts the point of interest data
     * into ActivityOption objects.
     * @param response JSON formatted string containing a OpenStreetMap Overpass API response.
     * @param type The type of points of interests contained in the response (Restaurant, Entertainment, etc.)
     * @return ArrayList of ActivityOption objects for each point of interest in API response.
     */
    public static ArrayList<ActivityOption> parseApiResponse(String response, ActivityType type) {
        ArrayList<ActivityOption> activityOptions = new ArrayList<>();

        JSONObject jsonResponse;
        try {
            // Convert response to JSON
            jsonResponse = new JSONObject(response);
            JSONArray responseElements = jsonResponse.getJSONArray("elements");

            // Populate arraylist with POI ActivityOption objects
            for(int i = 0; i < jsonResponse.length(); i++) {
                JSONObject element = responseElements.getJSONObject(i);
                JSONObject elementTags = element.getJSONObject("tags");

                //TODO Test that this correctly gets the correct info
                String name = elementTags.optString("name", "n/a");

                String address = "";
                address += elementTags.optString("addr:housenumber", "");
                address += " " + elementTags.optString("addr:street", "");

                // Check if at least Street and street number available, if not give lat/long instead
                if(address.length() > 1) {
                    // If the rest of the address is available then add it
                    String city = elementTags.optString("addr:city", "");
                    String state = elementTags.optString("addr:state", "");
                    String postcode = elementTags.optString("addr:postcode", "");

                    if (!city.isEmpty()) {
                        address += ", " + city;
                    }
                    if (!state.isEmpty()) {
                        address += " " + state;
                    }
                    if (!postcode.isEmpty()) {
                        address += " " + postcode;
                    }
                }
                // Address info is unavailable, set as the lat/long
                else {
                    String latitude = element.optString("lat", "n/a");
                    String longitude = element.optString("lon", "n/a");
                    address = latitude + ", " + longitude;
                }

                // OSM does not provide rating data, set negative as indicator that the field is n/a
                String rating = "n/a";

                ActivityOption option = new ActivityOption(name, address, rating, type);
                activityOptions.add(option);
            }
        }
        catch (JSONException e) {
            Log.d("OSM_CALLER_ERROR", "parseApiResponse: JSONException");
        }

        return activityOptions;
    }

    private static String convertStreamToString(InputStream inputStream) {
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
}
