package com.example.marketappauth.harvest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketappauth.HarvestDetailsActivity;
import com.example.marketappauth.R;

import com.example.marketappauth.adapters.MyAdapter;
import com.example.marketappauth.harvest.models.DailyDarvest;
import com.example.marketappauth.harvest.models.Model;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.card.MaterialCardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainHarvestActivity extends AppCompatActivity  {

    MaterialCardView climate_card , main_list , info;
    ArrayList <String> yeild ;
    ListView yied_list ;
    ArrayList <HashMap<String,String>> items = new ArrayList<>();
    ArrayList <HashMap<String,String>> Chartitems = new ArrayList<>();
    private RecyclerView recyclerView;
    Button HarvestDetailsIcon;

    TextInputEditText TextWeight;
    TextInputEditText TextPrice;
    TextInputLayout  Crop;
    TextInputLayout  Season;
    Button Save,Plot;

    BarChart barChart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String > lableName;
    ArrayList<DailyDarvest> DailyHarvestArrayList = new ArrayList<>();


    TextView temptv, time, longitude, latitude, humidity, sunrise, sunset, pressure, wind, country, city_nam, max_temp, min_temp, feels, wdesc;
    ImageView imageView;

    ArrayList<Entry> dataVals2;


    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("masterSheet");
    private DatabaseReference root2 = db.getReference().child("SellerHarvest");
    private MyAdapter adapter;
    private ArrayList<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_harvest);

        TextPrice = findViewById(R.id.price);
        TextWeight = findViewById(R.id.weight);
        Crop = findViewById(R.id.menuCrop);
        Season = findViewById(R.id.menuSeason);
        Save = findViewById(R.id.Save);
        Plot = findViewById(R.id.plot);


        humidity =findViewById(R.id.humid);
        pressure =findViewById(R.id.pressure1);
        wind = findViewById(R.id.wind1);
        max_temp = findViewById(R.id.temp);
        min_temp = findViewById(R.id.min_temp);
        wdesc = findViewById(R.id.rain);
        feels = findViewById(R.id.today_weather);
        imageView = findViewById(R.id.Wicon);

        recyclerView = findViewById(R.id.yield_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new MyAdapter(this ,list );

        barChart = findViewById(R.id.BarC);
        barEntriesArrayList = new ArrayList<>();
        lableName  = new ArrayList<>();

        FindWeather();

        ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CROPS);
        AutoCompleteTextView textView1 = (AutoCompleteTextView) findViewById(R.id.crops);
        textView1.setAdapter(myadapter1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView1.setKeyboardNavigationCluster(false);
        }


        ArrayAdapter<String> myadapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SEASON);
        AutoCompleteTextView textView2 = (AutoCompleteTextView) findViewById(R.id.seasons);
        textView2.setAdapter(myadapter2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView2.setKeyboardNavigationCluster(false);
        }
        ArrayAdapter<String> myadapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AREA);
        AutoCompleteTextView textView3 = (AutoCompleteTextView) findViewById(R.id.areas);
        textView3.setAdapter(myadapter3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView3.setKeyboardNavigationCluster(false);
        }

        ArrayAdapter<String> myadapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CROPS);
        AutoCompleteTextView textView4 = (AutoCompleteTextView) findViewById(R.id.cropGraph);
        textView4.setAdapter(myadapter4);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView4.setKeyboardNavigationCluster(false);
        }




        climate_card = findViewById(R.id.climate_card);
        climate_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ClimateActivity.class));
            }
        });

        //harvest Info
        info = findViewById(R.id.info);
        main_list = findViewById(R.id.mainlist);
       info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(getApplicationContext(),
                                HarvestDetailsActivity.class));
            }
        });


        //add seller harvest data to db
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCrop;
                String selectedSeason;
                String selectedArea;
                String Weight;
                String Price;

                selectedCrop = String.valueOf(textView1.getText());
                selectedArea =String.valueOf(textView3.getText());
                selectedSeason =String.valueOf(textView2.getText());
                Weight = (TextWeight.getText().toString());
                Price = (TextPrice.getText().toString());

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String Date = formatter.format(date);

                HashMap<String , String> HarvestMap = new HashMap<>();

                HarvestMap.put("Crop" , selectedCrop);
                HarvestMap.put("Season" , selectedSeason);
                HarvestMap.put("Area" , selectedArea);
                HarvestMap.put("Weight" , Weight);
                HarvestMap.put("Price" , Price);
                HarvestMap.put("Date" , Date);
                root2.push().setValue(HarvestMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        //plot graph
        Plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCrop;
                String selectedSeason;
                String selectedArea;
                String Weight;
                String Price;

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String Date = formatter.format(date);

                dataVals2= new ArrayList<Entry>();

                selectedCrop = String.valueOf(textView4.getText());

                root2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<BarEntry> yield = new ArrayList<>();
                        try {
                            DailyHarvestArrayList.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                Model model = dataSnapshot.getValue(Model.class);
                                list.add(model);

                                int x= Integer.parseInt(model.getPrice());
                                Float y= Float.parseFloat(model.getWeight());
                                String crop = model.getCrop();
                                String date = model.getDate();
                                if(crop.equals(selectedCrop)){
                                    DailyHarvestArrayList.add(new DailyDarvest(y,date));
                                }

                            }

                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_LONG).show();
                        }



                        for (int i =0; i < DailyHarvestArrayList.size();i++){
                            String Date = DailyHarvestArrayList.get(i).getDate();
                            String newDate = Date.substring(0,5);
                            Float Weight = DailyHarvestArrayList.get(i).getWeight();
                            barEntriesArrayList.add(new BarEntry(i,Weight));
                            lableName.add(newDate);
                        }

                        BarDataSet barDataSet=new BarDataSet(barEntriesArrayList,"Weight(Kg)");
                        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet.setValueTextColor(Color.BLACK);
                        barDataSet.setValueTextSize(16f);

                        BarData barData =new BarData(barDataSet);

                        barChart.setFitBars(true);
                        barChart.setData(barData);
                        barChart.getDescription().setText("Daily harvest");

                        XAxis xaxis =barChart.getXAxis();
                        xaxis.setValueFormatter(new IndexAxisValueFormatter(lableName));
                        xaxis.setLabelCount(lableName.size());
                        xaxis.setLabelRotationAngle(0);
                        xaxis.setCenterAxisLabels(true);
                        xaxis.setDrawLabels(true);
                        xaxis.setTextSize(3);
                        barChart.animateY(1000);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error : " + error, Toast.LENGTH_LONG).show();
                    }
                });



            }

        });


