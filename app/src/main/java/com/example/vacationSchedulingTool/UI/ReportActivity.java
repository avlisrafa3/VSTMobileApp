package com.example.vacationSchedulingTool.UI;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vacationSchedulingTool.R;

public class ReportActivity extends AppCompatActivity {

    private WebView reportWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Initialize reportWebView
        reportWebView = findViewById(R.id.reportWebView);

        // Enable the back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Retrieve report data from Intent
        String reportData = getIntent().getStringExtra("reportData");

        // Load the report data into the WebView
        reportWebView.loadData(reportData, "text/html", "UTF-8");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
