package com.example.cryptool.FileVerification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptool.AES.AESImageChooserActivity;
import com.example.cryptool.AES.AES_Activity;
import com.example.cryptool.R;
import com.google.android.material.button.MaterialButton;

public class FileVerificationMethodChooserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_verification_method_chooser);

        MaterialButton addFingerprintBtn = findViewById(R.id.addFingerprintBtn);
        MaterialButton verifyFingerprintBtn = findViewById(R.id.checkFingerprintBtn);

        addFingerprintBtn.setOnClickListener((View v) -> startActivity(new Intent(this, AddDigitalFingerprintActivity.class)));

        verifyFingerprintBtn.setOnClickListener((View v) -> startActivity(new Intent(this, VerifyDigitalFingerprintActivity.class)));

    }
}