package com.example.session3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InputActivity extends AppCompatActivity {

    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button send = findViewById(R.id.sendbtn);
        message = findViewById(R.id.inputScreen);
        message.setText("");

        Log.d("InputActivity", "onCreate called");

        send.setOnClickListener(view -> {
            String sendText = message.getText().toString();
            Intent intent = new Intent(InputActivity.this, EchoActivity.class);
            intent.putExtra("USER_MESSAGE", sendText);
            startActivity(intent);
        });
    }
}