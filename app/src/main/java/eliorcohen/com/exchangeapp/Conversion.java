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

    private ArrayAdapter<String> spinnerArrayAdapterExch1, spinnerArrayAdapterExch2;
    private ArrayList<String> stringArrayListExch1, stringArrayListExch2;
    private Spinner spinnerExch1, spinnerExch2;
    private Button myBtnConversion;
    private TextView myTextConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initUI();
        initListeners();
        getData();
        getData2(getItemSpinner1(), getItemSpinner2());
    }

    private void initUI() {
        spinnerExch1 = findViewById(R.id.spinnerExch1);
        spinnerExch2 = findViewById(R.id.spinnerExch2);
        myBtnConversion = findViewById(R.id.myBtnConversion);
        myTextConversion = findViewById(R.id.myTextConversion);

        stringArrayListExch1 = new ArrayList<String>();
        stringArrayListExch2 = new ArrayList<String>();
    }

    private void initListeners() {
        myBtnConversion.setOnClickListener(this);
    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free.currconv.com/api/v7/currencies?apiKey=" + getString(R.string.api_key), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    JSONObject mainObj2 = mainObj.getJSONObject("results");
                    Iterator<String> keys = mainObj2.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (mainObj2.get(key) instanceof JSONObject) {
//                            String valueStr = mainObj2.getString(key);
                            stringArrayListExch1.add(key);
                            stringArrayListExch2.add(key);
                        }
                    }
                    spinnerArrayAdapterExch1 = new ArrayAdapter<String>(Conversion.this, R.layout.spinner_item, stringArrayListExch1);
                    spinnerArrayAdapterExch1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerExch1.setAdapter(spinnerArrayAdapterExch1);

                    spinnerArrayAdapterExch2 = new ArrayAdapter<String>(Conversion.this, R.layout.spinner_item, stringArrayListExch2);
                    spinnerArrayAdapterExch2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerExch2.setAdapter(spinnerArrayAdapterExch2);
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

    private void getData2(final String fromExch, final String toExch) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free.currconv.com/api/v7/convert?q="
                + fromExch + "_" + toExch +
                "&compact=ultra&apiKey=" + getString(R.string.api_key), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    double mainObj2 = mainObj.getDouble(fromExch + "_" + toExch);
                    myTextConversion.setText(String.valueOf(mainObj2));
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

    private String getItemSpinner1() {
        String meExch = String.valueOf(spinnerExch1.getSelectedItem());
        return meExch;
    }

    private String getItemSpinner2() {
        String meExch = String.valueOf(spinnerExch2.getSelectedItem());
        return meExch;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myBtnConversion:
                getData();
                getData2(getItemSpinner1(), getItemSpinner2());
                break;
        }
    }

}
