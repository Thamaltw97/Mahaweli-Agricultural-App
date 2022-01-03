package com.example.marketappauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

//import com.bhargavms.dotloader.DotLoader;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    //SharedPreferences preferences = getSharedPreferences("mypref",MODE_PRIVATE);
    Button bsinhala, benglish , btamil , bnext;
    LinearLayout languagebg;
    Boolean isLanguageSelected=false;
   // DotLoader dotLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        bsinhala = findViewById(R.id.btnSinhala);
        benglish = findViewById(R.id.btnEnglish);
        btamil = findViewById(R.id.btnTamil);
        bnext = findViewById(R.id.btnNext);
        languagebg = findViewById(R.id.languageSelect);
      //  dotLoader = (DotLoader) findViewById(R.id.dot_loader);


        bsinhala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("si");
                isLanguageSelected = true;
                languagebg.setBackgroundResource(R.drawable.language_bg_s);

            }
        });
        benglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
                isLanguageSelected = true;
                languagebg.setBackgroundResource(R.drawable.language_bg_e);
            }
        });
        btamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("ta");
                isLanguageSelected = true;
                languagebg.setBackgroundResource(R.drawable.language_bg_t);
            }
        });

        bnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                dotLoader.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dotLoader.setNumberOfDots(5);
                    }
                }, 3000);*/
              //  dotLoader.setVisibility(View.VISIBLE);

                if(isLanguageSelected){
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                }else{
                    Toast.makeText(getApplicationContext(), "Please select language.", Toast.LENGTH_SHORT).show();
                }


            //    dotLoader.setVisibility(View.GONE);
            }
        });




    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("miPrefs", MODE_PRIVATE).edit();
        editor.putString("my_lang", lang);
        editor.apply();

    }

/*    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dotLoader.resetColors();

    }*/
/*    private static class DotIncrementRunnable implements Runnable {
        private int mNumberOfDots;
        private DotLoader mDotLoader;

        private DotIncrementRunnable(int numberOfDots, DotLoader dotLoader) {
            mNumberOfDots = numberOfDots;
            mDotLoader = dotLoader;
        }

        @Override
        public void run() {
            mDotLoader.setNumberOfDots(mNumberOfDots);
        }
    }*/

}