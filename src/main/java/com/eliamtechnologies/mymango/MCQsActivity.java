package com.eliamtechnologies.mymango;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MCQsActivity extends AppCompatActivity {

    private List<Question> questions;
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqs);
        // Set the title of the ActionBar
        getSupportActionBar().setTitle("Software Engineering MCQs");

        // Get the user email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        // Check if the user has already taken the test
        UserDatabaseHelper dbHelper = new UserDatabaseHelper(MCQsActivity.this);
        if (dbHelper.hasUserTakenTest(email, "Software Engineering MCQs")) {
            // If the user has taken the test, start ScoreActivity
            Intent intent = new Intent(MCQsActivity.this, ScoreActivity.class);
            startActivity(intent);
            return;
        }

        // Initialize the list of questions
        questions = new ArrayList<>();
        questions.add(new Question("1. Which of the following is a software development process model that emphasizes on the iterative and incremental development of software?",
                "Waterfall model", "Agile model", "Spiral model", "RAD model", "B"));
        questions.add(new Question("2. Which of the following is a software development methodology that is based on the principles of lean manufacturing and just-in-time production?",
                "Waterfall model", "Agile model", "Six Sigma", "Kanban", "D"));
        questions.add(new Question("3. Which of the following is a software testing technique that involves testing the software with a large volume of data to ensure that it can handle the expected workload?",
                "Load testing", "Regression testing", "Acceptance testing", "System testing", "A"));
        questions.add(new Question("4. Which of the following is a measure of the complexity of a software system?",
                "Code coverage", "Cyclomatic complexity", "Coupling", "Cohesion", "B"));
        questions.add(new Question("5. Which of the following is a software development principle that promotes the separation of the interface from the implementation of a software component?",
                "Encapsulation", "Inheritance", "Polymorphism", "Abstraction", "D"));
        questions.add(new Question("6. Which of the following is a software testing technique that involves testing the software with different combinations of inputs to ensure that it can handle various scenarios?",
                "Load testing", "Integration testing", "Boundary value analysis", "Acceptance testing", "C"));
        questions.add(new Question("7. Which of the following is a software development methodology that emphasizes on the importance of continuous feedback and collaboration between developers and customers?",
                "Waterfall model", "Agile model", "RAD model", "Spiral model", "B"));
        questions.add(new Question("8. Which of the following is a software design pattern that is used to encapsulate a group of individual factories that have a common theme?",
                "Adapter pattern", "Singleton pattern", "Factory method pattern", "Abstract factory pattern", "D"));
        questions.add(new Question("9. Which of the following is a software quality attribute that refers to the ease with which a software system can be modified or adapted?",
                "Portability", "Reliability", "Maintainability", "Usability", "C"));
        questions.add(new Question("10. Which of the following is a software development technique that involves breaking down a large project into smaller, more manageable parts that can be completed independently?",
                "Agile development", "Scrum", "Waterfall development", "Divide and conquer", "D"));


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create the adapter and set it to the RecyclerView
        adapter = new QuestionAdapter(questions);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = 0;
                for (Question question : questions) {
                    if (question.isAnswerCorrect()) {
                        score++;
                    }
                }

                // Get the user email from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
                String email = sharedPreferences.getString("email", "");

                // Get the current date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String currentDate = sdf.format(new Date());

                // Save the score to the database
                UserDatabaseHelper dbHelper = new UserDatabaseHelper(MCQsActivity.this);
                dbHelper.addTestScore(email, "Software Engineering MCQs", score, currentDate);

                // Create an Intent to start ScoreActivity
                Intent intent = new Intent(MCQsActivity.this, ScoreActivity.class);
                // Pass the score to ScoreActivity
                intent.putExtra("score", score);
                // Start ScoreActivity
                startActivity(intent);
            }
        });

    }
    private class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

        private List<Question> questions;

        QuestionAdapter(List<Question> questions) {
            this.questions = questions;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Question question = questions.get(position);
            holder.bind(question);
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView questionText;
            RadioGroup optionsGroup;
            RadioButton optionA, optionB, optionC, optionD;

            ViewHolder(View itemView) {
                super(itemView);
                questionText = itemView.findViewById(R.id.question_text);
                optionsGroup = itemView.findViewById(R.id.options_group);
                optionA = itemView.findViewById(R.id.option_a);
                optionB = itemView.findViewById(R.id.option_b);
                optionC = itemView.findViewById(R.id.option_c);
                optionD = itemView.findViewById(R.id.option_d);
            }

            void bind(Question question) {
                questionText.setText(question.getQuestion());
                optionA.setText(question.getOptionA());
                optionB.setText(question.getOptionB());
                optionC.setText(question.getOptionC());
                optionD.setText(question.getOptionD());

                // Unregister the listener before setting check state
                optionsGroup.setOnCheckedChangeListener(null);

                // Clear the check state
                optionsGroup.clearCheck();

                // If a button was previously selected, check it
                if (question.getSelectedOptionId() != -1) {
                    optionsGroup.check(question.getSelectedOptionId());
                }

                // Reset the listener
                optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.option_a:
                                question.setUserAnswer("A");
                                break;
                            case R.id.option_b:
                                question.setUserAnswer("B");
                                break;
                            case R.id.option_c:
                                question.setUserAnswer("C");
                                break;
                            case R.id.option_d:
                                question.setUserAnswer("D");
                                break;
                        }

                        // Save the checked option's ID
                        question.setSelectedOptionId(checkedId);
                    }
                });
            }
        }
    }
}