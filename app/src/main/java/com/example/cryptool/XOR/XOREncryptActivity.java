package com.example.cryptool.XOR;

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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.cryptool.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class XOREncryptActivity extends AppCompatActivity {

    private ImageView userImage;
    private LinearLayout chooserLayout;
    private MaterialButton encryptBtn;
    private LinearLayout imageLayout;
    private TextInputEditText key;
    private static final String[] PERMISSION_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xorencrypt);

        init();
        listeners();
    }

    private void listeners() {

        encryptBtn.setOnClickListener((View v) -> {
            startEncryption();
        });

        imageLayout.setOnClickListener((View v) -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_PICK);
            resultLauncher.launch(i);
        });

    }

    private void startEncryption() {

        String keyText = key.getText().toString().trim();

        if (!keyText.isEmpty()) {
            try {

                checkStoragePermission();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                int i = 0, intKey = 0;
                byte[] keyData = key.getText().toString().trim().getBytes(StandardCharsets.UTF_8);

                for (byte k : keyData) {
                    intKey += k;
                }

                for (byte b : imageBytes) {
                    imageBytes[i] = (byte) (b ^ intKey);
                    i++;
                }

                File path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File file1 = new File(path1, "XOR_Enc_Img.jpeg");

                FileOutputStream fos = new FileOutputStream(file1.getAbsolutePath());
                fos.write(imageBytes);
                fos.close();

                Toast.makeText(XOREncryptActivity.this, "Encryption Done...!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            key.setError("Please enter the key");
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

        userImage = findViewById(R.id.xorEncryptedImage);
        chooserLayout = findViewById(R.id.xorEncryptLayout);
        encryptBtn = findViewById(R.id.xorImageEncryptBtn);
        imageLayout = findViewById(R.id.xorEncryptImageChooser);
        key = findViewById(R.id.xorKey);

    }
}