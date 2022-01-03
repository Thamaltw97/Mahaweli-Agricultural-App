package com.example.marketappauth.harvest;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketappauth.R;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import static com.mikepenz.iconics.Iconics.getApplicationContext;

public class ClimateActivity extends AppCompatActivity {



    //climate details
    EditText editText;
    Button button;
    ImageView imageView;
    ListView listForecast;
    TextView temptv, time, longitude, latitude, humidity, sunrise, sunset, pressure, wind, country, city_nam, max_temp, min_temp, feels, wdesc;
    ArrayList <HashMap<String,String>> items = new ArrayList<>();

    String city;
    RequestQueue requestQueue;
    StringRequest stringRequest1,stringRequest2;
    double lat= 0.0, lng= 0.0;

    ArrayList <HashMap<String,String>> items1 = new ArrayList<>();
    ArrayList <String> yeild ;

    String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climate);

        //
        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageViewicon);
        temptv = findViewById(R.id.textView3);
        time =findViewById(R.id.textView2);
        wdesc = findViewById(R.id.w_desc);
        listForecast = findViewById(R.id.forecastList);


        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        humidity =findViewById(R.id.humidity);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        pressure =findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        country = findViewById(R.id.country);
        city_nam =findViewById(R.id.city_nam);
        max_temp = findViewById(R.id.temp_max);
        min_temp = findViewById(R.id.min_temp);
        feels = findViewById(R.id.feels);

        requestQueue = Volley.newRequestQueue(this);


        yeild = new ArrayList<>();
        yeild.add("21434");
        yeild.add("14344");
        yeild.add("43674");
        yeild.add("32452");
        yeild.add("11232");
        yeild.add("23145");
        yeild.add("20876");

     //   filldetailcheck(yeild);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                city = editText.getText().toString();


               FindWeather();
               // requestQueue.stop();
                getLatLongFromGivenAddress(city);

                FindWeatherForecast();
               // requestQueue.stop();
            }
        });
    }

