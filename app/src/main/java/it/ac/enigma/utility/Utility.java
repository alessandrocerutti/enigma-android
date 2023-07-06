package it.ac.enigma.utility;

import static it.ac.enigma.utility.Constants.Activity.PENALITA;
import static it.ac.enigma.utility.Constants.Activity.PUNTEGGI_EXTRA;
import static it.ac.enigma.utility.Constants.Activity.PUNTEGGI_PROVE;
import static it.ac.enigma.utility.Constants.Activity.QRCODE;
import static it.ac.enigma.utility.Constants.EXTRA.IS_PENALITA;
import static it.ac.enigma.utility.Constants.EXTRA.IS_PUNTEGGIO_PROVA;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import it.ac.enigma.R;
import it.ac.enigma.activity.PunteggioActivity;
import it.ac.enigma.activity.QRCodeActivity;

public class Utility {

    public static Boolean isListNullOrEmpty(List list){
        return list == null || list.isEmpty();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hexadecimal representation
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Intent getIntent(Context c, String activity){
        Intent i = new Intent();

        switch(activity){
            case QRCODE:
                i.setClass(c, QRCodeActivity.class);
                break;
            case PUNTEGGI_EXTRA:
                i.putExtra(IS_PUNTEGGIO_PROVA, false);
                i.putExtra(IS_PENALITA, false);
                i.setClass(c, PunteggioActivity.class);
                break;
            case PUNTEGGI_PROVE:
                i.putExtra(IS_PUNTEGGIO_PROVA, true);
                i.putExtra(IS_PENALITA, false);
                i.setClass(c, PunteggioActivity.class);
                break;
            case PENALITA:
                i.putExtra(IS_PUNTEGGIO_PROVA, false);
                i.putExtra(IS_PENALITA, true);
                i.setClass(c, PunteggioActivity.class);
                break;
        }

        return i;
    }

    public static class Time {
        public static Date getTime(){
            return new Date();
        }
    }

    public static class Dialog {
        public static void showAlertDialogWithSpinner(Context c, DialogInterface.OnClickListener onOKClickListener, DialogInterface.OnClickListener onKOClickListener, List<String> options) {
            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            LayoutInflater inflater = LayoutInflater.from(c);
            View dialogView = inflater.inflate(R.layout.dialog_spinner, null);
            builder.setView(dialogView);
            Spinner spinner = dialogView.findViewById(R.id.dialog_spinner);
            // Set up the spinner with data
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, options );
            spinner.setAdapter(spinnerAdapter);
            // Set up button click listeners
            builder.setPositiveButton(R.string.dialog_ok, onOKClickListener);
            builder.setNegativeButton(R.string.dialog_cancel, onKOClickListener);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }




}
