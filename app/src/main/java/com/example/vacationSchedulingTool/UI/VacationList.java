package com.example.vacationSchedulingTool.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.vacationSchedulingTool.Databse.Repository;
import com.example.vacationSchedulingTool.Entities.Vacation;
import com.example.vacationSchedulingTool.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//Activity class for displaying the list of vacations
public class VacationList extends AppCompatActivity {
    private Repository repository;

    //Called when the activity is starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        // Set a click listener for the FloatingActionButton
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the VacationDetails activity
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                // Add data to the intent
                intent.putExtra("name", "");
                intent.putExtra("hotel", " ");
                intent.putExtra("startDate", "");
                intent.putExtra("endDate", "");
                startActivity(intent);
            }
        });

        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    //Initialize the contents of the Activity's standard options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationlist, menu);
        return true;
    }

    // Called when a menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.report) {
            // Generate the report data
            String reportData = generateReport();

            // Start the ReportActivity to display the report
            Intent intent = new Intent(this, ReportActivity.class);
            intent.putExtra("reportData", reportData);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //Called when the activity will start interacting with the user
    @Override
    protected void onResume() {
        super.onResume();
        List<Vacation> allVacations = repository.getAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }

    // Function to generate the report data
    private String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("<html><head><title>Vacation Report</title></head><body>");
        report.append("<h1>Vacation Report</h1>");
        report.append("<p>Date: ").append(getCurrentDateTime()).append("</p>");
        report.append("<table border='1'><tr>")
                .append("<th>ID</th>")
                .append("<th>Name</th>")
                .append("<th>Hotel</th>")
                .append("<th>Start Date</th>")
                .append("<th>End Date</th>")
                .append("</tr>");

        List<Vacation> allVacations = repository.getAllVacations();
        for (Vacation vacation : allVacations) {
            report.append("<tr>")
                    .append("<td>").append(vacation.getVacationID()).append("</td>")
                    .append("<td>").append(vacation.getVacationTitle()).append("</td>")
                    .append("<td>").append(vacation.getVacationHotel()).append("</td>")
                    .append("<td>").append(vacation.getVacationStartDate()).append("</td>")
                    .append("<td>").append(vacation.getVacationEndDate()).append("</td>")
                    .append("</tr>");
        }

        report.append("</table></body></html>");
        return report.toString();
    }

    // Function to get the current date-time stamp
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
