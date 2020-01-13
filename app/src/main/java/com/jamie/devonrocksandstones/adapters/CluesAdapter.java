package com.jamie.devonrocksandstones.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.jamie.devonrocksandstones.activities.ClueActivity;
import com.jamie.devonrocksandstones.activities.ProfileActivity;
import com.jamie.devonrocksandstones.api.RetrofitClient;
import com.jamie.devonrocksandstones.models.Clue;
import com.jamie.devonrocksandstones.models.DefaultResponse;
import com.jamie.devonrocksandstones.models.User;
import com.jamie.devonrocksandstones.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CluesAdapter extends RecyclerView.Adapter<CluesAdapter.CluesViewHolder> {

    private Context mCtx;
    private List<Clue> clueList;
    private int stone;
    private int location;

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
        //Get extras passed to intent
        Intent intent = ((Activity) mCtx).getIntent();
        Bundle extras = intent.getExtras();
        stone = extras.getInt("stone");
        location = extras.getInt("location");

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

        //Hide delete button if clue does not belong to user
        if(!clue.isDeletable()) {
            holder.buttonDeleteClue.setVisibility(View.GONE);
        }

        //Listen for delete button to be clicked
        holder.buttonDeleteClue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Get stored user
                User user = SharedPrefManager.getInstance(mCtx).getUser();

                //Make API call with the stored users access token and post ID relating to the clicked on button
                Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().deleteClue(user.getAccessToken(),clueList.get(position).getId());

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
                            //Reload intent
                            Intent intent = new Intent(mCtx, ClueActivity.class);
                            intent.putExtra("stone",stone);
                            intent.putExtra("location",location);
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
        return clueList.size();
    }

    class CluesViewHolder extends RecyclerView.ViewHolder {

        //Create items for rows
        TextView textViewAddedBy;
        TextView textViewContent;
        ImageView imageViewStone;
        Button buttonDeleteClue;

        public CluesViewHolder(View itemView) {
            super(itemView);

            textViewAddedBy = itemView.findViewById(R.id.textViewAddedBy);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            imageViewStone = itemView.findViewById(R.id.imageViewStone);
            buttonDeleteClue = itemView.findViewById(R.id.buttonDeleteClue);
        }
    }
}
