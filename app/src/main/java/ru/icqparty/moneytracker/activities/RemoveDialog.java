package ru.icqparty.moneytracker.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

/**
 * Created by icqparty on 24.03.2018.
 */

public class RemoveDialog extends DialogFragment {
    private static final String TAG = "RemoveDialog";
    private RemoveDialogListener listener = null;

    public void setListener(RemoveDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Удалени")
                .setMessage("Вы уверны?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.i(TAG, "onClick: YES");
                        if (listener != null) {
                            listener.onPositiveBtnClicked();
                        }
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "onClick: NO");
                        if (listener != null) {
                            listener.onNegativeBtnClicked();
                        }
                    }
                })
                .create();

        return dialog;
    }
}