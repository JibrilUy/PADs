package com.example.pads;
import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.util.Log;


import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "PADs.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "sales";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_MONTH = "month";

    private static final String COLUMN_NET_INCOME = "net_income";

    private static final String COLUMN_EXPENSES = "expenses";

    private static final String TAG = "Creo"; // Declare the tag



    public DatabaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Database Helper Created");
        try {
            String query = "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MONTH + " TEXT, " +
                    COLUMN_NET_INCOME + " DOUBLE, " +
                    COLUMN_EXPENSES + " DOUBLE );";
            db.execSQL(query);
        } catch (SQLException e) {
            Log.d(TAG, "Error creating table: " + e.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addData(String month, double netIncome, double expenses) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MONTH, month);
        cv.put(COLUMN_NET_INCOME, netIncome);
        cv.put(COLUMN_EXPENSES, expenses);

        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Log.d(TAG, "Failed to Save Data");
            Toast.makeText(context,"Failed to Save Data",Toast.LENGTH_SHORT).show();
        }else{
            Log.d(TAG, "Saved successfully");
            Toast.makeText(context,"Saved Successfully!",Toast.LENGTH_SHORT).show();
        }

    }

    Cursor readAllData() {
        String query = "SELECT month, net_income, expenses FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public ArrayList<Double> getNetIncomeData(){
        ArrayList<Double> netIncome = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT net_income FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null );

        if(cursor.moveToFirst()){
            do{
                double income =cursor.getDouble(0);
                netIncome.add(income);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return netIncome;
    }

    public ArrayList<Double> getExpenses(){
        ArrayList<Double> netIncome = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT expenses FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null );

        if(cursor.moveToFirst()){
            do{
                double income =cursor.getDouble(0);
                netIncome.add(income);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return netIncome;
    }



}
