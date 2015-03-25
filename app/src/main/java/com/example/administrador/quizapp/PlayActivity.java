package com.example.administrador.quizapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.administrador.quizapp.R.layout.play_activity;
import static com.example.administrador.quizapp.R.menu.play_menu;


/**
 * Created by AlbertoCR on 11/03/2015.
 */
public class PlayActivity extends ActionBarActivity{
    private final int questionValue[] = {100,200,300,500,1000,2000,4000,8000,16000,32000,64000,125000,250000,500000,1000000};
    private ArrayList<Question> listaPreguntas = new ArrayList<Question>();
    private Question pregunta = null;
    private int numeroPregunta = 0;
    private int score = 0;
    private  int hint;
    private Button [] botonesRespuesta;
    private Menu menu;
    private SQLiteDatabase sc;
    private String nombreJugador;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(play_activity);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        sc = this.openOrCreateDatabase("scores", MODE_PRIVATE, null);
        sc.beginTransaction();
        try {
            sc.execSQL("create table scores ("
                    + "id integer PRIMARY KEY autoincrement,"
                    + "name text,"
                    + "score text)");
            sc.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.d("[S1:A2]", "Excepcion \"SQLiteException\" al crear la tabla scores en Play activity: " + e.toString());
        } finally {
            sc.endTransaction();
        }

        Cursor qr = sc.rawQuery("select count(*) from sqlite_master where type = 'table'", null);
        if (qr.getCount()==0) {
            sc.execSQL("create table scores ("
            + "id integer PRYMARY KEY autoincrement,"
            + "name text,"
            + "score text)");

        }
        botonesRespuesta = new Button []{
                (Button) findViewById(R.id.button_answer1),
                (Button) findViewById(R.id.button_answer2),
                (Button) findViewById(R.id.button_answer3),
                (Button) findViewById(R.id.button_answer4)};


        recoverState();

