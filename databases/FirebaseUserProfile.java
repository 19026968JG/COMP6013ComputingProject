package com.example.workouttrackerapplication.databases;

import android.nfc.cardemulation.HostApduService;

import java.util.HashMap;
import java.util.Objects;

public class FirebaseUserProfile {

    private String userName;
    private float squat;
    private float bench;
    private float deadlift;

    public FirebaseUserProfile(String userName, float squat, float bench, float deadlift) {
        this.userName = userName;
        this.squat = squat;
        this.bench = bench;
        this.deadlift = deadlift;
    }

    public HashMap<String, Object> getAllValues() {
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("userName", userName);
        userData.put("SQUAT", squat);
        userData.put("BENCH", bench);
        userData.put("DEADLIFT", deadlift);
        return userData;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getSquat() {
        return squat;
    }

    public void setSquat(float squat) {
        this.squat = squat;
    }

    public float getBench() {
        return bench;
    }

    public void setBench(float bench) {
        this.bench = bench;
    }

    public float getDeadlift() {
        return deadlift;
    }

    public void setDeadlift(float deadlift) {
        this.deadlift = deadlift;
    }
}
