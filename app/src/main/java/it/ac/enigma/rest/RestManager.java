package it.ac.enigma.rest;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RestManager {

    private RequestQueue requestQueue;

    public RestManager(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }



        private static final String BASE_URL = "https://api.example.com/";


        public void makeLoginRequest(String username, String password, final LoginCallback callback) {
            String url = BASE_URL + "login";

            //TODO cambiare con oggetto RequestLogin
            JSONObject requestBody = new JSONObject();
            try {
                requestBody.put("username", username);
                requestBody.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                    (Response.Listener<JSONObject>) response -> {
                        // Gestisci la risposta qui
                        callback.onSuccess(response);
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Gestisci l'errore qui
                            callback.onError(error.getMessage());
                        }
                    });

            requestQueue.add(request);
        }

        // Aggiungi altri metodi per le diverse chiamate REST che devi gestire


    public interface LoginCallback {
        void onSuccess(JSONObject responseLogin);
        void onError(String message);
    }
}
