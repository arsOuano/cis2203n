package com.example.calc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView screen;
    private String num1 = "";
    private String num2 = "";
    private String operator = "";
    private boolean isOperatorPressed = false;

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

        screen = findViewById(R.id.screenView);

        if (savedInstanceState != null) {
            num1 = savedInstanceState.getString("num1", "");
            num2 = savedInstanceState.getString("num2", "");
            operator = savedInstanceState.getString("operator", "");
            isOperatorPressed = savedInstanceState.getBoolean("isOperatorPressed", false);
            screen.setText(savedInstanceState.getString("screenText", ""));
        } else {
            screen.setText("");
        }

        int[] digitIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        View.OnClickListener digitListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String currentText = screen.getText().toString();

                if (isOperatorPressed) {
                    screen.setText(b.getText().toString());
                    isOperatorPressed = false;
                } else {
                    screen.setText(currentText + b.getText().toString());
                }
            }
        };
        for (int id : digitIds) findViewById(id).setOnClickListener(digitListener);

        int[] opIds = {R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv};
        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                num1 = screen.getText().toString();
                operator = b.getText().toString();
                isOperatorPressed = true;
            }
        };
        for (int id : opIds) findViewById(id).setOnClickListener(opListener);

        findViewById(R.id.btnEq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num2 = screen.getText().toString();
                if (!num1.isEmpty() && !num2.isEmpty() && !operator.isEmpty()) {
                    calculate();
                }
            }
        });

        findViewById(R.id.btnc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1 = "";
                num2 = "";
                operator = "";
                isOperatorPressed = false;
                screen.setText("");
            }
        });

        View btnReq = findViewById(R.id.btnReq);
        if (btnReq != null) {
            btnReq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currentText = screen.getText().toString();
                    if (!currentText.isEmpty()) {
                        try {
                            double val = Double.parseDouble(currentText);
                            double result = val * 4.90;

                            String resStr = String.valueOf(result);
                            // Clean up .0 if it's an integer
                            if (resStr.endsWith(".0")) resStr = resStr.substring(0, resStr.length() - 2);
                            
                            screen.setText(resStr);
                            
                            // Treat this as a completed calculation result
                            num1 = resStr;
                            num2 = "";
                            operator = "";
                            isOperatorPressed = false;
                        } catch (Exception e) {
                            screen.setText("Error");
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("num1", num1);
        outState.putString("num2", num2);
        outState.putString("operator", operator);
        outState.putBoolean("isOperatorPressed", isOperatorPressed);
        outState.putString("screenText", screen.getText().toString());
    }

    private void calculate() {
        try {
            double d1 = Double.parseDouble(num1);
            double d2 = Double.parseDouble(num2);
            double result = 0;

            switch (operator) {
                case "+": result = d1 + d2; break;
                case "-": result = d1 - d2; break;
                case "*": result = d1 * d2; break;
                case "/":
                    if (d2 == 0) {
                        screen.setText("Cannot divide by zero");
                        num1 = ""; num2 = ""; operator = "";
                        return;
                    }
                    result = d1 / d2;
                    break;
            }

            String resStr = String.valueOf(result);
            if (resStr.endsWith(".0")) resStr = resStr.substring(0, resStr.length() - 2);
            screen.setText(resStr);

            num1 = "";
            num2 = "";
            operator = "";
            isOperatorPressed = false;
        } catch (Exception e) {
            screen.setText("Error");
        }
    }
}
