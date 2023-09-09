package com.curracon.currencyconverterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainPage extends AppCompatActivity {

    private TextView USDtoINR;
    private TextView GBPtoINR;
    private TextView CADtoINR;
    private Button convertButton;

    private int images[];
    private SlideData12 slideData12;
    private SliderView sliderView;
    private TextView messageTextview;
    private TextView messagetextview1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        USDtoINR = findViewById(R.id.usdtoinrtextview);
        GBPtoINR = findViewById(R.id.gbptoinrtextview);
        CADtoINR = findViewById(R.id.cadtoinrtextview);

        convertButton = findViewById(R.id.convertnowbutton);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConvertPage.class);
                startActivity(intent);
            }
        });

        sliderView = findViewById(R.id.slider);
        images = new int[]{R.drawable.image11, R.drawable.image22, R.drawable.image33};
        slideData12 = new SlideData12(images);
        sliderView.setSliderAdapter(slideData12);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        sliderView.startAutoCycle();
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        messageTextview = findViewById(R.id.wishtextview);
        messagetextview1 = findViewById(R.id.messagetextview1);

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        String greetingMessage;
        if (hourOfDay >= 0 && hourOfDay < 12) {
            greetingMessage = "Good Morning,";
        } else if (hourOfDay >= 12 && hourOfDay < 18) {
            greetingMessage = "Good Afternoon,";
        } else {
            greetingMessage = "Good Evening,";
        }
        messageTextview.setText(greetingMessage);

        Calendar calendar1 = Calendar.getInstance();
        int hourOfDay1 = calendar1.get(Calendar.HOUR_OF_DAY);
        String greetingMessage1;
        if (hourOfDay1 >= 0 && hourOfDay1 < 12) {
            greetingMessage1 = "\"Start your day with seamless conversions!\"";
        } else if (hourOfDay1 >= 12 && hourOfDay1 < 18) {
            greetingMessage1 = "\"Converting made easy. Good afternoon!\"";
        } else {
            greetingMessage1 = "\"Wrap up the day with accurate conversions.\"";
        }
        messagetextview1.setText(greetingMessage1);




        String urlString = "https://api.exchangerate.host/latest?base=USD";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlString).get().build();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    String stringResponse = response.body().string();

                    JSONObject jsonObject = new JSONObject(stringResponse);
                    JSONObject ratesObject =jsonObject.getJSONObject("rates");
                    Double rate = ratesObject.getDouble("INR");

                    DecimalFormat decimalFormat = new DecimalFormat("#.###");
                    String formatetedvalue = decimalFormat.format(rate);

                    USDtoINR.setText(formatetedvalue);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();

        String urlStringgbp = "https://api.exchangerate.host/latest?base=GBP";

        OkHttpClient clientgbp = new OkHttpClient();
        Request requestgbp = new Request.Builder().url(urlStringgbp).get().build();

        Thread threadgbp = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Response response = clientgbp.newCall(requestgbp).execute();
                    String stringResponse = response.body().string();

                    JSONObject jsonObject = new JSONObject(stringResponse);
                    JSONObject ratesObject =jsonObject.getJSONObject("rates");
                    Double rate = ratesObject.getDouble("INR");

                    DecimalFormat decimalFormat = new DecimalFormat("#.###");
                    String formatetedvalue = decimalFormat.format(rate);

                    GBPtoINR.setText(formatetedvalue);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        threadgbp.start();

        String urlStringcad = "https://api.exchangerate.host/latest?base=CAD";

        OkHttpClient clientcad = new OkHttpClient();
        Request requestcad = new Request.Builder().url(urlStringcad).get().build();

        Thread threadcad = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Response response = clientcad.newCall(requestcad).execute();
                    String stringResponse = response.body().string();

                    JSONObject jsonObject = new JSONObject(stringResponse);
                    JSONObject ratesObject =jsonObject.getJSONObject("rates");
                    Double rate = ratesObject.getDouble("INR");

                    DecimalFormat decimalFormat = new DecimalFormat("#.###");
                    String formatetedvalue = decimalFormat.format(rate);

                    CADtoINR.setText(formatetedvalue);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        threadcad.start();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_logout){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setIcon(getDrawable(R.drawable.curracon12));
            alertDialogBuilder.setTitle("Logout App");
            alertDialogBuilder.setMessage("Are You Sure You Want Logout ?  ");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), LoginPageCC.class);
                    startActivity(intent);
                    finish();
                }
            });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setIcon(getDrawable(R.drawable.curracon12)).setTitle("Exit App !")
                .setMessage("Do you want to exit the app?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Exit the app
                    Intent homeScreenIntent = new Intent(Intent.ACTION_MAIN);
                    homeScreenIntent.addCategory(Intent.CATEGORY_HOME);
                    homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(homeScreenIntent);
                    finish();

                })
                .setNegativeButton("No", (dialog, which) -> {
                    // Do nothing and dismiss the dialog
                    dialog.dismiss();
                })
                .show();
    }
}