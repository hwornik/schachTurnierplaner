package eu.wornik.android.com.schachturnierplaner;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class Turniererstellung extends AppCompatActivity {

    private String teilnehmer = "";
    private ArrayList<String> teilnListe = new ArrayList<String>();
    private ArrayList<TextView> textViewTeiln = new ArrayList<TextView>();
    private ArrayList<CheckBox> chkTeiln = new ArrayList<CheckBox>();
    private ArrayList<String> turniertln = new ArrayList<String>();
    private ArrayList<String> ergebnis = new ArrayList<String>();
    private ArrayList<ArrayList> turnier = new ArrayList<ArrayList>();
    private ArrayList<ArrayList> runde = new ArrayList<ArrayList>();
    private ArrayList<String> spiele = new ArrayList<String>();
    private int state=0;
    private RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turniererstellung);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //this.readFromFile(getApplicationContext());
        this.drawTeilnehmerAuswahl();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(awesomeOnClickListener);
    }

    private View.OnClickListener awesomeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //layout.removeAllViewsInLayout();
            //layout.setVisibility(RelativeLayout.GONE);
            if(state==0)
            {
                if(turniertln.size()>0)
                {
                    for(int i=0;i<turniertln.size();i++)
                        turniertln.remove(i);
                }
                if(ergebnis.size()>0)
                {
                    for(int i=0;i<ergebnis.size();i++)
                        ergebnis.remove(i);
                }
                if(turnier.size()>0)
                {
                    for(int i=0;i<turnier.size();i++)
                        turnier.remove(i);
                }
                if(runde.size()>0)
                {
                    for(int i=0;i<runde.size();i++)
                        runde.remove(i);
                }
                if(spiele.size()>0)
                {
                    for(int i=0;i<spiele.size();i++)
                        spiele.remove(i);
                }
                for(int i=0;i<chkTeiln.size();i++) {
                    if (chkTeiln.get(i).isChecked()) {
                        turniertln.add(teilnListe.get(i));
                        ergebnis.add("");
                    }
                }
                //chooseTurnier();
            }
        }
    };

    private void drawTeilnehmerAuswahl(){
        layout = (RelativeLayout)findViewById(R.id.inhalte_turnier);
        String inputtxt=this.readFromFile(getApplicationContext());
        Toast.makeText(getApplicationContext(), inputtxt, Toast.LENGTH_SHORT).show();
        Random rnd = new Random();
        int prevTextViewId = 0;
        for(int i = 0; i < 10; i++)
        {
            final TextView textView = new TextView(this);
            textView.setText(inputtxt);
            textView.setTextColor(rnd.nextInt() | 0xff000000);

            int curTextViewId = prevTextViewId + 1;
            textView.setId(curTextViewId);
            final RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

            params.addRule(RelativeLayout.BELOW, prevTextViewId);
            textView.setLayoutParams(params);

            prevTextViewId = curTextViewId;
            layout.addView(textView, params);
        }
    }


    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("Teiln.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private  String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("Teiln.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
