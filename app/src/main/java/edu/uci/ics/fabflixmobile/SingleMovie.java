package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SingleMovie extends Activity {

    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "cs122b_spring21_project1_api_example_war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;


    public void getSingleMovieInfo() {
        final StringRequest movieRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/single-movie" + "?id=",
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
    }
}
