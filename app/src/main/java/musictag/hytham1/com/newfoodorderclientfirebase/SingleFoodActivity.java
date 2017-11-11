package musictag.hytham1.com.newfoodorderclientfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleFoodActivity extends AppCompatActivity {
    ImageView mFoodImage;
    TextView mTitle , mDesc , mPrice;
    DatabaseReference mdatabase ,mUser;
    FirebaseAuth mAuth;
    private String food_key = null;
    private FirebaseUser cuurentUser;
    private DatabaseReference mRef;
    String food_name , food_desc , food_image , food_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        food_key = getIntent().getExtras().getString("foodId");
        mFoodImage = findViewById(R.id.singleImageView);
        mTitle = findViewById(R.id.singleTitle);
        mDesc = findViewById(R.id.singleDesc);
        mPrice = findViewById(R.id.singlePrice);

        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Item4");
        mRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        cuurentUser = mAuth.getCurrentUser();

        mUser = FirebaseDatabase.getInstance().getReference().child("users").child(cuurentUser.getUid());

        mdatabase.child(food_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 food_name = (String) dataSnapshot.child("name").getValue();
                 food_desc = (String) dataSnapshot.child("desc").getValue();
                 food_price = (String) dataSnapshot.child("price").getValue();
                 food_image = (String) dataSnapshot.child("image").getValue();

                mTitle.setText(food_name);
                mDesc.setText(food_desc);
                mPrice.setText(food_price);

                Picasso.with(SingleFoodActivity.this).load(food_image).into(mFoodImage);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void orderItemClicked(View view) {
        final DatabaseReference newOrder = mRef.push();
        mUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newOrder.child("itemName").setValue(food_name);
                newOrder.child("userName").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()){
                            AlertDialog.Builder alert = new AlertDialog.Builder(SingleFoodActivity.this);

                            alert.setMessage("Are you sure ?! you want this item ...");
                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(SingleFoodActivity.this, "Your order has already submitted,Thank you ! ", Toast.LENGTH_SHORT).show();
                                    startActivity( new Intent(SingleFoodActivity.this , MenuActivity.class));

                                }
                            });
                            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    Toast.makeText(SingleFoodActivity.this, "Please Choose another ", Toast.LENGTH_SHORT).show();
                                    startActivity( new Intent(SingleFoodActivity.this , MenuActivity.class));
                                }
                            });
                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();

                        }

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
