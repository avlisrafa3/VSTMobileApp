package com.example.vacationSchedulingTool.Databse;

import android.app.Application;

import com.example.vacationSchedulingTool.Dao.ExcursionDAO;
import com.example.vacationSchedulingTool.Dao.UserDAO;
import com.example.vacationSchedulingTool.Dao.VacationDAO;
import com.example.vacationSchedulingTool.Entities.Excursion;
import com.example.vacationSchedulingTool.Entities.User;
import com.example.vacationSchedulingTool.Entities.Vacation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Repository class that acts as a mediator between data sources and the rest of the application.
public class Repository {
    private final ExcursionDAO mExcursionDAO; // Data Access Object for Excursion
    private final VacationDAO mVacationDAO; // Data Access Object for Vacation
    private final UserDAO mUserDAO; // Data Access Object for User
    private List<Vacation> mAllVacations; // Cached list of all vacations
    private List<Excursion> mAllExcursions; // Cached list of all excursions

    private User mUserByEmail;

    private static final int NUMBER_OF_THREADS = 4; // Number of threads for the executor service
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS); // Fixed thread pool executor

    public Repository(Application application) {
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application); // Get the database instance
        mExcursionDAO = db.excursionDAO(); // Initialize ExcursionDAO
        mVacationDAO = db.vacationDAO(); // Initialize VacationDAO
        mUserDAO = db.userDAO(); // Initialize UserDao
        getAllVacations(); // Get all vacations when Repository is initialized
    }

    public List<Vacation> getAllVacations() {
        databaseExecutor.execute(() -> {
            mAllVacations = mVacationDAO.getAllVacations(); // Fetch all vacations
        });

        try {
            Thread.sleep(1000); // Wait for the background operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllVacations; // Return the cached list
    }

    public void insert(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.insert(vacation); // Insert vacation
        });
        try {
            Thread.sleep(1000); // Wait for the background operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.update(vacation); // Update vacation
        });
        try {
            Thread.sleep(1000); // Wait for the background operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.delete(vacation); // Delete vacation
        });
        try {
            Thread.sleep(1000); // Wait for the background operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Excursion> getAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAllExcursions(); // Fetch all excursions
        });

        try {
            Thread.sleep(1000); // Wait for the background operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions; // Return the cached list
    }

    public void insert(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.insert(excursion); // Insert excursion
        });
        try {
            Thread.sleep(1000); // Wait for the background operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.update(excursion); // Update excursion
        });
        try {
            Thread.sleep(1000); // Wait for the background operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.delete(excursion); // Delete excursion
        });
        try {
            Thread.sleep(1000); // Wait for the background operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to filter vacations based on a query string
    // It searches for vacations whose title contains the given query string
    // Returns a list of filtered vacations
    public List<Vacation> filterVacations(String query) {
        List<Vacation> filteredVacations = new ArrayList<>();
        for (Vacation vacation : mAllVacations) {
            if (vacation.getVacationTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredVacations.add(vacation);
            }
        }
        return filteredVacations;
    }

    public User getUserByEmail(String email) {
        databaseExecutor.execute(() -> {
            mUserByEmail = mUserDAO.getUserByEmail(email);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mUserByEmail;
    }

    public void insert(User user) {
        databaseExecutor.execute(() -> {
            mUserDAO.insert(user); // Insert user
        });
        try {
            Thread.sleep(1000); // Wait for the background operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Checks if email is already in use
    public boolean isEmailRegistered(String email) {
        User user = getUserByEmail(email);
        return user != null;
    }
}
