package eliorcohen.com.exchangeapp.ClassesPck;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import eliorcohen.com.exchangeapp.R;

public class HistoricalConversion extends AppCompatActivity implements View.OnClickListener {

    private LineChart mChartFrom, mChartTo;
    private ArrayList<Entry> yValuesFrom, yValuesTo;
    private ArrayList<ILineDataSet> dataSetsFrom, dataSetsTo;
    private double dateFrom1, dateFrom2, dateFrom3, dateFrom4, dateFrom5, dateFrom6, dateFrom7, dateFrom8, dateFrom9,
            dateTo1, dateTo2, dateTo3, dateTo4, dateTo5, dateTo6, dateTo7, dateTo8, dateTo9;
    private YAxis yAxisRightFrom, yAxisRightTo;
    private YAxis yAxisLeftFrom, yAxisLeftTo;
    private XAxis xAxisFrom, xAxisTo;
    private Legend legendFrom, legendTo;
    private LineDataSet setFrom, setTo;
    private LineData lineDataFrom, lineDataTo;
    private ArrayAdapter<String> spinnerArrayAdapterHistoryFrom, spinnerArrayAdapterHistoryTo, spinnerArrayAdapterHistory3;
    private ArrayList<String> stringArrayListHistoryFrom, stringArrayListHistoryTo, stringArrayListHistory3;
    private Spinner spinnerHistoryFrom, spinnerHistoryTo, spinnerHistory3;
    private ImageView btnHistory;
    private int selectedColorText, selectedColorSeparate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }
        setContentView(R.layout.activity_history);

        initUI();
        initListeners();
        getSpinner3();
    }

    private void initUI() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mChartFrom = findViewById(R.id.linechart1);
        mChartTo = findViewById(R.id.linechart2);
        spinnerHistoryFrom = findViewById(R.id.spinnerHistoryFrom);
        spinnerHistoryTo = findViewById(R.id.spinnerHistoryTo);
        spinnerHistory3 = findViewById(R.id.spinnerHistory3);
        btnHistory = findViewById(R.id.btnHistory);

        mChartFrom.setDragEnabled(true);
        mChartFrom.setScaleEnabled(true);
        mChartTo.setDragEnabled(true);
        mChartTo.setScaleEnabled(true);

        yValuesFrom = new ArrayList<>();
        yValuesTo = new ArrayList<>();
        dataSetsFrom = new ArrayList<>();
        dataSetsTo = new ArrayList<>();
        stringArrayListHistoryFrom = new ArrayList<String>();
        stringArrayListHistoryTo = new ArrayList<String>();
        stringArrayListHistory3 = new ArrayList<String>();
    }

    private void initListeners() {
        btnHistory.setOnClickListener(this);
    }

    private void getDataHistory(final String fromHistory, final String toHistory, String spinnerFrom, String spinnerTo) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free.currconv.com/api/v7/convert?apiKey="
                + getString(R.string.api_key1) +
                "&q=" + fromHistory + "_" + toHistory + "," + toHistory + "_" + fromHistory + "&compact=ultra&date="
                + spinnerFrom +
                "&endDate=" + spinnerTo, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                yValuesFrom.clear();
                yValuesTo.clear();
                dataSetsFrom.clear();
                dataSetsTo.clear();
                mChartFrom.invalidate();
                mChartTo.invalidate();
                mChartFrom.clear();
                mChartTo.clear();
                try {
                    JSONObject mainObj = new JSONObject(response);

                    JSONObject mainObj2 = mainObj.getJSONObject(fromHistory + "_" + toHistory);
                    dateFrom1 = mainObj2.getDouble(getBeforeEightDateString3(0));
                    dateFrom2 = mainObj2.getDouble(getBeforeEightDateString3(1));
                    dateFrom3 = mainObj2.getDouble(getBeforeEightDateString3(2));
                    dateFrom4 = mainObj2.getDouble(getBeforeEightDateString3(3));
                    dateFrom5 = mainObj2.getDouble(getBeforeEightDateString3(4));
                    dateFrom6 = mainObj2.getDouble(getBeforeEightDateString3(5));
                    dateFrom7 = mainObj2.getDouble(getBeforeEightDateString3(6));
                    dateFrom8 = mainObj2.getDouble(getBeforeEightDateString3(7));
                    dateFrom9 = mainObj2.getDouble(getBeforeEightDateString3(8));

                    JSONObject mainObj3 = mainObj.getJSONObject(toHistory + "_" + fromHistory);
                    dateTo1 = mainObj3.getDouble(getBeforeEightDateString3(0));
                    dateTo2 = mainObj3.getDouble(getBeforeEightDateString3(1));
                    dateTo3 = mainObj3.getDouble(getBeforeEightDateString3(2));
                    dateTo4 = mainObj3.getDouble(getBeforeEightDateString3(3));
                    dateTo5 = mainObj3.getDouble(getBeforeEightDateString3(4));
                    dateTo6 = mainObj3.getDouble(getBeforeEightDateString3(5));
                    dateTo7 = mainObj3.getDouble(getBeforeEightDateString3(6));
                    dateTo8 = mainObj3.getDouble(getBeforeEightDateString3(7));
                    dateTo9 = mainObj3.getDouble(getBeforeEightDateString3(8));

                    yValuesFrom.add(new Entry(0, (float) dateFrom1));
                    yValuesFrom.add(new Entry(1, (float) dateFrom2));
                    yValuesFrom.add(new Entry(2, (float) dateFrom3));
                    yValuesFrom.add(new Entry(3, (float) dateFrom4));
                    yValuesFrom.add(new Entry(4, (float) dateFrom5));
                    yValuesFrom.add(new Entry(5, (float) dateFrom6));
                    yValuesFrom.add(new Entry(6, (float) dateFrom7));
                    yValuesFrom.add(new Entry(7, (float) dateFrom8));
                    yValuesFrom.add(new Entry(8, (float) dateFrom9));

                    yValuesTo.add(new Entry(0, (float) dateTo1));
                    yValuesTo.add(new Entry(1, (float) dateTo2));
                    yValuesTo.add(new Entry(2, (float) dateTo3));
                    yValuesTo.add(new Entry(3, (float) dateTo4));
                    yValuesTo.add(new Entry(4, (float) dateTo5));
                    yValuesTo.add(new Entry(5, (float) dateTo6));
                    yValuesTo.add(new Entry(6, (float) dateTo7));
                    yValuesTo.add(new Entry(7, (float) dateTo8));
                    yValuesTo.add(new Entry(8, (float) dateTo9));

                    selectedColorText = Color.rgb(0, 204, 0);
                    selectedColorSeparate = Color.rgb(255, 255, 255);

                    yAxisRightFrom = mChartFrom.getAxisRight();
                    yAxisLeftFrom = mChartFrom.getAxisLeft();
                    xAxisFrom = mChartFrom.getXAxis();
                    legendFrom = mChartFrom.getLegend();
                    yAxisRightTo = mChartTo.getAxisRight();
                    yAxisLeftTo = mChartTo.getAxisLeft();
                    xAxisTo = mChartTo.getXAxis();
                    legendTo = mChartTo.getLegend();

                    yAxisRightFrom.setTextColor(selectedColorSeparate);
                    yAxisLeftFrom.setTextColor(selectedColorSeparate);
                    xAxisFrom.setTextColor(selectedColorSeparate);
                    legendFrom.setTextColor(selectedColorSeparate);
                    yAxisRightTo.setTextColor(selectedColorSeparate);
                    yAxisLeftTo.setTextColor(selectedColorSeparate);
                    xAxisTo.setTextColor(selectedColorSeparate);
                    legendTo.setTextColor(selectedColorSeparate);

                    setFrom = new LineDataSet(yValuesFrom, "Exchange set " + fromHistory + " to " + toHistory);
                    setFrom.setFillAlpha(110);
                    setFrom.setLineWidth(3f);
                    setFrom.setValueTextSize(10f);
                    setFrom.setColor(Color.RED);
                    setFrom.setValueTextColor(selectedColorText);
                    setFrom.setCircleColorHole(Color.CYAN);

                    setTo = new LineDataSet(yValuesTo, "Exchange set " + toHistory + " to " + fromHistory);
                    setTo.setFillAlpha(110);
                    setTo.setLineWidth(3f);
                    setTo.setValueTextSize(10f);
                    setTo.setColor(Color.BLUE);
                    setTo.setValueTextColor(selectedColorText);
                    setTo.setCircleColorHole(Color.CYAN);

                    dataSetsFrom.add(setFrom);
                    lineDataFrom = new LineData(dataSetsFrom);
                    mChartFrom.setData(lineDataFrom);

                    dataSetsTo.add(setTo);
                    lineDataTo = new LineData(dataSetsTo);
                    mChartTo.setData(lineDataTo);
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

    private void getDataCurrencies() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free.currconv.com/api/v7/currencies?apiKey="
                + getString(R.string.api_key1), new com.android.volley.Response.Listener<String>() {
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
                                stringArrayListHistoryFrom.add(key + "=" + valueCurrencySymbolGet);
                                stringArrayListHistoryTo.add(key + "=" + valueCurrencySymbolGet);
                            } else {
                                stringArrayListHistoryFrom.add(key);
                                stringArrayListHistoryTo.add(key);
                            }
                        }
                    }

                    getCollections(stringArrayListHistoryFrom);
                    getCollections(stringArrayListHistoryTo);
                    spinnerArrayAdapterHistoryFrom = new ArrayAdapter<String>(HistoricalConversion.this, R.layout.spinner_item, stringArrayListHistoryFrom);
                    spinnerArrayAdapterHistoryFrom.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerHistoryFrom.setAdapter(spinnerArrayAdapterHistoryFrom);

                    spinnerArrayAdapterHistoryTo = new ArrayAdapter<String>(HistoricalConversion.this, R.layout.spinner_item, stringArrayListHistoryTo);
                    spinnerArrayAdapterHistoryTo.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerHistoryTo.setAdapter(spinnerArrayAdapterHistoryTo);
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

    private void getSpinner3() {
        stringArrayListHistory3.clear();
        for (int i = 0; i < 358; i++) {
            if (i < 10) {
                stringArrayListHistory3.add(getBeforeEightDateString(i) + "->" + getBeforeEightDateString2(i) + "(" + "00" + i + ")");
            } else if (i < 100) {
                stringArrayListHistory3.add(getBeforeEightDateString(i) + "->" + getBeforeEightDateString2(i) + "(" + "0" + i + ")");
            } else {
                stringArrayListHistory3.add(getBeforeEightDateString(i) + "->" + getBeforeEightDateString2(i) + "(" + i + ")");
            }
        }

        spinnerArrayAdapterHistory3 = new ArrayAdapter<String>(HistoricalConversion.this, R.layout.spinner_item, stringArrayListHistory3);
        spinnerArrayAdapterHistory3.setDropDownViewResource(R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerHistory3.setAdapter(spinnerArrayAdapterHistory3);
    }

    private String getItemSpinnerFrom() {
        String meHistoryFrom = String.valueOf(spinnerHistoryFrom.getSelectedItem());
        return meHistoryFrom;
    }

    private String getItemSpinnerTo() {
        String meHistoryTo = String.valueOf(spinnerHistoryTo.getSelectedItem());
        return meHistoryTo;
    }

    private String getItemSpinner3() {
        String meHistory3 = String.valueOf(spinnerHistory3.getSelectedItem());
        return meHistory3;
    }

    private String getBeforeEightDateString(int num) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -8 - num);
        return dateFormat.format(cal.getTime());
    }

    private String getBeforeEightDateString2(int num) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -num);
        return dateFormat.format(cal.getTime());
    }

    private String getBeforeEightDateString3(int num) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -num - Integer.parseInt(getItemSpinner3().substring(23, 26)));
        return dateFormat.format(cal.getTime());
    }

    @Override
    protected void onResume() {
        super.onResume();

        getDataCurrencies();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHistory:
                getDataHistory(getItemSpinnerFrom().substring(0, 3), getItemSpinnerTo().substring(0, 3), getItemSpinner3().substring(0, 10), getItemSpinner3().substring(12, 22));
                break;
        }
    }

}