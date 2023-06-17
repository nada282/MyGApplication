package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {
    private TextView textName;
    private TextView textPassword;
    private TextView textEmail;
    private TextView textDob;
    private TextView textLocation;
    private Button button,button2;

    //   private ProgressBar progressBar;
    private String Name,Password,Email,Dob,Location;
    private ImageView imageView;
    private FirebaseAuth authFirebase;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textName = findViewById(R.id.textName);
        textPassword = findViewById(R.id.textPassword);
        textEmail = findViewById(R.id.textEmail);
        textDob = findViewById(R.id.textDob);
        textLocation= findViewById(R.id.textLocation);
         button= findViewById(R.id.button);
         button2= findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1;
                in1 = new Intent (Profile.this,Update.class);
                startActivity(in1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent in2;
                in2 = new Intent (Profile.this,Registration.class);
                startActivity(in2);
                finish();
            }
        });

//        progressBar =findViewById(R.id.progressBarr);
        authFirebase =FirebaseAuth.getInstance();

        FirebaseUser firebaseUser= authFirebase.getCurrentUser();
        if (firebaseUser==null){
            Toast.makeText(this, "Something Wrong With DataBase", Toast.LENGTH_SHORT).show();
        }
        else {
            //  progressBar.setVisibility(View.VISIBLE);
            showProfile(firebaseUser);
        }
    }

    private void showProfile(FirebaseUser firebaseUser) {
        String UserId=firebaseUser.getUid();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDetails details= snapshot.getValue(UserDetails.class);
                if(details != null) {
                    Name = firebaseUser.getDisplayName();
                    Email = firebaseUser.getEmail();
               //     Dob = details.getDob();
                    Password=details.getPassword();
                    Location =details.Location;
                    textName.setText(Name);
                    textEmail.setText(Email);
                    textDob.setText(Dob);
                    textPassword.setText(Password);
                    textLocation.setText(Location);

                }
                //   progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Somthing Wrong...!", Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public TextView getTextName() {
        return textName;
    }

    public void setTextName(TextView textName) {
        this.textName = textName;
    }

    public TextView getTextPassword() {
        return textPassword;
    }

    public void setTextPassword(TextView textPassword) {
        this.textPassword = textPassword;
    }

    public TextView getTextEmail() {
        return textEmail;
    }

    public void setTextEmail(TextView textEmail) {
        this.textEmail = textEmail;
    }

    public TextView getTextDob() {
        return textDob;
    }

    public void setTextDob(TextView textDob) {
        this.textDob = textDob;
    }



}