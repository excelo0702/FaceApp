package com.example.tanishyadav.faceapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceAttribute;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String data = getIntent().getStringExtra("list_faces");
        Gson gson = new Gson();
        Face[] face = gson.fromJson(data, Face[].class);
        ListView mylistview = (ListView)findViewById(R.id.list);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.child);
        CustomAdapter customAdapter = new CustomAdapter(face,this,bitmap);
        mylistview.setAdapter(customAdapter);
    }
}
