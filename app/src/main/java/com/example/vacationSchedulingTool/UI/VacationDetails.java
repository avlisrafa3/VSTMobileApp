package com.example.vacationSchedulingTool.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationSchedulingTool.Databse.Repository;
import com.example.vacationSchedulingTool.Entities.Excursion;

import com.example.vacationSchedulingTool.Entities.Vacation;
import com.example.vacationSchedulingTool.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    String name;
    String hotel;
    int vacationID;
    EditText editName;
    EditText editHotel;
    TextView editStartDate;
    TextView editEndDate;
    Repository repository;
    Vacation currentVacation;
    int numExcursions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        // Initialize repository and views
        repository = new Repository(getApplication());
        editName = findViewById(R.id.title);
        editHotel = findViewById(R.id.hotel);
        editStartDate = findViewById(R.id.StartDate);
        editEndDate = findViewById(R.id.endDate);

        // Retrieve vacation details from intent extras
        String startDateStr = getIntent().getStringExtra("startDate");
        String endDateStr = getIntent().getStringExtra("endDate");
        editStartDate.setText(startDateStr);
        editEndDate.setText(endDateStr);
        name = getIntent().getStringExtra("name");
        editName.setText(name);
        hotel = getIntent().getStringExtra("hotel");
        editHotel.setText(hotel);
        vacationID = getIntent().getIntExtra("id", -1);

        if (vacationID != -1) {
            setupRecyclerView();
        }

        // Setup date pickers
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        final Calendar myCalendarStart = Calendar.getInstance();
        final Calendar myCalendarEnd = Calendar.getInstance();
        // Date Pickers
        DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Set start date when selected
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart(sdf.format(myCalendarStart.getTime()));
            }

            private void updateLabelStart(String formattedDate) {
                // Update start date text view
                TextView startDateTextView = findViewById(R.id.StartDate);
                startDateTextView.setText(formattedDate);
            }
        };
        // Set click listener for start date text view
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(VacationDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Set end date when selected, ensuring it's after start date
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if (myCalendarEnd.getTimeInMillis() < myCalendarStart.getTimeInMillis()) {
                    Toast.makeText(VacationDetails.this, "End date must be after start date", Toast.LENGTH_SHORT).show();
                    myCalendarEnd.setTimeInMillis(myCalendarStart.getTimeInMillis() + (24 * 60 * 60 * 1000));
                    updateLabelEnd(sdf.format(myCalendarEnd.getTime()));
                } else {
                    updateLabelEnd(sdf.format(myCalendarEnd.getTime()));
                }
            }

            private void updateLabelEnd(String formattedDate) {
                // Update end date text view
                TextView endDateTextView = findViewById(R.id.endDate);
                endDateTextView.setText(formattedDate);
            }
        };
        // Set click listener for end date text view
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(VacationDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        // Set click listener for FloatingActionButton to handle adding an excursion
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vacationID != -1) {
                    // Create intent to start ExcursionDetails activity with necessary data
                    Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                    intent.putExtra("vacationID", vacationID);
                    intent.putExtra("vacationStartDate", editStartDate.getText().toString());
                    intent.putExtra("vacationEndDate", editEndDate.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(VacationDetails.this, "Please save vacation before adding an excursion", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource file into the menu
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.vacationsave) {
            // Save or update vacation details
            Vacation vacation;
            String name = editName.getText().toString();
            String hotel = editHotel.getText().toString();
            String startDate = editStartDate.getText().toString();
            String endDate = editEndDate.getText().toString();
            // Check if all fields are filled before saving
            if (name.isEmpty() || hotel.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(VacationDetails.this, "Please fill all fields before saving", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (vacationID == -1) { // New vacation
                if (repository.getAllVacations().isEmpty()) {
                    vacationID = 1;
                } else {
                    vacationID = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationID() + 1;
                }
                vacation = new Vacation(vacationID, name, hotel, startDate, endDate);
                repository.insert(vacation);
                Toast.makeText(VacationDetails.this, "New vacation saved", Toast.LENGTH_SHORT).show();
            } else { // Existing vacation
                vacation = new Vacation(vacationID, name, hotel, startDate, endDate);
                try {
                    repository.update(vacation);
                    Toast.makeText(VacationDetails.this, "Vacation updated", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(VacationDetails.this, "Failed to update vacation", Toast.LENGTH_SHORT).show();
                    Log.e("UpdateVacation", "Error updating vacation", e);
                }
            }
            FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
            fab.setEnabled(true);
            return true;
        }
        if (item.getItemId() == R.id.vacationdelete) {
            // Delete vacation if it has no excursions
            for (Vacation vac : repository.getAllVacations()) {
                if (vac.getVacationID() == vacationID) {
                    currentVacation = vac;
                }
            }
            numExcursions = 0;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
            }
            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationTitle() + " was deleted", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete Vacation with Excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if (item.getItemId() == R.id.notify1) {
            // Set notification for vacation start and end dates
            String startDateString = editStartDate.getText().toString();
            String endDateString = editEndDate.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
            try {
                Date startDate = sdf.parse(startDateString);
                Date endDate = sdf.parse(endDateString);
                long startTrigger = startDate.getTime(); // Get start time in milliseconds
                long endTrigger = endDate.getTime(); // Get end time in milliseconds
                // Get the current time using Calendar
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
                currentCalendar.set(Calendar.MINUTE, 0);
                currentCalendar.set(Calendar.SECOND, 0);
                currentCalendar.set(Calendar.MILLISECOND, 0);
                long currentTime = currentCalendar.getTimeInMillis();
                // Create intents for start and end notifications
                Intent startIntent = new Intent(VacationDetails.this, MyReceiver.class);
                startIntent.putExtra("key", "Vacation Start: " + editName.getText().toString());
                PendingIntent startSender = PendingIntent.getBroadcast(VacationDetails.this, 0, startIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
                Intent endIntent = new Intent(VacationDetails.this, MyReceiver.class);
                endIntent.putExtra("key", "Vacation End: " + editName.getText().toString());
                PendingIntent endSender = PendingIntent.getBroadcast(VacationDetails.this, 1, endIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                // Check if start date is in the future or current date
                if (startTrigger >= currentTime) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(VacationDetails.this, "Error setting alerts", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (item.getItemId() == R.id.share) {
            // Share vacation details
            String vacationDetails = "Title: " + editName.getText().toString() + "\n" +
                    "Hotel: " + editHotel.getText().toString() + "\n" +
                    "Start Date: " + editStartDate.getText().toString() + "\n" +
                    "End Date: " + editEndDate.getText().toString();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, vacationDetails);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Vacation Details");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "Share via");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(shareIntent);
            } else {
                Toast.makeText(this, "No app to handle share action", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item); // Return the super method if no item is selected
    }

    //Sets up the RecyclerView to display excursions
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter partAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(partAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredParts = new ArrayList<>();
        // Iterate through all excursions and filter the ones belonging to the current vacation
        for (Excursion p : repository.getAllExcursions()) {
            if (p.getVacationID() == vacationID) filteredParts.add(p);
        }
        partAdapter.setExcursions(filteredParts);
    }

    // Called when the activity will start interacting with the user
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        // Iterate through all excursions and filter the ones belonging to the current vacation
        for (Excursion p : repository.getAllExcursions()) {
            if (p.getVacationID() == vacationID) filteredExcursions.add(p);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }
}
