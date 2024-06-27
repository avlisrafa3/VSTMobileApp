package com.example.vacationSchedulingTool.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Entity class representing an Excursion in the database.
@Entity(tableName = "excursion")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    // Marking excursionID as the primary key and auto-generating its value
    private int excursionID;

    private String excursionTitle; // Title of the excursion
    private String excursionDate; // Date of the excursion
    private int vacationID; // Foreign key to the related vacation

    public Excursion(int excursionID, String excursionTitle, String excursionDate, int vacationID) {
        this.excursionID = excursionID;
        this.excursionTitle = excursionTitle;
        this.excursionDate = excursionDate;
        this.vacationID = vacationID;
    }

    //Gets the ID of the excursion
    public int getExcursionID() {
        return excursionID;
    }

    //Sets the ID of the excursion
    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    //Gets the title of the excursion
    public String getExcursionTitle() {
        return excursionTitle;
    }

    //Sets the title of the excursion
    public void setExcursionTitle(String excursionTitle) {
        this.excursionTitle = excursionTitle;
    }

    //Gets the date of the excursion
    public String getExcursionDate() {
        return excursionDate;
    }

    //Sets the date of the excursion
    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }

    //Gets the ID of the related vacation
    public int getVacationID() {
        return vacationID;
    }

    // Sets the ID of the related vacation
    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }
}
