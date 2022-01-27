package com.example.cryptool.AES;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptool.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESDecryptImageActivity extends AppCompatActivity {

    private ImageView userImage;
    private MaterialButton decryptBtn;
    private TextInputEditText key;
    private LinearLayout imageChooser;
    private TextView imageName;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aesdecrypt_image);

        init();
        listeners();

    }

    private void listeners() {

        decryptBtn.setOnClickListener((View v) -> {
            startEncryption();
        });

        imageChooser.setOnClickListener((View v) -> {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            resultLauncher.launch(i);
        });

    }

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imageUri = result.getData().getData();
                        imageName.setText(imageUri.getLastPathSegment().substring(imageUri.getLastPathSegment().lastIndexOf("/")+1));
                    }
                }
            });

    private void startEncryption() {

        String keyText = key.getText().toString().trim();

        if (!keyText.isEmpty() && imageUri != null) {
            try {

                String path = imageUri.getLastPathSegment();
                File file = new File(Environment.getExternalStorageDirectory() + "/" + path.substring(path.lastIndexOf(":")+1));
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] imageBytes = new byte[fileInputStream.available()];
                fileInputStream.read(imageBytes);

                byte[] keyData = key.getText().toString().trim().getBytes(StandardCharsets.UTF_8);

                try {

                    MessageDigest sha = MessageDigest.getInstance("SHA-256");
                    byte[] hash = sha.digest(keyData);

                    hash = Arrays.copyOf(hash, 32);

                    SecretKeySpec keySpec = new SecretKeySpec(hash, "AES");
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.DECRYPT_MODE, keySpec);
                    byte[] decryptedImage = cipher.doFinal(imageBytes);

                    File path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File file1 = new File(path1, "AES_Dec_Img.jpeg");
                    FileOutputStream fos = new FileOutputStream(file1);
                    fos.write(decryptedImage);
                    fos.close();
                    fileInputStream.close();

                    File path2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                    File file2 = new File(path2, "AES_Dec_Img.jpeg");

                    userImage.setImageURI(Uri.fromFile(file2));

                    Toast.makeText(AESDecryptImageActivity.this, "Decryption Done...!", Toast.LENGTH_SHORT).show();

                }catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (imageUri == null) {
            Toast.makeText(this, "Please select an Image", Toast.LENGTH_SHORT).show();
        }else {
            key.setError("Please enter the key");
        }

    }

    private void init() {

        userImage = findViewById(R.id.aesDecryptedImage);
        decryptBtn = findViewById(R.id.aesImageDecryptBtn);
        key = findViewById(R.id.aesImageKey2);
        imageChooser = findViewById(R.id.aesDecryptImageChooser);
        imageName = findViewById(R.id.aesDecryptedImageName);

    }
}