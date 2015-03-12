package com.example.administrador.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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

    ArrayList<Question> listaPreguntas = new ArrayList<Question>();
    Question pregunta = null;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(play_activity);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        leerXML();
        cargarPregunta();

    }

    private void cargarPregunta() {
        pregunta = listaPreguntas.get(0);
        ((EditText)findViewById(R.id.question)).setText(pregunta.text);
        ((EditText)findViewById(R.id.answer1)).setText(pregunta.answer1);
    }

    private void leerXML() {
        try{
            BufferedReader _fin = new BufferedReader(new InputStreamReader(openFileInput("questions-1.xml")));

            XmlPullParser _parser = XmlPullParserFactory.newInstance().newPullParser();
            _parser.setInput(_fin);

            int tagType = _parser.getEventType();

            while (tagType != XmlPullParser.END_DOCUMENT){
                if (tagType == XmlPullParser.START_TAG){
                    pregunta.setNumber(_parser.getAttributeValue(null, "number"));
                    pregunta.setText(_parser.getAttributeValue(null, "text"));
                    pregunta.setAnswer1(_parser.getAttributeValue(null, "answer1"));
                    pregunta.setAnswer2(_parser.getAttributeValue(null, "answer2"));
                    pregunta.setAnswer3(_parser.getAttributeValue(null, "answer3"));
                    pregunta.setAnswer4(_parser.getAttributeValue(null, "answer4"));
                    pregunta.setRight(_parser.getAttributeValue(null, "right"));
                    pregunta.setAudience(_parser.getAttributeValue(null, "audience"));
                    pregunta.setPhone(_parser.getAttributeValue(null, "phone"));
                    pregunta.setFifty1(_parser.getAttributeValue(null, "fifty1"));
                    pregunta.setFifty2(_parser.getAttributeValue(null, "fifty2"));
                }

                listaPreguntas.add(pregunta);
                _parser.next();
            }

            _fin.close();
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
