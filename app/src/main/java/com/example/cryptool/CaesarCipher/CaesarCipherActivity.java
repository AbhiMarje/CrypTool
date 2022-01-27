package com.example.cryptool.CaesarCipher;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptool.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CaesarCipherActivity extends AppCompatActivity {

    MaterialButton startBtn;
    TextInputEditText plainText;
    TextInputEditText key;
    TextView encryptedTv;
    TextView decryptedTv;
    LinearLayout cipherLayout;
    TextView learnMore;
    TextView codeSample;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar_cipher);

        init();
        listeners();

        cipherLayout.setVisibility(View.GONE);

    }

    private void startEncryptionAndDecryption() {
        String text = plainText.getText().toString();
        int mKey = Integer.parseInt(Objects.requireNonNull(key.getText()).toString());

        encryptedTv.setText(encrypt(text, mKey).toString());
        decryptedTv.setText(decrypt(encryptedTv.getText().toString(), mKey).toString());

        cipherLayout.setVisibility(View.VISIBLE);
    }

    private void listeners() {

        startBtn.setOnClickListener((View v) -> nullCheck());

        codeSample.setOnClickListener((View v) -> sendCodeSample());

        learnMore.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void nullCheck() {
        if (plainText.getText().toString().isEmpty()) {
            plainText.setError("Please enter some text");
        }else if (key.getText().toString().isEmpty()) {
            key.setError("Please enter some key");
        }else {
            startEncryptionAndDecryption();
        }
    }

    private void init() {
        startBtn = findViewById(R.id.caesarStartBtn);
        plainText = findViewById(R.id.caesarPlainText);
        key = findViewById(R.id.caesarKey);
        cipherLayout = findViewById(R.id.cipherLayout);
        encryptedTv = findViewById(R.id.encryptedText);
        decryptedTv = findViewById(R.id.decryptedText);
        learnMore = findViewById(R.id.caesarLearnMore);
        codeSample = findViewById(R.id.caesarCheckCodeSample);
        textView = new TextView(this);
    }

    private StringBuilder encrypt(String msg, int key) {
        StringBuilder encryptedString = new StringBuilder();

        for (char character: msg.toCharArray()) {
            if (Character.isUpperCase(character)) {
                int ch = (int) character + key;
                if (ch > 90) {
                    ch = 64 + (ch - 90);
                }
                encryptedString.append((char) ch);
            }
            else if (Character.isLowerCase(character)){
                int ch = (int) character + key;
                if (ch > 122) {
                    ch = 96 + (ch - 122);
                }
                encryptedString.append((char) ch);
            } else {
                encryptedString.append(character);
            }
        }
        return encryptedString;
    }

    private StringBuilder decrypt(String msg, int key) {
        StringBuilder decryptedString = new StringBuilder();

        for (char character: msg.toCharArray()) {
            if (Character.isUpperCase(character)) {
                int ch = (int) character - key;
                if (ch < 65) {
                    ch = 90 - (64 - ch);
                }
                decryptedString.append((char) ch);
            }
            else if (Character.isLowerCase(character)){
                int ch = (int) character - key;
                if (ch < 97) {
                    ch = 122 - (96 - ch);
                }
                decryptedString.append((char) ch);
            }else {
                decryptedString.append(character);
            }
        }
        return decryptedString;
    }

    private void sendCodeSample() {

        String code = "private StringBuilder encrypt(String msg, int key) {\n" +
                "        StringBuilder encryptedString = new StringBuilder();\n" +
                "\n" +
                "        for (char character: msg.toCharArray()) {\n" +
                "            if (Character.isUpperCase(character)) {\n" +
                "                int ch = (int) character + key;\n" +
                "                if (ch > 90) {\n" +
                "                    ch = 64 + (ch - 90);\n" +
                "                }\n" +
                "                encryptedString.append((char) ch);\n" +
                "            }\n" +
                "            else if (Character.isLowerCase(character)){\n" +
                "                int ch = (int) character + key;\n" +
                "                if (ch > 122) {\n" +
                "                    ch = 96 + (ch - 122);\n" +
                "                }\n" +
                "                encryptedString.append((char) ch);\n" +
                "            } else {\n" +
                "                encryptedString.append(character);\n" +
                "            }\n" +
                "        }\n" +
                "        return encryptedString;\n" +
                "    }\n" +
                "\n" +
                "    private StringBuilder decrypt(String msg, int key) {\n" +
                "        StringBuilder decryptedString = new StringBuilder();\n" +
                "\n" +
                "        for (char character: msg.toCharArray()) {\n" +
                "            if (Character.isUpperCase(character)) {\n" +
                "                int ch = (int) character - key;\n" +
                "                if (ch < 65) {\n" +
                "                    ch = 90 - (64 - ch);\n" +
                "                }\n" +
                "                decryptedString.append((char) ch);\n" +
                "            }\n" +
                "            else if (Character.isLowerCase(character)){\n" +
                "                int ch = (int) character - key;\n" +
                "                if (ch < 97) {\n" +
                "                    ch = 122 - (96 - ch);\n" +
                "                }\n" +
                "                decryptedString.append((char) ch);\n" +
                "            }else {\n" +
                "                decryptedString.append(character);\n" +
                "            }\n" +
                "        }\n" +
                "        return decryptedString;\n" +
                "    }\n\n\n";

        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, findViewById(R.id.bottomSheet));
        LinearLayout bottomSheetLayout = view.findViewById(R.id.bottomSheetLayout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 0, 20, 20);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(params);

        TextView textView = new TextView(this);
        textView.setText("Java Code Sample");
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setTextSize(20);
        textView.setLayoutParams(params);
        linearLayout.addView(textView);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(Color.parseColor("#EFEFEF"));
        scrollView.setLayoutParams(params);

        TextView codeTv = new TextView(this);
        codeTv.setText(code);
        codeTv.setTextColor(Color.BLACK);
        codeTv.setPadding(10,20,10,20);
        scrollView.addView(codeTv);
        linearLayout.addView(scrollView);
        bottomSheetLayout.addView(linearLayout);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    bottomSheetLayout.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetStyle);
        bottomSheetDialog.getBehavior().setPeekHeight(this.findViewById(R.id.caesarLayout).getHeight());
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}