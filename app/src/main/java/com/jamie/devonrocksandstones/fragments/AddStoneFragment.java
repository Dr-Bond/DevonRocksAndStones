package com.jamie.devonrocksandstones.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jamie.devonrocksandstones.R;
import com.jamie.devonrocksandstones.api.RetrofitClient;
import com.jamie.devonrocksandstones.models.DefaultResponse;
import com.jamie.devonrocksandstones.models.User;
import com.jamie.devonrocksandstones.storage.SharedPrefManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStoneFragment extends Fragment implements View.OnClickListener {

    private EditText editTextLocation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_stone_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Get text field
        editTextLocation = view.findViewById(R.id.editTextLocation);
        //List to button event
        view.findViewById(R.id.buttonAdd).setOnClickListener(this);
    }

    private void addStone() {

        //Get text from field
        String location = editTextLocation.getText().toString().trim();

        //Field can not be empty
        if (location.isEmpty()) {
            editTextLocation.setError("Location is required");
            editTextLocation.requestFocus();
            return;
        }

        //Get stored user
        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        //Api Call
        Call<DefaultResponse> call = RetrofitClient.getInstance()
                .getApi().addStone(
                        user.getAccessToken(),
                        location
                );

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                //Success message
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                //Error message
                if (!response.body().isError()) {
                    Toast.makeText(getActivity(), "Could not add stone", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAdd:
                addStone();
                break;
        }
    }
}
