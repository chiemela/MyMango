package com.eliamtechnologies.mymango;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter class for the RecyclerView that will display the questions
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder> {

    // List of questions to be displayed
    private List<Question> questions;
    // Context of the application
    private Context context;

    // Constructor for the QuestionAdapter
    public QuestionsAdapter(List<Question> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    // Method to create ViewHolder instances
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate a new view hierarchy from the item_question XML file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        // Create and return a new QuestionViewHolder that holds the view hierarchy
        return new QuestionViewHolder(view);
    }

    // Method to bind data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        // Get the question at the specified position
        Question question = questions.get(position);
        // Bind the question to the holder
        // holder.bind(question);
    }

    // Method to get the number of items in the data set
    @Override
    public int getItemCount() {
        return questions.size();
    }

    // ViewHolder class that describes an item view and metadata about its place within the RecyclerView
    class QuestionViewHolder extends RecyclerView.ViewHolder {

        // Views in the item layout
        TextView questionText;
        RadioButton optionA, optionB, optionC, optionD;

        // Constructor for the ViewHolder
        QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find the views within the item layout
            questionText = itemView.findViewById(R.id.question_text);
            optionA = itemView.findViewById(R.id.option_a);
            optionB = itemView.findViewById(R.id.option_b);
            optionC = itemView.findViewById(R.id.option_c);
            optionD = itemView.findViewById(R.id.option_d);
        }

        /*
        // Method to bind the question data to the views
        void bind(Question question) {
            // Set the question text
            questionText.setText(question.getQuestionText());

            // Assuming your options are always of size 4
            String[] options = question.getOptions();

            // Set the text for the option RadioButtons
            optionA.setText(options[0]);
            optionB.setText(options[1]);
            optionC.setText(options[2]);
            optionD.setText(options[3]);

            // Create a RadioGroup programmatically and add the RadioButtons to it
            RadioGroup radioGroup = new RadioGroup(context);
            radioGroup.addView(optionA);
            radioGroup.addView(optionB);
            radioGroup.addView(optionC);
            radioGroup.addView(optionD);

            // Handle RadioButton selection
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton selectedOption = group.findViewById(checkedId);
                question.setSelectedAnswer(selectedOption.getText().toString());
            });

            // Handle pre-selected answer
            switch (question.getSelectedAnswer()) {
                case "A":
                    radioGroup.check(optionA.getId());
                    break;
                case "B":
                    radioGroup.check(optionB.getId());
                    break;
                case "C":
                    radioGroup.check(optionC.getId());
                    break;
                case "D":
                    radioGroup.check(optionD.getId());
                    break;
                default:
                    radioGroup.clearCheck();
            }
        }
        */
    }

}
