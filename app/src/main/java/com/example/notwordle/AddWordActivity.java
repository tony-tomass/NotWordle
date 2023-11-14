package com.example.notwordle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
                //https://stackoverflow.com/questions/38948905/how-can-i-check-if-a-value-exists-already-in-a-firebase-data-class-android
                // TODO: orderBy is VERY IMPORTANT, IT DOES NOT WORK WITHOUT IT
                Query query = myDB.child("words").orderByValue().equalTo(s);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Log.i("EXISTS", "True");
                            Toast.makeText(getApplicationContext(), "Error: Word already exists in word bank!",
                                    Toast.LENGTH_LONG).show();
                            //enter_word_et.setTextColor(Color.parseColor("purple"));
                        }
                        else {
                            Log.i("EXISTS", "False");
                            myDB.child("words").push().setValue(s);
                            Toast.makeText(getApplicationContext(), "Word has been added to word bank!",
                                    Toast.LENGTH_LONG).show();
                            enter_word_et.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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