package com.example.workouttrackerapplication;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection connection;
    String username,password,ip,port,database;

    public Connection connectionClass(){
        ip = "127.1.1.0";
        database="workout_app_max_lifts";
        port="3306";
        password="14g072Fu1440.";
        username="root";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection con = null;
        String ConnectionURL=null;

        try {
            Class.forName("com.example.workouttracker");
            ConnectionURL = "jdbc:mysql://"+ip+":"+port+"/"+database+"?user="+username+"&password="+password;
            con = DriverManager.getConnection(ConnectionURL);
        }catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
        return con;
    }
}
