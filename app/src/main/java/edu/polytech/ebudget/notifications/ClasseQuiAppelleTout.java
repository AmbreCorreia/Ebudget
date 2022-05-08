package edu.polytech.ebudget.notifications;

import android.app.Fragment;
import android.view.ViewGroup;

import edu.polytech.ebudget.notifications.mvc.NotificationController;
import edu.polytech.ebudget.notifications.mvc.NotificationModel;
import edu.polytech.ebudget.notifications.mvc.NotificationView;

public class ClasseQuiAppelleTout extends Fragment {
    public static <T extends ViewGroup> void onViewCreated(T layout) {
        NotificationView view = new NotificationView(layout.getContext(), layout);
        NotificationModel model = new NotificationModel(null);

        model.addObserver(view);

        NotificationController controller = new NotificationController(view, model);
        model.setController(controller);
        view.setListener();
    }
}
