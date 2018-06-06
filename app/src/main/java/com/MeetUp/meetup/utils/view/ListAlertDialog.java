package com.MeetUp.meetup.utils.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.MeetUp.meetup.R;

public class ListAlertDialog {

    private AlertDialog dialog;
    private ListView listView;
    private TextView tvTitle;
    private ArrayAdapter adapter;
    private Activity activity;

    public ListAlertDialog(Activity activity,
                           ArrayAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
        buildDialog();
    }

    public void setTitle(String title) {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
    }

    public void setListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }


    private void buildDialog() {
        if (activity.isFinishing()) {
            return;
        }
        dialog = new android.app.AlertDialog.Builder(activity).create();
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_list_view, null);
        dialog.setView(view);
        tvTitle = view.findViewById(R.id.title);
        listView = view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        dialog.setCancelable(false);
    }
}
