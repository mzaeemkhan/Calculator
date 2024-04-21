package com.atrule.fcalculator.shared_preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.atrule.fcalculator.model.MyCalculationClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class PreferenceManager {

    private static final String LIST_KEY = "list_key";

    /* Writing Data into SharedPreference */
    // region
    public static void writeListInPreference(Context context, ArrayList<MyCalculationClass> list)
    {
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();

    }
    // endregion

    /* Reading Data from SharedPreference */
    // region
    public static ArrayList<MyCalculationClass> readListFromPreference(Context context)
    {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        String jsonString = sharedPreferences.getString(LIST_KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<MyCalculationClass>>(){}.getType();

        return gson.fromJson(jsonString, type);
    }
    // endregion

}
