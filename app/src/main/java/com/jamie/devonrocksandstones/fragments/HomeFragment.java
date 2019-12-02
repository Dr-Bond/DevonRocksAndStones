package com.jamie.devonrocksandstones.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jamie.devonrocksandstones.R;
import com.jamie.devonrocksandstones.storage.SharedPrefManager;

public class HomeFragment extends Fragment {

    private TextView textViewEmail;
    private TextView textViewFirstName;
    private TextView textViewSurname;
    private TextView textViewAddressLineOne;
    private TextView textViewAddressLineTwo;
    private TextView textViewCity;
    private TextView textViewCounty;
    private TextView textViewPostcode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewFirstName = view.findViewById(R.id.textViewFirstName);
        textViewSurname = view.findViewById(R.id.textViewSurname);
        textViewAddressLineOne = view.findViewById(R.id.textViewAddressLineOne);
        textViewAddressLineTwo = view.findViewById(R.id.textViewAddressLineTwo);
        textViewCity = view.findViewById(R.id.textViewCity);
        textViewCounty = view.findViewById(R.id.textViewCounty);
        textViewPostcode = view.findViewById(R.id.textViewPostcode);

        textViewEmail.setText(SharedPrefManager.getInstance(getActivity()).getUser().getEmail());
        textViewFirstName.setText(SharedPrefManager.getInstance(getActivity()).getUser().getFirstName());
        textViewSurname.setText(SharedPrefManager.getInstance(getActivity()).getUser().getSurname());
        textViewAddressLineOne.setText(SharedPrefManager.getInstance(getActivity()).getUser().getAddressLineOne());
        textViewAddressLineTwo.setText(SharedPrefManager.getInstance(getActivity()).getUser().getAddressLineTwo());
        textViewCity.setText(SharedPrefManager.getInstance(getActivity()).getUser().getCity());
        textViewCounty.setText(SharedPrefManager.getInstance(getActivity()).getUser().getCounty());
        textViewPostcode.setText(SharedPrefManager.getInstance(getActivity()).getUser().getPostcode());

    }
}
