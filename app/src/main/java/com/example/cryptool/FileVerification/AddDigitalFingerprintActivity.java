package com.example.cryptool.FileVerification;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AddDigitalFingerprintActivity extends AppCompatActivity {

    private ImageView userImage;
    private LinearLayout chooserLayout;
    private MaterialButton addFingerprintBtn;
    private LinearLayout imageLayout;
    private TextView userHash;
    private static final String[] PERMISSION_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Uri imageUri;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_digital_fingerprint);
        
        init();
        listeners();
    }

    private void listeners() {
        
        addFingerprintBtn.setOnClickListener((View v) -> {
            addDigitalFingerprint();
        });
        
        imageLayout.setOnClickListener((View v) -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_PICK);
            resultLauncher.launch(i);
        });
        
    }

    private void addDigitalFingerprint() {
        
        if (imageUri == null) {
            Toast.makeText(AddDigitalFingerprintActivity.this, "Please Select the Image", Toast.LENGTH_SHORT).show();
        }else {

            checkStoragePermission();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                MessageDigest sha = MessageDigest.getInstance("SHA-256");
                byte[] hash = sha.digest(imageBytes);

                hash = Arrays.copyOf(hash, 32);

                userHash.setText(Hex.encodeHexString(hash));

                byte[] finalImageBytes = new byte[imageBytes.length + hash.length];
                System.arraycopy(imageBytes, 0, finalImageBytes, 0, imageBytes.length);
                System.arraycopy(hash, 0, finalImageBytes, imageBytes.length, hash.length);

                File path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File file1 = new File(path1, "Verified_Image.jpeg");
                FileOutputStream fos = new FileOutputStream(file1.getAbsolutePath());
                fos.write(finalImageBytes);
                fos.close();

                Toast.makeText(AddDigitalFingerprintActivity.this, "Digital Fingerprint Added Successfully...!", Toast.LENGTH_SHORT).show();


            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        
    }

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imageUri = result.getData().getData();

                        if (imageUri != null) {
                            chooserLayout.setVisibility(View.GONE);
                            userImage.setVisibility(View.VISIBLE);

                            userImage.setImageURI(imageUri);
                        }
                    }
                }
            });

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
    
    private void init() {
        
        userImage = findViewById(R.id.addFingerprintChosenImage);
        chooserLayout = findViewById(R.id.addFingerprintImageLayout);
        addFingerprintBtn = findViewById(R.id.addFingerprintBtn);
        imageLayout = findViewById(R.id.addFingerprintImageChooser);
        userHash = findViewById(R.id.addFingerprintHash);
        
    }
}