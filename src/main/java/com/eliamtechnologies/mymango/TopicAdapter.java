package com.eliamtechnologies.mymango;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private List<Topic> topics;
    private Context context;

    public TopicAdapter(List<Topic> topics, Context context) {
        this.topics = topics;
        this.context = context;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_card, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topics.get(position);
        holder.title.setText(topic.getTitle());
        holder.topicIcon.setImageResource(R.drawable.baseline_library_books_24); // Add the appropriate icon resource here.
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, topic.getActivityClass());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    class TopicViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView topicIcon;

        TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.topic_title);
            topicIcon = itemView.findViewById(R.id.topic_icon);
        }
    }
}
