package musictag.hytham1.com.newfoodorderclientfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText EDITTEXT_USER , EDITTEXT_PASS;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EDITTEXT_USER = findViewById(R.id.et_userEmail);
        EDITTEXT_PASS = findViewById(R.id.et_userPass);
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public void loginButtonClicked(View view) {

        String user_text = EDITTEXT_USER.getText().toString().trim();
        String user_pass = EDITTEXT_PASS.getText().toString().trim();

        if (TextUtils.isEmpty(user_text) || TextUtils.isEmpty(user_pass))
            Toast.makeText(this, "Please fill all the fields and make sure connect the internet", Toast.LENGTH_LONG).show();


        if (!TextUtils.isEmpty(user_text) && !TextUtils.isEmpty(user_pass)){

            mAuth.signInWithEmailAndPassword( user_text , user_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        checkUserExists();

                    }
                }
            });
        }
    }
    public void checkUserExists(){
        final String user_id = mAuth.getCurrentUser().getUid();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)){
                    Intent intent = new Intent(LoginActivity.this , MenuActivity.class);
                    startActivity(intent);
                }

                }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
