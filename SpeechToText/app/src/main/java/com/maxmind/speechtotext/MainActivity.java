package com.maxmind.speechtotext;


import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private RadioButton ckEnglish;
    private RadioButton ckChinese;
    private RadioGroup radioGroup;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private  boolean startflag =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        ckEnglish= (RadioButton) findViewById(R.id.rbEnglish);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.action_Exit:
                         finish();
                        System.exit(0);
                        return true;
                }

                return false;
            }
        });

        AutoCompleteTextView languageTextView;
        languageTextView=(AutoCompleteTextView)findViewById(R.id.languageTextView);
        String[] buildings = new String[] {"TDA001",  "TDA002",  "TDA003",  "TDA004"};
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,buildings);

        languageTextView.setAdapter(adapter);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.yourRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

            }
        });
      //  ckChinese= (RadioButton)  findViewById(R.id.rbChinese);


        btnSpeak.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                     promptSpeechInput();

               }
            });

    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        if( ckEnglish.isChecked())
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        else
            intent.putExtra( RecognizerIntent.EXTRA_LANGUAGE,"zh" );

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getString(R.string.speech_prompt));
        try {
        startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
            getString(R.string.speech_not_supported),
            Toast.LENGTH_SHORT).show();
        }
        }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        switch(requestCode){
            case REQ_CODE_SPEECH_INPUT:{
                if(resultCode==RESULT_OK && null!=data){

                    ArrayList<String>result=data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
