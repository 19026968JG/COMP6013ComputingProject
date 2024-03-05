package com.example.workouttrackerapplication.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.workouttrackerapplication.databinding.UserLoginBinding;

public class UserLoginFragment extends Fragment {

    private UserLoginBinding binding;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = UserLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}
