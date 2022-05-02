package edu.polytech.ebudget.notifications;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import edu.polytech.ebudget.R;

public class NotificationController implements IViewClick {

    private final String TAG = "notifications " + getClass().getSimpleName();
    private NotificationView view;
    private NotificationModel model;

    public NotificationController(NotificationView view, NotificationModel model){
        Log.d(TAG, "Controller is created" );
        this.view = view;
        this.model = model;

        ArrayList<String> spinnerArray = new ArrayList<String>();
        Spinner spinnerTri = ((Spinner)view.getLayout()).findViewById(R.id.spinnerTri);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(view.getLayout().getContext(), android.R.layout.simple_spinner_item,spinnerArray);
        spinnerArrayAdapter.add("Date");
        spinnerArrayAdapter.add("Catégorie");
        spinnerTri.setAdapter(spinnerArrayAdapter);

    }


    @Override
    public void onClickNotification(NotificationModel notification, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getLayout().getContext())
                .setMessage("La supression de : " + notification.category + ", sera définitive. Veuillez confirmer")
                .setNeutralButton("Annuler", null)
                .setNeutralButton("Supprimer", (dialogInterface, i) -> {
                    System.out.println("click sur supprimer");
                    notification.deleteFromDatabase();
                });
        builder.show();
    }
}
