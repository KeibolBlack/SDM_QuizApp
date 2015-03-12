package com.example.administrador.quizapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        recoverState();
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void saveState() {
        SharedPreferences _prefs = getSharedPreferences("MisPreferencias", Activity.MODE_PRIVATE);

        if (_prefs==null) return;
        SharedPreferences.Editor _prefsEditor = _prefs.edit();
        if(_prefsEditor==null) return;

        _prefsEditor.putString("NombreJugador", ((EditText) findViewById(R.id.NombreJugador)).getText().toString());
        _prefsEditor.putString("NumeroOportunidades", ((EditText) findViewById(R.id.NumeroOportunidades)).getText().toString());
        _prefsEditor.putString("NombreJugadorAmigo", ((EditText) findViewById(R.id.NombreJugadorAmigo)).getText().toString());

        _prefsEditor.commit();
    }

    private void recoverState(){
        SharedPreferences _prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        if (_prefs==null) return;

        ((EditText) findViewById(R.id.NombreJugador)).setText(_prefs.getString("NombreJugador","Empty"));
        ((EditText) findViewById(R.id.NumeroOportunidades)).setText(_prefs.getString("NumeroOportunidades","Empty"));
        ((EditText) findViewById(R.id.NombreJugadorAmigo)).setText(_prefs.getString("NombreJugadorAmigo","Empty"));
    }
}
