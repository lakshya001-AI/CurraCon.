package com.curracon.currencyconverterapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConvertPage extends AppCompatActivity {

    private TextView ConversionDisplaytextview;
    private Spinner spinnerconertfrom;
    private Spinner spinnerconertto;

    private EditText Amountedittext;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_page);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Button convertButton = findViewById(R.id.convertbutton);
        ConversionDisplaytextview  = findViewById(R.id.conversionvaluetextview);

        spinnerconertfrom = findViewById(R.id.convertfromspinner);
        spinnerconertto = findViewById(R.id.converttospinner);

        Amountedittext = findViewById(R.id.amountedittext);


        String[] convertfromitems = {"AUD", "BYN", "CAD","USD","EUR","RUB","AED", "CNY","EGP","HKD", "INR", "JPY", "KWD", "MYR", "NZD", "SAR", "SGD","LKR"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.my_selected_item, convertfromitems);
        adapter1.setDropDownViewResource(R.layout.my_drop_down_item);
        spinnerconertfrom.setAdapter(adapter1);

        spinnerconertfrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





        String[] conerttoitems = {"AUD", "BYN", "CAD","USD","EUR","RUB","AED", "CNY","EGP","HKD", "INR", "JPY", "KWD", "MYR", "NZD", "SAR", "SGD","LKR"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,R.layout.my_selected_item , conerttoitems);
        adapter2.setDropDownViewResource(R.layout.my_drop_down_item);
        spinnerconertto.setAdapter(adapter2);

        spinnerconertto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TextView noteTextview = findViewById(R.id.noteText);
        noteTextview.setOnClickListener(v -> {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("https://static.developer.mastercard.com/content/cross-border-services/uploads/ISO%204217%20Currency%20Codes.pdf"));
            startActivity(intent);

        });

        convertButton.setOnClickListener(v -> {

            String from = spinnerconertfrom.getSelectedItem().toString();
            String To = spinnerconertto.getSelectedItem().toString();

            String urlString = "https://api.exchangerate.host/latest?base=" + from.toUpperCase();


            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(urlString).get().build();

            Thread thread = new Thread(() -> {

                try {
                    Response response = client.newCall(request).execute();
                    String stringResponse = response.body().string();

                    JSONObject jsonObject = new JSONObject(stringResponse);
                    JSONObject ratesObject =jsonObject.getJSONObject("rates");

                    DecimalFormat decimalFormat1 = new DecimalFormat("#.#####");

                    Float rate = (float) ratesObject.getInt(To.toUpperCase());

                    Float rate1 = Float.valueOf(decimalFormat1.format(rate));
                    Float conversionquantity = Float.valueOf(Amountedittext.getText().toString());

                   String result = String.valueOf(rate1 * conversionquantity);

                   ConversionDisplaytextview.setText(result);

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        });





    }


}