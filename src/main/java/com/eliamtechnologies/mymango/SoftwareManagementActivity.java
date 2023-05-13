package com.eliamtechnologies.mymango;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoftwareManagementActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private SlideAdapter slideAdapter;
    private List<String> slideImageNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_management);
        getSupportActionBar().setTitle("Software Management");

        viewPager = findViewById(R.id.slide_view_pager);
        slideImageNames = loadSlideImageNamesFromAssets();
        slideAdapter = new SlideAdapter(slideImageNames, this);
        viewPager.setAdapter(slideAdapter);
    }

    private List<String> loadSlideImageNamesFromAssets() {
        List<String> fileNamesList = new ArrayList<>();
        try {
            String[] fileNames = getAssets().list("slides4");
            if (fileNames != null) {
                for (String fileName : fileNames) {
                    if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                        fileNamesList.add("slides4/" + fileName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileNamesList;
    }
}