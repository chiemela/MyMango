package com.eliamtechnologies.mymango;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<String> topicsList;
    private Context context;
    private Map<String, Class<?>> topicToActivityMap;

    // Constructor
    public SearchAdapter(List<String> topicsList, Context context, Map<String, Class<?>> topicToActivityMap) {
        this.topicsList = topicsList;
        this.context = context;
        this.topicToActivityMap = topicToActivityMap;
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView topicTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            topicTextView = itemView.findViewById(R.id.topic_text_view);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                String topic = topicsList.get(position);
                Class<?> targetActivity = topicToActivityMap.get(topic);
                if (targetActivity != null) {
                    context.startActivity(new Intent(context, targetActivity));
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String topic = topicsList.get(position);
        holder.topicTextView.setText(topic);
    }

    @Override
    public int getItemCount() {
        return topicsList.size();
    }

    public void updateData(List<String> newTopicsList) {
        this.topicsList = newTopicsList;
        notifyDataSetChanged();
    }
}
