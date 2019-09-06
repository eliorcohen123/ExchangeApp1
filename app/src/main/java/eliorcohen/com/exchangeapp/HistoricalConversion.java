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

    private LineChart mChartFrom, mChartTo;
    private ArrayList<Entry> yValuesFrom, yValuesTo;
    private ArrayList<ILineDataSet> dataSetsFrom, dataSetsTo;
    private double dateFrom1, dateFrom2, dateFrom3, dateFrom4, dateFrom5, dateFrom6, dateFrom7, dateFrom8, dateFrom9,
            dateTo1, dateTo2, dateTo3, dateTo4, dateTo5, dateTo6, dateTo7, dateTo8, dateTo9;
    private LineDataSet setFrom, setTo;
    private LineData lineDataFrom, lineDataTo;
    private ArrayAdapter<String> spinnerArrayAdapterHistoryFrom, spinnerArrayAdapterHistoryTo;
    private ArrayList<String> stringArrayListHistoryFrom, stringArrayListHistoryTo;
    private Spinner spinnerHistoryFrom, spinnerHistoryTo;
    private Button btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initUI();
        initListeners();
        getDataHistory(getItemSpinnerFrom(), getItemSpinnerTo());
        getDataCurrencies();
    }

    private void initUI() {
        mChartFrom = findViewById(R.id.linechart1);
        mChartTo = findViewById(R.id.linechart2);
        spinnerHistoryFrom = findViewById(R.id.spinnerHistoryFrom);
        spinnerHistoryTo = findViewById(R.id.spinnerHistoryTo);
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
    }

    private void initListeners() {
        btnHistory.setOnClickListener(this);
    }

    private void getDataHistory(final String fromHistory, final String toHistory) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free.currconv.com/api/v7/convert?apiKey="
                + getString(R.string.api_key) +
                "&q=" + fromHistory + "_" + toHistory + "," + toHistory + "_" + fromHistory + "&compact=ultra&date="
                + getBeforeEightDateString(8) +
                "&endDate=" + getBeforeEightDateString(0), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    yValuesFrom.clear();
                    yValuesTo.clear();
                    dataSetsFrom.clear();
                    dataSetsTo.clear();
                    mChartFrom.invalidate();
                    mChartTo.invalidate();
                    mChartFrom.clear();
                    mChartTo.clear();

                    JSONObject mainObj = new JSONObject(response);

                    JSONObject mainObj2 = mainObj.getJSONObject(fromHistory + "_" + toHistory);
                    dateFrom1 = mainObj2.getDouble(getBeforeEightDateString(0));
                    dateFrom2 = mainObj2.getDouble(getBeforeEightDateString(1));
                    dateFrom3 = mainObj2.getDouble(getBeforeEightDateString(2));
                    dateFrom4 = mainObj2.getDouble(getBeforeEightDateString(3));
                    dateFrom5 = mainObj2.getDouble(getBeforeEightDateString(4));
                    dateFrom6 = mainObj2.getDouble(getBeforeEightDateString(5));
                    dateFrom7 = mainObj2.getDouble(getBeforeEightDateString(6));
                    dateFrom8 = mainObj2.getDouble(getBeforeEightDateString(7));
                    dateFrom9 = mainObj2.getDouble(getBeforeEightDateString(8));

                    JSONObject mainObj3 = mainObj.getJSONObject(toHistory + "_" + fromHistory);
                    dateTo1 = mainObj3.getDouble(getBeforeEightDateString(0));
                    dateTo2 = mainObj3.getDouble(getBeforeEightDateString(1));
                    dateTo3 = mainObj3.getDouble(getBeforeEightDateString(2));
                    dateTo4 = mainObj3.getDouble(getBeforeEightDateString(3));
                    dateTo5 = mainObj3.getDouble(getBeforeEightDateString(4));
                    dateTo6 = mainObj3.getDouble(getBeforeEightDateString(5));
                    dateTo7 = mainObj3.getDouble(getBeforeEightDateString(6));
                    dateTo8 = mainObj3.getDouble(getBeforeEightDateString(7));
                    dateTo9 = mainObj3.getDouble(getBeforeEightDateString(8));

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

                    int selectedColor = Color.rgb(0, 204, 0);

                    setFrom = new LineDataSet(yValuesFrom, "Exchange set " + fromHistory + " to " + toHistory);
                    setFrom.setFillAlpha(110);
                    setFrom.setLineWidth(3f);
                    setFrom.setValueTextSize(10f);
                    setFrom.setColor(Color.RED);
                    setFrom.setValueTextColor(selectedColor);
                    setFrom.setCircleColorHole(Color.CYAN);

                    setTo = new LineDataSet(yValuesTo, "Exchange set " + toHistory + " to " + fromHistory);
                    setTo.setFillAlpha(110);
                    setTo.setLineWidth(3f);
                    setTo.setValueTextSize(10f);
                    setTo.setColor(Color.BLUE);
                    setTo.setValueTextColor(selectedColor);
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
                + getString(R.string.api_key), new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObj = new JSONObject(response);
                    JSONObject mainObj2 = mainObj.getJSONObject("results");

                    Iterator<String> keys = mainObj2.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (mainObj2.get(key) instanceof JSONObject) {
                            stringArrayListHistoryFrom.add(key);
                            stringArrayListHistoryTo.add(key);
                        }
                    }

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

    private String getItemSpinnerFrom() {
        String meHistoryFrom = String.valueOf(spinnerHistoryFrom.getSelectedItem());
        return meHistoryFrom;
    }

    private String getItemSpinnerTo() {
        String meHistoryTo = String.valueOf(spinnerHistoryTo.getSelectedItem());
        return meHistoryTo;
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
                getDataHistory(getItemSpinnerFrom(), getItemSpinnerTo());
                getDataCurrencies();
                break;
        }
    }

}
