package edu.polytech.ebudget.camera;

import android.Manifest;
import android.content.ContentValues;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalTime;

import edu.polytech.ebudget.R;

public class StorageFragment extends Fragment {
    private IStorageActivity activity;
    private Button buttonSave;
    private Button buttonLoad;
    private String pictureName;
    private String directoryName;

    public StorageFragment() {
    }

    public StorageFragment(IStorageActivity activity){
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_storage, container, false);
        pictureName = "_test.jpg";
        ContextWrapper contextWrapper = new ContextWrapper(getContext());
        directoryName = contextWrapper.getDir("imageDir", ContextWrapper.MODE_PRIVATE).getPath(); //path to /data/user/0/edu.polytech.ebudget.camera/app_imageDir

        buttonSave = rootView.findViewById(R.id.button_save);
        setEnableSaveButton();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap picture = activity.getPictureToSave();
                if (picture != null) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},IStorageActivity.REQUEST_MEDIA_WRITE);
                    }else {
                        saveToInternalStorage(picture);
                        setDisableSaveButton();
                    }

                }
            }
        });
        return rootView;
    }

    public void saveToInternalStorage (Bitmap picture){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, pictureName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/*");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, directoryName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
        File file = new File(directoryName,pictureName);
        try{
            FileOutputStream fout = new FileOutputStream(file);
            picture.compress(Bitmap.CompressFormat.PNG,90,fout);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void setDisableSaveButton() {
        buttonSave.setEnabled(false);
    }

    public void setEnableSaveButton() {
        buttonSave.setEnabled(true);
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }
}