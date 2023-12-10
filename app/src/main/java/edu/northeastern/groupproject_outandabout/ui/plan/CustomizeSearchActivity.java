package edu.northeastern.groupproject_outandabout.ui.plan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import androidx.gridlayout.widget.GridLayout;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.northeastern.groupproject_outandabout.ActivityType;
import edu.northeastern.groupproject_outandabout.R;

public class CustomizeSearchActivity extends AppCompatActivity {

    private Spinner searchRadiusSpinner;
    private GridLayout restaurantGrid;
    private GridLayout entertainmentGrid;
    private GridLayout nightlifeGrid;
    private GridLayout outdoorGrid;

    private ActivityType currType;
    private double searchLat;
    private double searchLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_search);

        searchRadiusSpinner = findViewById(R.id.searchRadiusSpinner);

        // References to different activity type search options
        restaurantGrid = findViewById(R.id.restaurantGrid);
        entertainmentGrid = findViewById(R.id.entertainmentGrid);
        nightlifeGrid = findViewById(R.id.nightlifeGrid);
        outdoorGrid = findViewById(R.id.outdoorGrid);

        // Get intent to determine the customization type
        Intent intent = getIntent();
        searchLat = intent.getDoubleExtra("Latitude", 0);
        searchLong = intent.getDoubleExtra("Longitude", 0);
        currType = (ActivityType)intent.getSerializableExtra("ActivityType");
        setLayoutByType(currType);

        // Setup search radius spinner
        setUpSearchRadiusSpinner();

        Button customizeSearchButton = findViewById(R.id.customizeSearchButton);
        customizeSearchButton.setOnClickListener(view ->{
            onCustomSearchButtonClicked();
        });
    }

    /**
     * Reads state of custom search screen and sends custom query back to PreSwipe activity
     */
    private void onCustomSearchButtonClicked() {
        String latitude = Double.toString(searchLat);
        String longitude = Double.toString(searchLong);
        float searchRadiusMi = Float.parseFloat(searchRadiusSpinner.getSelectedItem().toString());
        String searchRadius = Float.toString(milesToMeters(searchRadiusMi));

        String includedTypes = "";
        // Read relevant checkboxes based on ActivityType
        CheckBox includeAllCB = findViewById(R.id.includeAllCB);
        switch (currType) {
            case RESTAURANT:
                if(includeAllCB.isChecked()) {
                    includedTypes += "\"restaurant\"";
                }
                else {
                    CheckBox americanCB = findViewById(R.id.americanCB);
                    if(americanCB.isChecked()) {
                        includedTypes += "\"american_restaurant\"";
                    }
                    CheckBox bakeryCB = findViewById(R.id.bakeryCB);
                    if (bakeryCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"bakery\"";
                    }
                    CheckBox barbecueCB = findViewById(R.id.barbecueCB);
                    if (barbecueCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"barbecue_restaurant\"";
                    }
                    CheckBox brazilianCB = findViewById(R.id.brazilianCB);
                    if (brazilianCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"brazilian_restaurant\"";
                    }
                    CheckBox breakfastCB = findViewById(R.id.breakfastCB);
                    if (breakfastCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"breakfast_restaurant\"";
                    }
                    CheckBox cafeCB = findViewById(R.id.cafeCB);
                    if (cafeCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"cafe\", \"coffee_shop\"";
                    }
                    CheckBox chineseCB = findViewById(R.id.chineseCB);
                    if (chineseCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"chinese_restaurant\"";
                    }
                    CheckBox fastFoodCB = findViewById(R.id.fastFoodCB);
                    if (fastFoodCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"fast_food_restaurant\"";
                    }
                    CheckBox frenchCB = findViewById(R.id.frenchCB);
                    if (frenchCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"french_restaurant\"";
                    }
                    CheckBox greekCB = findViewById(R.id.greekCB);
                    if (greekCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"greek_restaurant\"";
                    }
                    CheckBox indianCB = findViewById(R.id.indianCB);
                    if (indianCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"indian_restaurant\"";
                    }
                    CheckBox italianCB = findViewById(R.id.italianCB);
                    if (italianCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"italian_restaurant\"";
                    }
                    CheckBox japaneseCB = findViewById(R.id.japaneseCB);
                    if (japaneseCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"japanese_restaurant\"";
                    }
                    CheckBox koreanCB = findViewById(R.id.koreanCB);
                    if (koreanCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"korean_restaurant\"";
                    }
                    CheckBox mediterraneanCB = findViewById(R.id.mediterraneanCB);
                    if (mediterraneanCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"mediterranean_restaurant\"";
                    }
                    CheckBox mexicanCB = findViewById(R.id.mexicanCB);
                    if (mexicanCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"mexican_restaurant\"";
                    }
                    CheckBox middleEasternCB = findViewById(R.id.middleEasternCB);
                    if (middleEasternCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"middle_eastern_restaurant\"";
                    }
                    CheckBox pizzaCB = findViewById(R.id.pizzaCB);
                    if (pizzaCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"pizza_restaurant\"";
                    }
                    CheckBox ramenCB = findViewById(R.id.ramenCB);
                    if (ramenCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"ramen_restaurant\"";
                    }
                    CheckBox sandwichShopCB = findViewById(R.id.sandwichShopCB);
                    if (sandwichShopCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"sandwich_shop\"";
                    }
                    CheckBox seafoodCB = findViewById(R.id.seafoodCB);
                    if (seafoodCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"seafood_restaurant\"";
                    }
                    CheckBox spanishCB = findViewById(R.id.spanishCB);
                    if (spanishCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"spanish_restaurant\"";
                    }
                    CheckBox steakHouseCB = findViewById(R.id.steakHouseCB);
                    if (steakHouseCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"steak_house\"";
                    }
                    CheckBox thaiCB = findViewById(R.id.thaiCB);
                    if (thaiCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"thai_restaurant\"";
                    }
                    CheckBox turkishCB = findViewById(R.id.turkishCB);
                    if (turkishCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"turkish_restaurant\"";
                    }
                    CheckBox vegetarianCB = findViewById(R.id.vegetarianCB);
                    if (vegetarianCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"vegetarian_restaurant\"";
                    }
                    CheckBox vietnameseCB = findViewById(R.id.vietnameseCB);
                    if (vietnameseCB.isChecked()) {
                        if(!includedTypes.isEmpty()) {
                            includedTypes += ", ";
                        }
                        includedTypes += "\"vietnamese_restaurant\"";
                    }
                }
                break;

            case ENTERTAINMENT:
                CheckBox amusementCenterCB = findViewById(R.id.amusementCenterCB);
                if(includeAllCB.isChecked() || amusementCenterCB.isChecked()) {
                    includedTypes += "\"amusement_center\"";
                }
                CheckBox aquariumCB = findViewById(R.id.aquariumCB);
                if(includeAllCB.isChecked() || aquariumCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"aquarium\"";
                }
                CheckBox bowlingCB = findViewById(R.id.bowlingCB);
                if(includeAllCB.isChecked() || bowlingCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"bowling_alley\"";
                }
                CheckBox culturalCenterCB = findViewById(R.id.culturalCenterCB);
                if(includeAllCB.isChecked() || culturalCenterCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"cultural_center\"";
                }
                CheckBox eventVenueCB = findViewById(R.id.eventVenue);
                if(includeAllCB.isChecked() || eventVenueCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"event_venue\"";
                }
                CheckBox landmarkCB = findViewById(R.id.landmarkCB);
                if(includeAllCB.isChecked() || landmarkCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"historical_landmark\"";
                }
                CheckBox movieTheatreCB = findViewById(R.id.movieTheaterCB);
                if(includeAllCB.isChecked() || movieTheatreCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"movie_theater\"";
                }
                CheckBox museumCB = findViewById(R.id.museumCB);
                if(includeAllCB.isChecked() || museumCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"museum\"";
                }
                CheckBox performingArtsCB = findViewById(R.id.performingArtsCB);
                if(includeAllCB.isChecked() || performingArtsCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"performing_arts_theater\"";
                }
                CheckBox tourismCB = findViewById(R.id.tourismCB);
                if(includeAllCB.isChecked() || tourismCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"tourist_attraction\", \"visitor_center\"";
                }
                CheckBox zooCB = findViewById(R.id.zooCB);
                if(includeAllCB.isChecked() || zooCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"zoo\"";
                }

                break;

            case NIGHTLIFE:
                CheckBox barCB = findViewById(R.id.barCB);
                if(includeAllCB.isChecked() || barCB.isChecked()) {
                    includedTypes += "\"bar\"";
                }
                CheckBox casinoCB = findViewById(R.id.casinoCB);
                if(includeAllCB.isChecked() || casinoCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"casino\"";
                }
                CheckBox nightClubCB = findViewById(R.id.nightclubCB);
                if(includeAllCB.isChecked() || nightClubCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"night_club\"";
                }
                break;

            case OUTDOORS:
                CheckBox amusementParkCB = findViewById(R.id.amusementParkCB);
                if(includeAllCB.isChecked() || amusementParkCB.isChecked()) {
                    includedTypes += "\"amusement_park\"";
                }
                CheckBox dogParkCB = findViewById(R.id.dogParkCB);
                if(includeAllCB.isChecked() || dogParkCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"dog_park\"";
                }
                CheckBox golfCourseCB = findViewById(R.id.golfCourseCB);
                if(includeAllCB.isChecked() || golfCourseCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"golf_course\"";
                }
                CheckBox hikingAreaCB = findViewById(R.id.hikingAreaCB);
                if(includeAllCB.isChecked() || hikingAreaCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"hiking_area\"";
                }
                CheckBox marinaCB = findViewById(R.id.marinaCB);
                if(includeAllCB.isChecked() || marinaCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"marina\"";
                }
                CheckBox nationalParkCB = findViewById(R.id.nationalParkCB);
                if(includeAllCB.isChecked() || nationalParkCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"national_park\"";
                }
                CheckBox parkCB = findViewById(R.id.parkCB);
                if(includeAllCB.isChecked() || parkCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"park\"";
                }
                CheckBox skiResortCB = findViewById(R.id.skiResortCB);
                if(includeAllCB.isChecked() || skiResortCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"ski_resort\"";
                }
                CheckBox sportsClubCB = findViewById(R.id.sportsClubCB);
                if(includeAllCB.isChecked() || sportsClubCB.isChecked()) {
                    if(!includedTypes.isEmpty()) {
                        includedTypes += ", ";
                    }
                    includedTypes += "\"sports_club\"";
                }
                break;
            default:
                // No activity type passed, somethings wrong
        }

        // Format and send custom query back to preswipe activity
        if(!includedTypes.isEmpty()) {
            String customQuery = "{\n" +
                    "  \"includedTypes\": [" + includedTypes + "],\n" +
                    "  \"maxResultCount\": 20,\n" +
                    "  \"locationRestriction\": {\n" +
                    "    \"circle\": {\n" +
                    "      \"center\": {\n" +
                    "        \"latitude\": " + latitude + ",\n" +
                    "        \"longitude\": " + longitude + "},\n" +
                    "      \"radius\": " + searchRadius + "\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";

            Log.d("TEST CUSTOM", "onCustomSearchButtonClicked: " + customQuery);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("CustomQuery", customQuery);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
        // If no types selected, do not return and display toast
        else {
            Toast.makeText(this, "Please select at least one category", Toast.LENGTH_SHORT).show();
        }
    }

    private void setLayoutByType(ActivityType type) {
        Log.d("TEST", "setLayoutByType: " + type);
        switch (type) {
            case RESTAURANT:
                //restaurant layout
                restaurantGrid.setVisibility(View.VISIBLE);
                break;
            case ENTERTAINMENT:
                //entertainment layout
                entertainmentGrid.setVisibility(View.VISIBLE);
                break;
            case NIGHTLIFE:
                //nightlife layout
                nightlifeGrid.setVisibility(View.VISIBLE);
                break;
            case OUTDOORS:
                //outdoors layout
                outdoorGrid.setVisibility(View.VISIBLE);
                break;
            default:
                // No activity type passed, somethings wrong
        }
    }

    private void setUpSearchRadiusSpinner() {
        // Search Radius
        ArrayAdapter<CharSequence> searchRadiusAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.search_Radius_Options,
                android.R.layout.simple_spinner_item
        );
        searchRadiusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchRadiusSpinner.setAdapter(searchRadiusAdapter);
        // Set default to 2.0
        searchRadiusSpinner.setSelection(2);
    }

    /**
     *  Helper function to convert miles into meters used to set search query radius
     */
    private float milesToMeters(float miles) { return miles * 1609.34f; }
}