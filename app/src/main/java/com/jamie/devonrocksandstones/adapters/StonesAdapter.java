package com.jamie.devonrocksandstones.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jamie.devonrocksandstones.R;
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

        holder.textViewStatus.setText(stone.getStatus());

        //Listen for found stone button to be clicked
        holder.buttonFoundStone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Get stored user
                User user = SharedPrefManager.getInstance(mCtx).getUser();

                //Make API call with the stored users access token and stones ID relating to the clicked on button
                Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().foundStone(user.getAccessToken(),stoneList.get(position).getId());

                //Call back response to see what the API returns
                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                        //On error, return error message
                        if (!response.body().isError()) {
                            Toast.makeText(mCtx, "Failed to set stone as hidden.", Toast.LENGTH_LONG).show();
                        }

                        //On success, return error message
                        Toast.makeText(mCtx, response.body().getMessage(), Toast.LENGTH_LONG).show();
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
        return stoneList.size();
    }

    class StonesViewHolder extends RecyclerView.ViewHolder {

        //Create items for rows
        TextView textViewStatus;
        Button buttonFoundStone;

        public StonesViewHolder(View itemView) {
            super(itemView);

            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            buttonFoundStone = itemView.findViewById(R.id.buttonFoundStone);

        }
    }
}
