package com.example.myapplication;


import static android.content.ContentValues.TAG;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText fullName, email, mobileNumber, location,
            password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(getActivity(), "Successfully", Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        }
    }


    public Signup_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_signup, container, false);
        initViews();
        setListeners();
        return view;

    }

    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.fullName);
        email = (EditText) view.findViewById(R.id.userEmailId);
        mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        location = (EditText) view.findViewById(R.id.location);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);
        // ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Setting text selector over textviews
        try {
            @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.textview_selector);
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();
                break;

            case R.id.already_user:

                // Replace login fragment
                new Registration().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getEmailId = email.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0) {

            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");
        }
        // Check if email id valid or not
        else if (!m.find()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
        }
        // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword)) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");
        }
        // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");
        }
        mAuth.createUserWithEmailAndPassword(getEmailId, getPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Authentication Successful.", Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent = new Intent(getActivity(),MainActivity.class);
                            startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getActivity(), "Authentication Failed.", Toast.LENGTH_SHORT)
                                    .show();

                        }
                    }
                });
    }
}