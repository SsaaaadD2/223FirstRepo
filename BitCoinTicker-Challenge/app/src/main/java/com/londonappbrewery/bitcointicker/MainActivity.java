package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";
    private final String API_KEY= "mustreplace";

    // Member Variables:
    TextView mPriceTextView;
    RadioButton bidRB;
    JSONObject mJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        mJson = new JSONObject();
        bidRB = findViewById(R.id.bidRB);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                Log.d("Bitcoin", parent.getItemAtPosition(pos) + " was clicked");
                String url = BASE_URL + parent.getItemAtPosition(pos);
                letsDoSomeNetworking(url);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                Log.d("Bitcoin", "Nothing selected.");
            }

        });

    }



    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) {

//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // called when response HTTP status is "200 OK"
//                Log.d("Clima", "JSON: " + response.toString());
//                WeatherDataModel weatherData = WeatherDataModel.fromJson(response);
//                updateUI(weatherData);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
//                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
//                Log.d("Clima", "Request fail! Status code: " + statusCode);
//                Log.d("Clima", "Fail response: " + response);
//                Log.e("ERROR", e.toString());
//                Toast.makeText(WeatherController.this, "Request Failed", Toast.LENGTH_SHORT).show();
//            }
//        });

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-ba-key", API_KEY);

        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String price = response.getString("bid");
                    Log.d("Bitcoin", "Bid is " + price);
                    mJson = response;
                    bidRB.performClick();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Log.d("Bitcoin", responseString);

                Toast.makeText(getApplicationContext(), "Could not get prices", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(String uiText){
        mPriceTextView.setText(uiText);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        String text = "";

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.bidRB:
                Log.d("Bitcoin", "Bid RB pressed");
                try {
                    text = mJson.getString("bid");
                    break;
                } catch (JSONException e){
                    e.printStackTrace();
                    break;
                }
            case R.id.lowRB:
                Log.d("Bitcoin", "Low RB pressed");
                try {
                    text = mJson.getString("low");
                    break;
                } catch (JSONException e){
                    e.printStackTrace();
                    break;
                }
            case R.id.lastRB:
                Log.d("Bitcoin", "Last RB pressed");
                try {
                    text = mJson.getString("last");
                    break;
                } catch (JSONException e){
                    e.printStackTrace();
                    break;
                }
        }
        updateUI(text);
    }

}
