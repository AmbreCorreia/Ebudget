package edu.polytech.ebudget.notifications;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Observable;
import java.util.Observer;

import edu.polytech.ebudget.R;

public class NotificationView implements Observer {

    private final String TAG = "notifications " + getClass().getSimpleName();
    private  ViewAdapter adapter;
    private ViewGroup layout;
    private boolean modelCreated = false;
    IViewClick controller;

    public <T extends ViewGroup> NotificationView(Context context, T layout) {
        adapter = new ViewAdapter(context, this); //carrefull, model is null !
        this.layout = layout;
        Log.d(TAG, "View is created" );
    }

    public ViewGroup getLayout() {
        return layout;
    }
    public void setListener(IViewClick controller) {
        this.controller = controller;
    }
    public void onClickNotification(NotificationModel model, int position){
        controller.onClickNotification(model, position);
    }

    @Override
    public void update(Observable observable, Object o) {
        NotificationModel model = (NotificationModel) observable;
        if (!modelCreated) {        //fist time only
            adapter.updateModel(model);
            ListView listView = ((ListView)layout.findViewById(R.id.listView));
            listView.setAdapter(adapter);
            modelCreated = true;
        }
        else {
            adapter.refresh(model);
        }
        Log.d(TAG, "View update with ==> " + model);
    }
}
