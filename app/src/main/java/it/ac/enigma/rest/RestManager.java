package it.ac.enigma.rest;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import it.ac.enigma.model.PunteggioDto;
import it.ac.enigma.utility.Constants;
import it.ac.enigma.utility.SharedPreferencesUtils;

public class RestManager {
    private RequestQueue requestQueue;
    Map<String, String> headers;
    Gson gson;

    Context _context;
    private static final String BASE_URL = "http://192.168.1.11:3000/rest";

    public RestManager(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        gson = new Gson();
        _context = context;
        createHeaders();
    }
    public void makeLoginRequest(LoginRequestBody loginRequestBody, final LoginCallback callback) {

        String url = getFinalUrl(Constants.HTTP_PATH.LOGIN);

        String requestBody = gson.toJson(loginRequestBody);

        // Gestisci la risposta qui
        // Gestisci l'errore qui
        GsonRequest<ResponseLogin> request = new GsonRequest<>(Request.Method.POST, url, ResponseLogin.class,
                true,
                headers,
                requestBody,
                null,
                callback::onSuccess,
                callback::onError);

        requestQueue.add(request);
    }

    @NonNull
    private static String getFinalUrl(String path) {
        return BASE_URL.concat(path);
    }


    public void makeQrCodeStepRequest(String QrCode,Long idSquadra, final QRCodeStepCallback callback) {

        String url = getFinalUrl(Constants.HTTP_PATH.PATH_QRCODE);

        String token = getToken();

        QrCodeStepRequestBody qrCodeStepRequestBody = new QrCodeStepRequestBody(QrCode, idSquadra);
        String qrCodeStepRequestBodyString = gson.toJson(qrCodeStepRequestBody);

        // Gestisci la risposta qui
        // Gestisci l'errore qui
        GsonRequest<ResponseQRCodeStep> request = new GsonRequest<>(Request.Method.POST, url, ResponseQRCodeStep.class,
                true,
                headers,
                qrCodeStepRequestBodyString,
                token,
                callback::onSuccess,
                callback::onError);

        requestQueue.add(request);
    }

    public void getSquadreByIdCaccia(SquadreByIdCacciaCallback callback) {

        String url = getFinalUrl(String.format(
                Constants.HTTP_PATH.SQUADRE_IDCACCIA, SharedPreferencesUtils.getCacciaId(
                        _context
                )));

        String token = getToken();

        // Gestisci la risposta qui
        // Gestisci l'errore qui
        GsonRequest<ResponseSquadre> request = new GsonRequest<>(Request.Method.GET, url, ResponseSquadre.class,
                true,
                headers,
                null,
                token,
                callback::onSuccess,
                callback::onError);

        requestQueue.add(request);


    }

    private String getToken() {
        return SharedPreferencesUtils.getToken(_context);
    }


    private void createHeaders() {
        headers = new HashMap<>();
        headers.put(Constants.Header.CONTENT_TYPE, Constants.Header.Value.CONTENT_TYPE_APPLICATION_JSON);
        headers.put(Constants.Header.ACCEPT, Constants.Header.Value.CONTENT_TYPE_APPLICATION_JSON);
        headers.put(Constants.Header.ACCEPT_ENCODING, Constants.Header.Value.ACCEPT_ENCODING_VALUE);
    }

    public void savePunteggioExtra(PunteggioDto punteggioDto, SavePunteggioExtraCallback callback) {
        String url = getFinalUrl(Constants.HTTP_PATH.SAVE_PUNTEGGIO_EXTRA);

        String token = getToken();

        String punteggioDtoString = gson.toJson(punteggioDto);

        // Gestisci la risposta qui
        // Gestisci l'errore qui
        GsonRequest<ResponseSquadre> request = new GsonRequest<>(Request.Method.POST, url, ResponseSquadre.class,
                true,
                headers,
                punteggioDtoString,
                token,
                callback::onSuccess,
                callback::onError);

        requestQueue.add(request);

    }


    // Aggiungi altri metodi per le diverse chiamate REST che devi gestire
    public interface LoginCallback {
        void onSuccess(ResponseLogin responseLogin);
        void onError(VolleyError error);
    }

    public interface QRCodeStepCallback {
        void onSuccess(ResponseQRCodeStep responseQRCodeStep);
        void onError(VolleyError error);
    }

    public interface SquadreByIdCacciaCallback {
        void onSuccess(ResponseSquadre responseSquadre);
        void onError(VolleyError error);
    }

    public interface SavePunteggioExtraCallback {
        void onSuccess(ResponseSquadre responseSquadre);
        void onError(VolleyError error);
    }


}
