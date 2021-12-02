package com.example.fdbexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class Edit_Delete_Records extends AppCompatActivity {

    private EditText fName, lName, age, email;
    private TextView sID;
    private Button btnDelete, btnUpdate;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_delete_records);

         sID = (TextView) findViewById(R.id.txtStudentId);
        fName = (EditText) findViewById(R.id.etFirstName);
        lName = (EditText) findViewById(R.id.etLastName);
        age = (EditText) findViewById(R.id.etAge);
        email = (EditText) findViewById(R.id.etEmail);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);


        Intent intent = getIntent();
        sID.setText(Integer.toString(intent.getExtras().getInt("ID")));
        fName.setText(intent.getExtras().getString("fName"));
        lName.setText(intent.getExtras().getString("lName"));
        age.setText(Integer.toString(intent.getExtras().getInt("age")));
        email.setText(intent.getExtras().getString("email"));


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Student");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                student = new Student(fName.getText().toString(), lName.getText().toString(), Integer.parseInt(age.getText().toString()), email.getText().toString());

                databaseReference.child(sID.getText().toString()).setValue(student);
                Toast.makeText(Edit_Delete_Records.this, "Student Record Updated..", Toast.LENGTH_SHORT).show();

                // launching our main activity.
                Intent i = new Intent(Edit_Delete_Records.this, MainActivity.class);
                startActivity(i);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child(sID.getText().toString()).removeValue((DatabaseReference.CompletionListener) student);
                Intent i = new Intent(Edit_Delete_Records.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
}