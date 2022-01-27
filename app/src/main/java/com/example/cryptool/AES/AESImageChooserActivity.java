package com.example.cryptool.AES;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptool.R;
import com.google.android.material.button.MaterialButton;

public class AESImageChooserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes_image_chooser);

        MaterialButton aesEncrypt = findViewById(R.id.aesImageEncryptBtn);
        MaterialButton aesDecrypt = findViewById(R.id.aesImageDecryptBtn);

        aesDecrypt.setOnClickListener((View v) -> startActivity(new Intent(this, AESDecryptImageActivity.class)));

        aesEncrypt.setOnClickListener((View v) -> startActivity(new Intent(this, AESEncryptImageActivity.class)));

    }
}