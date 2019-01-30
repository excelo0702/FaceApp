package com.example.tanishyadav.faceapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceAttribute;
import com.microsoft.projectoxford.face.contract.FaceRectangle;

public class CustomAdapter extends BaseAdapter{
    private Face[] face;
    private Context context;
    private LayoutInflater inflater;
    private Bitmap originalBitmap;
    FaceAttribute faceAttribute;

    public CustomAdapter(Face[] face, Context context, Bitmap originalBitmap)
    {
        this.face = face;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.originalBitmap = originalBitmap;
    }


    @Override
    public int getCount() {
        return face.length;
    }

    @Override
    public Object getItem(int position) {
        return face[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null)
        {
            view = inflater.inflate(R.layout.listview,null);

            TextView txtage,txtGender,txtFacialHair,txtHeadPoss,txtSmile;
            ImageView imageView;
            txtage = (TextView)view.findViewById(R.id.txtage);
            txtGender = (TextView)view.findViewById(R.id.txtGender);
            txtFacialHair = (TextView)view.findViewById(R.id.txtFacialHair);
            txtSmile = (TextView)view.findViewById(R.id.txtsmile);

            imageView  = (ImageView)view.findViewById(R.id.imgThumb);

            txtage.setText("Age: "+face[position].faceAttributes.age);
            txtGender.setText("Gender: "+face[position].faceAttributes.gender);
            txtSmile.setText("Smile: "+face[position].faceAttributes.smile);
            /*txtFacialHair.setText(String.format("Facial hair: %f %f %f",face[position],faceAttribute.facialHair.moustache,
                    face[position].faceAttributes.facialHair.sideburns,
                    face[position].faceAttributes.facialHair.beard));*/
            Log.d("CustomAdapter","1111");


            Bitmap bitmap = ImageHelper.generateThumbnail(originalBitmap,face[position].faceRectangle);
            imageView.setImageBitmap(bitmap);
        }
        return view;
    }
}
