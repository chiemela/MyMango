package com.eliamtechnologies.mymango;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private LinearLayout testScoresContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getSupportActionBar().setTitle("Your Test Scores");

        // Find the TextView in the layout
        testScoresContainer = findViewById(R.id.test_scores_container);

        // Get the score from the Intent
        int score = getIntent().getIntExtra("score", 0);

        // Get the user email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        // Get the test scores from the database
        UserDatabaseHelper dbHelper = new UserDatabaseHelper(ScoreActivity.this);
        Cursor cursor = dbHelper.getTestScoresByEmail(email);

        // Iterate over the test scores and display them
        while (cursor.moveToNext()) {
            int testId = cursor.getInt(0);
            String testTitle = cursor.getString(2);
            int testScore = cursor.getInt(3);
            String testDate = cursor.getString(4);

            // Create a new TextView to display the test score
            TextView testScoreTextView = new TextView(this);
            testScoreTextView.setText("Test ID: " + testId + "\nTest Title: " + testTitle + "\nScore: " + testScore + "\nDate: " + testDate);
            testScoreTextView.setTextSize(16);

            // Add the TextView to the layout
            testScoresContainer.addView(testScoreTextView);
        }

        cursor.close();
    }

    @Override
    public void onBackPressed() {
        // Create an Intent to start AssessmentActivity
        Intent intent = new Intent(ScoreActivity.this, AssessmentActivity.class);
        startActivity(intent);

        // Call finish() to close the current activity and remove it from the back stack
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = new Intent(this, AssessmentActivity.class);
                startActivity(upIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

