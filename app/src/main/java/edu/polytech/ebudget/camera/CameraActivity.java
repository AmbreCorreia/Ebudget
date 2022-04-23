package edu.polytech.ebudget.camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import edu.polytech.ebudget.R;

public class CameraActivity extends AppCompatActivity implements IPictureActivity {

    private Bitmap picture;
    private PictureFragment pictureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        pictureFragment = (PictureFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPicture);
        if (pictureFragment == null){
            pictureFragment = new PictureFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentPicture, pictureFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization granted", Toast.LENGTH_LONG);
                    toast.show();
                    pictureFragment.takePicture();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization NOT granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CAMERA){
            if(resultCode == RESULT_OK){
                picture = (Bitmap) data.getExtras().get("data");
                pictureFragment.setImage(picture);
            } else if(resultCode == RESULT_CANCELED){
                Toast toast = Toast.makeText(getApplicationContext(), "Picture canceled", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "action failed", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}