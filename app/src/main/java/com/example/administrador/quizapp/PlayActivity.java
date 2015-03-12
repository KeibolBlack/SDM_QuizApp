package com.example.administrador.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.administrador.quizapp.R.layout.play_activity;
import static com.example.administrador.quizapp.R.menu.play_menu;


/**
 * Created by AlbertoCR on 11/03/2015.
 */
public class PlayActivity extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(play_activity);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        leerXML();

    }

    private void leerXML() {
        ArrayList<Question> listaPreguntas = null;
        Question pregunta = null;

        try{
            BufferedReader _fin = new BufferedReader(new InputStreamReader(openFileInput("questions-1.xml")));

            XmlPullParser _parser = XmlPullParserFactory.newInstance().newPullParser();
            _parser.setInput(_fin);

            int tagType = _parser.getEventType();

            while (tagType != XmlPullParser.END_DOCUMENT){
                if (tagType == XmlPullParser.START_TAG){


                }

                _parser.next();
            }

        } catch (Exception e){}

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(play_menu, menu);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch  (item.getItemId())
        {
            case R.id.action_settings:
                // Do  something, like starting an activity, for instance
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case 1:
                return true;
            case 2:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
