package ru.icqparty.moneytracker.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * Created by icqparty on 24.03.2018.
 */

public class RemoveDialog extends DialogFragment {

    private ConfirmationDialogListener listener = null;

    public void setListener(ConfirmationDialogListener listener) {
        this.listener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Удалени")
                .setMessage("Вы уверны?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onPositiveBtnClicked();
                        }
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onNegativeBtnClicked();
                        }
                    }
                })
                .create();

        return dialog;
    }
}