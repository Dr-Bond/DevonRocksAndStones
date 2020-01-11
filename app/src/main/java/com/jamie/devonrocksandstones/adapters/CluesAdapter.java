package com.jamie.devonrocksandstones.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jamie.devonrocksandstones.R;
import com.jamie.devonrocksandstones.models.Clue;

import java.util.List;

public class CluesAdapter extends RecyclerView.Adapter<CluesAdapter.CluesViewHolder> {

    private Context mCtx;
    private List<Clue> clueList;

    public CluesAdapter(Context mCtx, List<Clue> clueList) {
        this.mCtx = mCtx;
        this.clueList = clueList;
    }

    @NonNull
    @Override
    public CluesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create view
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_clues, parent, false);
        return new CluesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CluesViewHolder holder,final int position) {
        Clue clue = clueList.get(position);

        holder.textViewAddedBy.setText(clue.getAddedBy());
        holder.textViewContent.setText(clue.getContent());

        //Check if image is null, if not null, download image and add to holder, if null, hide the holder
        if(clue.getImage() != null) {
            Glide.with(mCtx).load(Uri.parse(clue.getImage()))
                    .thumbnail(0.5f)
                    .into(holder.imageViewStone);
        }
        else {
            holder.imageViewStone.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return clueList.size();
    }

    class CluesViewHolder extends RecyclerView.ViewHolder {

        //Create items for rows
        TextView textViewAddedBy;
        TextView textViewContent;
        ImageView imageViewStone;

        public CluesViewHolder(View itemView) {
            super(itemView);

            textViewAddedBy = itemView.findViewById(R.id.textViewAddedBy);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            imageViewStone = itemView.findViewById(R.id.imageViewStone);
        }
    }
}
