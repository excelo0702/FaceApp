package com.example.tanishyadav.faceapi;

import android.graphics.Bitmap;

import com.microsoft.projectoxford.face.contract.FaceRectangle;

public class ImageHelper {

    public static FaceRectangle calculateFaceRectangle(Bitmap bitmap,FaceRectangle faceRectangle,double faceRectEnlargeRatio)
    {
        double sideLength = faceRectangle.width*faceRectEnlargeRatio;
        sideLength = Math.min(sideLength,bitmap.getWidth());
        sideLength = Math.min(sideLength,bitmap.getHeight());
        //Make the left edge to left more

        double left = faceRectangle.left - faceRectangle.width*(faceRectEnlargeRatio-1.0)*0.5;
        left = Math.max(left,0.0);
        left = Math.min(left,bitmap.getWidth() - sideLength);

        double top = faceRectangle.top - faceRectangle.height*(faceRectEnlargeRatio - 1.0)*0.5;
        top = Math.max(top,0.0);
        top = Math.min(top,bitmap.getHeight()-sideLength);

        double shiftTop = faceRectEnlargeRatio -1.0;
        shiftTop = Math.max(shiftTop,0.0);
        shiftTop = Math.min(shiftTop,1.0);
        top = 0.15*shiftTop*faceRectangle.height;
        top = Math.max(top,0.0);

        //Set the Result
        FaceRectangle result = new FaceRectangle();
        result.left = (int)left;
        result.top = (int)top;
        result.width = (int)sideLength;
        result.height = (int)sideLength;
        return result;
    }

    public static Bitmap generateThumbnail(Bitmap bitmap,FaceRectangle faceRectangle)
    {
        FaceRectangle face = calculateFaceRectangle(bitmap,faceRectangle,1.3);
        return Bitmap.createBitmap(bitmap,faceRectangle.left,faceRectangle.top,faceRectangle.width,faceRectangle.height);
    }
}
