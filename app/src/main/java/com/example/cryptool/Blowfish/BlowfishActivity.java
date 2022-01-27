package com.example.cryptool.Blowfish;

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
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class BlowfishActivity extends AppCompatActivity {

    private MaterialButton startBtn;
    private TextInputEditText plainText;
    private TextInputEditText userKey;
    private TextView encryptedTv;
    private TextView decryptedTv;
    private LinearLayout blowfishLayout;
    private TextView learnMore;
    private TextView codeSample;
    private byte[] hash;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blowfish);
        init();
        listeners();

        blowfishLayout.setVisibility(View.GONE);

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
//        }else if (userKey.getText().toString().length() < 4){
//            userKey.setError("Key should be more than 4 characters");
//        }else if (userKey.getText().toString().length() >56){
//            userKey.setError("Key should be less than 56 characters");
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
        Log.e("tag", Arrays.toString(byteKey));

//        try {
//            // using SHA-512 to convert any length of input to valid hash for Blowfish
//            MessageDigest sha = MessageDigest.getInstance("SHA-512");
//            hash = sha.digest(byteKey);
//
//            // converting the hash to 256 bits key you can use array size from 4 to 56 to get key from 32 to 448 bits
//            hash = Arrays.copyOf(hash, 32);
//            Log.d("AESKey", Arrays.toString(hash));
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

        SecretKeySpec keySpec = new SecretKeySpec(byteKey, "Blowfish");

        encryptedTv.setText(encrypt(text, keySpec));
        decryptedTv.setText(decrypt(encryptedTv.getText().toString(), keySpec));

        blowfishLayout.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String encrypt(String text, SecretKeySpec key) {

        try {

            Cipher cipher = Cipher.getInstance("Blowfish");
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

            Cipher decipher = Cipher.getInstance("Blowfish");
            decipher.init(Cipher.DECRYPT_MODE, key);
            return new String(decipher.doFinal(Hex.decodeHex(text)));

        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException | DecoderException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void init() {
        startBtn = findViewById(R.id.blowfishStartBtn);
        plainText = findViewById(R.id.blowfishPlainText);
        userKey = findViewById(R.id.blowfishKey);
        blowfishLayout = findViewById(R.id.blowfishLayout);
        encryptedTv = findViewById(R.id.blowfishEncryptedText);
        decryptedTv = findViewById(R.id.blowfishDecryptedText);
        learnMore = findViewById(R.id.blowfishLearnMore);
        codeSample = findViewById(R.id.blowfishCheckCodeSample);
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
                "            // using SHA-512 to convert any length of input to valid hash for Blowfish\n" +
                "            MessageDigest sha = MessageDigest.getInstance(\"SHA-512\");\n" +
                "            hash = sha.digest(byteKey);\n" +
                "\n" +
                "            // converting the hash to 256 bits key you can use array size from 4 to 56 to get key from 32 to 448 bits\n" +
                "            hash = Arrays.copyOf(hash, 32);\n" +
                "            Log.d(\"AESKey\", Arrays.toString(hash));\n" +
                "\n" +
                "        } catch (NoSuchAlgorithmException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "\n" +
                "        SecretKeySpec keySpec = new SecretKeySpec(hash, \"Blowfish\");\n" +
                "\n" +
                "        encryptedTv.setText(encrypt(text, keySpec));\n" +
                "        decryptedTv.setText(decrypt(encryptedTv.getText().toString(), keySpec));\n" +
                "\n" +
                "        blowfishLayout.setVisibility(View.VISIBLE);\n" +
                "    }\n" +
                "\n\n\n" +
                "private String encrypt(String text, SecretKeySpec key) {\n" +
                "\n" +
                "        try {\n" +
                "\n" +
                "            Cipher cipher = Cipher.getInstance(\"Blowfish\");\n" +
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
                "            Cipher decipher = Cipher.getInstance(\"Blowfish\");\n" +
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
        bottomSheetDialog.getBehavior().setPeekHeight(this.findViewById(R.id.blowfishScreenLayout).getHeight());
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}