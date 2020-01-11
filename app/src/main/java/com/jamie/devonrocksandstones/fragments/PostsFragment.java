package com.jamie.devonrocksandstones.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jamie.devonrocksandstones.R;
import com.jamie.devonrocksandstones.activities.AddPostActivity;
import com.jamie.devonrocksandstones.adapters.PostsAdapter;
import com.jamie.devonrocksandstones.api.RetrofitClient;
import com.jamie.devonrocksandstones.models.Post;
import com.jamie.devonrocksandstones.models.PostResponse;
import com.jamie.devonrocksandstones.models.User;
import com.jamie.devonrocksandstones.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private PostsAdapter adapter;
    private List<Post> postList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.posts_fragment, container, false);

        Button b = view.findViewById(R.id.buttonAddPost);
        b.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddPost:
                Intent intent = new Intent(getActivity(), AddPostActivity.class);
                startActivity(intent);
            break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        Call<PostResponse> call = RetrofitClient.getInstance().getApi().getPosts(user.getAccessToken());

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {

                postList = response.body().getPosts();
                adapter = new PostsAdapter(getActivity(), postList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });

    }

}
