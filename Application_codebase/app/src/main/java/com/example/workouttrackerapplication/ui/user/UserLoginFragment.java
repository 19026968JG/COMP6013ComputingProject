package com.example.workouttrackerapplication.ui.user;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.workouttrackerapplication.R;
import com.example.workouttrackerapplication.databases.DatabaseSavedWorkouts;
import com.example.workouttrackerapplication.databases.FirebaseUserProfile;
import com.example.workouttrackerapplication.databinding.UserLoginBinding;
import com.example.workouttrackerapplication.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UserLoginFragment extends Fragment {

    private UserLoginBinding binding;
    private DatabaseReference fdb;
    private FirebaseUserProfile profile;
    private DatabaseSavedWorkouts db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = UserLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = new DatabaseSavedWorkouts(getContext());

        fdb = FirebaseDatabase.getInstance("https://workoutdatabaseserver-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        binding.saveLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = requireActivity().getSupportFragmentManager();

                if (TextUtils.isEmpty(binding.usernameInput.getText().toString())) {
                    Toast.makeText(getContext(), "You Must Enter a Username to Continue",Toast.LENGTH_SHORT).show();
                }
                else {

                    Context context = requireContext();
                         fdb.child("LeaderboardValues").child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(binding.usernameInput.getText().toString())) {
                                    Toast.makeText(context, "Username Already Exists", Toast.LENGTH_LONG).show();
                                } else {

                                    saveUserToBothDatabases();
                                    Toast.makeText(context, "Username Saved", Toast.LENGTH_SHORT).show();

                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.replace(R.id.home_fragment, new HomeFragment());
                                    manager.popBackStack();
                                    transaction.commit();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("Database error", error.getMessage());
                            }
                        });
                }
            }
        });


        return root;
    }

    private void saveUserToBothDatabases() {
        db = new DatabaseSavedWorkouts(getContext());
        fdb = FirebaseDatabase.getInstance("https://workoutdatabaseserver-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        try {
            db.addToUsersTable(binding.usernameInput.getText().toString());
            FirebaseUserProfile profile = new FirebaseUserProfile(
                    binding.usernameInput.getText().toString(),
                    -1, -1, -1);

            fdb
                .child("LeaderboardValues")
                .child("Users")
                .child(profile.getUserName())
                .setValue(profile.getAllValues());
        }

        catch (Exception e) {
            Log.e("Database error", e.getMessage());
            Toast.makeText(getContext(), "Error Adding to Database",Toast.LENGTH_SHORT).show();}

    }
}
