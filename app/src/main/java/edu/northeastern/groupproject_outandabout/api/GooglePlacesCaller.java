package edu.northeastern.groupproject_outandabout.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.northeastern.groupproject_outandabout.ActivityType;
import edu.northeastern.groupproject_outandabout.BuildConfig;
import edu.northeastern.groupproject_outandabout.ui.plan.ActivityOption;

/**
 * Utility class used to call the Google Places API and retrieve point of interest data.
 */
public class GooglePlacesCaller {

    final static String API_URL = "https://places.googleapis.com/v1/places:searchNearby";
    final static String GEOCODING_ENDPOINT = "https://maps.googleapis.com/maps/api/geocode/json";
    final static String API_KEY = BuildConfig.GOOGLE_API_KEY;
    final static String FIELD_MASKS = "places.displayName,places.formattedAddress,places.rating";

    /**
     * Handles POST request to Google Places API Nearby Search (new) using the given parameters
     * as the request body. ***DO NOT CALL IN MAIN THREAD***
     *
     * @param parameters JSON formatted string containing the body of API call.
     * @return JSON formatted String containing the search response and point of interest data.
     */
    public static String fetchPoiData(String parameters) {

        String apiKey = API_KEY;//"ENTER_API_KEY_HERE"; // Replace with API_KEY to use. USE SPARINGLY WHILE TESTING

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
    public static List<ActivityOption> parseApiResponse(String response, ActivityType type) {
        List<ActivityOption> activityOptions = new ArrayList<>();

        JSONObject jsonResponse;
        try {
            // Convert response to JSON
            jsonResponse = new JSONObject(response);
            JSONArray responseElements = jsonResponse.getJSONArray("places");

            // Populate arraylist with POI ActivityOption objects
            for(int i = 0; i < responseElements.length(); i++) {
                JSONObject pointOfInterest = responseElements.getJSONObject(i);

                String name = pointOfInterest.getJSONObject("displayName").optString("text", "n/a");
                String address = pointOfInterest.optString("formattedAddress", "n/a");
                String rating = pointOfInterest.optString("rating", "n/a");

                ActivityOption option = new ActivityOption(name, address.replaceAll("\\R", ""), rating, type);
                activityOptions.add(option);
            }
        }
        catch (JSONException e) {
            Log.d("GOOGLE_PLACES_CALLER_ERROR", "parseApiResponse: JSONException");
        }

        return activityOptions;
    }

    /**
     * Takes an address and calls Google Geocoding API to return its latitude and longitude as well as
     * a boolean flag determining if search found results (1) or if there were no results (-1)
     */
    public static double[] geocodeAddress(String address) {
        double[] latLong = new double[3];
        String apiKey = API_KEY;

        String formattedAddress = "";
        // Replace spaces of address with "%"
        try {
            formattedAddress = URLEncoder.encode(address, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String geocodingURL = GEOCODING_ENDPOINT + "?address=" + formattedAddress + "&key=" + apiKey;

        // Make API call and get response
        URL url = null;
        try {
            url = new URL(geocodingURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String response = "none"; // Initialize default value
        try {
            // Open connection for POST request
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            // Get request response
            try {
                InputStream inputStream = conn.getInputStream();
                response = convertStreamToString(inputStream);
            }
            catch (FileNotFoundException e) {
                String errorMessage = convertStreamToString(conn.getErrorStream());
                Log.d("GOOGLE_PLACES_CALLER_ERROR", "geocodeAddress:\n" + errorMessage);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        Log.d("GEO RESPONSE", "geocodeAddress: " + response);

        // Parse response to get latitude/longitude data
        JSONObject jsonResponse;
        try {
            jsonResponse = new JSONObject(response);
            // Check response status
            String status = jsonResponse.optString("status", "n/a");
            if(status.equals("OK")) {
                latLong[0] = 1;

                JSONArray results = jsonResponse.getJSONArray("results");
                JSONObject result = results.getJSONObject(0);
                result = result.getJSONObject("geometry");
                JSONObject coordinates = result.getJSONObject("location");

                latLong[1] = coordinates.optDouble("lat", 0);
                latLong[2] = coordinates.optDouble("lng", 0);
            }
            else {
                latLong[0] = -1; // Error or no results found
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("LAT/LONG", "geocodeAddress: lat/long :" + latLong[0] + ": " + latLong[1] + ", " + latLong[2]);
        return latLong;
    }

    public static String getMockApiResponse() {
        return "{\n" +
                "  \"places\": [\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Tasty Bites\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"123 Delicious St, Boston, MA, USA\",\n" +
                "      \"rating\": 4.7\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Cuisine Central\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"456 Savory Ave, Boston, MA, USA\",\n" +
                "      \"rating\": 4.2\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Food Haven\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"789 Flavorful Rd, Boston, MA, USA\",\n" +
                "      \"rating\": 4.5\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Treat Spot\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"321 Yummy Lane, Boston, MA, USA\",\n" +
                "      \"rating\": 4.8\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Flavor Junction\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"876 Tantalizing Blvd, Boston, MA, USA\",\n" +
                "      \"rating\": 4.1\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Delish Delight\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"543 Tasteful Rd, Boston, MA, USA\",\n" +
                "      \"rating\": 4.6\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Yum Yum Eatery\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"567 Tasty Blvd, Boston, MA, USA\",\n" +
                "      \"rating\": 4.6\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Savor Street\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"234 Yum Ave, Boston, MA, USA\",\n" +
                "      \"rating\": 4.3\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Tasty Treats\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"890 Delightful St, Boston, MA, USA\",\n" +
                "      \"rating\": 4.4\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Yummy Grub\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"432 Flavor Ave, Boston, MA, USA\",\n" +
                "      \"rating\": 4.2\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Savory Eats\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"987 Tasty Rd, Boston, MA, USA\",\n" +
                "      \"rating\": 4.9\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Flavorful Feasts\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"654 Delicious Blvd, Boston, MA, USA\",\n" +
                "      \"rating\": 4.7\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Tantalizing Treats\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"219 Yum Lane, Boston, MA, USA\",\n" +
                "      \"rating\": 4.5\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Delightful Dishes\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"876 Savorful St, Boston, MA, USA\",\n" +
                "      \"rating\": 4.6\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Taste Haven\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"543 Yummy Ave, Boston, MA, USA\",\n" +
                "      \"rating\": 4.2\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Yummy Eats\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"109 Tasty Rd, Boston, MA, USA\",\n" +
                "      \"rating\": 4.3\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Flavorful Bites\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"210 Savory Blvd, Boston, MA, USA\",\n" +
                "      \"rating\": 4.8\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Savory Treats\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"678 Delicious Rd, Boston, MA, USA\",\n" +
                "      \"rating\": 4.4\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Tasty Delights\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"345 Flavorful St, Boston, MA, USA\",\n" +
                "      \"rating\": 4.5\n" +
                "    },\n" +
                "    {\n" +
                "      \"displayName\": {\n" +
                "        \"text\": \"Delicious Cuisine\",\n" +
                "        \"languageCode\": \"en\"\n" +
                "      },\n" +
                "      \"formattedAddress\": \"876 Yum Ave, Boston, MA, USA\",\n" +
                "      \"rating\": 4.7\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";

         //*/
    }

    private static String convertStreamToString(InputStream inputStream) {
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
}
