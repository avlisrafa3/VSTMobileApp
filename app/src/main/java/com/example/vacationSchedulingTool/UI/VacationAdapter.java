package com.example.vacationSchedulingTool.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationSchedulingTool.Entities.Vacation;
import com.example.vacationSchedulingTool.R;

import java.util.List;

// Adapter class to adapt Vacation data into a RecyclerView
public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {

    // ViewHolder for each item in the RecyclerView
    class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;

        private VacationViewHolder(View itemView) {
            super(itemView);
            vacationItemView = itemView.findViewById(R.id.textView); // Initialize the TextView
            // Set an OnClickListener to handle item clicks
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the position of the clicked item
                    int position = getAdapterPosition();
                    // Get the current Vacation object at that position
                    final Vacation current = mVacations.get(position);
                    // Create an Intent to start the VacationDetails activity
                    Intent intent = new Intent(context, VacationDetails.class);
                    // Pass relevant Vacation data as extras to the Intent
                    intent.putExtra("id", current.getVacationID());
                    intent.putExtra("name", current.getVacationTitle());
                    intent.putExtra("hotel", current.getVacationHotel());
                    intent.putExtra("startDate", current.getVacationStartDate());
                    intent.putExtra("endDate", current.getVacationEndDate());
                    // Start the VacationDetails activity
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflater;

    // Constructor to initialize the adapter
    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout and create a ViewHolder
        View itemView = mInflater.inflate(R.layout.vacation_list_item, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationViewHolder holder, int position) {
        if (mVacations != null) {
            // Get the Vacation object at the current position
            Vacation current = mVacations.get(position);
            // Set the Vacation title to the TextView in the ViewHolder
            String name = current.getVacationTitle();
            holder.vacationItemView.setText(name);
        } else {
            holder.vacationItemView.setText("No Vacation Title");
        }
    }

    // Method to set the list of Vacations and notify the adapter of changes
    public void setVacations(List<Vacation> vacations) {
        mVacations = vacations;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the list of Vacations
        if (mVacations != null) {
            return mVacations.size();
        } else {
            return 0;
        }
    }

    // Clears Recycler Viewer on Searches
    public void clearVacations() {
        if (mVacations != null) {
            mVacations.clear();
            notifyDataSetChanged();
        }
    }

}