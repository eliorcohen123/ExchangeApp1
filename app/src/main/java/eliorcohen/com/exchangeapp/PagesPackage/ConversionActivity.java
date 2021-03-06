package eliorcohen.com.exchangeapp.PagesPackage;

import android.app.ProgressDialog;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import eliorcohen.com.exchangeapp.InterfacesPackage.ConversionInterface;
import eliorcohen.com.exchangeapp.R;

public class ConversionActivity extends AppCompatActivity implements ConversionInterface, View.OnClickListener {

    private ArrayList<String> stringArrayListExchFromTo;
    private Spinner spinnerExchFrom, spinnerExchTo;
    private ImageView myBtnConversion;
    private TextView myTextConversion;
    private ProgressDialog progressDialog;
    private EditText myEditTextAmountConversion;

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

    @Override
    protected void onResume() {
        super.onResume();

        getDataCurrencies();
    }

    private void initUI() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        spinnerExchFrom = findViewById(R.id.spinnerExchFrom);
        spinnerExchTo = findViewById(R.id.spinnerExchTo);
        myBtnConversion = findViewById(R.id.myBtnConversion);
        myTextConversion = findViewById(R.id.myTextConversion);
        myEditTextAmountConversion = findViewById(R.id.myEditTextAmountConversion);

        stringArrayListExchFromTo = new ArrayList<String>();

        progressDialog = new ProgressDialog(this);

        startProgressDialog();
    }

    private void initListeners() {
        myBtnConversion.setOnClickListener(this);
    }

    private void getDataCurrencies() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free.currconv.com/api/v7/currencies?apiKey="
                + getString(R.string.api_key2), response -> {
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
                            stringArrayListExchFromTo.add(key + "=" + valueCurrencySymbolGet);
                        } else {
                            stringArrayListExchFromTo.add(key);
                        }
                    }
                }

                getCollections(stringArrayListExchFromTo);

                createSpinner(stringArrayListExchFromTo, spinnerExchFrom);
                createSpinner(stringArrayListExchFromTo, spinnerExchTo);

                stopProgressDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getDataCurrconv(final String fromExch, final String toExch) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free.currconv.com/api/v7/convert?q="
                + fromExch + "_" + toExch +
                "&compact=ultra&apiKey=" + getString(R.string.api_key1), response -> {
            try {
                JSONObject mainObj = new JSONObject(response);
                double AmountConvert = mainObj.getDouble(fromExch + "_" + toExch);
                double myAmount = Double.parseDouble(myEditTextAmountConversion.getText().toString());
                myTextConversion.setText(String.valueOf(myAmount * AmountConvert));

                stopProgressDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getCollections(ArrayList<String> arrayList) {
        Collections.sort(arrayList, String::compareToIgnoreCase);
    }

    private void createSpinner(ArrayList<String> arrayList, Spinner spinner) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(arrayAdapter);
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
    public void startProgressDialog() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myBtnConversion:
                getDataCurrconv(getItemSpinnerFrom().substring(0, 3), getItemSpinnerTo().substring(0, 3));

                startProgressDialog();
                break;
        }
    }

}
