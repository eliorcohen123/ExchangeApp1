package eliorcohen.com.exchangeapp.ClassesPck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import eliorcohen.com.exchangeapp.R;

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
    }

    private void initUI() {
        btnMyConverter = findViewById(R.id.btnMyConverter);
        btnMyHistory = findViewById(R.id.btnMyHistory);
    }

    private void initListeners() {
        btnMyConverter.setOnClickListener(this);
        btnMyHistory.setOnClickListener(this);
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
