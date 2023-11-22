package edu.northeastern.groupproject_outandabout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import edu.northeastern.groupproject_outandabout.api.GooglePlacesCaller;
import edu.northeastern.groupproject_outandabout.api.OpenStreetMapCaller;


/**
 * Test environment for API calling, not part of app functionality.
 * API Responses are logged in logcat to view
 */
public class ApiTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_test);

        // OpenStreetMap Test
        Button osmTestButton = findViewById(R.id.osmApiCallButton);
        osmTestButton.setOnClickListener(view -> {
            Thread testThread = new Thread(() -> {
                String testQuery =
                        "[out:json];\n" +
                        "area[\"name\"=\"United States\"]->.usa;\n" +
                        "area[\"name\"=\"Boston\"]->.boston;\n" +
                        "node[\"amenity\"=\"restaurant\"](area.usa)(area.boston);\n" +
                        "out 5;";

                String response = OpenStreetMapCaller.fetchPoiData(testQuery);
                Log.d("OSM API RESPONSE: ", response);
            });
            testThread.start();
        });

        // Google Places Test
        Button googleTestButton = findViewById(R.id.googleApiCallButton);
        googleTestButton.setOnClickListener(view -> {
            Thread testThread = new Thread(() -> {
                String testParameters = "{\n" +
                        "  \"includedTypes\": [\"restaurant\"],\n" +
                        "  \"maxResultCount\": 3,\n" +
                        "  \"locationRestriction\": {\n" +
                        "    \"circle\": {\n" +
                        "      \"center\": {\n" +
                        "        \"latitude\": 37.7937,\n" +
                        "        \"longitude\": -122.3965},\n" +
                        "      \"radius\": 500.0\n" +
                        "    }\n" +
                        "  }\n" +
                        "}";

                String response = GooglePlacesCaller.fetchPoiData(testParameters);
                Log.d("GOOGLE API RESPONSE: ", response);
            });
            testThread.start();
        });
    }
}