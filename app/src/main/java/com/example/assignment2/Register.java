package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

public class Register extends AppCompatActivity {



    String e, p, t;
    User u;
    User tu=new User();

    boolean Exist=false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public boolean NameCheck(String n){

        Query query = db.collection("User").whereEqualTo("email",n);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentSnapshot doc : value){
                    tu = doc.toObject(User.class);
                }
            }
        });
        if(tu.getEmail()!=""){
            return true;
        }
        return false;
    }


    public void onRadioButtonClicked(View v){
        boolean checked = ((RadioButton) v).isChecked();

        // Check which radio button was clicked
        switch(v.getId()) {
            case R.id.admin:
                if (checked)
                    t="admin";
                    break;
            case R.id.player:
                if (checked)
                    t="player";
                    break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = new Intent(this, Login.class);

        EditText email = (EditText) findViewById(R.id.email);
        EditText pw = (EditText) findViewById(R.id.pw);

        RadioGroup type = (RadioGroup) findViewById(R.id.type);
        RadioButton RB = findViewById(type.getCheckedRadioButtonId());

        Button register = (Button) findViewById(R.id.register);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                NameCheck(email.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                NameCheck(email.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                e = email.getText().toString();
                p = pw.getText().toString();

                if (e==""||p==""){
                    Toast.makeText(Register.this, "Please enter all fields",Toast.LENGTH_LONG);
                }else if(p.length()<6){
                    Toast.makeText(Register.this,"Too short password (more than 6)",Toast.LENGTH_LONG);
                }else if (Exist==true){
                    Toast.makeText(Register.this,"Existing email. Please use another email",Toast.LENGTH_LONG);
                }else{
                    firebaseAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(Register.this,"Registered successfully",Toast.LENGTH_LONG);
                        }
                    });
                    SaveU(e,t);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    public void SaveU(String email, String type){
        User u = new User(email,type);
        db.collection("User").document(email).set(u);
    }
}