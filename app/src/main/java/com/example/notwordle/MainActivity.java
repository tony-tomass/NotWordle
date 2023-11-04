package com.example.notwordle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

//TODO: Have check for null values

public class MainActivity extends AppCompatActivity {

    Button submit_bt;
    Button restart_bt;
    Button clear_bt;
    GridLayout grid;
    char[] word;
    String answer;
    int[][] box_state;
    static final int ROW_SIZE = 5;
    static final int WIN_CONDITION = 10;
    int current_row_index;

    View.OnClickListener submit_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*
            //For testing color
            box_state[0][0] = -2;
            box_state[0][1] = -1;
            box_state[0][2] = 0;
            box_state[0][3] = 1;
            box_state[0][4] = 2;
             */

            checkWord();
            if (!checkWinCondition()) {
                if (current_row_index < 5) {
                    current_row_index++;
                    toNextRow();
                }
                else {
                    Toast.makeText(getApplicationContext(), "you losed womp womp", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "your winner!!!", Toast.LENGTH_LONG).show();
            }
        }
    };
    View.OnClickListener restart_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //
        }
    };
    View.OnClickListener clear_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit_bt = findViewById(R.id.submit_BT);
        restart_bt = findViewById(R.id.restart_BT);
        clear_bt = findViewById(R.id.clear_BT);
        grid = findViewById(R.id.box_grid_GL);

        submit_bt.setOnClickListener(submit_listener);
        restart_bt.setOnClickListener(restart_listener);
        clear_bt.setOnClickListener(clear_listener);

        //TODO: Implement database to pull answers from
        answer = "brain";

        /*
        -2 = next row (greyed out)
        -1 = incorrect
        0 = current row
        1 = almost correct
        2 = correct
        Starting off, the top row must all be 0 and all rows below -2
         */
        current_row_index = 0;
        box_state = new int[][]{
                {0, 0, 0, 0, 0},
                {-2, -2, -2, -2, -2},
                {-2, -2, -2, -2, -2},
                {-2, -2, -2, -2, -2},
                {-2, -2, -2, -2, -2},
                {-2, -2, -2, -2, -2}
        };
    }

    public void checkRows() {
        for (int i = 0; i < grid.getChildCount(); i++) {
            EditText et = (EditText) grid.getChildAt(i);

            //Log.i("child_count", String.valueOf(grid.getChildCount()));

            //Gets EditText box's row and column numbers; explanations are there bc I'm slow
            int row = i / ROW_SIZE; //Int rounds down => First row = 0 and so on
            int col = i % ROW_SIZE; //0 % 5 = 0, 1 % 5 = 1....5 % 5 = 0, 6 % 5 = 0 and so on

            //Log.i("starting", "This is Row " + row + " Col. " + col);
            switch (box_state[row][col]) {
                //https://stackoverflow.com/questions/12523005/how-set-background-drawable-programmatically-in-android
                case -2:
                    //TODO: Technically not needed since it won't be changing to this state ever, only starting at it
                    et.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.not_yet_et));
                    et.setTextColor(getResources().getColor(R.color.white));
                    et.setEnabled(false);
                    //Log.i("unselected", "This box is now grey-out and not selectable");
                    break;
                case -1:
                    et.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.incorrect_et));
                    et.setTextColor(getResources().getColor(R.color.white));
                    et.setEnabled(false);
                    //Log.i("incorrect", "This box is now dark gray and incorrect");
                    break;
                case 0:
                    et.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.current_et));
                    et.setTextColor(getResources().getColor(R.color.black));
                    et.setEnabled(true);
                    //Log.i("current", "This box is now part of the current row and is selectable");
                    break;
                case 1:
                    et.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.almost_correct_et));
                    et.setTextColor(getResources().getColor(R.color.white));
                    et.setEnabled(false);
                    //Log.i("almost", "This box is now yellow and almost correct");
                    break;
                case 2:
                    et.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.correct_et));
                    et.setTextColor(getResources().getColor(R.color.white));
                    et.setEnabled(false);
                    //Log.i("correct", "This box is now green and correct!");
                    break;
            }
        }
    }

    public void toNextRow() {
        //Log.i("setting_next_row", "Setting up to next row...");
        for (int i = 0; i < ROW_SIZE; i++) {
            box_state[current_row_index][i] = 0;
            //Log.i("boxes_done", "Box #" + i + " done.");
        }
        checkRows();
    }

    public void checkWord() {
        StringBuilder stringBuilder = new StringBuilder();
        String input_word = "";
        //Log.i("current_index_check", String.valueOf(current_row_index));



        for (int i = 0; i < ROW_SIZE; i++) {
            EditText et = (EditText) grid.getChildAt(getChildAtCalculator(i));
            //Log.i("int_i_check", String.valueOf(i));
            //Log.i("current_letter", et.getText().toString());
            stringBuilder.append(et.getText().toString());
        }
        input_word = stringBuilder.toString().toLowerCase();
        //Log.i("input_word", input_word);

        char[] input_chars = input_word.toCharArray();
        char[] answer_chars = answer.toCharArray();

        for (int i = 0; i < ROW_SIZE; i++) {
            EditText et = (EditText) grid.getChildAt(getChildAtCalculator(i));
            String box_letter = et.getText().toString().toLowerCase();
            //Log.i("box_letter", box_letter);
            //Log.i("check_if_contain", String.valueOf(answer.contains(box_letter)));
            if (answer.contains(box_letter)) {
                //Log.i("index_of_letter", "Index of Input Letter '" + box_letter + "': " + input_word.indexOf(box_letter));
                //Log.i("index_of_ans_letter", "Index of Answer Letter '" + box_letter + "': " + answer.indexOf(box_letter));
                if (input_chars[i] == answer_chars[i]) {
                    //Correct
                    box_state[current_row_index][i] = 2;
                }
                else {
                    //Almost Correct
                    box_state[current_row_index][i] = 1;
                }
            }
            else {
                //Incorrect
                box_state[current_row_index][i] = -1;
            }
            checkRows();
        }
    }

    public boolean checkWinCondition() {
        int points = 0;
        for (int i = 0; i < box_state[current_row_index].length; i++) {
            points += box_state[current_row_index][i];
        }
        Log.i("points", String.valueOf(points));
        return points >= 10;
    }

    public int getChildAtCalculator(int i) {
        //(Current row index * (Length of row - 1) + i
        //(0 * 4) + 0 = 0 => B
        //(0 * 4) + 1 = 1 => R
        //...
        //(1 + 4) + 0 = 5
        return (current_row_index * (box_state[current_row_index].length)) + i;
    }

}