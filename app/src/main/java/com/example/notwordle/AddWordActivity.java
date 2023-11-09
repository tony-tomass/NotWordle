package com.example.notwordle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddWordActivity extends AppCompatActivity {

    Button switch_bt;
    Button add_bt;
    EditText enter_word_et;
    FirebaseDatabase database;
    DatabaseReference myDB;

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
            String s = enter_word_et.getText().toString().toLowerCase().trim();
            if (!checkWord(s)) {
                Toast.makeText(getApplicationContext(), "Error: Empty field or invalid length", Toast.LENGTH_LONG).show();
            }
            else {
                myDB.child("words").push().setValue(s);
                Toast.makeText(getApplicationContext(), "Word has been added to word bank!", Toast.LENGTH_LONG).show();
                enter_word_et.setText("");
            }
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

        database = FirebaseDatabase.getInstance();
        myDB = database.getReference();

    }

    public boolean checkWord(String word) {
        return word != null && word.length() == 5;
    }
}