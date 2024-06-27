package com.example.vacationSchedulingTool.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vacationSchedulingTool.Databse.Repository;
import com.example.vacationSchedulingTool.Entities.User;
import com.example.vacationSchedulingTool.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button registerButton = findViewById(R.id.registerButton);

        repository = new Repository(getApplication());

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                // Validate email format
                if (!isValidEmail(email)) {
                    // Show error message if email format is invalid
                    Toast.makeText(RegisterActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check if email already exists
                if (repository.getUserByEmail(email) != null) {
                    // Show error message if email already exists
                    Toast.makeText(RegisterActivity.this, "Email already in use", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Validate password
                if (!PasswordValidator.isValidPassword(password)) {
                    // Show error message if password is invalid
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters, contain a number and a special character", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Create a new user object
                User user = new User(email, password);
                repository.insert(user);
                // Show registration successful message
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                // Navigate back to the login activity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Show error message if email or password is empty
                Toast.makeText(RegisterActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Check email
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    static class PasswordValidator {
        public static boolean isValidPassword(String password) {
            // Check if password is at least 6 characters long
            if (password.length() < 6) {
                return false;
            }
            // Check if password contains a number
            if (!password.matches(".*\\d.*")) {
                return false;
            }
            // Check if password contains a special character
            if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                return false;
            }
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
