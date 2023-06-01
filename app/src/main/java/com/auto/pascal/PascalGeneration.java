package com.auto.pascal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

public class PascalGeneration extends AppCompatActivity {

    Button clearBtn, generateBtn;
    ImageButton backBtn;
    EditText input;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pascal_generation);

        //Set Button IDs
        clearBtn = findViewById(R.id.clr_btn);
        generateBtn = findViewById(R.id.generate_btn);
        backBtn = findViewById(R.id.back_btn);

        //Set EditText Ids
        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
        output.setEnabled(false);

        final int initialWidth = output.getLayoutParams().width;
        output.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Calculate the width based on the text length
                int textWidth = (int) output.getPaint().measureText(s.toString());
                int editTextWidth = textWidth + output.getPaddingLeft() + output.getPaddingRight();

                // Adjust the width of the EditText
                output.getLayoutParams().width = Math.max(editTextWidth, initialWidth);
            }
        });

        //Set button actions
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
                output.setText("");
            }
        });

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numRowsStr = input.getText().toString();

                if (numRowsStr.isEmpty()) {
                    input.setError("Please enter the number of rows");
                    return;
                }

                int numRows = Integer.parseInt(numRowsStr);

                StringBuilder sb = new StringBuilder();

                // Generate the equilateral Pascal triangle
                for (int i = 0; i < numRows; i++) {
                    BigInteger num = BigInteger.ONE;
                    int spacing = numRows - i;

                    // Add spacing before the row
                    for (int j = 0; j < spacing; j++) {
                        sb.append(" ");
                    }

                    // Append the row
                    for (int j = 0; j <= i; j++) {
                        sb.append(String.format("%-2d ", num));
                        num = num.multiply(BigInteger.valueOf(i - j)).divide(BigInteger.valueOf(j + 1));
                    }

                    // Add spacing after the row
                    for (int j = 0; j < spacing; j++) {
                        sb.append(" ");
                    }

                    // Append a new line
                    sb.append("\n");
                }

                // Set the generated triangle in the output TextView
                output.setText(sb.toString());
            }
        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public static List<List<BigInteger>> generateEquilateralPascalTriangle(int numRows) {
        List<List<BigInteger>> triangle = new ArrayList<>();

        if (numRows <= 0) {
            return triangle;
        }

        // First row
        List<BigInteger> firstRow = new ArrayList<>();
        firstRow.add(BigInteger.ONE);
        triangle.add(firstRow);

        // Generate subsequent rows
        for (int i = 1; i < numRows; i++) {
            List<BigInteger> previousRow = triangle.get(i - 1);
            List<BigInteger> currentRow = new ArrayList<>();

            // First number in the row is always 1
            currentRow.add(BigInteger.ONE);

            // Calculate the numbers in between
            for (int j = 1; j < i; j++) {
                BigInteger num = previousRow.get(j - 1).add(previousRow.get(j));
                currentRow.add(num);
            }

            // Last number in the row is always 1
            currentRow.add(BigInteger.ONE);

            triangle.add(currentRow);
        }
        return triangle;
    }

}