package com.example.cryptool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.cryptool.AES.AESMethodChooserActivity;
import com.example.cryptool.Blowfish.BlowfishActivity;
import com.example.cryptool.CaesarCipher.CaesarCipherActivity;
import com.example.cryptool.DES.DES_Activity;
import com.example.cryptool.FileVerification.FileVerificationMethodChooserActivity;
import com.example.cryptool.XOR.XORChooserActivity;

public class MainActivity extends AppCompatActivity {

    private CardView caesarCipherBtn;
    private CardView aesBtn;
    private CardView desBtn;
    private CardView xorBtn;
    private CardView blowfishBtn;
    private CardView fileVerificationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        caesarCipherBtn.setOnClickListener((View v) ->
                startActivity(new Intent(MainActivity.this, CaesarCipherActivity.class))
        );

        aesBtn.setOnClickListener((View v) ->
                startActivity(new Intent(MainActivity.this, AESMethodChooserActivity.class))
        );

        desBtn.setOnClickListener((View v) ->
                startActivity(new Intent(MainActivity.this, DES_Activity.class))
        );

        xorBtn.setOnClickListener((View v) ->
                startActivity(new Intent(MainActivity.this, XORChooserActivity.class))
        );

        blowfishBtn.setOnClickListener((View v) ->
                startActivity(new Intent(MainActivity.this, BlowfishActivity.class))
        );

        fileVerificationBtn.setOnClickListener((View v) ->
                startActivity(new Intent(MainActivity.this, FileVerificationMethodChooserActivity.class))
        );
    }

    private void init() {
        caesarCipherBtn = findViewById(R.id.caesarCipherBtn);
        aesBtn = findViewById(R.id.aesBtn);
        desBtn = findViewById(R.id.desBtn);
        xorBtn = findViewById(R.id.xorBtn);
        blowfishBtn = findViewById(R.id.blowfishBtn);
        fileVerificationBtn = findViewById(R.id.fileVerificationBtn);
    }
}