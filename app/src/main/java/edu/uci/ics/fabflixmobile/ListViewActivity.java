package edu.uci.ics.fabflixmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import java.util.ArrayList;

public class ListViewActivity extends Activity {

    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "cs122b_spring21_project1_api_example_war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        // TODO: this should be retrieved from the backend server
        final ArrayList<Movie> movies = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        String searchString = "";
        if (extras != null) {
            searchString = extras.getString("data");
            search(searchString, movies);
            //The key argument here must match that used in the other activity
        } else {
            movies.add(new Movie("The Terminal 2", (short) 2004));
            movies.add(new Movie("The Final Season", (short) 2007));
        }



    }

    public void displayMovies(ArrayList<Movie> movies) {
        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // TODO: send the information to the single movie page
            Movie movie = movies.get(position);
            String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        });
    }

    public void search(String query, ArrayList<Movie> movies) {

        // pass search query to the listview activity page
        // build the single page data!

        // use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        // request type is POST



        final StringRequest loginRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/search-suggestion" + "?query=" + query,
                response -> {
                    // TODO: should parse the json response to redirect to appropriate functions
                    //  upon different response value.
                    Log.d("login.success", response);


                    try {
                        JSONArray jsonArr = new JSONArray(response);

                        for (int i = 0; i < jsonArr.length(); i++)
                        {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            JSONArray keys = jsonObj.names ();


//                                String key = keys.getString (j); // Here's your key
                            String movieName = jsonObj.getString ("value"); // Here's your value
                            getMovieInfo(queue, movieName);
                            movies.add(new Movie(movieName, (short)2004));
                        }
                        displayMovies(movies);

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

//         important: queue.add is where the login request is actually sent
        queue.add(loginRequest);

    }

    public void getMovieInfo(RequestQueue queue, String movieName) {
        final StringRequest movieRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/movie-list" + "?searchQuery=" + movieName + "-null-null-null",
                response -> {
                    // TODO: should parse the json response to redirect to appropriate functions
                    //  upon different response value.
                    Log.d("movieinfo.success", response);


                    try {
                        JSONArray jsonArr = new JSONArray(response);

                        for (int i = 0; i < jsonArr.length(); i++)
                        {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            JSONArray keys = jsonObj.names ();


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