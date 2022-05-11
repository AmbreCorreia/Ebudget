package edu.polytech.ebudget.camera;

import android.graphics.Bitmap;

public interface IStorageActivity {
    int REQUEST_MEDIA_WRITE = 1001;
    Bitmap getPictureToSave();
}
