package com.jamie.devonrocksandstones.activities;

import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.jamie.devonrocksandstones.R;
import com.jamie.devonrocksandstones.api.RetrofitClient;
import com.jamie.devonrocksandstones.models.DefaultResponse;
import com.jamie.devonrocksandstones.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextSurname;
    private EditText editDateOfBirth;
    private EditText editAddressLineOne;
    private EditText editAddressLineTwo;
    private EditText editTextCity;
    private EditText editTextCounty;
    private EditText editTextPostcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editDateOfBirth  = findViewById(R.id.editDateOfBirth);
        editAddressLineOne  = findViewById(R.id.editTextAddressLineOne);
        editAddressLineTwo  = findViewById(R.id.editTextAddressLineTwo);
        editTextCity  = findViewById(R.id.editTextCity);
        editTextCounty  = findViewById(R.id.editTextCounty);
        editTextPostcode  = findViewById(R.id.editTextPostcode);

        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //Check if user is signed in, if so, load profile
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userSignUp() {
        //Assign fields to variables
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String dateOfBirth = editDateOfBirth.getText().toString().trim();
        String addressLineOne = editAddressLineOne.getText().toString().trim();
        String addressLineTwo = editAddressLineTwo.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String county = editTextCounty.getText().toString().trim();
        String postcode = editTextPostcode.getText().toString().trim();

        //Carry out validation
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }

        if (firstName.isEmpty()) {
            editTextFirstName.setError("First Name Required");
            editTextFirstName.requestFocus();
            return;
        }

        if (surname.isEmpty()) {
            editTextSurname.setError("Surname Required");
            editTextSurname.requestFocus();
            return;
        }

        //Create user from fields, API call
        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email, password, firstName, surname, dateOfBirth, addressLineOne, addressLineTwo, city, county, postcode);


        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {
                    //Success response, display message, move to login
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    setContentView(R.layout.activity_login);

                } else if (response.code() == 422) {
                    //Failure response, user already exist, do nothing
                    Toast.makeText(MainActivity.this, "User Already Exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignUp:
                userSignUp();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
