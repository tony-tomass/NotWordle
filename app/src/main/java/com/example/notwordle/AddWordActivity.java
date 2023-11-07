package com.example.notwordle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class AddWordActivity extends AppCompatActivity {

    Button switch_bt;
    Button add_bt;
    EditText enter_word_et;

    View.OnClickListener switch_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener add_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        switch_bt = findViewById(R.id.back_BT);
        add_bt = findViewById(R.id.add_word_ADACT_BT);
        enter_word_et = findViewById(R.id.enter_word_ET);

        switch_bt.setOnClickListener(switch_listener);
        add_bt.setOnClickListener(add_listener);

    }
}