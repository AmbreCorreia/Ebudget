package edu.polytech.ebudget.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import edu.polytech.ebudget.R;

public class PictureFragment extends Fragment {

    private ImageView imageView;

    public PictureFragment(){

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_picture, container, false);

        imageView = rootView.findViewById(R.id.imageView);

        rootView.findViewById(R.id.buttonImage2).setOnClickListener(click ->{
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[] {Manifest.permission.CAMERA},
                        IPictureActivity.REQUEST_CAMERA);
            } else {
                takePicture();
            }
        });


        return rootView;
    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(intent, IPictureActivity.REQUEST_CAMERA);
    }

    public void setImage(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }
}
