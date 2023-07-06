package it.ac.enigma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.List;

import it.ac.enigma.model.ProvaDto;
import it.ac.enigma.rest.ResponseSquadre;
import it.ac.enigma.rest.RestManager;
import it.ac.enigma.utility.Constants;
import it.ac.enigma.utility.SharedPreferencesUtils;
import it.ac.enigma.utility.Utility;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    Context _context;
    RestManager restManager;

    Gson _Gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _Gson = new Gson();
        restManager = new RestManager(this);
        _context = this;

        //chiamata per salvarsi le squadre

        restManager.getSquadreByIdCaccia(squadreByIdCacciaCallback);

    }


    RestManager.SquadreByIdCacciaCallback squadreByIdCacciaCallback = new RestManager.SquadreByIdCacciaCallback() {
        @Override
        public void onSuccess(ResponseSquadre responseSquadre) {

            List<ProvaDto> squadraDtoList = responseSquadre.getSquadraList();
            if(Utility.isListNullOrEmpty(squadraDtoList)){
                Utility.showToast(_context, getString(R.string.toast_error));
            } else{
                //salvo le squadre come lista di json
                String squadreJson = _Gson.toJson(squadraDtoList);
                SharedPreferencesUtils.saveSquadre(_context, squadreJson);
            }
        }

        @Override
        public void onError(VolleyError error) {
            Utility.showToast(_context, error.getMessage());
        }
    };



    public void apriQRCode(View v){
        openNextActivity(Constants.Activity.QRCODE);
    }
    public void apriAltriPunteggi(View v){
        openNextActivity(Constants.Activity.PUNTEGGI_EXTRA);
    }

    public void apriPenalita(View v){
        openNextActivity(Constants.Activity.PENALITA);
    }
    public void apriPunteggiProve(View v){
        openNextActivity(Constants.Activity.PUNTEGGI_PROVE);
    }

    private void openNextActivity(String activity){
        startActivity(Utility.getIntent(MainActivity.this, activity));
    }

}