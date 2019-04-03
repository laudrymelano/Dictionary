package com.vrjulianti.dictionary.Class;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.vrjulianti.dictionary.R;

public class DictPreference {
    private Context context;
    private SharedPreferences prefs;

    public DictPreference(Context context)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input)
    {
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.app_first_run);
        editor.putBoolean(key, input);
        editor.apply();
    }

    public Boolean getFirstRun()
    {
        String key = context.getResources().getString(R.string.app_first_run);
        return prefs.getBoolean(key, true);
    }
}
