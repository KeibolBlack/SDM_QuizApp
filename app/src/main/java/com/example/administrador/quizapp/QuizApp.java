package com.example.administrador.quizapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class QuizApp extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_app);

        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setTitle(R.string.acBar_Title); //Establecer titulo
        actionBar.setSubtitle(R.string.acBar_Subtitle); //Establecer Subtitulo
    }



    public void playButtonClick(View v) {
        startActivity(new Intent(this, PlayActivity.class));
        Log.d("[S1:A2]", "Click en el Boton Play");
    }
    public void scoresButtonClick(View v) {
        startActivity(new Intent(this, ScoresActivity.class));
        Log.d("[S1:A3]", "Click en el Boton Scores");
    }
    public void settingsButtonClick(View v) {
        startActivity(new Intent(this, SettingsActivity.class));
        Log.d("[S1:A3]", "Click en el Boton Settings");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_app, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (item.getItemId())
        {
            case R.id.action_settings:
                // Do  something, like starting an activity, for instance
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case  R.id.action_credits:
                // Do another thing
                startActivity(new Intent(this, CreditsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
