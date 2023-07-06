package it.ac.enigma.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import it.ac.enigma.model.ProvaDto;

public class SharedPreferencesUtils {
    private static final String PREFERENCES_NAME = "UserPreferences";
    private static final String KEY_CACCIA_ID = "CacciaIdKey";
    public static final String TOKEN_KEY = "TokenKey";
    public static final String CACCIA_DESCR_KEY = "CacciaDescrizione";
    public static final String SQUADRE_KEY = "Squadre";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public static String getToken(Context context) {
        return getSharedPreferences(context).getString(TOKEN_KEY, null);
    }

    public static void clearToken(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(TOKEN_KEY);
        editor.apply();
    }

    public static void saveCacciaId(Context context, Long cacciaId) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(KEY_CACCIA_ID, cacciaId);
        editor.apply();
    }

    public static Long getCacciaId(Context context) {
        return getSharedPreferences(context).getLong(KEY_CACCIA_ID, -1);
    }

    public static void clearCacciaId(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_CACCIA_ID);
        editor.apply();
    }

    public static void saveCacciaDescrizione(Context context, String cacciaDescrizione) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(CACCIA_DESCR_KEY, cacciaDescrizione);
        editor.apply();
    }

    public static String getCacciaDescrizione(Context context) {
        return getSharedPreferences(context).getString(CACCIA_DESCR_KEY, null);
    }

    public static void clearCacciaDescrizione(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(CACCIA_DESCR_KEY);
        editor.apply();
    }
    public static void saveSquadre(Context context, String squadre) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(SQUADRE_KEY, squadre);
        editor.apply();
    }

    public static List<ProvaDto> getSquadre(Context context) {
        String squadreJson =  getSharedPreferences(context).getString(SQUADRE_KEY, null);
        List<ProvaDto>squadraDtoList = new ArrayList<>();
        Type listType = new TypeToken<List<ProvaDto>>() {}.getType();
        if(squadreJson!=null){
            squadraDtoList.addAll(new Gson().fromJson(squadreJson, listType));
        }
        return squadraDtoList;
    }

}
