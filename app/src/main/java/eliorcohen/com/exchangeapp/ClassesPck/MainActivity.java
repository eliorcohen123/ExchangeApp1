package eliorcohen.com.exchangeapp.ClassesPck;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import eliorcohen.com.exchangeapp.R;
import guy4444.smartrate.SmartRate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnMyConverter, btnMyHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {

        }
        setContentView(R.layout.main_activity);

        initUI();
        initListeners();
        initAppRater();
    }

    private void initUI() {
        btnMyConverter = findViewById(R.id.btnMyConverter);
        btnMyHistory = findViewById(R.id.btnMyHistory);
    }

    private void initListeners() {
        btnMyConverter.setOnClickListener(this);
        btnMyHistory.setOnClickListener(this);
    }

    private void initAppRater() {
        SmartRate.Rate(MainActivity.this
                , "Rate Us"
                , "Tell others what you think about this app"
                , "Continue"
                , "Please take a moment and rate us on Google Play"
                , "click here"
                , "Ask me later"
                , "Never ask again"
                , "Cancel"
                , "Thanks for the feedback"
                , Color.parseColor("#2196F3")
                , 5
                , 1
                , 1
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMyConverter:
                Intent intentConversion = new Intent(this, Conversion.class);
                startActivity(intentConversion);
                break;
            case R.id.btnMyHistory:
                Intent intentHistory = new Intent(this, HistoricalConversion.class);
                startActivity(intentHistory);
                break;
        }
    }

}