/*        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCrop;
                String Crop;
                String Season;
                String Area;
                String Weight;
                String Price;
                root.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Model model = dataSnapshot.getValue(Model.class);
                            list.add(model);
                        }
                        //  adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error : " + error, Toast.LENGTH_LONG).show();
                    }
                });


                selectedCrop = String.valueOf(textView4.getText());

                //line chart

                LineChart lineChart= findViewById(R.id.LineC);
                LineDataSet lineDataSet1 = new LineDataSet(dataVals1(list),"Yield");
                ArrayList <ILineDataSet> dataSets =  new ArrayList<>();
                dataSets.add(lineDataSet1);

                LineData data = new LineData(dataSets);
                lineChart.setData(data);
                lineChart.invalidate();

            }

        });*/

        //HarvestDetailsIcon = findViewById(R.id.detailButton);
/*        lineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HarvestDetailsActivity.class));
            }
        });*/


/*        yied_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
                startActivity(new Intent(getApplicationContext(), HarvestDetailsActivity.class));
            }


        });*/


    }

    //
    public void FindWeather()
    {
        String city = "Colombo";
        //   requestQueue = Volley.newRequestQueue(this);
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=462f445106adc1d21494341838c10019&units=metric";
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //find temperature
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject object = jsonObject.getJSONObject("main");
                    double temp = object.getDouble("temp");


                    //find country
                    JSONObject object8 = jsonObject.getJSONObject("sys");
                    String count = object8.getString("country");
                    //  country.setText(count+"  :");

                    //find city
                    String city = jsonObject.getString("name");
                    //   city_nam.setText(city);

                    //find icon
                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject obj = jsonArray.getJSONObject(0);
                    String icon = obj.getString("icon");
                    String desc = obj.getString("description");
                    feels.setText(desc);
                    Picasso.get().load("https://openweathermap.org/img/wn/"+icon+"@2x.png").into(imageView);

                    //find date & time
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat std = new SimpleDateFormat("HH:mm a \nE, MMM dd yyyy");
                    String date = std.format(calendar.getTime());
                    //   time.setText(date);


                    //find humidity
                    JSONObject object4 = jsonObject.getJSONObject("main");
                    int humidity_find = object4.getInt("humidity");
                    humidity.setText(humidity_find+"  %");

                    //find pressure
                    JSONObject object7 = jsonObject.getJSONObject("main");
                    String pressure_find = object7.getString("pressure");
                    pressure.setText(pressure_find+"  hPa");

                    //find wind speed
                    JSONObject object9 = jsonObject.getJSONObject("wind");
                    String wind_find = object9.getString("speed");
                    wind.setText(wind_find+"  km/h");


                    //find max temperature
                    JSONObject object12 = jsonObject.getJSONObject("main");
                    double maxtemp = object12.getDouble("temp_max");
                    max_temp.setText(""+maxtemp+" °C");

                    //find feels
                    JSONObject object13 = jsonObject.getJSONObject("main");
                    double feels_find = object13.getDouble("feels_like");
                    wdesc.setText(feels_find+"");




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest1);
    }

    private static final String[] CROPS= new String[] {
            "Onion", "Paddy", "Potato"
    };
    private static final String[] SEASON= new String[] {
            "Yala", "Maha"
    };
    private static final String[] AREA= new String[] {
            "Welikanda", "Aralaganvila",
            "Manampitiya", "Dehiaththakandiya",
            "Giradurukotte", "Bisopura",
            "Laggala ", "Bakamoona ",
            "Nochchiyagama", "Thambuththegama",
            "Thalawa", "Galnewa",
            "Meegalawa", "Sampathnuwara",
            "Madatugama", "Galkiriyagama",
            "Palugaswewa", "Embilipitiya",
            "Sooriyawewa", "Angunukolapelessa",
            "Digana", "Maha Oya",
            "Padiyathalawa"
    };


        //dataset
    private ArrayList<Entry> dataVals1(){
        ArrayList<Entry> dataVals= new ArrayList<Entry>();
        dataVals.add(new Entry(0,5));
        dataVals.add(new Entry(1,10));
        dataVals.add(new Entry(2,15));
        dataVals.add(new Entry(3,20));
        dataVals.add(new Entry(4,25));
        dataVals.add(new Entry(5,35));

        return dataVals;
    }

    private ArrayList<Entry> dataVals1(ArrayList<Model> arr,String Selectedcrop){
        ArrayList<Entry> dataVals= new ArrayList<Entry>();

        for(int i=0;i<arr.size();i++){
            Model m = new Model();
            m=arr.get(i);
            int y= Integer.parseInt(m.getWeight());
            int x=Integer.parseInt(m.getDate());
            dataVals.add(new Entry(x,y));
        }



        dataVals.add(new Entry(0,5));
        dataVals.add(new Entry(1,10));
        dataVals.add(new Entry(2,15));
        dataVals.add(new Entry(3,20));
        dataVals.add(new Entry(4,25));
        dataVals.add(new Entry(5,35));

        return dataVals;
    }




}