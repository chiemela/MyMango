package com.eliamtechnologies.mymango;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigationBar();
        getSupportActionBar().setTitle("MyMango");

        // Find the RecyclerView by its ID and set its layout manager to a linear layout manager
        RecyclerView recyclerView = findViewById(R.id.topics_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a list of Topic objects and add the topics to the list
        List<Topic> topics = new ArrayList<>();
        topics.add(new Topic("Introduction to Software Engineering", IntroductionActivity.class));
        topics.add(new Topic("System Dependability and Security", SystemDependabilityActivity.class));
        topics.add(new Topic("Advanced Software Engineering", AdvancedSoftwareEngineeringActivity.class));
        topics.add(new Topic("Software Management", SoftwareManagementActivity.class));

        // Create a TopicAdapter with the list of topics and set it as the adapter for the RecyclerView
        TopicAdapter adapter = new TopicAdapter(topics, this);
        recyclerView.setAdapter(adapter);

    }

    // this handles the bottom navigation
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

        bottomNavigationView.setSelectedItemId(R.id.navigation_main); // Set the selected item
    }
}