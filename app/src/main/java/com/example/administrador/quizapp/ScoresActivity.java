package com.example.administrador.quizapp;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.administrador.quizapp.R.menu.menu_scores;
import static com.example.administrador.quizapp.R.menu.scores_menu;


public class ScoresActivity extends ActionBarActivity {

    private SQLiteDatabase sc;
    private String [] namesScores = new String[2];
    private ArrayList<String[]> myScores = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

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
        PopulateScoresList();
    }

    private void PopulateScoresList() {
        sc.beginTransaction();
        try {
            Cursor scores = sc.rawQuery("select name, score from  scores", null);
            int name = scores.getColumnIndex("name");
            int score = scores.getColumnIndex("score");
            while (scores.moveToNext()){
                TableRow tableScore = new TableRow(this, null);
                /*tableScore.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.MATCH_PARENT
                ));*/
                LinearLayout lnScore =new LinearLayout(this, null);
                /*lnScore.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                ));*/
                tableScore.addView(lnScore);
                TextView textName = new TextView(this, null);

                /*textName.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));*/
                lnScore.addView(textName);
                //TextView textScore = new TextView(this, null);
                /*textScore.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));*/
                //lnScore.addView(textScore);
                ((LinearLayout) findViewById(R.id.llScores)).addView(tableScore);
                textName.setText(scores.getString(name) + ": " + scores.getString(score));
                textName.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                textName.setGravity(1);
                //textScore.setText(scores.getString(score));
                //textScore.setGravity(1);
                /*namesScores[0] = scores.getString(name);
                namesScores[1] = scores.getString(score);
                myScores.add (namesScores);*/
            }
            sc.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.d("[S1:A2]", "Excepcion \"SQLiteException\" al crear la tabla scores en Play activity: " + e.toString());
        } finally {
            sc.endTransaction();
        }

        /*for (String[] c: myScores){
            TextView textName = new TextView(this, null);
            textName.setText(c[0]);
            textName.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            textName.setGravity(1);
            textName.setLayoutParams(new ViewGroup.LayoutParams(this, ));
            //textName.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView textScore = new TextView(this, null);
            textScore.setText(c[1]);
            textScore.setGravity(1);
            //textScore.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TableRow tableScore = new TableRow(this, null);
            tableScore.addView(textName);
            tableScore.addView(textScore);
            ((LinearLayout) findViewById(R.id.llScores)).addView(tableScore);
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menu_scores, menu);

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
