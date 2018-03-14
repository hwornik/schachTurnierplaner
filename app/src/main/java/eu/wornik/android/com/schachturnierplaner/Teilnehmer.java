package eu.wornik.android.com.schachturnierplaner;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;


public class Teilnehmer extends AppCompatActivity {


    private AutoCompleteTextView intext;
    private ArrayList<String> teilnListe = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teilnehmer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.showInputMask();
        this.loadArray();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeNewName();
                finish();
            }
        });
    }

    private void loadArray() {
        String teilnehmer=this.readFromFile(this);
        StringTokenizer tokens = new StringTokenizer(teilnehmer, ";");
        while (tokens.hasMoreTokens()) {
            teilnListe.add(tokens.nextToken());
        }
    }

    private void storeNewName() {

        String newname=intext.getText().toString();
        int anzahl=teilnListe.size(),elem=0;
        boolean inserted=false;
        int first=0,last=anzahl;
        if(newname.length()>0 ) {
            if(anzahl>0) {
                elem=anzahl/2;
                while (!inserted) {
                    if(elem<0)
                    {
                        return;
                    }
                    else {
                        if(teilnListe.get(elem - 1).charAt(0)>newname.charAt(0))
                        {
                            last=elem;
                            elem-=(last-first)/2;
                        }
                        else if(teilnListe.get(elem - 1).charAt(0)==newname.charAt(0))
                        {
                            if(elem>=anzahl)
                            {
                                teilnListe.add(newname);
                                inserted=true;
                            }
                            else {
                                for (int i = elem - 1; i < anzahl; i++) {
                                    if (teilnListe.get(i).charAt(0) > newname.charAt(0)) {
                                        insertList(newname, i,teilnListe);
                                        inserted=true;;
                                    }
                                }
                            }
                        }else
                        {
                            first=elem;
                            elem+=(last-first)/2;
                        }
                        if(last==first)
                        {
                            insertList(newname, first,teilnListe);
                            inserted=true;
                        }
                    }
                }
            }else
            {
                this.writeToFile(newname+";",getApplicationContext());
            }

        }
        newname="";
        for(int i=0;i<teilnListe.size();i++)
        {
            newname+=teilnListe.get(i);
        }
        this.writeToFile(newname,getApplicationContext());

    }

    private void insertList(String newname, int first, ArrayList<String> teilnListe) {
        int anzahl=teilnListe.size();
        if(anzahl==first)
        {
            if(teilnListe.get(first).charAt(0)>newname.charAt(0))
            {
                teilnListe.add(teilnListe.get(first));
                teilnListe.set(first-1,newname);
            }else
            {
                teilnListe.add(newname);
            }
        }
        else{
            teilnListe.add(newname);
            for(int i=anzahl;i<first;i--)
            {
                teilnListe.set(i+1,teilnListe.get(i));
            }
            teilnListe.set(first,newname);

        }
    }

    private void showInputMask(){
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.inhalte_teilnehmer);
            intext = new AutoCompleteTextView(this);
            intext.setTextColor(Color.BLACK);
            intext.setHint("Name ?");
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 100;
            params.topMargin = 100;
             //textView.setLayoutParams(params);
            layout.addView(intext, params);
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
