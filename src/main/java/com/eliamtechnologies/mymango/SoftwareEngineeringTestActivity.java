package com.eliamtechnologies.mymango;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class SoftwareEngineeringTestActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuestionsAdapter questionAdapter;
    private Button submitButton;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software_engineering_test);

        // Initialize the list of questions
        questions = new ArrayList<>();
        questions.add(new Question("Which of the following is a software development process model that emphasizes on the iterative and incremental development of software?",
                "Waterfall model", "Agile model", "Spiral model", "RAD model", "B"));
        questions.add(new Question("Which of the following is a software development methodology that is based on the principles of lean manufacturing and just-in-time production?",
                "Waterfall model", "Agile model", "Six Sigma", "Kanban", "D"));
        questions.add(new Question("Which of the following is a software testing technique that involves testing the software with a large volume of data to ensure that it can handle the expected workload?",
                "Load testing", "Regression testing", "Acceptance testing", "System testing", "A"));
        questions.add(new Question("Which of the following is a measure of the complexity of a software system?",
                "Code coverage", "Cyclomatic complexity", "Coupling", "Cohesion", "B"));
        questions.add(new Question("Which of the following is a software development principle that promotes the separation of the interface from the implementation of a software component?",
                "Encapsulation", "Inheritance", "Polymorphism", "Abstraction", "D"));
        questions.add(new Question("Which of the following is a software testing technique that involves testing the software with different combinations of inputs to ensure that it can handle various scenarios?",
                "Load testing", "Integration testing", "Boundary value analysis", "Acceptance testing", "C"));
        questions.add(new Question("Which of the following is a software development methodology that emphasizes on the importance of continuous feedback and collaboration between developers and customers?",
                "Waterfall model", "Agile model", "RAD model", "Spiral model", "B"));
        questions.add(new Question("Which of the following is a software design pattern that is used to encapsulate a group of individual factories that have a common theme?",
                "Adapter pattern", "Singleton pattern", "Factory method pattern", "Abstract factory pattern", "D"));
        questions.add(new Question("Which of the following is a software quality attribute that refers to the ease with which a software system can be modified or adapted?",
                "Portability", "Reliability", "Maintainability", "Usability", "C"));
        questions.add(new Question("Which of the following is a software development technique that involves breaking down a large project into smaller, more manageable parts that can be completed independently?",
                "Agile development", "Scrum", "Waterfall development", "Divide and conquer", "D"));


        // Find the RecyclerView in the layout
        recyclerView = findViewById(R.id.questions_recycler_view);

        // Create a new QuestionAdapter with the list of questions
        questionAdapter = new QuestionsAdapter(questions, this);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(questionAdapter);

        // Use a LinearLayoutManager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            // Calculate the score
            int score = 0;
            for (Question question : questions) {
                if (question.isCorrect()) {
                    score++;
                }
            }

            // Save the score to the database
            // This depends on how you're handling your database
            // For now, this is just a placeholder
            // ...

            // Start the ScoreActivity
            Intent intent = new Intent(this, ScoreActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        });
        */
    }
}
