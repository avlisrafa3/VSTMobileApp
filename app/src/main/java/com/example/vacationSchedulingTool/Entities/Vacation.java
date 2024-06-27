package com.example.vacationSchedulingTool.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Entity class representing a Vacation in the database
@Entity(tableName = "vacation")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    // Marking vacationID as the primary key and auto-generating its value
    private int vacationID;

    private String vacationTitle; // Title of the vacation
    private String vacationHotel; // Hotel associated with the vacation
    private String vacationStartDate; // Start date of the vacation
    private String vacationEndDate; // End date of the vacation

    public Vacation(int vacationID, String vacationTitle, String vacationHotel, String vacationStartDate, String vacationEndDate) {
        this.vacationID = vacationID;
        this.vacationTitle = vacationTitle;
        this.vacationHotel = vacationHotel;
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
    }

    //Gets the ID of the vacation
    public int getVacationID() {
        return vacationID;
    }

    //Sets the ID of the vacation
    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    //Gets the title of the vacation
    public String getVacationTitle() {
        return vacationTitle;
    }

    //Sets the title of the vacation
    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }

    //Gets the hotel of the vacation
    public String getVacationHotel() {
        return vacationHotel;
    }

    //Sets the hotel of the vacation
    public void setVacationHotel(String vacationHotel) {
        this.vacationHotel = vacationHotel;
    }

    // Gets the start date of the vacation
    public String getVacationStartDate() {
        return vacationStartDate;
    }

    // Sets the start date of the vacation
    public void setVacationStartDate(String vacationStartDate) {
        this.vacationStartDate = vacationStartDate;
    }

    //Gets the end date of the vacation
    public String getVacationEndDate() {
        return vacationEndDate;
    }

    //Sets the end date of the vacation
    public void setVacationEndDate(String vacationEndDate) {
        this.vacationEndDate = vacationEndDate;
    }
}
