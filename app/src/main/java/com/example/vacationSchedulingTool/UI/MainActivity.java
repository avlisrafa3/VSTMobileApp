package com.example.vacationSchedulingTool.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationSchedulingTool.Dao.VacationDAO;
import com.example.vacationSchedulingTool.Databse.Repository;
import com.example.vacationSchedulingTool.Entities.Vacation;
import com.example.vacationSchedulingTool.R;

import java.util.List;

// Main activity of the application
public class MainActivity extends AppCompatActivity {
    public static int numAlert; // Static variable to keep track of alert numbers
    private Repository repository;
    private VacationAdapter vacationAdapter;
    private RecyclerView vacationRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the repository
        repository = new Repository(getApplication());

        // Initialize RecyclerView
        vacationRecyclerView = findViewById(R.id.vacationRecyclerView);
        vacationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter = new VacationAdapter(this);
        vacationRecyclerView.setAdapter(vacationAdapter);

        // Initialize the button and set an OnClickListener
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VacationList.class);
                startActivity(intent);
            }
        });

        EditText searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                searchVacations(query);
            }
        });
    }

    private void searchVacations(String query) {
        if (query.isEmpty()) {
            // If the query is empty, clear the RecyclerView and hide it
            vacationAdapter.clearVacations();
            vacationRecyclerView.setVisibility(View.GONE);
        } else {
            // Use repository to filter vacations
            List<Vacation> filteredVacations = repository.filterVacations(query);

            if (filteredVacations.isEmpty()) {
                // If there are no matching vacations, clear the RecyclerView and hide it
                vacationAdapter.clearVacations();
                vacationRecyclerView.setVisibility(View.GONE);
            } else {
                // If there are matching vacations, update the RecyclerView's adapter and show it
                vacationAdapter.setVacations(filteredVacations);
                vacationAdapter.notifyDataSetChanged();
                vacationRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
}