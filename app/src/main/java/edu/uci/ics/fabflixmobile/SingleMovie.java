package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingleMovie extends Activity {

    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "cs122b_spring21_project1_api_example_war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;
    private  TextView genreText;
    private  TextView titleText;
    private  TextView dirText;
    private  TextView starsText;
    private  TextView yearText;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singlemovie);
        // TODO: this should be retrieved from the backend server
        Bundle extras = getIntent().getExtras();
        String id = "";
        String genres = "";
        String title = "";
        Short year = 0;
        String stars = "";
        String dir = "";


        if (extras != null) {
            id = extras.getString("id");
            genres = extras.getString("genres");
            title = extras.getString("title");
            dir = extras.getString("dir");
            year = extras.getShort("year");
            stars = extras.getString("stars");


            //The key argument here must match that used in the other activity
        }
        genreText = findViewById(R.id.genres);
        genreText.setText(genres);

        starsText = findViewById(R.id.stars);
        starsText.setText(stars);

        yearText = findViewById(R.id.year);
        yearText.setText(String.valueOf(year));

        dirText = findViewById(R.id.director);
        dirText.setText(dir);

        titleText = findViewById(R.id.title);
        titleText.setText(title);
//        getSingleMovieInfo(id);

        backButton = findViewById(R.id.back);

        //assign a listener to call a function to handle the user request when clicking a button
        backButton.setOnClickListener(view -> back());
    }

    public void back() {
        Intent mainPage = new Intent(SingleMovie.this, Main.class);
        // activate the list page.
        startActivity(mainPage);
    }

    public void getSingleMovieInfo(String id) {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;

        final StringRequest movieRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/single-movie" + "?id=" + id,
                response -> {
                    // TODO: should parse the json response to redirect to appropriate functions
                    //  upon different response value.
                    Log.d("login.success", response);


                    try {
                        JSONArray jsonArr = new JSONArray(response);

                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            JSONArray keys = jsonObj.names();


//                                String key = keys.getString (j); // Here's your key
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    // TODO: make another request to get the movie information


                    // TODO: add to the movies here!

                    // initialize the activity(page)/destination
//                    Intent mainPage = new Intent(Main.this, ListViewActivity.class);
//                    // activate the list page.
//                    mainPage.putExtra("data", response);
//                    startActivity(mainPage);
                },
                error -> {
                    // error
                    Log.d("login.error", error.toString());
                }) {
            @Override
            protected Map<String, String> getParams() {
                // POST request form data
                final Map<String, String> params = new HashMap<>();
//                params.put("query", username.getText().toString());
//                params.put("password", password.getText().toString());

                return params;
            }
        };

        queue.add(movieRequest);

    }
}
