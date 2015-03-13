package com.example.administrador.quizapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


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

    public void saveState(View view) {
        SharedPreferences _prefs = getSharedPreferences("MisPreferencias", Activity.MODE_PRIVATE);
        SharedPreferences.Editor _prefsEditor = _prefs.edit();

        EditText addFriend = (EditText) findViewById(R.id.PlayerFriendName);
        String prefFriends = _prefs.getString("FriendsList", null);
        if (prefFriends==null) prefFriends = addFriend.getText().toString();
        else prefFriends += "\n " + addFriend.getText().toString();

        _prefsEditor.putString("FriendsList", prefFriends);

        _prefsEditor.commit();

        addFriend.setText("", null);

        ((TextView) findViewById(R.id.textViewFriends)).setText(_prefs.getString("FriendsList",null));
    }

    public void saveState() {
        SharedPreferences _prefs = getSharedPreferences("MisPreferencias", Activity.MODE_PRIVATE);

        if (_prefs==null) return;
        SharedPreferences.Editor _prefsEditor = _prefs.edit();
        if(_prefsEditor==null) return;

        _prefsEditor.putString("playerName", ((EditText) findViewById(R.id.PlayerName)).getText().toString());
        _prefsEditor.putString("chanceNumber", ((EditText) findViewById(R.id.ChancesNumber)).getText().toString());

        _prefsEditor.commit();
    }

    private void recoverState(){
        SharedPreferences _prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        if (_prefs==null) return;

        ((EditText) findViewById(R.id.PlayerName)).setText(_prefs.getString("playerName", null));
        ((EditText) findViewById(R.id.ChancesNumber)).setText(_prefs.getString("chanceNumber","3"));
        ((TextView) findViewById(R.id.textViewFriends)).setText(_prefs.getString("FriendsList",null));
    }
}
