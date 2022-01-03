package com.example.marketappauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BuyerSellerActivity extends AppCompatActivity {

    Button Seller, Buyer,nextbtn , backbtn;
    ConstraintLayout cl ;
    TextView selectUser, seller_txt, buyer_txt, next, seller_buyer_txt;
    Boolean isBuyer=false,isSeller=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_seller);
        Seller = findViewById(R.id.buttonSeller);
        Buyer = findViewById(R.id.buttonBuyer);
        cl = findViewById(R.id.selectUserBackground);
        selectUser = findViewById(R.id.textView11);
        seller_txt = findViewById(R.id.textView13);
        buyer_txt = findViewById(R.id.textView12);
        nextbtn = findViewById(R.id.nextbtn);
        backbtn = findViewById(R.id.backbtn);
        next = findViewById(R.id.textView14);
        seller_buyer_txt = findViewById(R.id.textView22);

        //clearing data in sharedpref
        this.getSharedPreferences("buyer_seller", 0).edit().clear().apply();


        //loading seller backgroud
        Seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               cl.setBackgroundResource(R.drawable.seller2);
               selectUser.setVisibility(View.GONE);
               seller_txt.setVisibility(View.GONE);
               buyer_txt.setVisibility(View.GONE);
               next.setVisibility(View.VISIBLE);
               seller_buyer_txt.setVisibility(View.VISIBLE);
               nextbtn.setVisibility(View.VISIBLE);
               isSeller=true;
            }
        });

        //loading Buyer backgroud
        Buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cl.setBackgroundResource(R.drawable.buyer2);
                selectUser.setVisibility(View.GONE);
                seller_txt.setVisibility(View.GONE);
                buyer_txt.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                seller_buyer_txt.setVisibility(View.VISIBLE);
                nextbtn.setVisibility(View.VISIBLE);
                isBuyer=true;
            }
        });

        //next button click directs to home page
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setTextColor(Color.BLUE);
                String MY_PREFS_NAME="buyer_seller";
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean("isBuyer", isBuyer);
                editor.putBoolean("isSeller", isSeller);
                editor.apply();
                startActivity(new Intent(getApplicationContext(),MahaweliHomeActivity.class));
            }
        });

        //back button
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSeller || isBuyer){
                    cl.setBackgroundResource(R.drawable.buyer_seller);
                    selectUser.setVisibility(View.VISIBLE);
                    seller_txt.setVisibility(View.VISIBLE);
                    buyer_txt.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                    seller_buyer_txt.setVisibility(View.GONE);
                    nextbtn.setVisibility(View.GONE);
                    next.setTextColor(Color.BLACK);
                    isBuyer=false;
                    isSeller=false;
                }else{

                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }

            }
        });
    }
}