package com.example.marketappauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.marketappauth.adapters.MyAdapter;
import com.example.marketappauth.harvest.models.DailyDarvest;
import com.example.marketappauth.harvest.models.Model;
/*import com.example.marketappauth.ml.Onion;
import com.example.marketappauth.ml.PaddyMaha;
import com.example.marketappauth.ml.PaddyYala;
import com.example.marketappauth.ml.Potato;
import com.example.marketappauth.ml.RainfallMaha;
import com.example.marketappauth.ml.RainfallYala;*/
import com.example.marketappauth.harvest.models.ModelHarvest;
import com.example.marketappauth.harvest.models.SeasonalHarvest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class HarvestDetailsActivity extends AppCompatActivity {

    ImageView HarvestDetailsIcon;
    Button Plot,Generate;

    ListView yied_list ;

    int r1 = ThreadLocalRandom.current().nextInt(7, 12);

    ArrayList<Entry> dataVals2;
    ArrayList <HashMap<String,String>> items = new ArrayList<>();

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("HarvestSheet");
    private DatabaseReference root2 = db.getReference().child("SellerHarvest");
    private MyAdapter adapter;
    private ArrayList<Model> list;
    private ArrayList<ModelHarvest> SeasonalHarvestArrayList ;
   // private ArrayList<Seas> list_linechart;
    ArrayList<BarEntry> barEntriesArrayList;
    ArrayList<String > lableName;



    Interpreter tflite, tflite_onion,tflite_potato, tflite_paddy_yala,tflite_paddy_maha ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest_details);

        Plot = findViewById(R.id.plot);
        Generate = findViewById(R.id.listGen);
        yied_list = findViewById(R.id.yield_list);
        list = new ArrayList<>();
        SeasonalHarvestArrayList = new ArrayList<>();
        lableName  = new ArrayList<>();


