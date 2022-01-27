package com.example.cryptool.DES;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cryptool.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DES_Activity extends AppCompatActivity {

    private MaterialButton startBtn;
    private TextInputEditText plainText;
    private TextInputEditText userKey;
    private TextView encryptedTv;
    private TextView decryptedTv;
    private LinearLayout desLayout;
    private TextView learnMore;
    private TextView codeSample;
    private byte[] hash;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des);

        init();
        listeners();

        desLayout.setVisibility(View.GONE);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void listeners() {

        startBtn.setOnClickListener((View v) -> nullCheck());

        codeSample.setOnClickListener((View v) -> sendCodeSample());

        learnMore.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void nullCheck() {
        if (plainText.getText().toString().isEmpty()) {
            plainText.setError("Please enter some text");
        }else if (userKey.getText().toString().isEmpty()){
            userKey.setError("Please enter some key");
        }else {
            startEncryptionAndDecryption();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startEncryptionAndDecryption() {
        String text = Objects.requireNonNull(plainText.getText()).toString();
        String key = Objects.requireNonNull(userKey.getText()).toString();

        byte[] byteKey;

        // writing the given input to byte array
        byteKey = key.getBytes(StandardCharsets.UTF_8);

        try {
            // using SHA-1 to convert any length of input to valid hash for DES
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            hash = sha.digest(byteKey);

            // using the hash value as key by storing it in 8 size array to get 64 bits key
            hash = Arrays.copyOf(hash, 8);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        SecretKeySpec keySpec = new SecretKeySpec(hash, "DES");

        encryptedTv.setText(encrypt(text, keySpec));
        decryptedTv.setText(decrypt(encryptedTv.getText().toString(), keySpec));

        desLayout.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String encrypt(String text, SecretKeySpec key) {

        try {

            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Hex.encodeHexString(cipher.doFinal(text.getBytes()));

        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String decrypt(String text, SecretKeySpec key) {

        try {

            Cipher decipher = Cipher.getInstance("DES");
            decipher.init(Cipher.DECRYPT_MODE, key);
            return new String(decipher.doFinal(Hex.decodeHex(text)));

        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException | DecoderException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void init() {
        startBtn = findViewById(R.id.desStartBtn);
        plainText = findViewById(R.id.desPlainText);
        userKey = findViewById(R.id.desKey);
        desLayout = findViewById(R.id.desLayout);
        encryptedTv = findViewById(R.id.desEncryptedText);
        decryptedTv = findViewById(R.id.desDecryptedText);
        learnMore = findViewById(R.id.desLearnMore);
        codeSample = findViewById(R.id.desCheckCodeSample);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void sendCodeSample() {

        String code = "private void startEncryptionAndDecryption() {\n" +
                "        String text = getting the plain text from user\n" +
                "        String key = getting the key from user\n" +
                "\n" +
                "        byte[] byteKey;\n" +
                "\n" +
                "        // writing the given input to byte array\n" +
                "        byteKey = key.getBytes(StandardCharsets.UTF_8);\n" +
                "\n" +
                "        try {\n" +
                "            // using SHA-1 to convert any length of input to valid hash for DES\n" +
                "            MessageDigest sha = MessageDigest.getInstance(\"SHA-1\");\n" +
                "            hash = sha.digest(byteKey);\n" +
                "\n" +
                "            // using the hash value as key by storing it in 8 size array to get 64 bits key\n" +
                "            hash = Arrays.copyOf(hash, 8);\n" +
                "\n" +
                "        } catch (NoSuchAlgorithmException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "\n" +
                "        SecretKeySpec keySpec = new SecretKeySpec(hash, \"DES\");\n" +
                "\n" +
                "        encryptedTv.setText(encrypt(text, keySpec));\n" +
                "        decryptedTv.setText(decrypt(encryptedTv.getText().toString(), keySpec));\n" +
                "\n" +
                "        desLayout.setVisibility(View.VISIBLE);\n" +
                "    }\n" +
                "\n\n\n" +
                "private String encrypt(String text, SecretKeySpec key) {\n" +
                "\n" +
                "        try {\n" +
                "\n" +
                "            Cipher cipher = Cipher.getInstance(\"DES\");\n" +
                "            cipher.init(Cipher.ENCRYPT_MODE, key);\n" +
                "            return Hex.encodeHexString(cipher.doFinal(text.getBytes()));\n" +
                "\n" +
                "        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "\n" +
                "        return null;\n" +
                "    }\n" +
                "\n\n\n" +
                "private String decrypt(String text, SecretKeySpec key) {\n" +
                "\n" +
                "        try {\n" +
                "\n" +
                "            Cipher decipher = Cipher.getInstance(\"DES\");\n" +
                "            decipher.init(Cipher.DECRYPT_MODE, key);\n" +
                "            return new String(decipher.doFinal(Hex.decodeHex(text)));\n" +
                "\n" +
                "        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException | DecoderException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "\n" +
                "        return null;\n" +
                "    }";

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
        bottomSheetDialog.getBehavior().setPeekHeight(this.findViewById(R.id.desScreenLayout).getHeight());
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}