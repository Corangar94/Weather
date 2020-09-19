package com.example.elrondweather.Helper;

import com.example.elrondweather.Constants;
import com.example.elrondweather.Helper.Retrofit.JsonHolderAPI;
import com.example.elrondweather.Helper.Retrofit.RetrofitClient;
import com.example.elrondweather.Helper.Retrofit.WeatherCallback;
import com.example.elrondweather.Helper.WeatherClasses.JsonToJava.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherAPICall {

   static JSONObject data = null;
    String lang = "EN";
    static JSONObject result;
    private Map<String, String> options;
    private static final String QUERY = "q";
    private static final String UNITS = "units";
    private static final String APPID = "appId";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lon";
    private static final String EXCLUDE = "exclude";

    public WeatherAPICall(){
        options = new LinkedHashMap<>();



    }
    public void setUnits(String units){
        options.put(UNITS, units);
    }

    public void loadInBackground(final WeatherCallback callback) {
        Retrofit retrofit = RetrofitClient.getInstance();
        JsonHolderAPI jsonHolderAPI = retrofit.create(JsonHolderAPI.class);
        String exclude="minutely,hourly";
        options.put(LATITUDE, String.valueOf(CurrentLocation.getLocation().getLatitude()));
        options.put(LONGITUDE, String.valueOf(CurrentLocation.getLocation().getLongitude()));
        setUnits("metric");
        options.put(EXCLUDE, exclude);
        options.put(APPID, Constants.API);
        //options= finalString;
        Call<Weather> call = jsonHolderAPI.getCurrentWeatherByCityName(options);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                handleThreeHourForecastResponse(response,callback);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                callback.onFailure(t);

            }
        });
    }
    private void handleThreeHourForecastResponse(Response<Weather> response, WeatherCallback callback){
            callback.onSuccess(response.body());
    }
    private Throwable NoAppIdErrMessage() {
        return new Throwable("UnAuthorized. Please set a valid OpenWeatherMap API KEY.");
    }


    private Throwable NotFoundErrMsg(String str) {
        Throwable throwable = null;
        try {
            JSONObject obj = new JSONObject(str);
            throwable = new Throwable(obj.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (throwable == null){
            throwable = new Throwable("An unexpected error occurred.");
        }


        return throwable;
    }
//
       // //Here we will initiate AsyncTaskLoader and handle task in background
       // Void res = null;
       // try {
       //     URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&" + "&units=metric" + "&appid=8e33ed387f59ba576a91cb7859e8366e");
//
       //     HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
       //     BufferedReader reader =
       //             new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
       //     StringBuffer json = new StringBuffer(1024);
       //     String tmp = "";
//
       //     while ((tmp = reader.readLine()) != null)
       //         json.append(tmp).append("\n");
       //     reader.close();
//
       //     data = new JSONObject(json.toString());
//
       //     if (data.getInt("cod") != 200) {
       //         System.out.println("Cancelled");
       //     }
       // } catch (Exception e) {
//
       //     System.out.println("Exception " + e.getMessage());
       // }
       // return null;




}
