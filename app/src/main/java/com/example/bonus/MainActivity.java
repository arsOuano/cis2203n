package com.example.bonus;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout tilPrincipal, tilTargetAmount;
    private TextInputEditText etPrincipal, etTargetAmount, etRate, etPeriods, etTime;
    private Spinner spinnerTimeUnit;
    private MaterialButtonToggleGroup toggleGroup;
    private Button btnCalculate;
    private TextView tvResult;

    private boolean isFindingAmount = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        tilPrincipal = findViewById(R.id.tilPrincipal);
        tilTargetAmount = findViewById(R.id.tilTargetAmount);
        etPrincipal = findViewById(R.id.etPrincipal);
        etTargetAmount = findViewById(R.id.etTargetAmount);
        etRate = findViewById(R.id.etRate);
        etPeriods = findViewById(R.id.etPeriods);
        etTime = findViewById(R.id.etTime);
        spinnerTimeUnit = findViewById(R.id.spinnerTimeUnit);
        toggleGroup = findViewById(R.id.toggleGroup);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        // Set up toggle group logic
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btnFindA) {
                    isFindingAmount = true;
                    tilPrincipal.setVisibility(View.VISIBLE);
                    tilTargetAmount.setVisibility(View.GONE);
                } else if (checkedId == R.id.btnFindP) {
                    isFindingAmount = false;
                    tilPrincipal.setVisibility(View.GONE);
                    tilTargetAmount.setVisibility(View.VISIBLE);
                }
            }
        });

        btnCalculate.setOnClickListener(v -> calculate());
    }

    private void calculate() {
        String rateStr = etRate.getText().toString();
        String periodsStr = etPeriods.getText().toString();
        String timeStr = etTime.getText().toString();

        if (rateStr.isEmpty() || periodsStr.isEmpty() || timeStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all common fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double R = Double.parseDouble(rateStr);
            double n = Double.parseDouble(periodsStr);
            double timeInput = Double.parseDouble(timeStr);
            double r = R / 100.0;

            // Convert time to years
            double t;
            String unit = spinnerTimeUnit.getSelectedItem().toString();
            switch (unit) {
                case "Months":
                    t = timeInput / 12.0;
                    break;
                case "Days":
                    t = timeInput / 365.0;
                    break;
                default:
                    t = timeInput;
                    break;
            }

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

            if (isFindingAmount) {
                String pStr = etPrincipal.getText().toString();
                if (pStr.isEmpty()) {
                    Toast.makeText(this, "Please enter Principal", Toast.LENGTH_SHORT).show();
                    return;
                }
                double P = Double.parseDouble(pStr);
                // A = P(1 + r/n)^(nt)
                double A = P * Math.pow(1 + (r / n), (n * t));
                double I = A - P;
                
                tvResult.setText(String.format("Final Amount (A): %s\nTotal Interest (I): %s", 
                        currencyFormat.format(A), currencyFormat.format(I)));
            } else {
                String aStr = etTargetAmount.getText().toString();
                if (aStr.isEmpty()) {
                    Toast.makeText(this, "Please enter Target Amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                double A = Double.parseDouble(aStr);
                // P = A / (1 + r/n)^(nt)
                double P = A / Math.pow(1 + (r / n), (n * t));
                double I = A - P;

                tvResult.setText(String.format("Required Deposit (P): %s\nTotal Interest Earned: %s", 
                        currencyFormat.format(P), currencyFormat.format(I)));
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input. Please enter numbers.", Toast.LENGTH_SHORT).show();
        }
    }
}
