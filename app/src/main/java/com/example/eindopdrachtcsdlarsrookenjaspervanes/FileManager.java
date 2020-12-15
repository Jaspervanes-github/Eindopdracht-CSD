package com.example.eindopdrachtcsdlarsrookenjaspervanes;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileManager {

    private static String fileName = "data.txt";

    public static void writeToFile(String data, Context context){
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        } catch (IOException e) {
            Log.e("EXCEPTION", "Can not write to a file" + e.toString());
        }
    }

    public static String readFromFile(Context context){
        String value = "";
        try{
            InputStream inputStream = context.openFileInput(fileName);
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                value = stringBuilder.toString();
            }

        } catch (IOException e) {
            Log.e("EXCEPTION", "Can not read from a file" + e.toString());
        }

        return value;
    }

    //TODO: making a separator for the text that is saved in the file
    //private ArrayList<>

}
