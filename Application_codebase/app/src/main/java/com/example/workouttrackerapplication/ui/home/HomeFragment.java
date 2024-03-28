package com.example.workouttrackerapplication.ui.home;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databinding.FragmentHomeBinding;
import com.example.workouttrackerapplication.ui.user.UserLoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private CalendarView calendarView;
    private List<String> calendarDates;
    private View circleHighlightVew;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        BottomNavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setVisibility(View.VISIBLE);
        calendarView = binding.calendarView;
        circleHighlightVew = LayoutInflater.from(getContext()).inflate(R.layout.coloured_circle,null);
        
        DatabaseSavedWorkouts db = new DatabaseSavedWorkouts(getContext());
        calendarDates = db.getAllDates();

        final TextView textView = binding.greetingTextHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        checkIfFirstLogin();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                long dateInMilliseconds = view.getDate();
            }
        });

        highlightCalendarDates(calendarDates);

        return root;
    }

    private void highlightCalendarDates(List calendarDates) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        Calendar calendar = Calendar.getInstance();


        for (Object dateStirng : calendarDates) {
            Date date = null;
            try {
                date = format.parse(dateStirng.toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            calendar.set(Calendar.DAY_OF_WEEK,Calendar.DAY_OF_MONTH);


            long dateInMillisecs = calendar.getTimeInMillis();
            int day = calendar.get(Calendar.DAY_OF_MONTH);


            calendarView.setDateTextAppearance(R.style.CustomCalendarDateStyle);

        }
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