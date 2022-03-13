package com.test.toyotomi_new_era;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.Map;
import java.util.Objects;

public class Utilities
{
    static void popUpDone(Context context) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(R.layout.pop_up_done, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Εντάξει", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        //Create alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    static void popUpHelp(Context context, int xmlFile) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View promptView = layoutInflater.inflate(xmlFile, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Εντάξει", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        //Create alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
