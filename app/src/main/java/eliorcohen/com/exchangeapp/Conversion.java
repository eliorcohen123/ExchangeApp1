package eliorcohen.com.exchangeapp;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Conversion extends AppCompatActivity implements View.OnClickListener {

    private ArrayAdapter<String> spinnerArrayAdapterExchFrom, spinnerArrayAdapterExchTo;
    private ArrayList<String> stringArrayListExchFrom, stringArrayListExchTo;
    private Spinner spinnerExchFrom, spinnerExchTo;
    private Button myBtnConversion;
    private TextView myTextConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }
        setContentView(R.layout.activity_conversion);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initUI();
        initListeners();
    }

    private void initUI() {
        spinnerExchFrom = findViewById(R.id.spinnerExchFrom);
        spinnerExchTo = findViewById(R.id.spinnerExchTo);
        myBtnConversion = findViewById(R.id.myBtnConversion);
        myTextConversion = findViewById(R.id.myTextConversion);

        stringArrayListExchFrom = new ArrayList<String>();
        stringArrayListExchTo = new ArrayList<String>();
    }

    private void initListeners() {
        myBtnConversion.setOnClickListener(this);
    }

    private void getDataCurrencies() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free.currconv.com/api/v7/currencies?apiKey="
                + getString(R.string.api_key), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    JSONObject results = mainObj.getJSONObject("results");

                    Iterator<String> keys = results.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (results.get(key) instanceof JSONObject) {
                            stringArrayListExchFrom.add(key);
                            stringArrayListExchTo.add(key);
                        }
                    }

                    spinnerArrayAdapterExchFrom = new ArrayAdapter<String>(Conversion.this, R.layout.spinner_item, stringArrayListExchFrom);
                    spinnerArrayAdapterExchFrom.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerExchFrom.setAdapter(spinnerArrayAdapterExchFrom);

                    spinnerArrayAdapterExchTo = new ArrayAdapter<String>(Conversion.this, R.layout.spinner_item, stringArrayListExchTo);
                    spinnerArrayAdapterExchTo.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerExchTo.setAdapter(spinnerArrayAdapterExchTo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getDataCurrconv(final String fromExch, final String toExch) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free.currconv.com/api/v7/convert?q="
                + fromExch + "_" + toExch +
                "&compact=ultra&apiKey=" + getString(R.string.api_key), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    double nameConvert = mainObj.getDouble(fromExch + "_" + toExch);
                    myTextConversion.setText(String.valueOf(nameConvert));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private String getItemSpinnerFrom() {
        String meExchFrom = String.valueOf(spinnerExchFrom.getSelectedItem());
        return meExchFrom;
    }

    private String getItemSpinnerTo() {
        String meExchTo = String.valueOf(spinnerExchTo.getSelectedItem());
        return meExchTo;
    }

    @Override
    protected void onResume() {
        super.onResume();

        getDataCurrencies();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myBtnConversion:
                getDataCurrconv(getItemSpinnerFrom(), getItemSpinnerTo());
                break;
        }
    }

}
