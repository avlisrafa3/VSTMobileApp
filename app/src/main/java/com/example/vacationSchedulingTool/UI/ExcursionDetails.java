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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.vacationSchedulingTool.Databse.Repository;
import com.example.vacationSchedulingTool.Entities.Excursion;
import com.example.vacationSchedulingTool.Entities.Vacation;
import com.example.vacationSchedulingTool.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Activity for displaying and editing excursion details.
 */
public class ExcursionDetails extends AppCompatActivity {

    String title;
    int excursionID;
    int vacationID;

    EditText editExcursionTitle;
    EditText editExcursionDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener exstartDate;
    Date vacationStartDate;
    Date vacationEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        // Initialize repository
        repository = new Repository(getApplication());

        // Get data from Intent
        title = getIntent().getStringExtra("name");
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);

        // Set up EditText for excursion title
        editExcursionTitle = findViewById(R.id.title1);
        editExcursionTitle.setText(title);

        // Fetch and set vacation dates
        getVacationDates(vacationID);

        // Set up EditText for excursion date
        String excursionDate = getIntent().getStringExtra("exdate");
        editExcursionDate = findViewById(R.id.exdate);
        editExcursionDate.setText(excursionDate);

        // Date format and calendar setup
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        final Calendar myCalendarStart = Calendar.getInstance();

        // Populate vacation ID spinner
        ArrayList<Vacation> vacationArrayList = new ArrayList<>();
        vacationArrayList.addAll(repository.getAllVacations());
        ArrayList<Integer> vacationIdList = new ArrayList<>();
        for (Vacation vacation : vacationArrayList) {
            vacationIdList.add(vacation.getVacationID());
        }
        ArrayAdapter<Integer> vacationIdAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vacationIdList);
        Spinner spinner = findViewById(R.id.spinner1);
        spinner.setAdapter(vacationIdAdapter);

        // Date picker dialog setup
        exstartDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStartDate(sdf.format(myCalendarStart.getTime()));
            }

            private void updateLabelStartDate(String formattedDate) {
                TextView startDateTextView = findViewById(R.id.exdate);
                startDateTextView.setText(formattedDate);
                Log.d("ExcursionDetails", "Current excursion date: " + formattedDate);

                try {
                    Date selectedDate = sdf.parse(formattedDate);
                    if (selectedDate != null) {
                        if (vacationStartDate != null && vacationEndDate != null) {
                            if (selectedDate.before(vacationStartDate) || selectedDate.after(vacationEndDate)) {
                                Toast.makeText(ExcursionDetails.this, "Excursion date must be within the vacation period.", Toast.LENGTH_SHORT).show();
                                startDateTextView.setText("");
                            } else {
                                Toast.makeText(ExcursionDetails.this, "Excursion date is within the vacation period.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ExcursionDetails.this, "Vacation dates are not set.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        editExcursionDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ExcursionDetails.this, exstartDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    //Gets vacation start and end dates for the given vacation ID.

    private void getVacationDates(int vacID) {
        List<Vacation> list = repository.getAllVacations();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
        for (Vacation v : list) {
            if (v.getVacationID() == vacID) {
                try {
                    vacationStartDate = sdf.parse(v.getVacationStartDate());
                    vacationEndDate = sdf.parse(v.getVacationEndDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.excursionsave) {
            // Save or update excursion
            Excursion excursion;
            repository = new Repository(getApplication());
            String title = editExcursionTitle.getText().toString();
            String date = editExcursionDate.getText().toString();
            if (title.isEmpty() || date.isEmpty()) {
                Toast.makeText(ExcursionDetails.this, "Please fill all fields before saving", Toast.LENGTH_SHORT).show();
                return true;
            }

            if (excursionID == -1) {
                // Save new excursion
                if (repository.getAllExcursions().isEmpty()) excursionID = 1;
                else
                    excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;

                excursion = new Excursion(excursionID, editExcursionTitle.getText().toString(), editExcursionDate.getText().toString(), vacationID);
                repository.insert(excursion);
                Toast.makeText(ExcursionDetails.this, "Excursion saved", Toast.LENGTH_SHORT).show();
            } else {
                // Update existing excursion
                excursion = new Excursion(excursionID, editExcursionTitle.getText().toString(), editExcursionDate.getText().toString(), vacationID);
                repository.update(excursion);
                Toast.makeText(ExcursionDetails.this, "Excursion updated", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        if (item.getItemId() == R.id.share) {
            // Share excursion details
            String excursionDetails = "Title: " + editExcursionTitle.getText().toString() + "\n" + "Excursion Date: " + editExcursionDate.getText().toString();

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, excursionDetails);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Excursion Details");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Share via");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(shareIntent);
            } else {
                Toast.makeText(this, "No app to handle share action", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        if (item.getItemId() == R.id.notify) {
            // Set notification for excursion date
            String dateFromScreen = editExcursionDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                intent.putExtra("key", "Excursion Time");
                PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        if (item.getItemId() == R.id.excursionDelete) {
            // Delete excursion
            Excursion currentExcursion = null;
            for (Excursion exc : repository.getAllExcursions()) {
                if (exc.getExcursionID() == excursionID) {
                    currentExcursion = exc;
                    break;
                }
            }
            if (currentExcursion != null) {
                repository.delete(currentExcursion);
                Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionTitle() + " was deleted", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}