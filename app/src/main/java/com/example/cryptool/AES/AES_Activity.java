package com.example.cryptool.AES;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
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

public class AES_Activity extends AppCompatActivity {

    private MaterialButton startBtn;
    private TextInputEditText plainText;
    private TextInputEditText userKey;
    private TextView encryptedTv;
    private TextView decryptedTv;
    private LinearLayout aesLayout;
    private TextView learnMore;
    private TextView codeSample;
    private byte[] hash;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aes);

        init();
        listeners();

        aesLayout.setVisibility(View.GONE);

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
            // using SHA-256 to convert any length of input to valid hash for AES
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            hash = sha.digest(byteKey);

            // converting the hash to 256 bits key you can use array size as 16 to create 128 bits key or set size as 24 to get 192 bits key
            hash = Arrays.copyOf(hash, 32);
            Log.d("AESKey", Arrays.toString(hash));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        SecretKeySpec keySpec = new SecretKeySpec(hash, "AES");

        encryptedTv.setText(encrypt(text, keySpec));
        decryptedTv.setText(decrypt(encryptedTv.getText().toString(), keySpec));

        aesLayout.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String encrypt(String text, SecretKeySpec key) {

        try {

            Cipher cipher = Cipher.getInstance("AES");
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

            Cipher decipher = Cipher.getInstance("AES");
            decipher.init(Cipher.DECRYPT_MODE, key);
            return new String(decipher.doFinal(Hex.decodeHex(text)));

        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException | DecoderException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void init() {
        startBtn = findViewById(R.id.aesStartBtn);
        plainText = findViewById(R.id.aesPlainText);
        userKey = findViewById(R.id.aesKey);
        aesLayout = findViewById(R.id.aesLayout);
        encryptedTv = findViewById(R.id.aesEncryptedText);
        decryptedTv = findViewById(R.id.aesDecryptedText);
        learnMore = findViewById(R.id.aesLearnMore);
        codeSample = findViewById(R.id.aesCheckCodeSample);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void sendCodeSample() {

        String code = "byte[] byteKey;\n" +
                "\n" +
                "        // writing the given input to byte array\n" +
                "        byteKey = key.getBytes(StandardCharsets.UTF_8);\n" +
                "\n" +
                "        try {\n" +
                "            // using SHA-256 to convert any length of input to valid hash for AES\n" +
                "            MessageDigest sha = MessageDigest.getInstance(\"SHA-256\");\n" +
                "            hash = sha.digest(byteKey);\n" +
                "\n" +
                "            // converting the hash to 256 bits key you can use array size as 16 to create 128 bits key or set size as 24 to get 192 bits key\n" +
                "            hash = Arrays.copyOf(hash, 32);\n" +
                "            Log.e(\"tag\", Arrays.toString(hash));\n" +
                "\n" +
                "        } catch (NoSuchAlgorithmException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "\n" +
                "        SecretKeySpec keySpec = new SecretKeySpec(hash, \"AES\");\n" +
                "\n" +
                "        encryptedTv.setText(encrypt(text, keySpec));\n" +
                "        decryptedTv.setText(decrypt(encryptedTv.getText().toString(), keySpec));\n" +
                "\n" +
                "        aesLayout.setVisibility(View.VISIBLE);\n" +
                "    }\n" +
                "\n\n\n" +
                "private String decrypt(String text, SecretKeySpec key) {\n" +
                "\n" +
                "        try {\n" +
                "\n" +
                "            Cipher decipher = Cipher.getInstance(\"AES\");\n" +
                "            decipher.init(Cipher.DECRYPT_MODE, key);\n" +
                "            return new String(decipher.doFinal(Hex.decodeHex(text)));\n" +
                "\n" +
                "        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException | DecoderException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "\n" +
                "        return null;\n" +
                "    }\n" +
                "\n\n\n" +
                "private String encrypt(String text, SecretKeySpec key) {\n" +
                "\n" +
                "        try {\n" +
                "\n" +
                "            Cipher cipher = Cipher.getInstance(\"AES\");\n" +
                "            cipher.init(Cipher.ENCRYPT_MODE, key);\n" +
                "            return Hex.encodeHexString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));\n" +
                "\n" +
                "        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException e) {\n" +
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
        bottomSheetDialog.getBehavior().setPeekHeight(this.findViewById(R.id.aesScreenLayout).getHeight());
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}