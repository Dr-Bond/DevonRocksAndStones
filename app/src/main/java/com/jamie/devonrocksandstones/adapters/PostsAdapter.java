package com.jamie.devonrocksandstones.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jamie.devonrocksandstones.R;
import com.jamie.devonrocksandstones.activities.ProfileActivity;
import com.jamie.devonrocksandstones.api.RetrofitClient;
import com.jamie.devonrocksandstones.models.DefaultResponse;
import com.jamie.devonrocksandstones.models.Post;
import com.jamie.devonrocksandstones.models.User;
import com.jamie.devonrocksandstones.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        holder.textViewPostedBy.setText(post.getPostedBy());
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

        //Hide delete button if post does not belong to user
        if(!post.isDeletable()) {
            holder.buttonDeletePost.setVisibility(View.GONE);
        }

        //Listen for delete button to be clicked
        holder.buttonDeletePost.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            //Get stored user
            User user = SharedPrefManager.getInstance(mCtx).getUser();

            //Make API call with the stored users access token and post ID relating to the clicked on button
            Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().deletePost(user.getAccessToken(),postList.get(position).getId());

            //Call back response to see what the API returns
            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                    //On error, return error message
                    if (response.body().isError()) {
                        Toast.makeText(mCtx, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        //On success, return message
                        Toast.makeText(mCtx, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        //Reload fragment
                        Intent intent = new Intent(mCtx, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("fragment", "posts");
                        mCtx.startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                }
            });

            }
        });

    }

    @Override
    public int getItemCount() {
        if(postList == null) {
            return 0;
        }
        return postList.size();
    }

    class PostsViewHolder extends RecyclerView.ViewHolder {

        //Create items for rows
        TextView textViewPostedBy;
        TextView textViewContent;
        ImageView imageViewStone;
        Button buttonDeletePost;

        public PostsViewHolder(View itemView) {
            super(itemView);

            textViewPostedBy = itemView.findViewById(R.id.textViewPostedBy);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            imageViewStone = itemView.findViewById(R.id.imageViewStone);
            buttonDeletePost = itemView.findViewById(R.id.buttonDeletePost);

        }
    }
}
