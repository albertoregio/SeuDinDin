package com.example.regio.seudindin.utils;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadScript {
    public static int insertFromFile(Context context, int resourceCode, SupportSQLiteDatabase db) throws IOException {
        // Reseting Counter
        int result = 0;

        // Open the resource
        InputStream insertsStream = context.getResources().openRawResource(resourceCode);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

        // Iterate through lines (assuming each insert has its own line and theres no other stuff)
        while (insertReader.ready()) {
            String insertStmt = insertReader.readLine();
            if(insertStmt != null){
                if(insertStmt.isEmpty()) continue;
                db.execSQL(insertStmt);
                result++;
                Log.d("Statement #", Integer.toString(result));
            }
        }
        insertReader.close();

        // returning number of inserted rows
        return result;
    }
}