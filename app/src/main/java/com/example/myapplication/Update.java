package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update extends AppCompatActivity {
    private EditText textName;
    private EditText textPassword;
    private RadioGroup RadioGendrr;
    private RadioButton Selector;
    private EditText textDob;
    private EditText textMobile;
    //   private TextView textLocation;


    //   private ProgressBar progressBar;
    private String Name;
    private String Password;
    private String gender;
    //  private String Dob;
    private String mobile;
    private ImageView imageView;
    private FirebaseAuth authFirebase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        textName = findViewById(R.id.EditName);
        textPassword = findViewById(R.id.EditPassword);
        // textDob = findViewById(R.id.EditDob);
        textMobile= findViewById(R.id.EditMobile);
        RadioGendrr = findViewById(R.id.radio_button);


        // textLocation= findViewById(R.id.textLocation);
        authFirebase =FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= authFirebase.getCurrentUser();
        showProfile( firebaseUser);
        Button button=findViewById(R.id.EditEmail);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Update.this,UpdateEmail.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showProfile(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference(" Update Profile");
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDetails details= snapshot.getValue((UserDetails.class));
                if (details != null){
                    //    private String Name,Password,,Dob,gender;
                    Name=firebaseUser.getDisplayName();
                    //  Email=firebaseUser.getEmail();
                    //  Dob=details.getDob();
                    Password=details.getPassword();
                    gender=details.getGender();
                    mobile=details.getMobile();
                    // Location=details.location;
                    textName.setText(Name);
                    textPassword.setText(Password);
                    //    textDob.setText(Dob);
                    textMobile.setText(mobile);
                    // the gender

                }
                else {
                    Toast.makeText(Update.this, "Somthing Wrong...!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update.this, "Somthing Wrong...!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}