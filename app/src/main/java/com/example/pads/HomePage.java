package com.example.pads;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import static java.security.AccessController.getContext;

import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;  
import androidx.core.view.WindowInsetsCompat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class HomePage extends AppCompatActivity {
    private static final String TAG = "Creo"; // Declare the tag
    RecyclerView recyclerViewHomePage;
    FloatingActionButton btnAddDataToTable, btnChatBot;

    DatabaseHelper dbHelper;
    ArrayList<String> month, net_income, expenses;
    RecyclerViewAdapter recyclerViewAdapter;
    Button btnPrediction;
    LinearLayout predictionLayout;
    TextView textViewNetIncomeIncreased, textViewExpensesIncreased, textViewNetIncomePropability, textViewExpensesPropability;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG,"PADs Created");
        predictionLayout = findViewById(R.id.predictionLayout);
        predictionLayout.setVisibility(View.GONE);

        dbHelper = new DatabaseHelper(HomePage.this);
        month = new ArrayList<>();
        net_income = new ArrayList<>();
        expenses = new ArrayList<>();

        storeDataInArrays();
        recyclerViewHomePage = findViewById(R.id.recyclerViewHomePage);

        recyclerViewAdapter = new RecyclerViewAdapter(HomePage.this, month, net_income, expenses);
        recyclerViewHomePage.setAdapter(recyclerViewAdapter);
        recyclerViewHomePage.setLayoutManager(new LinearLayoutManager(HomePage.this));

        textViewNetIncomeIncreased = findViewById(R.id.textViewNetIncomeIncreaseMonth);
        textViewExpensesIncreased = findViewById(R.id.textViewExpensesIncreaseMonth);
        textViewNetIncomePropability = findViewById(R.id.textViewNetIncomePropability);
        textViewExpensesPropability = findViewById(R.id.textViewExpensesPropability);


        btnPrediction = findViewById(R.id.btnPrediction);
        btnPrediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Double> netIncome = dbHelper.getNetIncomeData();
                ArrayList<Double> expenses = dbHelper.getExpenses();
                if(!netIncome.isEmpty()){
                    predictionLayout.setVisibility(View.VISIBLE);
                    calculateBayesProbability(netIncome, textViewNetIncomeIncreased, textViewNetIncomePropability);
                    calculateBayesProbability(expenses, textViewExpensesIncreased, textViewExpensesPropability);



                }else{
                    Toast.makeText(HomePage.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnAddDataToTable = findViewById(R.id.btnAddDataToTable);
        btnAddDataToTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, AddData.class);
                startActivity(intent);
            }
        });

        btnChatBot = findViewById(R.id.btnChatBotHomePage);
        btnChatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, ChatBot.class);
                startActivity(intent);
            }
        });


    }

    private double calculateBayesProbability(ArrayList<Double> value, TextView textViewMonth, TextView textViewPrediction){

        double lowestIncome = Double.MIN_VALUE;
        double previousIncome = Double.MIN_VALUE;
        int countHighIncome = 0;
        for(double income : value){
            if(income > previousIncome){
                countHighIncome++;
            }
            previousIncome = income;
        }

        int totalMonths = value.size();
        double pNI = (double) countHighIncome / totalMonths;//Probability Net Income Increases
        Log.d(TAG, String.valueOf(countHighIncome));
        double percentage = pNI * 100;

        DecimalFormat df = new DecimalFormat("#.00");
        String formattedDouble = df.format(percentage);
        double finalPercentage = Double.parseDouble(formattedDouble);

        textViewMonth.setText(String.valueOf(countHighIncome));
        textViewPrediction.setText(String.valueOf(finalPercentage) + "%");


        Log.d(TAG, String.valueOf(finalPercentage));
        return finalPercentage;
    }

    void storeDataInArrays(){

        Cursor cursor = dbHelper.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            Log.d(TAG,"Cursor is retrieving Data");

            while (cursor.moveToNext()){
                month.add(cursor.getString(0));
                net_income.add(cursor.getString(1));
                expenses.add(cursor.getString(2));

            }
            Log.d(TAG, "Data storage in arrays is complete. Total records: " + month.size());

        }

    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}