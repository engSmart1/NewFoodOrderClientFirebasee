package musictag.hytham1.com.newfoodorderclientfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword ;
    Button signUp;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.et_email);
        editTextPassword = findViewById(R.id.et_password);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

    }

    public void signUpClicked(View view) {

        final String email_text = editTextEmail.getText().toString().trim();
        String pass_text = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_text))
            Toast.makeText(this, "Please fill all the fields and make sure connect the internet", Toast.LENGTH_LONG).show();



         try {
             if (!TextUtils.isEmpty(email_text) && !TextUtils.isEmpty(pass_text) ) {

               //  Toast.makeText(this, "Please Check if u have already an account or low password", Toast.LENGTH_SHORT).show();
                 mAuth.createUserWithEmailAndPassword(email_text, pass_text ).addOnCompleteListener
                         (new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             String user_id = mAuth.getCurrentUser().getUid();
                             DatabaseReference current_user = mDatabase.child(user_id);
                             current_user.child("Name").setValue(email_text);

                             startActivity( new Intent(MainActivity.this , LoginActivity.class));

                             editTextEmail.setText("");
                             editTextPassword.setText("");
                         }

                     }
                 });

             }
            // editTextEmail.setText("");
            // editTextPassword.setText("");

         } catch (Exception e){
             Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
         }
    }

    public void signInClicked(View view) {
        Intent loginIntent = new Intent(MainActivity.this , LoginActivity.class);
        startActivity(loginIntent);
    }
}
