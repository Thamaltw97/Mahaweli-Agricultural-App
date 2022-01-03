package com.example.marketappauth.healthiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.marketappauth.R;
import com.example.marketappauth.healthiness.models.Healthiness;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CropListActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    ListView croplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_list);

        croplist = (ListView)findViewById(R.id.listview);

        ArrayList<String> arrayList = new ArrayList<>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        Query query = FirebaseDatabase.getInstance().getReference("healthiness")
                .orderByChild("userId").equalTo(user.getUid());


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Healthiness health = postSnapshot.getValue(Healthiness.class);
                    String[] date = health.getDate().split(" ");
                    arrayList.add(date[0] + " " + date[1] + " " + date[2] + " " + date[5] + " " + date[3] + " - " + health.getCrop());
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);

                croplist.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //arrayList.add("Banana");
//        arrayList.add("Pineapple");



    }
}