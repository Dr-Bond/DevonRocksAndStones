package com.jamie.devonrocksandstones.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jamie.devonrocksandstones.R;
import com.jamie.devonrocksandstones.activities.ClueActivity;
import com.jamie.devonrocksandstones.activities.ProfileActivity;
import com.jamie.devonrocksandstones.api.RetrofitClient;
import com.jamie.devonrocksandstones.models.DefaultResponse;
import com.jamie.devonrocksandstones.models.Stone;
import com.jamie.devonrocksandstones.models.User;
import com.jamie.devonrocksandstones.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StonesAdapter extends RecyclerView.Adapter<StonesAdapter.StonesViewHolder> {

    private Context mCtx;
    private List<Stone> stoneList;

    public StonesAdapter(Context mCtx, List<Stone> stoneList) {
        this.mCtx = mCtx;
        this.stoneList = stoneList;
    }


    @NonNull
    @Override
    public StonesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create view
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_stones, parent, false);
        return new StonesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StonesViewHolder holder,final int position) {
        Stone stone = stoneList.get(position);

        holder.textViewStone.setText("Stone: "+stone.getId()+" - "+stone.getArea());
        holder.textViewStatus.setText("Status: "+stone.getStatus());

        //Listen for found stone button to be clicked
        holder.buttonFoundStone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Get stored user
                User user = SharedPrefManager.getInstance(mCtx).getUser();

                //Make API call with the stored users access token and stones ID relating to the clicked on button
                Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().foundStone(user.getAccessToken(),stoneList.get(position).getId(),stoneList.get(position).getLocation());

                //Call back response to see what the API returns
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                        //On error, return error message
                        if (response.body().isError()) {
                            Toast.makeText(mCtx, "Failed to set stone as hidden.", Toast.LENGTH_LONG).show();
                        } else {
                            //On success, return message
                            Toast.makeText(mCtx, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            //Reload fragment
                            Intent intent = new Intent(mCtx, ProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("fragment", "hidden_stones");
                            mCtx.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    }
                });

            }
        });

        holder.buttonStoneClues.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Start new intent and pass stone ID to intent
                Context context = v.getContext();
                Intent intent = new Intent(context, ClueActivity.class);
                intent.putExtra("stone",stoneList.get(position).getId());
                intent.putExtra("location",stoneList.get(position).getLocation());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stoneList.size();
    }

    class StonesViewHolder extends RecyclerView.ViewHolder {

        //Create items for rows
        TextView textViewStone;
        TextView textViewStatus;
        Button buttonFoundStone;
        Button buttonStoneClues;

        public StonesViewHolder(View itemView) {
            super(itemView);

            textViewStone = itemView.findViewById(R.id.textViewStone);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            buttonFoundStone = itemView.findViewById(R.id.buttonFoundStone);
            buttonStoneClues = itemView.findViewById(R.id.buttonStoneClues);

        }
    }
}
