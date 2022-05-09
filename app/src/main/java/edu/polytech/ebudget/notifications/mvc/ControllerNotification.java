package edu.polytech.ebudget.notifications.mvc;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import edu.polytech.ebudget.datamodels.Notification;

public class ControllerNotification implements INotificationAdapter {
    ModelNotification modelNotification;
    ViewNotification viewNotification;
    Boolean modelChanged = false;


    @Override
    public void onClickNotification(Context context, Notification notification, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage("La supression de : " + notification.getCategory() + ", sera définitive. Veuillez confirmer")
                .setNeutralButton("Annuler", null)
                .setNeutralButton("Supprimer", (dialogInterface, i) -> {
                    System.out.println("click sur supprimer");
                    modelNotification.deleteNotification(notification);


                });
        builder.show();
    }
}
