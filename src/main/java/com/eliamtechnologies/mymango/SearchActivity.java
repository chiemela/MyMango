package com.eliamtechnologies.mymango;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private List<String> allTopics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupBottomNavigationBar();
        getSupportActionBar().setTitle("Search");

        // Find the RecyclerView and EditText in the layout
        recyclerView = findViewById(R.id.search_recycler_view);
        EditText searchEditText = findViewById(R.id.search_edit_text);

        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list of all topics
        allTopics = Arrays.asList(
                "Introduction to Software Engineering",
                "System Dependability and Security",
                "Advanced Software Engineering",
                "Software Management",
                "Software Engineering MCQs",

                "Software Development Life Cycle (SDLC)",
                "Software Requirements Analysis",
                "Software Design",
                "Data Structures and Algorithms",
                "Software Testing",
                "Software Maintenance",
                "Software Project Management",
                "Software Quality Assurance (SQA)",
                "Software Configuration Management",
                "Software Metrics and Measurement",
                "Risk Management",
                "Software Process and Improvement",
                "Software Reliability and Fault Tolerance",
                "Human-Computer Interaction (HCI)",
                "Software Security",
                "Cloud Computing and Software Services"
        );

        // Map each topic to its corresponding activity
        Map<String, Class<?>> topicToActivityMap = new HashMap<>();
        topicToActivityMap.put("Introduction to Software Engineering", IntroductionActivity.class);
        topicToActivityMap.put("System Dependability and Security", SystemDependabilityActivity.class);
        topicToActivityMap.put("Advanced Software Engineering", AdvancedSoftwareEngineeringActivity.class);
        topicToActivityMap.put("Software Management", SoftwareManagementActivity.class);
        topicToActivityMap.put("Software Engineering MCQs", MCQsActivity.class);

        // Initialize the adapter with the list of all topics and set it as the adapter for the RecyclerView
        searchAdapter = new SearchAdapter(new ArrayList<>(allTopics), this, topicToActivityMap);
        recyclerView.setAdapter(searchAdapter);


        // Set a text watcher on the EditText to update the search results as the user types
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSearchResults(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void updateSearchResults(String query) {
        // Clear the current search results
        List<String> searchResults = new ArrayList<>();

        // Find the matching topics and add them to the search results
        for (String topic : allTopics) {
            if (topic.toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(topic);
            }
        }

        // Update the data in the adapter and notify it
        searchAdapter.updateData(searchResults);
    }

    private void setupBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Class<?> targetActivity;

            if (itemId == R.id.navigation_main) {
                targetActivity = MainActivity.class;
            } else if (itemId == R.id.navigation_assessment) {
                targetActivity = AssessmentActivity.class;
            } else if (itemId == R.id.navigation_search) {
                targetActivity = SearchActivity.class;
            } else if (itemId == R.id.navigation_profile) {
                targetActivity = ProfileActivity.class;
            } else {
                return false;
            }

            if (this.getClass() != targetActivity) {
                startActivity(new Intent(this, targetActivity));
                overridePendingTransition(0, 0);
            }
            return true;
        });

        bottomNavigationView.setSelectedItemId(R.id.navigation_search); // Set the selected item
    }
}