        try {
            leerXML();
        } catch (XmlPullParserException e) {
            Log.d("[S1:A2]", "Excepcion \"XmlPullParserException\" al leer el archivo XML: " + e.toString());
        } catch (IOException e) {
            Log.d("[S1:A2]", "Excepcion \"IOException\" al leer el archivo XML: " + e.toString());
        }
        try {
            cargarPregunta(numeroPregunta);
        } catch (Exception e) {
                Log.d("[S1:A2]", "Excepcion al ejecutar primer \"Cargar Pregunta\" " + numeroPregunta + ": " + e.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveState();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();

        saveState();

    }

    private void ComprobarRespuesta (String respuesta){
        if (respuesta.equals(listaPreguntas.get(numeroPregunta).getRight())) {
            score = questionValue[numeroPregunta];
            if (numeroPregunta == 15) {
                GuardarPuntuacion();
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.dialog_title_win))
                        .setMessage(getString(R.string.dialog_message_win))
                        .setPositiveButton(getString(R.string.dialog_button_win), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                resetBotones();
                                resetComodines();
                                saveState();
                                finish();
                            }
                        })
                        .show();
            } else {
                try {
                    cargarPregunta(++numeroPregunta);
                    resetBotones();
                } catch (Exception e) {
                    Log.d("[S1:A2]", "Excepcion al ejecutar siguiente \"Cargar Pregunta\" " + numeroPregunta + ": " + e.toString());
                }
            }
        }

        else {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_title_wrong_answer))
                    .setMessage(getString(R.string.dialog_message_wrong_answer))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            GuardarPuntuacion();
                            numeroPregunta = 0;
                            try {
                                cargarPregunta(numeroPregunta);
                            } catch (Exception e) {
                                Log.d("[S1:A2]", "Excepcion al reiniciar \"Cargar Pregunta\" " + numeroPregunta + ": " + e.toString());
                            }
                            resetBotones();
                            resetComodines();
                            saveState();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            GuardarPuntuacion();
                            numeroPregunta = 0;
                            resetComodines();
                            saveState();
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void GuardarPuntuacion() {
        score = questionValue[numeroPregunta];
        sc.beginTransaction();
        try {
            sc.execSQL("insert into scores"
                    + "(name, score)"
                    + "values ('" + nombreJugador + "', '" + score + "')");
            sc.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.d("[S1:A2]", "Excepcion \"SQLiteException\" al crear la tabla scores en Play activity: " + e.toString());
        } finally {
            sc.endTransaction();
        }
    }

    private void resetBotones() {
        for (Button b : botonesRespuesta) {
            b.setBackgroundColor(getResources().getColor(android.R.color.background_dark));
        }
    }

    private void resetComodines(){
        switch (hint) {
            case 0:
                menu.findItem(R.id.action_50).setEnabled(false);
                menu.findItem(R.id.action_people).setEnabled(false);
                menu.findItem(R.id.action_call).setEnabled(false);
                menu.findItem(R.id.action_50).setVisible(false);
                menu.findItem(R.id.action_people).setVisible(false);
                menu.findItem(R.id.action_call).setVisible(false);
                return;
            case 1:
                menu.findItem(R.id.action_50).setEnabled(true);
                menu.findItem(R.id.action_people).setEnabled(false);
                menu.findItem(R.id.action_call).setEnabled(false);
                menu.findItem(R.id.action_50).setVisible(true);
                menu.findItem(R.id.action_people).setVisible(false);
                menu.findItem(R.id.action_call).setVisible(false);
                return;
            case 2:
                menu.findItem(R.id.action_50).setEnabled(true);
                menu.findItem(R.id.action_people).setEnabled(false);
                menu.findItem(R.id.action_call).setEnabled(true);
                menu.findItem(R.id.action_50).setVisible(true);
                menu.findItem(R.id.action_people).setVisible(false);
                menu.findItem(R.id.action_call).setVisible(true);
                return;
            case 3:
                menu.findItem(R.id.action_50).setEnabled(true);
                menu.findItem(R.id.action_people).setEnabled(true);
                menu.findItem(R.id.action_call).setEnabled(true);
                menu.findItem(R.id.action_50).setVisible(true);
                menu.findItem(R.id.action_people).setVisible(true);
                menu.findItem(R.id.action_call).setVisible(true);
                return;
        }
    }

    public void ComprobarRespuestas1 (View v) {
        ComprobarRespuesta ("1");
    }

    public void ComprobarRespuestas2 (View v) {
        ComprobarRespuesta ("2");
    }

    public void ComprobarRespuestas3 (View v) {
        ComprobarRespuesta ("3");
    }

    public void ComprobarRespuestas4 (View v) {
        ComprobarRespuesta ("4");
    }

    private void cargarPregunta(int numeroPregunta) throws Exception {
        pregunta = listaPreguntas.get(numeroPregunta);

        ((TextView)findViewById(R.id.question_number_edit)).setText(String.valueOf(numeroPregunta+1));
        ((TextView)findViewById(R.id.play_for_edit)).setText(String.valueOf(questionValue[numeroPregunta]));
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
        this.menu = menu;
        if (numeroPregunta == 0){
            resetComodines();
            resetBotones();
        }
        else {
            SharedPreferences _prefs = getSharedPreferences("Preferencias", MODE_PRIVATE);
            if (_prefs==null) return super.onCreateOptionsMenu(menu);
            menu.findItem(R.id.action_50).setEnabled(_prefs.getBoolean("hint_50", true));
            menu.findItem(R.id.action_people).setEnabled(_prefs.getBoolean("hint_audience", true));
            menu.findItem(R.id.action_call).setEnabled(_prefs.getBoolean("hint_phone", true));
            menu.findItem(R.id.action_50).setVisible(_prefs.getBoolean("hint_50", true));
            menu.findItem(R.id.action_people).setVisible(_prefs.getBoolean("hint_audience", true));
            menu.findItem(R.id.action_call).setVisible(_prefs.getBoolean("hint_phone", true));
        }

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
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_50:
                botonesRespuesta[Integer.parseInt(pregunta.getFifty1())-1].setActivated(false);
                botonesRespuesta[Integer.parseInt(pregunta.getFifty2())-1].setActivated(false);
                botonesRespuesta[Integer.parseInt(pregunta.getFifty1())-1].setBackgroundColor(getResources().getColor(android.R.color.background_light));
                botonesRespuesta[Integer.parseInt(pregunta.getFifty2())-1].setBackgroundColor(getResources().getColor(android.R.color.background_light));
                menu.findItem(R.id.action_50).setEnabled(false);
                menu.findItem(R.id.action_50).setVisible(false);
                return true;
            case R.id.action_people:
                botonesRespuesta[Integer.parseInt(pregunta.getAudience())-1].setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                menu.findItem(R.id.action_people).setEnabled(false);
                menu.findItem(R.id.action_people).setVisible(false);
                return true;
            case R.id.action_call:
                botonesRespuesta[Integer.parseInt(pregunta.getPhone())-1].setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                menu.findItem(R.id.action_call).setEnabled(false);
                menu.findItem(R.id.action_call).setVisible(false);
                return true;
            case R.id.action_close:
                GuardarPuntuacion();
                numeroPregunta = 0;
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void saveState() {
        SharedPreferences _prefs = getSharedPreferences("Preferencias", Activity.MODE_PRIVATE);

        if (_prefs==null) return;
        SharedPreferences.Editor _prefsEditor = _prefs.edit();
        if(_prefsEditor==null) return;

        _prefsEditor.putString("numero_pregunta_actual", String.valueOf(numeroPregunta));
        _prefsEditor.putBoolean("hint_50", menu.findItem(R.id.action_50).isEnabled());
        _prefsEditor.putBoolean("hint_audience", menu.findItem(R.id.action_people).isEnabled());
        _prefsEditor.putBoolean("hint_phone", menu.findItem(R.id.action_call).isEnabled());

        _prefsEditor.commit();
    }

    private void recoverState(){
        SharedPreferences _prefs = getSharedPreferences("Preferencias", MODE_PRIVATE);
        if (_prefs==null) return;
        hint = (Integer.parseInt(_prefs.getString("spinner_ChancesNumber", "3")));
        numeroPregunta = (Integer.parseInt(_prefs.getString("numero_pregunta_actual", "0")));
        nombreJugador = (_prefs.getString("playerName", null));
    }
}
