package com.example.workouttrackerapplication.ui.home;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import androidx.annotation.NonNull;

import com.example.workouttrackerapplication.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Set;

public class CalendarDecoratorRecordedWorkouts implements DayViewDecorator {
    private final int colour;
    private final Set<CalendarDay> dates;

    public CalendarDecoratorRecordedWorkouts(int colour, Set<CalendarDay> dates){
        this.colour=colour;
        this.dates=dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(15, colour));
    }
    
}
