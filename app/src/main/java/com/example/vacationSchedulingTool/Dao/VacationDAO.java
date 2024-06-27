package com.example.vacationSchedulingTool.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vacationSchedulingTool.Entities.Vacation;

import java.util.List;


 //Data Access Object (DAO) for Vacation entity.
 //Encapsulates the database operations related to Vacation.

@Dao
public interface VacationDAO {
    //Inserts a Vacation object into the database.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    //Updates an existing Vacation object in the database.
    @Update
    void update(Vacation vacation);

    //Deletes a Vacation object from the database.
    @Delete
    void delete(Vacation vacation);

    //Queries the database to retrieve all Vacation objects.
    @Query("SELECT * FROM VACATION ORDER BY vacationID ASC")
    List<Vacation> getAllVacations();
}