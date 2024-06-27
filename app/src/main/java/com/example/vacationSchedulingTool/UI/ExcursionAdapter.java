package com.example.vacationSchedulingTool.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationSchedulingTool.Entities.Excursion;
import com.example.vacationSchedulingTool.R;

import java.util.List;

//Adapter class for displaying a list of Excursions in a RecyclerView
public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.PartViewHolder> {

    // ViewHolder class for holding the views of each item
    class PartViewHolder extends RecyclerView.ViewHolder {
        private final TextView partItemView;
        private final TextView partItemView2;

        private PartViewHolder(View itemView) {
            super(itemView);
            partItemView = itemView.findViewById(R.id.textView3);
            partItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition(); // Get position of clicked item
                    final Excursion current = mExcursions.get(position); // Get current Excursion
                    Intent intent = new Intent(context, ExcursionDetails.class); // Create intent to start ExcursionDetails activity
                    intent.putExtra("id", current.getExcursionID()); // Pass excursion ID
                    intent.putExtra("name", current.getExcursionTitle()); // Pass excursion title
                    intent.putExtra("exdate", current.getExcursionDate()); // Pass excursion date
                    intent.putExtra("vacationID", current.getVacationID()); // Pass vacation ID
                    context.startActivity(intent); // Start activity
                }
            });
        }
    }

    private List<Excursion> mExcursions; // Cached copy of Excursions
    private final Context context;
    private final LayoutInflater mInflater;


    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context); // Get layout inflater from context
        this.context = context; // Set context
    }

    @NonNull
    @Override
    public PartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for the RecyclerView
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new PartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PartViewHolder holder, int position) {
        // Bind data to the views in the ViewHolder
        if (mExcursions != null) {
            Excursion current = mExcursions.get(position);
            String name = current.getExcursionTitle();
            int prodID = current.getVacationID();
            holder.partItemView.setText(name);
            holder.partItemView2.setText(Integer.toString(prodID));
        } else {
            holder.partItemView.setText("No excursion title");
            holder.partItemView2.setText("No vacation id");
        }
    }

    public void setExcursions(List<Excursion> excursions) {
        mExcursions = excursions; // Set new data
        notifyDataSetChanged(); // Notify adapter to refresh the RecyclerView
    }

    @Override
    public int getItemCount() {
        // Return the total number of items
        if (mExcursions != null) {
            return mExcursions.size();
        } else return 0;
    }
}
