package edu.polytech.ebudget.notifications;

import android.app.Activity;
import android.app.Application;
import android.view.View;
import android.view.ViewGroup;

public class ClasseQuiAppelleTout extends Application {

    public <T extends ViewGroup> void onViewCreated(T layout) {
        NotificationView view = new NotificationView(getApplicationContext(), layout);
        NotificationModel model = new NotificationModel(null);

        model.addObserver(view);

        NotificationController controller = new NotificationController(view, model);
        model.setController(controller);
        view.setListener(controller);
    }
}
