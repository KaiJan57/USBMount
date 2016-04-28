package com.kai_jan_57.usbmount;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RootCommand cmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Globals.AppContext = this;
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        try {
            cmd = new RootCommand();
            cmd.exit();
        } catch (Exception e) {
            UI.showNoRootDialog(Globals.AppContext);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cmd != null) {
            cmd.exit();
        }
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.activity_main);
            Preference about = findPreference("about");
            about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(Globals.AppContext, About.class));
                    return false;
                }
            });
        }
    }
}