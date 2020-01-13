package com.jamie.devonrocksandstones.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jamie.devonrocksandstones.R;
import com.jamie.devonrocksandstones.adapters.CluesAdapter;
import com.jamie.devonrocksandstones.api.RetrofitClient;
import com.jamie.devonrocksandstones.models.Clue;
import com.jamie.devonrocksandstones.models.ClueResponse;
import com.jamie.devonrocksandstones.models.User;
import com.jamie.devonrocksandstones.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClueActivity extends AppCompatActivity implements View.OnClickListener {

    private CluesAdapter adapter;
    private RecyclerView recyclerView;
    private List<Clue> clueList;
    private int stone;
    private int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clues);
        recyclerView = findViewById(R.id.recyclerView);

        //Get stone and location id passed from fragment
        Bundle extras = getIntent().getExtras();
        stone = extras.getInt("stone");
        location = extras.getInt("location");

        Button addClue = findViewById(R.id.buttonAddClue);
        addClue.setOnClickListener(this);
        Button backToStones = findViewById(R.id.buttonBackToStones);
        backToStones.setOnClickListener(this);

        User user = SharedPrefManager.getInstance(ClueActivity.this).getUser();
        Call<ClueResponse> call = RetrofitClient.getInstance().getApi().getClues(user.getAccessToken(),stone,location);

        call.enqueue(new Callback<ClueResponse>() {
            @Override
            public void onResponse(Call<ClueResponse> call, Response<ClueResponse> response) {
                clueList = response.body().getClues();
                adapter = new CluesAdapter(ClueActivity.this, clueList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ClueActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ClueResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddClue:
                //Start new intent and pass stone id
                Intent clueIntent = new Intent(ClueActivity.this, AddClueActivity.class);
                clueIntent.putExtra("stone",stone);
                clueIntent.putExtra("location",location);
                startActivity(clueIntent);
                break;
            case R.id.buttonBackToStones:
                Intent fragmentIntent = new Intent(ClueActivity.this, ProfileActivity.class);
                fragmentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                fragmentIntent.putExtra("fragment","hidden_stones");
                startActivity(fragmentIntent);
            break;
        }
    }

}