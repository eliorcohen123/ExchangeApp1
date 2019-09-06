package eliorcohen.com.exchangeapp.ClassesPck;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import eliorcohen.com.exchangeapp.R;

public class Conversion extends AppCompatActivity implements View.OnClickListener {

    private ArrayAdapter<String> spinnerArrayAdapterExchFrom, spinnerArrayAdapterExchTo;
    private ArrayList<String> stringArrayListExchFrom, stringArrayListExchTo;
    private Spinner spinnerExchFrom, spinnerExchTo;
    private ImageView myBtnConversion;
    private TextView myTextConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }
        setContentView(R.layout.activity_conversion);

        initUI();
        initListeners();
    }

    private void initUI() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
                + getString(R.string.api_key2), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    JSONObject results = mainObj.getJSONObject("results");

                    Iterator<String> keys = results.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (results.get(key) instanceof JSONObject) {
                            String valueCurrencySymbol = results.getString(key);
                            JSONObject mainObjKey = new JSONObject(valueCurrencySymbol);
                            if (mainObjKey.has("currencySymbol")) {
                                String valueCurrencySymbolGet = mainObjKey.getString("currencySymbol");
                                stringArrayListExchFrom.add(key + "=" + valueCurrencySymbolGet);
                                stringArrayListExchTo.add(key + "=" + valueCurrencySymbolGet);
                            } else {
                                stringArrayListExchFrom.add(key);
                                stringArrayListExchTo.add(key);
                            }
                        }
                    }

                    getCollections(stringArrayListExchFrom);
                    getCollections(stringArrayListExchTo);
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
                "&compact=ultra&apiKey=" + getString(R.string.api_key1), new com.android.volley.Response.Listener<String>() {
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

    private void getCollections(ArrayList<String> arrayList) {
        Collections.sort(arrayList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
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
                getDataCurrconv(getItemSpinnerFrom().substring(0, 3), getItemSpinnerTo().substring(0, 3));
                break;
        }
    }

}
