package com.example.vacationSchedulingTool.Databse;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vacationSchedulingTool.Dao.ExcursionDAO;
import com.example.vacationSchedulingTool.Dao.UserDAO;
import com.example.vacationSchedulingTool.Dao.VacationDAO;
import com.example.vacationSchedulingTool.Entities.Excursion;
import com.example.vacationSchedulingTool.Entities.User;
import com.example.vacationSchedulingTool.Entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class, User.class}, version = 14, exportSchema = false)
public abstract class DatabaseBuilder extends RoomDatabase {
    //Abstract method to get the VacationDAO
    public abstract VacationDAO vacationDAO();

    //Abstract method to get the ExcursionDAO.
    public abstract ExcursionDAO excursionDAO();

    // Abstract method to get the UserDao
    public abstract UserDAO userDAO();

    // Single instance of the database
    private static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) { // Check if instance is null
            synchronized (DatabaseBuilder.class) { // Synchronize block to prevent multiple threads from creating multiple instances
                if (INSTANCE == null) { // Double-check if instance is still null
                    // Create the database instance
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DatabaseBuilder.class, "VacationDatabase.db")
                            .fallbackToDestructiveMigration() // Handle migrations by destroying and recreating the database
                            .build(); // Build the database
                }
            }
        }
        return INSTANCE; // Return the database instance
    }
}
