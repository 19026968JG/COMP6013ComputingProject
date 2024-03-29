package com.example.workouttrackerapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.databinding.FragmentHomeBinding;
import com.example.workouttrackerapplication.ui.user.UserLoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<String> dateList;
    private Calendar calendar;
    private TextView completedWorkoutTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        BottomNavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setVisibility(View.VISIBLE);
        MaterialCalendarView materialCalendar = binding.homeMaterialCalendar;
        completedWorkoutTextView = binding.editTextText2;
        
        DatabaseSavedWorkouts db = new DatabaseSavedWorkouts(getContext());
        dateList = db.getAllDates();

        final TextView textView = binding.greetingTextHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        calendar = Calendar.getInstance();
        checkIfFirstLogin();
        Set<CalendarDay> calendarDates = dateStringToHashSet(dateList, calendar);

        materialCalendar.addDecorator(new CalendarDecoratorRecordedWorkouts(ContextCompat
                .getColor(requireContext(),
                R.color.light_slate_grey), calendarDates));

        int monthlyWorkouts = countCurrentMonthWorkouts(dateList,calendar);
        StringBuilder builder = new StringBuilder(String.valueOf(monthlyWorkouts)
                + " Workouts Completed This Month");
        completedWorkoutTextView.setText(builder.toString());

        System.out.println(monthlyWorkouts);

        return root;
    }

    private int countCurrentMonthWorkouts(List<String> dateList, Calendar calendar) {
        int currentMonth = calendar.get(Calendar.MONTH);

        int count = 0;

        for (String date :
                dateList) {
            try {
                Calendar dateCalendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
                dateCalendar.setTime(simpleDateFormat.parse(date));

                if (dateCalendar.get(Calendar.MONTH) == currentMonth){
                    count++;
                }
            }catch (ParseException e){e.printStackTrace();}
        }
            return count;
    }

    private Set<CalendarDay> dateStringToHashSet(List<String> dates ,Calendar calendar) {
        Set<CalendarDay> calendarDates = new HashSet<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy",Locale.getDefault());

        for(String dateString : dates){
            try{
                Date date = dateFormat.parse(dateString);
                calendar.setTime(date);
                CalendarDay calendarDay = CalendarDay.from(calendar);
                calendarDates.add(calendarDay);
            } catch (ParseException e){ e.printStackTrace();}
        }
        return calendarDates;
    }

    private void checkIfFirstLogin() {
        try {
            DatabaseSavedWorkouts db = new DatabaseSavedWorkouts(getContext());

            // check if there is a user already and load login if not
            if (db.checkEmptyUsersTable()) {
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.add(R.id.container,new UserLoginFragment());
                transaction.addToBackStack("add username transition slide");
                transaction.commit();
            }

        }
        catch (Exception e){
            Toast.makeText(getContext(),"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}