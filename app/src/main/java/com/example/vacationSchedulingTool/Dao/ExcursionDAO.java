package com.example.vacationSchedulingTool.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vacationSchedulingTool.Entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {

  //Inserts an Excursion object into the database.
  //If there is a conflict (e.g., duplicate primary key), it ignores the new data.

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    //Updates an existing Excursion object in the database.

    @Update
    void update(Excursion excursion);

    //Deletes an Excursion object from the database.

    @Delete
    void delete(Excursion excursion);

    //Queries the database to retrieve all Excursion objects.

    @Query("SELECT * FROM EXCURSION ORDER BY excursionID ASC")
    List<Excursion> getAllExcursions();

}
