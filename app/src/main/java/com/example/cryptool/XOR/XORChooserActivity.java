package com.example.cryptool.XOR;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptool.R;
import com.google.android.material.button.MaterialButton;

public class XORChooserActivity extends AppCompatActivity {

    private MaterialButton encrypt;
    private MaterialButton decrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xorchooser);

        init();
        listeners();

    }

    private void listeners() {

        encrypt.setOnClickListener((View v) -> {
            startActivity(new Intent(this, XOREncryptActivity.class));
        });

        decrypt.setOnClickListener((View v) -> {
            startActivity(new Intent(this, XORDecryptActivity.class));
        });
    }

    private void init() {
        encrypt = findViewById(R.id.xorEncryptBtn);
        decrypt = findViewById(R.id.xorDecryptBtn);
    }
}