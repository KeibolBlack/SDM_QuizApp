package com.example.administrador.quizapp;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import android.content.res.XmlResourceParser;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.example.administrador.quizapp.R.layout.play_activity;
import static com.example.administrador.quizapp.R.menu.play_menu;
import static com.example.administrador.quizapp.R.xml.questions;


/**
 * Created by AlbertoCR on 11/03/2015.
 */
public class PlayActivity extends ActionBarActivity{
    private final int QuestionValue [] = {100,200,300,500,1000,2000,4000,8000,16000,32000,64000,125000,250000,500000,1000000};
    private ArrayList<Question> listaPreguntas = new ArrayList<Question>();
    private Question pregunta = null;
    private int numeroPregunta = 0;
    private int score = 0;

    Button b1;
    Button b2;
    Button b3;
    Button b4;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(play_activity);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            leerXML();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("[S1:A2]", "Excepcion al leer el archivo XML: " + e.toString());
        }
        try {
            cargarPregunta(numeroPregunta);
        } catch (Exception e) {
                Log.d("[S1:A2]", "Excepcion al ejecutar primer \"Cargar Pregunta\" " + numeroPregunta + ": " + e.toString());
        }

    }

    public void ComprobarRespuestas1 (View v) {
        if ("1".equals(listaPreguntas.get(numeroPregunta).getRight())) {
            score = QuestionValue[numeroPregunta];
            try {
                cargarPregunta(++numeroPregunta);
            } catch (Exception e) {
                Log.d("[S1:A2]", "Excepcion al ejecutar siguiente \"Cargar Pregunta\" " + numeroPregunta + ": " + e.toString());
            }
        } //else startActivity(new Intent(getParent(), QuizApp.class));
    }

    public void ComprobarRespuestas2 (View v) {
        if ("2".equals(listaPreguntas.get(numeroPregunta).getRight())) {
            score = QuestionValue[numeroPregunta];
            try {
                cargarPregunta(++numeroPregunta);
            } catch (Exception e) {
                Log.d("[S1:A2]", "Excepcion al ejecutar siguiente \"Cargar Pregunta\" " + numeroPregunta + ": " + e.toString());
            }
        } //else startActivity(new Intent(getParent(), QuizApp.class));
    }

    public void ComprobarRespuestas3 (View v) {
        if ("3".equals(listaPreguntas.get(numeroPregunta).getRight())) {
            score = QuestionValue[numeroPregunta];
            try {
                cargarPregunta(numeroPregunta++);
            } catch (Exception e) {
                Log.d("[S1:A2]", "Excepcion al ejecutar siguiente \"Cargar Pregunta\" " + numeroPregunta + ": " + e.toString());
            }
        } //else startActivity(new Intent(getParent(), QuizApp.class));
    }

    public void ComprobarRespuestas4 (View v) {
        if ("4".equals(listaPreguntas.get(numeroPregunta).getRight())) {
            score = QuestionValue[numeroPregunta];
            try {
                cargarPregunta(++numeroPregunta);
            } catch (Exception e) {
                Log.d("[S1:A2]", "Excepcion al ejecutar siguiente \"Cargar Pregunta\" " + numeroPregunta + ": " + e.toString());
            }
        } //else startActivity(new Intent(getParent(), QuizApp.class));
    }

    private void cargarPregunta(int numeroPregunta) throws Exception {
        pregunta = listaPreguntas.get(numeroPregunta);

        ((TextView)findViewById(R.id.question_number_edit)).setText(String.valueOf(numeroPregunta+1));
        ((TextView)findViewById(R.id.play_for_edit)).setText(String.valueOf(QuestionValue[numeroPregunta]));
        ((TextView)findViewById(R.id.question)).setText(pregunta.getText());
        ((TextView)findViewById(R.id.answer1)).setText(pregunta.getAnswer1());
        ((TextView)findViewById(R.id.answer2)).setText(pregunta.getAnswer2());
        ((TextView)findViewById(R.id.answer3)).setText(pregunta.getAnswer3());
        ((TextView)findViewById(R.id.answer4)).setText(pregunta.getAnswer4());
    }

    private void leerXML()
            throws XmlPullParserException, IOException
    {
        XmlPullParser _parser = getResources().getXml(R.xml.questions);

        String prueba = _parser.getAttributeValue(null, "number");

        //_parser.next();

        while (_parser.getEventType() != XmlPullParser.END_DOCUMENT){
            if (_parser.getEventType() == XmlPullParser.START_TAG && _parser.getName().equals("question")){
                pregunta = new Question();
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
                listaPreguntas.add(pregunta);
            }

            _parser.next();
        }
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
        int ans = 0;

        //noinspection SimplifiableIfStatement
        switch  (id)
        {
            case R.id.button_answer1: ans = 1;
                break;
            case R.id.button_answer2: ans = 2;
                break;
            case R.id.button_answer3: ans = 3;
                break;
            case R.id.button_answer4: ans = 4;
                break;
        }
        if (ans == Integer.getInteger(listaPreguntas.get(numeroPregunta).getRight(), 0)){
            score = QuestionValue[numeroPregunta];
            try {
                cargarPregunta(numeroPregunta++);
            } catch (Exception e) {
                Log.d("[S1:A2]", "Excepcion al ejecutar siguiente \"Cargar Pregunta\" " + numeroPregunta + ": " + e.toString());            }
        }

        return super.onOptionsItemSelected(item);
    }
}
