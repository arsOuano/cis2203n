package com.example.session3;

import static java.sql.Types.NULL;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EchoActivity extends AppCompatActivity {

    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_echo);

        Log.d("EchoActivity", "onCreate called");

        display = findViewById(R.id.echoscreen);

        Button returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(v -> {
            finish();
        });
        String receivedMessage = getIntent().getStringExtra("USER_MESSAGE");
        if (receivedMessage != null) {
            display.setText(receivedMessage);
        }

    }
}