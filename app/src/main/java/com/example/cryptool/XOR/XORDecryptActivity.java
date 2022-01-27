package com.example.cryptool.XOR;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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

public class XORDecryptActivity extends AppCompatActivity {

    private ImageView userImage;
    private MaterialButton decryptBtn;
    private TextInputEditText key;
    private LinearLayout imageChooser;
    private TextView imageName;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xordecrypt);

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
                Log.e("tag", String.valueOf(fileInputStream.available()));
                byte[] imageBytes = new byte[fileInputStream.available()];
                fileInputStream.read(imageBytes);

                int i = 0, intKey = 0;
                byte[] keyData = key.getText().toString().trim().getBytes(StandardCharsets.UTF_8);

                for (byte k : keyData) {
                    intKey += k;
                }

                for (byte b : imageBytes) {
                    imageBytes[i] = (byte) (b ^ intKey);
                    i++;
                }

                File path1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File file1 = new File(path1, "XOR_Dec_Img.jpeg");
                FileOutputStream fos = new FileOutputStream(file1);
                fos.write(imageBytes);
                fos.close();
                fileInputStream.close();

                File path2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File file2 = new File(path2, "XOR_Dec_Img.jpeg");

                userImage.setImageURI(Uri.fromFile(file2));

                Toast.makeText(XORDecryptActivity.this, "Decryption Done...!", Toast.LENGTH_SHORT).show();

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

        userImage = findViewById(R.id.xorDecryptedImage);
        decryptBtn = findViewById(R.id.xorImageDecryptBtn);
        key = findViewById(R.id.xorKey2);
        imageChooser = findViewById(R.id.xorDecryptImageChooser);
        imageName = findViewById(R.id.xorDecryptedImageName);

    }
}