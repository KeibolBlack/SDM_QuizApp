package com.example.administrador.quizapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;


public class SettingsActivity extends ActionBarActivity {

    private String [] friendArray;

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

    public void saveState(View view) {
        SharedPreferences _prefs = getSharedPreferences("Preferencias", Activity.MODE_PRIVATE);
        SharedPreferences.Editor _prefsEditor = _prefs.edit();

        EditText addFriend = (EditText) findViewById(R.id.PlayerFriendName);
        String addFriendText= addFriend.getText().toString();

        if (TextUtils.isEmpty(addFriend.getText())) return;

        String prefFriends = _prefs.getString("FriendsList", null);

        if (prefFriends==null) prefFriends = addFriendText;
        else prefFriends += "," + addFriendText;

        _prefsEditor.putString("FriendsList", prefFriends);

        _prefsEditor.commit();

        addFriend.setText("", null);
    }

    public void saveState() {
        SharedPreferences _prefs = getSharedPreferences("Preferencias", Activity.MODE_PRIVATE);

        if (_prefs==null) return;
        SharedPreferences.Editor _prefsEditor = _prefs.edit();
        if(_prefsEditor==null) return;

        _prefsEditor.putString("playerName", ((EditText) findViewById(R.id.PlayerName)).getText().toString());
        _prefsEditor.putString("spinner_ChancesNumber", ((Spinner) findViewById(R.id.spinner_ChancesNumber)).getSelectedItem().toString());
        Log.d("[S1:A2]", "Excepcion al convertir \"spinner_ChancesNumber\" " + ((Spinner) findViewById(R.id.spinner_ChancesNumber)).getSelectedItem().toString());

        _prefsEditor.commit();
    }

    private void recoverState(){
        SharedPreferences _prefs = getSharedPreferences("Preferencias", MODE_PRIVATE);
        if (_prefs==null) return;

        ((EditText) findViewById(R.id.PlayerName)).setText(_prefs.getString("playerName", null));
        ((Spinner) findViewById(R.id.spinner_ChancesNumber)).setSelection(Integer.parseInt(_prefs.getString("spinner_ChancesNumber", "3")));
        if (_prefs.getString("FriendsList", null) == null)return;
        friendArray = _prefs.getString("FriendsList", null).split(",");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                friendArray );
        ((ListView) findViewById(R.id.listViewFriends)).setAdapter(arrayAdapter);
    }
}
