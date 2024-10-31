package com.example.pads;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddData extends AppCompatActivity {


    FloatingActionButton btnReturnToTableView, btnChatBot;
    EditText editTextMonth, editTextNetIncome, editTextExpenses;
    Button btnAddDataAddTableActivity;
    String month;
    double netIncome, expenses;
    private static final String TAG = "Creo"; // Declare the tag

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG, "Add Data Activity Created");






        editTextMonth = findViewById(R.id.editTextMonth);
        editTextNetIncome = findViewById(R.id.editTextNetIncome);
        editTextExpenses = findViewById(R.id.editTextExpenses);

        btnAddDataAddTableActivity = findViewById(R.id.btnAddDataAddDataActivity);
        btnAddDataAddTableActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseHelper dbHelper = new DatabaseHelper(AddData.this);
                month = editTextMonth.getText().toString().trim();
                netIncome = Double.parseDouble(editTextNetIncome.getText().toString().trim());
                expenses = Double.parseDouble(editTextExpenses.getText().toString().trim());


                Log.d(TAG, "Adding Data: Month = " + month + ", NetIncome = " + netIncome + ", Expenses = " + expenses);
                dbHelper.addData(month, netIncome, expenses);
            }
        });


        btnChatBot = findViewById(R.id.btnChatBotAddData);
        btnChatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddData.this, ChatBot.class);
                startActivity(intent);
            }
        });


        btnReturnToTableView = findViewById(R.id.btnReturnToTableViewAddDataActivity);
        btnReturnToTableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }
}