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
import com.jamie.devonrocksandstones.models.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private Context mCtx;
    private List<Post> postList;

    public PostsAdapter(Context mCtx, List<Post> postList) {
        this.mCtx = mCtx;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create view
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_posts, parent, false);
        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder,final int position) {
        Post post = postList.get(position);

        holder.textViewContent.setText(post.getPostedBy());
        holder.textViewContent.setText(post.getContent());

        //Check if image is null, if not null, download image and add to holder, if null, hide the holder
        if(post.getImage() != null) {
            Glide.with(mCtx).load(Uri.parse(post.getImage()))
                    .thumbnail(0.5f)
                    .into(holder.imageViewStone);
        }
        else {
            holder.imageViewStone.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostsViewHolder extends RecyclerView.ViewHolder {

        //Create items for rows
        TextView textViewPostedBy;
        TextView textViewContent;
        ImageView imageViewStone;

        public PostsViewHolder(View itemView) {
            super(itemView);

            textViewPostedBy = itemView.findViewById(R.id.textViewPostedBy);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            imageViewStone = itemView.findViewById(R.id.imageViewStone);

        }
    }
}
