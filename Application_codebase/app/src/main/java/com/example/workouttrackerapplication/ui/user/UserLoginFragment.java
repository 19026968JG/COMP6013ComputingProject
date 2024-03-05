package com.example.workouttrackerapplication.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.workouttrackerapplication.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.MainActivity;
import com.example.workouttrackerapplication.databinding.UserLoginBinding;

public class UserLoginFragment extends Fragment {

    private UserLoginBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = UserLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DatabaseSavedWorkouts db = new DatabaseSavedWorkouts(getContext());



        binding.saveLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = requireActivity().getSupportFragmentManager();

                if (TextUtils.isEmpty(binding.usernameInput.getText().toString())) {
                    Toast.makeText(getContext(), "You Must Enter a Username to Continue",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(requireActivity().getApplicationContext(), "Username Saved", Toast.LENGTH_SHORT).show();

                    try {db.addToUsersTable(binding.usernameInput.getText().toString());}
                    catch (Exception e) {Toast.makeText(getContext(), "Error Adding to Database",Toast.LENGTH_SHORT).show();}

                    manager.popBackStack();
                }
            }
        });


        return root;
    }
}
