package it.ac.enigma;

import static it.ac.enigma.utility.Utility.isListNullOrEmpty;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.ac.enigma.model.CacciaDto;
import it.ac.enigma.rest.LoginRequestBody;
import it.ac.enigma.rest.ResponseLogin;
import it.ac.enigma.rest.RestManager;
import it.ac.enigma.utility.SharedPreferencesUtils;
import it.ac.enigma.utility.Utility;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = LoginActivity.class.getSimpleName();

    EditText usernameEditText;
    EditText passwordEditText;
    Context _context;
    RestManager restManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        restManager = new RestManager(this);
        _context = LoginActivity.this;
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
    }

    public void login(@NonNull View view){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        restManager.makeLoginRequest(new LoginRequestBody(username, password), loginCallback);
    }

    RestManager.LoginCallback loginCallback = new RestManager.LoginCallback() {
        @Override
        public void onSuccess(ResponseLogin responseLogin) {
            Log.d(TAG, "responseLogin - token: " + responseLogin.getToken());

            SharedPreferencesUtils.saveToken(_context, responseLogin.getToken());

            List<CacciaDto> cacciaDtoList = responseLogin.getCacciaList();

            if(!isListNullOrEmpty(cacciaDtoList)){
                if(cacciaDtoList.size()==1){
                    CacciaDto caccia = cacciaDtoList.get(0);
                    SharedPreferencesUtils.saveCacciaId(_context, caccia.getId());
                    SharedPreferencesUtils.saveCacciaDescrizione(_context, caccia.getDescrizione());

                    openMainActivity();
                } else{
                    List<String> strings = new ArrayList<>();

                    for (CacciaDto c: cacciaDtoList ) {
                        strings.add(c.getDescrizione());
                    }

                    Utility.Dialog.showAlertDialogWithSpinner(_context, onClickOKListener, onClickKOListener, strings);

                }

            } else{

            }

        }
        @Override
        public void onError(VolleyError error) {
            Log.d(TAG, "responseLogin - onError: " + error.getMessage());
        }
    };



    DialogInterface.OnClickListener onClickOKListener = (dialog, which) -> {
    };
    DialogInterface.OnClickListener onClickKOListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Utility.showToast(_context, getString(R.string.toast_on_cancel_dialog));
            dialog.dismiss();
        }
    };

    private void openMainActivity(){
        Intent i = new Intent(_context, MainActivity.class);
        startActivity(i);
    }
}