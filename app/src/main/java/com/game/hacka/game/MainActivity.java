package com.game.hacka.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences sp;
    private EditText edtName, edtAge;
    private Button btnSave;
    private ImageButton btnCamera;
    private ImageView imgUser;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName=findViewById(R.id.edtName);
        edtAge=findViewById(R.id.edtAge);
        imgUser=findViewById(R.id.imgUser);
        btnSave=findViewById(R.id.btnSave);
        btnCamera=findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        edtName.setText(sp.getString("username", ""));
        edtAge.setText(sp.getString("userage", ""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case(R.id.btnCamera):
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
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgUser.setImageBitmap(imageBitmap);
        }
    }
}

