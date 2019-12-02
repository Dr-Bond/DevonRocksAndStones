package com.jamie.devonrocksandstones.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jamie.devonrocksandstones.R;
import com.jamie.devonrocksandstones.adapters.StonesAdapter;
import com.jamie.devonrocksandstones.api.RetrofitClient;
import com.jamie.devonrocksandstones.models.Stone;
import com.jamie.devonrocksandstones.models.StoneResponse;
import com.jamie.devonrocksandstones.models.User;
import com.jamie.devonrocksandstones.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HiddenStonesFragment extends Fragment {

    private RecyclerView recyclerView;
    private StonesAdapter adapter;
    private List<Stone> stoneList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hidden_stones_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        Call<StoneResponse> call = RetrofitClient.getInstance().getApi().getStones(user.getAccessToken());

        call.enqueue(new Callback<StoneResponse>() {
            @Override
            public void onResponse(Call<StoneResponse> call, Response<StoneResponse> response) {

                stoneList = response.body().getStones();
                adapter = new StonesAdapter(getActivity(), stoneList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<StoneResponse> call, Throwable t) {

            }
        });

    }


    public void markStoneAsFound() {


    }
}
