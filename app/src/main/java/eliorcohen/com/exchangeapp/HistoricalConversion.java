package eliorcohen.com.exchangeapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class HistoricalConversion extends AppCompatActivity implements View.OnClickListener {

    private LineChart mChart1, mChart2;
    private ArrayList<Entry> yValues1, yValues2;
    private ArrayList<ILineDataSet> dataSets1, dataSets2;
    private double date1, date2, date3, date4, date5, date6, date7, date8, date9,
            date11, date22, date33, date44, date55, date66, date77, date88, date99;
    private LineDataSet set1, set2;
    private LineData lineData1, lineData2;
    private ArrayAdapter<String> spinnerArrayAdapterHistory1, spinnerArrayAdapterHistory2, spinnerArrayAdapterHistory3;
    private ArrayList<String> stringArrayListHistory1, stringArrayListHistory2, stringArrayListHistory3;
    private Spinner spinnerHistory1, spinnerHistory2, spinnerHistory3;
    private Button btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);

        initUI();
        initListeners();
        getData(getItemSpinner1(), getItemSpinner2());
        getData2();
    }

    private void initUI() {
        mChart1 = findViewById(R.id.linechart1);
        mChart2 = findViewById(R.id.linechart2);
        spinnerHistory1 = findViewById(R.id.spinnerHistory1);
        spinnerHistory2 = findViewById(R.id.spinnerHistory2);
        spinnerHistory3 = findViewById(R.id.spinnerHistory3);
        btnHistory = findViewById(R.id.btnHistory);

        mChart1.setDragEnabled(true);
        mChart1.setScaleEnabled(true);
        mChart2.setDragEnabled(true);
        mChart2.setScaleEnabled(true);

        yValues1 = new ArrayList<>();
        yValues2 = new ArrayList<>();
        dataSets1 = new ArrayList<>();
        dataSets2 = new ArrayList<>();
        stringArrayListHistory1 = new ArrayList<String>();
        stringArrayListHistory2 = new ArrayList<String>();
        stringArrayListHistory3 = new ArrayList<String>();
    }

    private void initListeners() {
        btnHistory.setOnClickListener(this);
    }

    private void getData(final String fromHistory, final String toHistory) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free.currconv.com/api/v7/convert?apiKey="
                + getString(R.string.api_key) +
                "&q=" + fromHistory + "_" + toHistory + "," + toHistory + "_" + fromHistory + "&compact=ultra&date="
                + getBeforeEightDateString(8) +
                "&endDate=" + getBeforeEightDateString(0), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    yValues1.clear();
                    yValues2.clear();
                    dataSets1.clear();
                    dataSets2.clear();
                    mChart1.invalidate();
                    mChart2.invalidate();
                    mChart1.clear();
                    mChart2.clear();

                    JSONObject mainObj = new JSONObject(response);

                    JSONObject mainObj2 = mainObj.getJSONObject(fromHistory + "_" + toHistory);
                    date1 = mainObj2.getDouble(getBeforeEightDateString(0));
                    date2 = mainObj2.getDouble(getBeforeEightDateString(1));
                    date3 = mainObj2.getDouble(getBeforeEightDateString(2));
                    date4 = mainObj2.getDouble(getBeforeEightDateString(3));
                    date5 = mainObj2.getDouble(getBeforeEightDateString(4));
                    date6 = mainObj2.getDouble(getBeforeEightDateString(5));
                    date7 = mainObj2.getDouble(getBeforeEightDateString(6));
                    date8 = mainObj2.getDouble(getBeforeEightDateString(7));
                    date9 = mainObj2.getDouble(getBeforeEightDateString(8));

                    JSONObject mainObj3 = mainObj.getJSONObject(toHistory + "_" + fromHistory);
                    date11 = mainObj3.getDouble(getBeforeEightDateString(0));
                    date22 = mainObj3.getDouble(getBeforeEightDateString(1));
                    date33 = mainObj3.getDouble(getBeforeEightDateString(2));
                    date44 = mainObj3.getDouble(getBeforeEightDateString(3));
                    date55 = mainObj3.getDouble(getBeforeEightDateString(4));
                    date66 = mainObj3.getDouble(getBeforeEightDateString(5));
                    date77 = mainObj3.getDouble(getBeforeEightDateString(6));
                    date88 = mainObj3.getDouble(getBeforeEightDateString(7));
                    date99 = mainObj3.getDouble(getBeforeEightDateString(8));

                    yValues1.add(new Entry(0, (float) date1));
                    yValues1.add(new Entry(1, (float) date2));
                    yValues1.add(new Entry(2, (float) date3));
                    yValues1.add(new Entry(3, (float) date4));
                    yValues1.add(new Entry(4, (float) date5));
                    yValues1.add(new Entry(5, (float) date6));
                    yValues1.add(new Entry(6, (float) date7));
                    yValues1.add(new Entry(7, (float) date8));
                    yValues1.add(new Entry(8, (float) date9));

                    yValues2.add(new Entry(0, (float) date11));
                    yValues2.add(new Entry(1, (float) date22));
                    yValues2.add(new Entry(2, (float) date33));
                    yValues2.add(new Entry(3, (float) date44));
                    yValues2.add(new Entry(4, (float) date55));
                    yValues2.add(new Entry(5, (float) date66));
                    yValues2.add(new Entry(6, (float) date77));
                    yValues2.add(new Entry(7, (float) date88));
                    yValues2.add(new Entry(8, (float) date99));

                    int selectedColor = Color.rgb(0, 204, 0);

                    set1 = new LineDataSet(yValues1, "Exchange set " + fromHistory + " to " + toHistory);
                    set1.setFillAlpha(110);
                    set1.setLineWidth(3f);
                    set1.setValueTextSize(10f);
                    set1.setColor(Color.RED);
                    set1.setValueTextColor(selectedColor);
                    set1.setCircleColorHole(Color.CYAN);

                    set2 = new LineDataSet(yValues2, "Exchange set " + toHistory + " to " + fromHistory);
                    set2.setFillAlpha(110);
                    set2.setLineWidth(3f);
                    set2.setValueTextSize(10f);
                    set2.setColor(Color.BLUE);
                    set2.setValueTextColor(selectedColor);
                    set2.setCircleColorHole(Color.CYAN);

                    dataSets1.add(set1);
                    lineData1 = new LineData(dataSets1);
                    mChart1.setData(lineData1);

                    dataSets2.add(set2);
                    lineData2 = new LineData(dataSets2);
                    mChart2.setData(lineData2);
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

    private void getData2() {
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
                            stringArrayListHistory1.add(key);
                            stringArrayListHistory2.add(key);
                        }
                    }
                    spinnerArrayAdapterHistory1 = new ArrayAdapter<String>(HistoricalConversion.this, R.layout.spinner_item, stringArrayListHistory1);
                    spinnerArrayAdapterHistory1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerHistory1.setAdapter(spinnerArrayAdapterHistory1);

                    spinnerArrayAdapterHistory2 = new ArrayAdapter<String>(HistoricalConversion.this, R.layout.spinner_item, stringArrayListHistory2);
                    spinnerArrayAdapterHistory2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerHistory2.setAdapter(spinnerArrayAdapterHistory2);
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
        String meExch = String.valueOf(spinnerHistory1.getSelectedItem());
        return meExch;
    }

    private String getItemSpinner2() {
        String meExch = String.valueOf(spinnerHistory2.getSelectedItem());
        return meExch;
    }

    private String getItemSpinner3() {
        String meExch = String.valueOf(spinnerHistory3.getSelectedItem());
        return meExch;
    }

    private String getBeforeEightDateString(int num) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -num);
        return dateFormat.format(cal.getTime());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHistory:
                getData(getItemSpinner1(), getItemSpinner2());
                getData2();
                break;
        }
    }

}
