package com.example.workouttrackerapplication.ui.workouts;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

public class WorkoutsHashMapAdapter extends BaseAdapter {

    private final ArrayList displayData;

    public WorkoutsHashMapAdapter(Map<String, String> map) {
        displayData = new ArrayList();
        displayData.addAll(map.entrySet());
    }
    @Override
    public int getCount() {
        return displayData.size();
    }

    @Override

    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) displayData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
