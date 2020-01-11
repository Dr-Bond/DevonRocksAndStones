package com.jamie.devonrocksandstones.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jamie.devonrocksandstones.R;
import com.jamie.devonrocksandstones.api.RetrofitClient;
import com.jamie.devonrocksandstones.models.DefaultResponse;
import com.jamie.devonrocksandstones.models.User;
import com.jamie.devonrocksandstones.storage.SharedPrefManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddClueActivity extends AppCompatActivity {

    private Button btnUploadClue, btnSelectImage;
    private EditText editTextContent;
    private String mediaPath;
    private ImageView imgView;
    private ProgressDialog progressDialog;
    private int stone;
    private int location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clue);

        //Get stone and location id passed from previous activity
        Bundle extras = getIntent().getExtras();
        stone = extras.getInt("stone");
        location = extras.getInt("location");

        //Get storage permissions
        requestPermissions();

        //Start progress bar
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading..");

        //Get view items
        btnUploadClue = findViewById(R.id.buttonUploadClue);
        btnSelectImage = findViewById(R.id.buttonSelectImg);
        editTextContent = findViewById(R.id.editTextContent);
        imgView = findViewById(R.id.imageView);

        btnUploadClue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        //Open image gallery to select image
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //If image is selected
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                //Get image from uploaded data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                //Display image preview.
                imgView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(AddClueActivity.this, "Image not selected", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(AddClueActivity.this, "Clue Failed", Toast.LENGTH_LONG).show();
        }

    }

    private void uploadFile() {
        progressDialog.show();

        //Multipart the file
        File file = new File(mediaPath);

        //Get file details
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        //Get stored user
        User user = SharedPrefManager.getInstance(this).getUser();

        //Get text and remove whitespace
        String content = editTextContent.getText().toString().trim();

        //Api call to upload file
        Call<DefaultResponse> call = RetrofitClient
                .getInstance().getApi().addClue(
                        user.getAccessToken(),
                        stone,
                        location,
                        fileToUpload,
                        filename,
                        content
                );

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                //Dismiss progress bar once uploaded and display post added
                progressDialog.dismiss();
                Toast.makeText(AddClueActivity.this, "Clue Added", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                //Clue failed message
                Toast.makeText(AddClueActivity.this, "Clue Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void  requestPermissions(){
        Dexter.withActivity(this)
            .withPermissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    //check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(getApplicationContext(), "Permissions Granted", Toast.LENGTH_SHORT).show();
                    }
                    //Check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied()) {
                        Toast.makeText(getApplicationContext(), "Must Grant Permissions", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).
            withErrorListener(new PermissionRequestErrorListener() {
                @Override
                public void onError(DexterError error) {
                    Toast.makeText(getApplicationContext(), "Permissions Error", Toast.LENGTH_SHORT).show();
                }
            })
            .onSameThread()
            .check();
    }

}
