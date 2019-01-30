package com.example.tanishyadav.faceapi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceAttribute;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private FaceServiceClient faceServiceClient = new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0","1fa5ca664ec24057acc763d86398db96");
    ImageView imageView;
    Bitmap bitmap;
    Button browse,takepic;
    private static int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        browse = (Button)findViewById(R.id.browse);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.child);
        imageView = (ImageView)findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(

                        MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
                Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
            }
        });



        Button btnProcess = (Button)findViewById(R.id.show);
        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectAndFrame(bitmap);
            }
        });
    }


    private void detectAndFrame(Bitmap bitmap){

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Toast.makeText(MainActivity.this,"press",Toast.LENGTH_SHORT).show();

        AsyncTask<InputStream,String,Face[]> detectTask = new AsyncTask<InputStream, String, Face[]>() {


            @Override
            protected Face[] doInBackground(InputStream... inputStreams) {
                try{


                    publishProgress("Detecting....");
                    FaceServiceClient.FaceAttributeType[] faceAttr = new FaceServiceClient.FaceAttributeType[]{
                            FaceServiceClient.FaceAttributeType.Age,
                            FaceServiceClient.FaceAttributeType.Gender,
                            FaceServiceClient.FaceAttributeType.Smile,
                            FaceServiceClient.FaceAttributeType.FacialHair,

                    };

                    Face[] result = new Face[0];
                    try {
                        Log.d("MainActivity.this","1112");
                        result = faceServiceClient.detect(inputStreams[0],
                                true,
                                false,
                                faceAttr);
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }

                    if(result == null)
                        {
                            publishProgress("Detection Finished..Nothing detected");
                        }
                        publishProgress(String.format("Detection Finished. %d face(s) detected",result.length));
                        return result;

                }catch (IOException e)
                {
                    e.printStackTrace();
                    return null;
                }

            }

            private ProgressDialog pd = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {

                pd.show();
            }

            @Override
            protected void onProgressUpdate(String... values) {
                Toast.makeText(MainActivity.this,"press",Toast.LENGTH_SHORT).show();
                pd.setMessage(values[0]);
            }

            @Override
            protected void onPostExecute(Face[] faces) {
                Toast.makeText(MainActivity.this,"pppppppp",Toast.LENGTH_SHORT).show();
                Log.v("MainActivity.this","00000");
                Toast.makeText(MainActivity.this,"Start",Toast.LENGTH_SHORT).show();
                pd.dismiss();
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                Gson gson = new Gson();
                String data = gson.toJson(faces);
                intent.putExtra("list_faces",data);
                startActivity(intent);
            }
        };

        detectTask.execute(inputStream);

    }


}
