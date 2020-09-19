package com.example.elrondweather.Helper;

import android.content.Context;

import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public  class ReadFromFile {
    static String FILENAME = "storage.json";
    private String location;
    private String result;
    private Context context;

    public ReadFromFile(Context context){
        this.context = context;
    }

    public String returnData(Weather weather) {
        String result = "";
        if (isFilePresent(FILENAME)) {
            result = read(FILENAME);
            if (result.equals("")) {
                create(FILENAME, String.valueOf(WeatherAPICall.result));
                result = String.valueOf(WeatherAPICall.result);
            }
        }
         else {
            create(FILENAME, result);
            result = read(FILENAME);
        }
        return result;

}
    private String read(String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            fis.close();
            return sb.toString();
        } catch (IOException fileNotFound) {
            return null;
        }

    }

    private boolean create(String fileName, String jsonString) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (IOException fileNotFound) {
            return false;
        }
    }

    public boolean isFilePresent(String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

}


//  new AsyncTask<Void,Void,Void>(){
//@Override
//protected Void doInBackground(Void... voids) {
//        getWeatherData(location, "en");
//        return null;
//        }
//        }.execute();