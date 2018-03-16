package com.game.hacka.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences sp;
    private EditText edtName, edtAge;
    private ImageView imgUser;
    private ImageButton btnSave, btnClose;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.myToolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle(" ");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(toolbar);
        }

        edtName=findViewById(R.id.edtName);
        edtAge=findViewById(R.id.edtAge);
        imgUser=findViewById(R.id.imgUser);
        btnSave=findViewById(R.id.btnSave);
        btnClose=findViewById(R.id.btnClose);
        imgUser.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        edtName.setText(sp.getString("username", ""));
        edtAge.setText(sp.getString("userage", ""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case(R.id.imgUser):
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
            case(R.id.btnSave):
                sp.edit()
                        .putString("username", edtName.getText().toString())
                        .putString("userage", edtAge.getText().toString())
                        .apply();
                Toast.makeText(this,"saved",Toast.LENGTH_SHORT).show();

                break;
            case(R.id.btnClose):
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgUser.setImageBitmap(imageBitmap);

          //  storeCameraPhotoInSDCard(imageBitmap);


          //  String storeFilename = "photo_user.jpg";
          //  Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
          //  imgUser.setImageBitmap(mBitmap);
        }
    }

    private Bitmap getImageFileFromSDCard(String filename) {
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStorageDirectory() + filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void storeCameraPhotoInSDCard(Bitmap imageBitmap) {
        File outputFile = new File(Environment.getExternalStorageDirectory(), "photo_user.jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


