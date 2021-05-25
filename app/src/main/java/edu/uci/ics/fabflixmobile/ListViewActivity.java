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

    private Button backButton;
    private Button prevButton;
    private Button nextButton;

    private int currentPage = 0;

    private ArrayList<Movie> movies = new ArrayList<>();

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
        }

        backButton = findViewById(R.id.back);
        prevButton = findViewById(R.id.prev);

        nextButton = findViewById(R.id.next);

        nextButton.setEnabled(true);

        //assign a listener to call a function to handle the user request when clicking a button
        backButton.setOnClickListener(view -> back());

        nextButton.setOnClickListener(view -> nextPage());
        prevButton.setOnClickListener(view -> prevPage());

        prevButton.setEnabled(false);

    }

    public void nextPage() {
        currentPage++;
        if (currentPage > 0) {
            prevButton.setEnabled(true);
        }
        if (movies.size() <= (currentPage +1) * 20) {
            nextButton.setEnabled(false);
        }
        displayMovies(movies);
    }

    public void prevPage() {
        currentPage--;
        if (currentPage == 0)
            prevButton.setEnabled(false);
        displayMovies(movies);
    }

    public void back() {
        Intent mainPage = new Intent(ListViewActivity.this, Main.class);
        // activate the list page.
        startActivity(mainPage);
    }

    public void displayMovies(ArrayList<Movie> movies) {
        ArrayList<Movie> currMovies = movies;
        if (movies.size() > 20) {
            int startIndex = currentPage * 20;
            int endIndex = Math.min(startIndex + 20, movies.size());
            currMovies = new ArrayList<>(movies.subList(startIndex, endIndex));
        }
        MovieListViewAdapter adapter = new MovieListViewAdapter(currMovies, this);

        // TODO: do pagination here! make sure to cache everything, just have 20 here


        if (movies.size() <= (currentPage +1) * 20) {
            nextButton.setEnabled(false);
        }
        else {
            nextButton.setEnabled(true);
        }


        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // TODO: send the information to the single movie page
            Movie movie = movies.get(position);
            String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());

            Intent mainPage = new Intent(ListViewActivity.this, SingleMovie.class);
            // activate the list page.
            mainPage.putExtra("id", movie.getId());
            mainPage.putExtra("title", movie.getName());
            mainPage.putExtra("dir", movie.getDirector());
            mainPage.putExtra("genres", movie.getGenres());
            mainPage.putExtra("stars", movie.getStars());
            mainPage.putExtra("year", movie.getYear());
            startActivity(mainPage);


            // movie.getId
        });
    }

    public void search(String query, ArrayList<Movie> movies) {

        // pass search query to the listview activity page
        // build the single page data!

        // use the same network queue across our application
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        getMovieInfo(queue, query, movies);

        // request type is POST



//        final StringRequest loginRequest = new StringRequest(
//                Request.Method.GET,
//                baseURL + "/api/search-suggestion" + "?query=" + query,
//                response -> {
//                    // TODO: should parse the json response to redirect to appropriate functions
//                    //  upon different response value.
//                    Log.d("login.success", response);
//
//
//                    try {
//                        JSONArray jsonArr = new JSONArray(response);
//
//                        for (int i = 0; i < jsonArr.length(); i++)
//                        {
//                            JSONObject jsonObj = jsonArr.getJSONObject(i);
//                            JSONArray keys = jsonObj.names ();
//
//
////                                String key = keys.getString (j); // Here's your key
//                            String movieName = jsonObj.getString ("value"); // Here's your value
//                            getMovieInfo(queue, movieName, movies);
////                            movies.add(new Movie(movieName, (short)2004));
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    // TODO: make another request to get the movie information
//
//
//                    // TODO: add to the movies here!
//
//                    // initialize the activity(page)/destination
////                    Intent mainPage = new Intent(Main.this, ListViewActivity.class);
////                    // activate the list page.
////                    mainPage.putExtra("data", response);
////                    startActivity(mainPage);
//                },
//                error -> {
//                    // error
//                    Log.d("login.error", error.toString());
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // POST request form data
//                final Map<String, String> params = new HashMap<>();
////                params.put("query", username.getText().toString());
////                params.put("password", password.getText().toString());
//
//                return params;
//            }
//        };

//         important: queue.add is where the login request is actually sent
//        queue.add(loginRequest);

    }

    public void getMovieInfo(RequestQueue queue, String movieName, ArrayList<Movie> movies) {
        final StringRequest movieRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/movie-list" + "?advSearchQuery=" + movieName,
                response -> {
                    // TODO: should parse the json response to redirect to appropriate functions
                    //  upon different response value.
                    Log.d("movieinfo.success", response);

                    // should match it EXACTLY for title
                    try {
                        JSONArray jsonArr = new JSONArray(response);

                        for (int i = 0; i < jsonArr.length(); i++)
                        {

                            JSONObject jsonObj = jsonArr.getJSONObject(i);
//                            if (jsonObj.get("mov_title").equals(movieName)) {
                                String t = jsonObj.get("mov_title").toString();
                                Short y = Short.parseShort(jsonObj.get("mov_year").toString());
                                String dir = jsonObj.get("movie_dir").toString();
                                String gen = jsonObj.get("movie_gens").toString();
                                String stars = jsonObj.get("movie_stars").toString();
                                String id = jsonObj.get("movie_id").toString();
                                movies.add(new Movie(t,y,dir,gen,stars,id));
//                            }
                            this.movies = movies;
                            displayMovies(movies);
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