package it.ac.enigma.activity;

import static it.ac.enigma.utility.Constants.EXTRA.IS_PENALITA;
import static it.ac.enigma.utility.Constants.EXTRA.IS_PUNTEGGIO_PROVA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import it.ac.enigma.R;
import it.ac.enigma.adapter.SquadreAdapter;
import it.ac.enigma.model.PunteggioDto;
import it.ac.enigma.model.ProvaDto;
import it.ac.enigma.rest.ResponseSquadre;
import it.ac.enigma.rest.RestManager;
import it.ac.enigma.utility.SharedPreferencesUtils;
import it.ac.enigma.utility.Utility;

public class PunteggioActivity extends AppCompatActivity {

    Context _context;
    RestManager restManager;

    EditText descrizioneEditText;
    EditText punteggioEditText;

    Boolean _isPunteggioProva;
    Boolean _isPenalita;

    private Spinner squadraSpinner;
    private Spinner provaSpinner;
    TextView labelProvaSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punteggio);

        restManager = new RestManager(this);
        _context = this;

        Intent i = getIntent();
        _isPunteggioProva = i.getBooleanExtra(IS_PUNTEGGIO_PROVA, false);
        _isPenalita = i.getBooleanExtra(IS_PENALITA, false);

        squadraSpinner = findViewById(R.id.squadra_spinner);

        provaSpinner = findViewById(R.id.prova_spinner);
        labelProvaSpinner = findViewById(R.id.label_prova_spinner);

        descrizioneEditText = findViewById(R.id.descrizionePunteggioEditText);
        punteggioEditText = findViewById(R.id.punteggioEditText);

        if(!_isPunteggioProva ){
            provaSpinner.setEnabled(false);
            provaSpinner.setVisibility(View.INVISIBLE);
            labelProvaSpinner.setVisibility(View.INVISIBLE);
        } else {
            aggiungiProveSpinner();
        }
        aggiungiSquadreSpinner();

    }

    private void aggiungiProveSpinner() {
        List<ProvaDto> proveDtoList = new ArrayList<ProvaDto>();
        //TODO
        proveDtoList.add(0, new ProvaDto(0L, "SEL", "-- seleziona una prova"));
        squadraSpinner.setAdapter(new SquadreAdapter(_context, proveDtoList));

        squadraSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void aggiungiSquadreSpinner() {
        List<ProvaDto> squadraDtoList = SharedPreferencesUtils.getSquadre(_context);
        squadraDtoList.add(0, new ProvaDto(0L, "SEL", "-- seleziona una squadra"));
        squadraSpinner.setAdapter(new SquadreAdapter(_context, squadraDtoList));
    }

    public void salva(View v){
        ProvaDto squadraDto = (ProvaDto) squadraSpinner.getSelectedItem();;
        if(squadraDto == null || squadraDto.getId().equals(0L)){
            Utility.showToast(_context, getString(R.string.scegli_squadra));
        }

        String descrizione = descrizioneEditText.getText().toString();
        String punteggio = punteggioEditText.getText().toString();

        String tipologia = _isPunteggioProva?"PROVA":"EXTRA";
        tipologia = _isPenalita?"PENALITA":tipologia;

        if(descrizione.trim().length()==0){
            Utility.showToast(_context, "Inserire una descrizione accurata del punteggio");
        }

        if(punteggio.trim().length()==0){
            Utility.showToast(_context, "Inserire un numero valido");
        }

        Integer punteggioInt = (_isPenalita?-1:1) * Integer.parseInt(punteggio);

        PunteggioDto punteggioDto = new PunteggioDto(descrizione, punteggioInt, tipologia, squadraDto.getId(), SharedPreferencesUtils.getCacciaId(_context));

        restManager.savePunteggioExtra(punteggioDto, savePunteggioExtraCallback);
    }

    RestManager.SavePunteggioExtraCallback savePunteggioExtraCallback = new RestManager.SavePunteggioExtraCallback() {
        @Override
        public void onSuccess(ResponseSquadre responseSquadre) {
            Utility.showToast(_context, "Punteggio salvato correttamente!");
            finish();
        }

        @Override
        public void onError(VolleyError error) {

        }
    };
}
