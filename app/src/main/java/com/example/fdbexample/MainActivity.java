package com.example.fdbexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EditText fName, lName, age, email;
    private Button btnAdd, btnView;
    int maxid ;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sID = (EditText) findViewById(R.id.etStudentId);
        fName = (EditText) findViewById(R.id.etFirstName);
        lName = (EditText) findViewById(R.id.etLastName);
        age = (EditText) findViewById(R.id.etAge);
        email = (EditText) findViewById(R.id.etEmail);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);

        // get the instance of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // get reference for our database.
        databaseReference = firebaseDatabase.getReference().child("Student");


        // adding on click listener for our button.
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  int sid = Integer.parseInt(sID.getText().toString());
                String fname = fName.getText().toString();
                String lname = lName.getText().toString();
                int sage = Integer.parseInt(age.getText().toString());
                String Email = email.getText().toString();


                student = new Student(fname, lname, sage, Email);


                databaseReference.child(String.valueOf(maxid+1)).setValue(student);

                //   sID.setText("");
                fName.setText("");
                lName.setText("");
                age.setText("");
                email.setText("");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            maxid = (int) snapshot.getChildrenCount();
                        } else {
                        }
                        Toast.makeText(MainActivity.this, "Student Record Added Successfully!!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Error Adding Student Records!!!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ViewRecords.class);
                startActivity(intent);

            }
        });
    }
}