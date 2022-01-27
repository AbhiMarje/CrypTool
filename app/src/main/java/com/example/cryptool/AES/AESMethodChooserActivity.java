package com.example.cryptool.AES;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptool.R;
import com.google.android.material.button.MaterialButton;

public class AESMethodChooserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aesmethod_chooser);

        MaterialButton aesTextEncrypt = findViewById(R.id.aesTextEncryptBtn);
        MaterialButton aesImageEncrypt = findViewById(R.id.aesImageEncryptBtn);

        aesTextEncrypt.setOnClickListener((View v) -> startActivity(new Intent(this, AES_Activity.class)));

        aesImageEncrypt.setOnClickListener((View v) -> startActivity(new Intent(this, AESImageChooserActivity.class)));

    }
}