package com.eliamtechnologies.mymango;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

    private List<String> slideImageNames;
    private Context context;

    public SlideAdapter(List<String> slideImageNames, Context context) {
        this.slideImageNames = slideImageNames;
        this.context = context;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide, parent, false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        try {
            InputStream inputStream = context.getAssets().open(slideImageNames.get(position));
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.slideImage.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return slideImageNames.size();
    }

    class SlideViewHolder extends RecyclerView.ViewHolder {

        ImageView slideImage;

        SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            slideImage = itemView.findViewById(R.id.slide_image);
        }
    }
}