/*
    private void filldetailcheck(ArrayList <String> y) {

        try {
            if (items.size()>0){
                items = new ArrayList<>();
            }
            for (int i = 0; i < y.size(); i++) {

                String f = y.get(i);

                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("yield", f + "");
                hm.put("year", "200"+String.valueOf(i+1));
                items.add(i, hm);
            }


            if (listForecast.getAdapter() != null) {
                listForecast.setAdapter(null);
            }

            String[] from = {"yeild","year"};

            int[] to = {R.id.yield_txt,R.id.yield_year};
            SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), items, R.layout.wforecast_list_item, from, to);
            listForecast.setAdapter(simpleAdapter);
           listForecast.setSelection(items.size()-1);



        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "List creation failed : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            System.out.println(e.getLocalizedMessage());
        }



    }
*/

    public void FindWeather()
    {
        //city = editText.getText().toString();
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=462f445106adc1d21494341838c10019&units=metric";
        stringRequest1 = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //find temperature
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject object = jsonObject.getJSONObject("main");
                            double temp = object.getDouble("temp");
                            temptv.setText("Temperature\n"+temp+"°C");

                            //find country
                            JSONObject object8 = jsonObject.getJSONObject("sys");
                            String count = object8.getString("country");
                            country.setText(count+"  :");

                            //find city
                            String city = jsonObject.getString("name");
                            city_nam.setText(city);

                            //find icon
                            JSONArray jsonArray = jsonObject.getJSONArray("weather");
                            JSONObject obj = jsonArray.getJSONObject(0);
                            String icon = obj.getString("icon");
                            String desc = obj.getString("description");
                            wdesc.setText(desc);
                            Picasso.get().load("https://openweathermap.org/img/wn/"+icon+"@2x.png").into(imageView);

                            //find date & time
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat std = new SimpleDateFormat("HH:mm a \nE, MMM dd yyyy");
                            String date = std.format(calendar.getTime());
                            time.setText(date);

/*

                            //find latitude
                            JSONObject object2 = jsonObject.getJSONObject("coord");
                            double lat_find = object2.getDouble("lat");
                            latitude.setText(lat_find+"°  N");

                            //find longitude
                            JSONObject object3 = jsonObject.getJSONObject("coord");
                            double long_find = object3.getDouble("lon");
                            longitude.setText(long_find+"°  E");

                            //find humidity
                            JSONObject object4 = jsonObject.getJSONObject("main");
                            int humidity_find = object4.getInt("humidity");
                            humidity.setText(humidity_find+"  %");

                            //find sunrise
                            JSONObject object5 = jsonObject.getJSONObject("sys");
                            String sunrise_find = object5.getString("sunrise");
                            sunrise.setText(sunrise_find+"  am");

                            //find sunrise
                            JSONObject object6 = jsonObject.getJSONObject("sys");
                            String sunset_find = object6.getString("sunset");
                            sunset.setText(sunset_find+"  pm");

                            //find pressure
                            JSONObject object7 = jsonObject.getJSONObject("main");
                            String pressure_find = object7.getString("pressure");
                            pressure.setText(pressure_find+"  hPa");

                            //find wind speed
                            JSONObject object9 = jsonObject.getJSONObject("wind");
                            String wind_find = object9.getString("speed");
                            wind.setText(wind_find+"  km/h");

                            //find min temperature
                            JSONObject object10 = jsonObject.getJSONObject("main");
                            double mintemp = object10.getDouble("temp_min");
                            min_temp.setText("Min Temp\n"+mintemp+" °C");

                            //find max temperature
                            JSONObject object12 = jsonObject.getJSONObject("main");
                            double maxtemp = object12.getDouble("temp_max");
                            max_temp.setText("Max Temp\n"+maxtemp+" °C");

                            //find feels
                            JSONObject object13 = jsonObject.getJSONObject("main");
                            double feels_find = object13.getDouble("feels_like");
                            feels.setText(feels_find+" °C");*/




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

        requestQueue.add(stringRequest1);
    }



    //forecast

    public void FindWeatherForecast()
    {

        //String lat="",longi="";
       // String url ="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=462f445106adc1d21494341838c10019&units=metric";

        //obtaining lat and lon
        String location=city;
        String inputLine = "";
        String result = "";
        location=location.replaceAll(" ", "%20");
        String myUrl="http://maps.google.com/maps/geo?q="+location+"&output=csv";
        try{
            java.net.URL url=new URL(myUrl);
            URLConnection urlConnection=url.openConnection();
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(urlConnection.getInputStream()));
            while ((inputLine = in.readLine()) != null) {
                result=inputLine;
            }
           // lat = result.substring(6, result.lastIndexOf(","));
          //  longi = result.substring(result.lastIndexOf(",") + 1);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        requestQueue = Volley.newRequestQueue(this);
        String URL = "https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lng+"&exclude=current,minutely,hourly,alerts&appid=462f445106adc1d21494341838c10019&units=metric";


        stringRequest2 = new StringRequest(Request.Method.GET,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    //find temperature
                   JSONObject jsonObject = new JSONObject(response);

   //                 JSONObject object = jsonObject.getJSONObject("main");


/*
                    JSONObject object = jsonObject.getJSONObject("main");
                    double temp = object.getDouble("temp");
                    temptv.setText("Temperature\n"+temp+"°C");

                    //find country
                    JSONObject object8 = jsonObject.getJSONObject("sys");
                    String count = object8.getString("country");
                    country.setText(count+"  :");

                    //find city
                    String city = jsonObject.getString("name");
                    city_nam.setText(city);
*/


                    //find icon
                    JSONArray jsonArray = jsonObject.getJSONArray("daily");

                    //find date & time
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat std = new SimpleDateFormat("yyyy/MM/dd");
                    today = std.format(calendar.getTime());

                   // JSONArray weatherArr = jsonObject.toJSONArray(jsonObject.names());
                    filldetails(jsonArray);
                    Toast.makeText(getApplicationContext(), "Scroll down to check forecast. " , Toast.LENGTH_LONG).show();
                    //requestQueue.stop();



                  //  time.setText(date);

                    //find latitude
  /*                  JSONObject object2 = jsonObject.getJSONObject("coord");
                    double lat_find = object2.getDouble("lat");
                    latitude.setText(lat_find+"°  N");

                    //find longitude
                    JSONObject object3 = jsonObject.getJSONObject("coord");
                    double long_find = object3.getDouble("lon");
                    longitude.setText(long_find+"°  E");

                    //find humidity
                    JSONObject object4 = jsonObject.getJSONObject("main");
                    int humidity_find = object4.getInt("humidity");
                    humidity.setText(humidity_find+"  %");

                    //find sunrise
                    JSONObject object5 = jsonObject.getJSONObject("sys");
                    String sunrise_find = object5.getString("sunrise");
                    sunrise.setText(sunrise_find+"  am");

                    //find sunrise
                    JSONObject object6 = jsonObject.getJSONObject("sys");
                    String sunset_find = object6.getString("sunset");
                    sunset.setText(sunset_find+"  pm");

                    //find pressure
                    JSONObject object7 = jsonObject.getJSONObject("main");
                    String pressure_find = object7.getString("pressure");
                    pressure.setText(pressure_find+"  hPa");

                    //find wind speed
                    JSONObject object9 = jsonObject.getJSONObject("wind");
                    String wind_find = object9.getString("speed");
                    wind.setText(wind_find+"  km/h");

                    //find min temperature
                    JSONObject object10 = jsonObject.getJSONObject("main");
                    double mintemp = object10.getDouble("temp_min");
                    min_temp.setText("Min Temp\n"+mintemp+" °C");

                    //find max temperature
                    JSONObject object12 = jsonObject.getJSONObject("main");
                    double maxtemp = object12.getDouble("temp_max");
                    max_temp.setText("Max Temp\n"+maxtemp+" °C");

                    //find feels
                    JSONObject object13 = jsonObject.getJSONObject("main");
                    double feels_find = object13.getDouble("feels_like");
                    feels.setText(feels_find+" °C");

*/

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

        requestQueue.add(stringRequest2);

    }

    private void filldetails(JSONArray array) {

        try {
            if (items.size()>0){
                items = new ArrayList<>();
            }


            for (int i = 0; i < array.length(); i++) {

                JSONObject obj = array.getJSONObject(i);

                //find icon
                JSONArray jsonArray = obj.getJSONArray("weather");
                JSONObject wobj = jsonArray.getJSONObject(0);
                String icon = wobj.getString("icon");
                String desc = wobj.getString("description");

                today = getNextDate(today);

          //      wdesc.setText(desc);
          //      Picasso.get().load("https://openweathermap.org/img/wn/"+icon+"@2x.png").into(imageView);


                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("desc", desc);
                hm.put("icon", icon);
                hm.put("date",today);
                items.add(i, hm);
            }


            if (listForecast.getAdapter() != null) {
                listForecast.setAdapter(null);
            }

            //todo
           // String[] from = {"desc","icon"};
            String[] from = {"desc","date"};
          //  int[] to = {R.id.weather_desc,R.id.climate_icon};
            int[] to = {R.id.desc_txt,R.id.date_txt};
            SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), items, R.layout.wforecast_list_item, from, to);
            listForecast.setAdapter(simpleAdapter);
            listForecast.setSelection(items.size()-1);




        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "List creation failed : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            System.out.println(e.getLocalizedMessage());
        }
    }

/*
    public void getLatLongFromGivenAddress(String youraddress) {
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                youraddress + "&sensor=false";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

        try {

        }  catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            longi = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            Log.d("latitude", lat);
            Log.d("longitude", lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
*/
public  void getLatLongFromGivenAddress(String address)
{


    Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
    try
    {
        List<Address> addresses = geoCoder.getFromLocationName(address , 1);
        if (addresses.size() > 0)
        {
            GeoPoint p = new GeoPoint(
                    (int) (addresses.get(0).getLatitude()),
                    (int) (addresses.get(0).getLongitude() ));

            lat=p.getLatitude();
            lng=p.getLongitude();


            Log.d("Latitude", ""+lat);
            Log.d("Longitude", ""+lng);
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
}

    public static String getNextDate(String  curDate) throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        final Date date = format.parse(curDate);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return format.format(calendar.getTime());
    }
}