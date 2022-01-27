package com.example.cryptool.FileVerification;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.cryptool.R;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class VerifyDigitalFingerprintActivity extends AppCompatActivity {

    private ImageView userImage1;
    private LinearLayout chooserLayout1;
    private MaterialButton verifyFingerprintBtn;
    private LinearLayout imageLayout1;
    private ImageView userImage2;
    private LinearLayout chooserLayout2;
    private LinearLayout imageLayout2;
    private static final String[] PERMISSION_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Uri imageUri1;
    private Uri imageUri2;
    private TextView verifiedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_digital_fingerprint);

        init();
        listeners();

    }

    private void listeners() {

        verifyFingerprintBtn.setOnClickListener((View v) -> {
            verifyDigitalFingerprint();
        });

        imageLayout1.setOnClickListener((View v) -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher1.launch(i);
        });

        imageLayout2.setOnClickListener((View v) -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher2.launch(i);
        });

    }

    private void verifyDigitalFingerprint() {

        if (imageUri1 == null || imageUri2 == null) {
            Toast.makeText(VerifyDigitalFingerprintActivity.this, "Please select the Image", Toast.LENGTH_SHORT).show();
        }else {
            checkStoragePermission();

            try {
                String path1 = imageUri1.getLastPathSegment();
                File file1 = new File(Environment.getExternalStorageDirectory() + "/" + path1.substring(path1.lastIndexOf(":")+1));
                FileInputStream fileInputStream1 = new FileInputStream(file1);
                byte[] imageBytes1 = new byte[fileInputStream1.available()];
                fileInputStream1.read(imageBytes1);

                String path2 = imageUri2.getLastPathSegment();
                File file2 = new File(Environment.getExternalStorageDirectory() + "/" + path2.substring(path2.lastIndexOf(":")+1));
                FileInputStream fileInputStream2 = new FileInputStream(file2);
                byte[] imageBytes2 = new byte[fileInputStream2.available()];
                fileInputStream2.read(imageBytes2);

                byte[] imageHash1 = Arrays.copyOfRange(imageBytes1, imageBytes1.length-32, imageBytes1.length);
                byte[] finalImageBytes2 = Arrays.copyOfRange(imageBytes2, 0, imageBytes1.length-32);

                MessageDigest sha = MessageDigest.getInstance("SHA-256");
                byte[] hash = sha.digest(finalImageBytes2);

                hash = Arrays.copyOf(hash, 32);

                Log.e("hash", Arrays.toString(hash));
                Log.e("ImageHash", Arrays.toString(imageHash1));

                if (Arrays.equals(imageHash1,hash)) {
                    verifiedText.setText("Receiver Image is same as Original Image");
                }else {
                    verifiedText.setText("Receiver Image is not same as Original Image");
                }

            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

    }

    private void checkStoragePermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
        int permission2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    private final ActivityResultLauncher<Intent> resultLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imageUri1 = result.getData().getData();

                        if (imageUri1 != null) {
                            chooserLayout1.setVisibility(View.GONE);
                            userImage1.setVisibility(View.VISIBLE);

                            userImage1.setImageURI(imageUri1);
                        }
                    }
                }
            });


    private final ActivityResultLauncher<Intent> resultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imageUri2 = result.getData().getData();

                        if (imageUri2 != null) {
                            chooserLayout2.setVisibility(View.GONE);
                            userImage2.setVisibility(View.VISIBLE);

                            userImage2.setImageURI(imageUri2);
                        }
                    }
                }
            });


    private void init() {
        userImage1 = findViewById(R.id.checkFingerprintChosenImage1);
        chooserLayout1 = findViewById(R.id.checkFingerprintImageLayout1);
        imageLayout1 = findViewById(R.id.checkFingerprintImageChooser1);
        userImage2 = findViewById(R.id.checkFingerprintChosenImage2);
        chooserLayout2 = findViewById(R.id.checkFingerprintImageLayout2);
        imageLayout2 = findViewById(R.id.checkFingerprintImageChooser2);
        verifyFingerprintBtn = findViewById(R.id.verifyFingerprintBtn);
        verifiedText = findViewById(R.id.verifyFingerprintText);

    }
}