 package com.example.contatti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.contatti.ui.login.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

 public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_profile);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String key = encodeUserEmail(auth.getCurrentUser().getEmail());
        final TextView nicknameEditText = findViewById(R.id.contatto_nickname);
        final TextView emailEditText = findViewById(R.id.contatto_email);
        final TextView numberEditText = findViewById(R.id.contatto_numeroDiTelefono);
        emailEditText.setText(decodeUserEmail(key));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(key).child("nickname").getValue()!=null && snapshot.child(key).child("numeroDiTelefono").getValue()!=null) {
                    nicknameEditText.setText(snapshot.child(key).child("nickname").getValue().toString());
                    numberEditText.setText(snapshot.child(key).child("numeroDiTelefono").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final Button indietro = findViewById(R.id.indietro_profilo);
        indietro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                finish();
            }
        });
        final Button edit = findViewById(R.id.modifica_profilo);
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditActivity.class));
                finish();
            }
        });
    }
     static String encodeUserEmail(String userEmail) {
         return userEmail.replace(".", ",");
     }

     static String decodeUserEmail(String userEmail) {
         return userEmail.replace(",", ".");
     }
}