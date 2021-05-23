package edu.uci.ics.fabflixmobile;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main extends Activity {

    private EditText username;
    private Button searchButton;

    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "cs122b_spring21_project1_api_example_war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        username = findViewById(R.id.username);

        searchButton = findViewById(R.id.search);

        //assign a listener to call a function to handle the user request when clicking a button
        searchButton.setOnClickListener(view -> search());

//        // TODO: this should be retrieved from the backend server
//        final ArrayList<Movie> movies = new ArrayList<>();
//        movies.add(new Movie("The Terminal", (short) 2004));
//        movies.add(new Movie("The Final Season", (short) 2007));
//
//        MovieListViewAdapter adapter = new MovieListViewAdapter(movies, this);
//
//        ListView listView = findViewById(R.id.list);
//        listView.setAdapter(adapter);
//
//        // have a search bar with a search button
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Movie movie = movies.get(position);
//            String message = String.format("Clicked on position: %d, name: %s, %d", position, movie.getName(), movie.getYear());
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//        });
    }

    public void search() {

        Intent mainPage = new Intent(Main.this, ListViewActivity.class);
        // activate the list page.
        mainPage.putExtra("data", username.getText().toString());
        startActivity(mainPage);
    }
}