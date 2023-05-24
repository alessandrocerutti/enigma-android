package it.ac.enigma;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import it.ac.enigma.rest.RestManager;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;

    EditText passwordEditText;

    RestManager restManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        restManager = new RestManager(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

    }


    private void login(@NonNull View view){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        restManager.makeLoginRequest(username, password, new RestManager.LoginCallback() {
            @Override
            public void onSuccess(JSONObject responseLogin) {

            }

            @Override
            public void onError(String message) {
                Toast.makeText(LoginActivity.this, getString(R.string.toast_error_login), Toast.LENGTH_SHORT).show();
            }
        });

    }
}