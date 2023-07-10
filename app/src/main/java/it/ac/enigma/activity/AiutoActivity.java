package it.ac.enigma.activity;

import static it.ac.enigma.utility.Constants.EXTRA.IS_PENALITA;
import static it.ac.enigma.utility.Constants.EXTRA.IS_PUNTEGGIO_PROVA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import it.ac.enigma.R;
import it.ac.enigma.adapter.SquadreAdapter;
import it.ac.enigma.model.ProvaDto;
import it.ac.enigma.rest.RestManager;
import it.ac.enigma.utility.SharedPreferencesUtils;

public class AiutoActivity extends AppCompatActivity {

    Context _context;
    RestManager restManager;

    EditText descrizioneEditText;

    private Spinner squadraSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punteggio);

        restManager = new RestManager(this);
        _context = this;

        squadraSpinner = findViewById(R.id.squadra_spinner);

        descrizioneEditText = findViewById(R.id.descrizionePunteggioEditText);

        aggiungiSquadreSpinner();

    }

    private void aggiungiSquadreSpinner() {
        List<ProvaDto> squadraDtoList = SharedPreferencesUtils.getSquadre(_context);
        squadraDtoList.add(0, new ProvaDto(0L, "SEL", "-- seleziona una squadra"));
        squadraSpinner.setAdapter(new SquadreAdapter(_context, squadraDtoList));
    }


}
