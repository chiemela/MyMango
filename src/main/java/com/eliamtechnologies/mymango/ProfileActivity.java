package com.eliamtechnologies.mymango;

import static com.eliamtechnologies.mymango.UserDatabaseHelper.calculateInSampleSize;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private Button sendCertificateButton, logoutButton;
    private ImageView imageView;
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUIRED_WIDTH = 200;
    private static final int REQUIRED_HEIGHT = 200;

    // Add TextViews
    private TextView firstNameText;
    private TextView lastNameText;
    private TextView emailText;
    private TextView testTitleText;
    private TextView scoreText;
    private TextView dateText;
    private TextView test_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupBottomNavigationBar();
        getSupportActionBar().setTitle("Profile");

        // Initialize the TextViews
        firstNameText = findViewById(R.id.first_name);
        lastNameText = findViewById(R.id.last_name);
        emailText = findViewById(R.id.email);
        testTitleText = findViewById(R.id.test_title);
        scoreText = findViewById(R.id.score);
        dateText = findViewById(R.id.date);
        test_id = findViewById(R.id.test_id);
        sendCertificateButton = findViewById(R.id.send_certificate_button);
        logoutButton = findViewById(R.id.logout_button);



        imageView = findViewById(R.id.app_custom_icon);

        // Get the user email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        final String email = sharedPreferences.getString("email", "");

        final UserDatabaseHelper dbHelper = new UserDatabaseHelper(ProfileActivity.this);

        // Get user details from database and set the views
        Cursor userCursor = dbHelper.getUserDetails(email);
        if (userCursor.moveToFirst()) {
            int firstNameIndex = userCursor.getColumnIndex(UserDatabaseHelper.COLUMN_FIRST_NAME);
            int lastNameIndex = userCursor.getColumnIndex(UserDatabaseHelper.COLUMN_LAST_NAME);
            if (firstNameIndex != -1 && lastNameIndex != -1) {
                String firstName = userCursor.getString(firstNameIndex);
                String lastName = userCursor.getString(lastNameIndex);

                firstNameText.setText(firstName);
                lastNameText.setText(lastName);
                emailText.setText(email);
            }
        }

        // Get test scores from database and set the views
        // Assuming you only want to display the most recent test score
        Cursor scoresCursor = dbHelper.getTestScoresByEmail(email);
        if (scoresCursor.moveToLast()) {
            int testTitleIndex = scoresCursor.getColumnIndex(UserDatabaseHelper.COLUMN_TEST_TITLE);
            int scoreIndex = scoresCursor.getColumnIndex(UserDatabaseHelper.COLUMN_SCORE);
            int dateIndex = scoresCursor.getColumnIndex(UserDatabaseHelper.COLUMN_DATE);

            if (testTitleIndex != -1 && scoreIndex != -1 && dateIndex != -1) {
                String testTitle = scoresCursor.getString(testTitleIndex);
                int score = scoresCursor.getInt(scoreIndex);
                String date = scoresCursor.getString(dateIndex);
                int testId = scoresCursor.getInt(0);


                test_id.setText(String.valueOf(testId));
                testTitleText.setText(testTitle);
                scoreText.setText(String.valueOf(score));
                dateText.setText(date);
            }
        }


        byte[] image = dbHelper.getUserProfileImage(email);
        if (image != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = calculateInSampleSize(options, REQUIRED_WIDTH, REQUIRED_HEIGHT);

            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length, options);
            imageView.setImageBitmap(bitmap);
        } else {
            // Set default profile image from drawable if no image is found for the user
            imageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }


        logoutButton.setOnClickListener(v -> {
            // Clear user data
            SharedPreferences sharedPreferences1 = getSharedPreferences("user_info", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.clear();
            editor.apply();

            // Redirect to LoginActivity
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            // Call finishAffinity() to close all active activities.
            finishAffinity();
        });

        sendCertificateButton.setOnClickListener(v -> {
            String firstName = firstNameText.getText().toString();
            String lastName = lastNameText.getText().toString();
            String userEmail = emailText.getText().toString();
            String testTitle = testTitleText.getText().toString();
            String score = scoreText.getText().toString();
            String date = dateText.getText().toString();
            String testId = test_id.getText().toString();

            String subject = "Certificate for " + testTitle;
            String body = "Dear " + firstName + " " + lastName + ",\n\n"
                    + "Congratulations on completing the " + testTitle + "!\n"
                    + "Your score: " + score + "/10\n"
                    + "Date of completion: " + date + "\n"
                    + "Certificate ID: " + testId + "\n\n"
                    + "Keep up the good work!\n\n";

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{userEmail});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);

            try {
                startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ProfileActivity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] imgByte = outputStream.toByteArray();

                UserDatabaseHelper dbHelper = new UserDatabaseHelper(ProfileActivity.this);

                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                String email = sharedPreferences.getString("email", "");

                String lastUpdateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                dbHelper.saveUserProfileImage(email, imgByte, lastUpdateDate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

        bottomNavigationView.setSelectedItemId(R.id.navigation_profile); // Set the selected item
    }
}