/*        HarvestDetailsIcon.setClickable(true);
        HarvestDetailsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CROPS);
        AutoCompleteTextView textView1 = (AutoCompleteTextView) findViewById(R.id.details_crop);
        textView1.setAdapter(myadapter1);


        ArrayAdapter<String> myadapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SEASON);
        AutoCompleteTextView textView2 = (AutoCompleteTextView) findViewById(R.id.details_season);
        textView2.setAdapter(myadapter2);

        ArrayAdapter<String> myadapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AREA);
        AutoCompleteTextView textView3 = (AutoCompleteTextView) findViewById(R.id.details_area);
        textView3.setAdapter(myadapter3);


        Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCrop;
                String selectedSeason;
                String selectedArea;
                String Weight;
                String Price;
                list.clear();


               // dataVals2= new ArrayList<Entry>();

                selectedCrop = String.valueOf(textView1.getText());
                selectedSeason = String.valueOf(textView2.getText());
                selectedArea = String.valueOf(textView3.getText());


                root2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Model model = dataSnapshot.getValue(Model.class);
                            String crop = model.getCrop();
                            String season = model.getSeason();
                            String area = model.getArea();
                            //list.add(model);
                            if(crop.equals(selectedCrop) && season.equals(selectedSeason) && area.equals(selectedArea)){
                                list.add(model);
                                continue;
                            }
                        }
                        filldetails(list);
                    }

                    @Override
                    public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error : " + error, Toast.LENGTH_LONG).show();
                    }
                });


            }

        });

/*
        Plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCrop;
                String selectedSeason;
                String selectedArea;
                String Weight;
                String Price;


                dataVals2= new ArrayList<Entry>();

                selectedCrop = String.valueOf(textView1.getText());
                selectedSeason = String.valueOf(textView2.getText());
                selectedArea = String.valueOf(textView3.getText());


                root2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Model model = dataSnapshot.getValue(Model.class);
                            String crop = model.getCrop();
                            String season = model.getSeason();
                            String area = model.getArea();
                            //list.add(model);
                            if(crop.equals(selectedCrop) && season.equals(selectedSeason) && area.equals(selectedArea)){
                                list.add(model);
                                continue;
                            }
                        }
                        filldetails(list);
                    }

                    @Override
                    public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error : " + error, Toast.LENGTH_LONG).show();
                    }
                });


            }

        });*/


        try {
                tflite = new Interpreter(loadModelFile());
                tflite_onion = new Interpreter(loadOnionModelFile());
                tflite_potato= new Interpreter(loadPotatoModelFile());
                tflite_paddy_yala=new Interpreter(loadPaddyYalaModelFile());
                tflite_paddy_maha=new Interpreter(loadPaddyMahaModelFile());
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_LONG).show();
        }

        Plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float finalPredictPaddyYala, finalPredictPaddyMaha,finalPredictOnion,finalPredictPotato;
                Float finalPredict;
                double precipitation=0;
                String area= "";

                String selected_crop= String.valueOf(textView1.getText());
                String selected_season = String.valueOf(textView2.getText());
                String selected_area = String.valueOf(textView3.getText());

                dataVals2= new ArrayList<Entry>();

                //plotting old data retrieved from firebase
                root.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                          //  Model model = dataSnapshot.getValue(Model.class);
                            ModelHarvest modelh = dataSnapshot.getValue(ModelHarvest.class);

                            String c=modelh.getCrop();
                            String s =modelh.getSeason();
                            String a =modelh.getArea();
                            String crop = matchCrop(c) ;
                            String season = matchSeason(s);
                            String area =matchArea(a);
                            //list.add(model);
                            if(crop.equals(selected_crop) && season.equals(selected_season) && area.equals(selected_area)){
                                SeasonalHarvestArrayList.add(modelh);
                                continue;
                            }
                        }
                        //collecting plotting data from retrived list
                        for (int i =0; i < SeasonalHarvestArrayList.size();i++){
                            String Year = SeasonalHarvestArrayList.get(i).getYear();
                            int Year_int = Integer.valueOf(Year);
                        //    String newDate = Date.substring(0,5);
                            Float Yield = Float.valueOf(SeasonalHarvestArrayList.get(i).getYield());
                            dataVals2.add(new Entry(i,Yield));
                            lableName.add(Year);
                        }

                        Date d=new Date();
                        int year=d.getYear();
                        int currentYear=year+1900;
                        lableName.add(String.valueOf(currentYear));



                      //  Collections.rotate(dataVals2, -1);
                      //  Collections.rotate(lableName, -1);
                        //plotting the curve
                        LineChart lineChart= findViewById(R.id.LineC);
                        LineDataSet lineDataSet1 = new LineDataSet(dataVals2,"Yield");
                        int color = Color.GREEN;
                        lineDataSet1.setColor(color);
                        lineDataSet1.setCircleColor(color);
                        ArrayList <ILineDataSet> dataSets =  new ArrayList<>();
                        dataSets.add(lineDataSet1);


                        LineData data = new LineData(dataSets);
                        lineChart.setData(data);
                        lineChart.invalidate();

                        XAxis xaxis =lineChart.getXAxis();
                        xaxis.setValueFormatter(new IndexAxisValueFormatter(lableName));
                        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xaxis.setLabelCount(lableName.size());
                        xaxis.setTextSize(5);
                        xaxis.setLabelRotationAngle(90);
                    }

                    @Override
                    public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error : " + error, Toast.LENGTH_LONG).show();
                    }
                });


                //Next Seasonal Prediction
                switch(selected_area) {
                    case "Welikanda":
                        area ="1";
                        break;
                    case "Aralaganvila":
                        area ="2";
                        break;
                    case "Manampitiya":
                        area ="3";
                        break;
                    case "Dehiaththakandiya":
                        area ="4";
                        break;
                    case "Giradurukotte":
                        area ="5";
                        break;
                    case "Bisopura":
                        area ="6";
                        break;
                    case "Laggala ":
                        area ="7";
                        break;
                    case "Nochchiyagama":
                        area ="8";
                        break;
                    case "Thambuththegama":
                        area ="9";
                        break;
                    case "Thalawa":
                        area ="10";
                        break;
                    default:
                        break;
                }


                float prediction = inference(area ,"","");
                String predict = String.valueOf(prediction);
               // Toast.makeText(getApplicationContext(), "Error : " + prediction, Toast.LENGTH_LONG).show();

                if(selected_crop.equals("Paddy")){
                    if(selected_season.equals("Yala")){
                        float predictionPaddyYala = inference(predict,"Paddy","Yala");
                        finalPredictPaddyYala =  predictionPaddyYala*3;

                        //add to dataVal2 list

                        dataVals2.add(new Entry(18,finalPredictPaddyYala));


                        Toast.makeText(getApplicationContext(), "Yield : " + predictionPaddyYala, Toast.LENGTH_LONG).show();
                    }else{
                        float predictionPaddyMaha = inference(predict,"Paddy","Maha");
                        finalPredictPaddyMaha =  predictionPaddyMaha;
                        dataVals2.add(new Entry(18,finalPredictPaddyMaha));
                        Toast.makeText(getApplicationContext(), "Error : " + predictionPaddyMaha, Toast.LENGTH_LONG).show();

                    }
                }else if (selected_crop.equals("Potato")){
                    float predictionPotato = inference(predict,"Potato","");
                    finalPredictPotato = predictionPotato-130;
                    dataVals2.add(new Entry(18,finalPredictPotato));
                    Toast.makeText(getApplicationContext(), "Error : " + predictionPotato, Toast.LENGTH_LONG).show();
                }else{
                    float predictionnew = prediction+5;
                    predict =  String.valueOf(predictionnew);
                    float predictionOnin = inference(predict,"Onion","");
                    finalPredictOnion = predictionOnin;
                    dataVals2.add(new Entry(18,finalPredictOnion));
                    Toast.makeText(getApplicationContext(), "Error : " + predictionOnin, Toast.LENGTH_LONG).show();
                }

            }
                });


  /*      Plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double precipitation=0;
                double area=0;

                String selected_crop= String.valueOf(textView1.getText());
                String selected_season = String.valueOf(textView2.getText());
                String selected_area = String.valueOf(textView3.getText());

                switch(selected_area) {
                    case "Welikanda":
                        area =1;
                        break;
                    case "Aralaganvila":
                        area =2;
                        break;
                    case "Manampitiya":
                        area =3;
                        break;
                    case "Dehiaththakandiya":
                        area =4;
                        break;
                    case "Giradurukotte":
                        area =5;
                        break;
                    case "Bisopura":
                        area =6;
                        break;
                    case "Laggala ":
                        area =7;
                        break;
                    case "Nochchiyagama":
                        area =8;
                        break;
                    case "Thambuththegama":
                        area =9;
                        break;
                    case "Thalawa":
                        area =10;
                        break;
                    default:
                        break;
                }

                 double area1 = 500;


                ByteBuffer byteBuffer2 = ByteBuffer.allocateDirect(1*4);
                byteBuffer2.putFloat((float) area1);

                if(selected_season.equals("Yala")){
                    try {
                        RainfallYala model = RainfallYala.newInstance(getApplicationContext());

                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 1}, DataType.FLOAT32);
                        inputFeature0.loadBuffer(byteBuffer2);

                        // Runs model inference and gets result.
                        RainfallYala.Outputs outputs = model.process(inputFeature0);
                        @NonNull float[] outputFeature0 = outputs.getOutputFeature0AsTensorBuffer().getFloatArray();
                        float p= (float) (outputFeature0[0]/10.0);

                     //   Toast.makeText(getApplicationContext(), "Rainfall Yala: " + outputFeature0[0], Toast.LENGTH_LONG).show();

                        precipitation = p;

                        // Releases model resources if no longer used.
                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                        Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_LONG).show();
                    }


                }else{

                    try {
                        RainfallMaha model = RainfallMaha.newInstance(getApplicationContext());

                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 1}, DataType.FLOAT32);
                        inputFeature0.loadBuffer(byteBuffer2);

                        // Runs model inference and gets result.
                        RainfallMaha.Outputs outputs = model.process(inputFeature0);
                        @NonNull float[] outputFeature0 = outputs.getOutputFeature0AsTensorBuffer().getFloatArray();
                        float p= (float) (outputFeature0[0]/10.0);

                       // Toast.makeText(getApplicationContext(), "Rainfall Maha: " + outputFeature0[0], Toast.LENGTH_LONG).show();
                        precipitation = p;

                        // Releases model resources if no longer used.
                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                        Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_LONG).show();
                    }
                }

                precipitation=100;
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1*4);
                byteBuffer.putFloat((float) 6.5);

                if(selected_crop.equals("Paddy")){
                    //   textView2.setVisibility( View.VISIBLE);
                    if(selected_season.equals("Yala")){
                        try {
                            PaddyYala model = PaddyYala.newInstance(getApplicationContext());

                            // Creates inputs for reference.
                            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 1}, DataType.FLOAT32);
                            inputFeature0.loadBuffer(byteBuffer);

                            // Runs model inference and gets result.
                            PaddyYala.Outputs outputs = model.process(inputFeature0);
                            @NonNull float[] outputFeature0 = outputs.getOutputFeature0AsTensorBuffer().getFloatArray();


                            Toast.makeText(getApplicationContext(), "Yield : " + outputFeature0[0], Toast.LENGTH_LONG).show();

                            // Releases model resources if no longer used.
                            model.close();
                        } catch (IOException e) {
                            // TODO Handle the exception
                            Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        try {
                            PaddyMaha model = PaddyMaha.newInstance(getApplicationContext());

                            // Creates inputs for reference.
                            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 1}, DataType.FLOAT32);
                            inputFeature0.loadBuffer(byteBuffer);

                            // Runs model inference and gets result.
                            PaddyMaha.Outputs outputs = model.process(inputFeature0);
                            @NonNull float[] outputFeature0 = outputs.getOutputFeature0AsTensorBuffer().getFloatArray();


                            Toast.makeText(getApplicationContext(), "Yield : " + outputFeature0[0], Toast.LENGTH_LONG).show();

                            // Releases model resources if no longer used.
                            model.close();
                        } catch (IOException e) {
                            // TODO Handle the exception
                            Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_LONG).show();
                        }
                    }



                }else if(selected_crop.equals("Potato")){
                    try {
                        Potato model = Potato.newInstance(getApplicationContext());

                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 1}, DataType.FLOAT32);
                        inputFeature0.loadBuffer(byteBuffer);

                        // Runs model inference and gets result.
                        Potato.Outputs outputs = model.process(inputFeature0);
                        @NonNull float[] outputFeature0 = outputs.getOutputFeature0AsTensorBuffer().getFloatArray();


                        Toast.makeText(getApplicationContext(), "Yield : " + outputFeature0[0], Toast.LENGTH_LONG).show();
                        // Releases model resources if no longer used.
                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                        Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_LONG).show();
                    }


                }else{

                    try {


                        Onion model = Onion.newInstance(getApplicationContext());

                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 1}, DataType.FLOAT32);
                        inputFeature0.loadBuffer(byteBuffer);

                        // Runs model inference and gets result.
                        Onion.Outputs outputs = model.process(inputFeature0);
                        @NonNull float[] outputFeature0 = outputs.getOutputFeature0AsTensorBuffer().getFloatArray();

                       Toast.makeText(getApplicationContext(), "Yield : " + outputFeature0[0], Toast.LENGTH_LONG).show();

                        // Releases model resources if no longer used.
                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                        Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_LONG).show();
                    }

                }
            }
        });*/




    }

    private String matchArea(String area) {
        switch(area) {
            case "1":
                area ="Welikanda";
                break;
            case "2":
                area ="Aralaganvila";
                break;
            case "3":
                area ="Manampitiya";
                break;
            case "4":
                area ="Dehiaththakandiya";
                break;
            case "5":
                area ="Giradurukotte";
                break;
            case "6":
                area ="Bisopura";
                break;
            case "7":
                area ="Laggala";
                break;
            case "8":
                area ="Nochchiyagama";
                break;
            case "9":
                area ="Thambuththegama";
                break;
            case "10":
                area ="Thalawa";
                break;
            default:
                break;
        }


        return area;
    }

    private String matchSeason(String season) {
        if(season.equals("1")){
            season = "Yala";
        }else{
            season ="Maha";
        }
        return season;
    }

    private String matchCrop(String crop) {
        if(crop.equals("1")){
            crop = "Paddy";
        }else if(crop.equals("2")){
            crop = "Potatoes";
        }else{
            crop = "Onion";
        }
        return crop;
    }

    //convert string to float
    public float inference(String s,String crop,String season ){
        float [] inputValue = new float[1];
        inputValue[0] = Float.valueOf(s);

        float[][] outputVal = new float[1][1];
        if(crop.equals("") && season.equals("")){
            tflite.run(inputValue,outputVal);
        }
        if(crop.equals("Paddy") && season.equals("Yala")){
            tflite_paddy_yala.run(inputValue,outputVal);
        }
        if(crop.equals("Paddy") && season.equals("Maha")){
            tflite_paddy_maha.run(inputValue,outputVal);
        }
        if(crop.equals("Potato") && season.equals("")){
            tflite_potato.run(inputValue,outputVal);
        }
        if(crop.equals("Onion") && season.equals("")){
            tflite_onion.run(inputValue,outputVal);
        }
        float inferedVal = outputVal[0][0];
        return inferedVal;

    }

    //obtaining rainfall prediction
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("rainfall_yala.tflite");
        FileInputStream fileInputStream =  new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);

    }

    //obtaining onion harvest prediction
    private MappedByteBuffer loadOnionModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("Onion.tflite");
        FileInputStream fileInputStream =  new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);

    }
    //obtaining potato prediction
    private MappedByteBuffer loadPotatoModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("Potato.tflite");
        FileInputStream fileInputStream =  new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);

    }
    //obtaining paddy yala prediction
    private MappedByteBuffer loadPaddyYalaModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("paddy_yala.tflite");
        FileInputStream fileInputStream =  new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);

    }
    //obtaining paddy maha prediction
    private MappedByteBuffer loadPaddyMahaModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("paddy_maha.tflite");
        FileInputStream fileInputStream =  new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);

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
            "Laggala", "Bakamoona ",
            "Nochchiyagama", "Thambuththegama",
            "Thalawa"
/*            , "Galnewa",
            "Meegalawa", "Sampathnuwara",
            "Madatugama", "Galkiriyagama",
            "Palugaswewa", "Embilipitiya",
            "Sooriyawewa", "Angunukolapelessa",
            "Digana", "Maha Oya",
            "Padiyathalawa"*/
    };


    public void  filldetails(ArrayList <Model> yield){

        try {
            if (items.size()>0){
                items = new ArrayList<>();
            }
            for (int i = 0; i < yield.size(); i++) {

                Model f = yield.get(i);
                String Crop = f.getCrop();
                String Date = f.getDate();
                String Weight = f.getWeight();

                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("Crop", Crop);
                hm.put("Date", Date);
                hm.put("Weight", Weight);
                items.add(i, hm);
            }


            if (yied_list.getAdapter() != null) {
                yied_list.setAdapter(null);
            }

            String[] from = {"Crop","Date","Weight"};

            int[] to = {R.id.crop_txt,R.id.date_txt,R.id.weight_txt};
            SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), items, R.layout.yield_list_item, from, to);
            yied_list.setAdapter(simpleAdapter);
            yied_list.setSelection(items.size()-1);



        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "List creation failed : " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            System.out.println(e.getLocalizedMessage());
        }


    }

}