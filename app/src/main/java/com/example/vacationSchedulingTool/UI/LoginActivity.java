package com.example.vacationSchedulingTool.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vacationSchedulingTool.Databse.Repository;
import com.example.vacationSchedulingTool.Entities.User;
import com.example.vacationSchedulingTool.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        repository = new Repository(getApplication());

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        TextView registerTextView = findViewById(R.id.registerTextView);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the registration activity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().toLowerCase(); // Convert to lowercase
                String password = passwordEditText.getText().toString();

                User user = repository.getUserByEmail(username);

                if (user != null && user.getPassword().equals(password)) {
                    // Successful login, grant access to the app
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    // Display error message for invalid credentials
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isValidEmail(String email) {
        // Regular expression to match email pattern
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        // Check if the entered email matches the pattern
        return email.matches(emailPattern);
    }

    public boolean validatePassword(String password) {
        // Check if the password meets the criteria (e.g., minimum length)
        return password.length() >= 8; // Example: Password should be at least 8 characters long
    }

    @Override
    protected void onResume() {
        super.onResume();
        repository = new Repository(getApplication());
    }
}
