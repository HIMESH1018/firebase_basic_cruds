package com.stepheninnovations.my_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stepheninnovations.my_app.Adapters.ViewAdapter;
import com.stepheninnovations.my_app.Firebase.DB_User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataView extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView view_recycler;
    ViewAdapter adapter;
    DatabaseReference databaseReference;
    ArrayList<User> users;
    ArrayList<String> keylist;
    String key;
    EditText searchview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);

        view_recycler = findViewById(R.id.view_recycler);

        users = new ArrayList<>();
        keylist = new ArrayList<>();
        searchview = findViewById(R.id.searchview);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        loadData();

//        searchview.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(editable.toString() != null){
//
//                    loadData(editable.toString());
//                }
//                else{
//                    loadData("");
//                }
//            }
//        });


    }

    private void loadData(){//String data) {
//        Query query = databaseReference.orderByChild("username").startAt(data).endAt(data+"\uf8ff");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {

                    User user = data.getValue(User.class);
                    users.add(user); // user details added to user list
                    Log.e("usernamecheck", "" + user.getUsername());
                    Log.e("checkkey", "" + data.getKey());
                    Log.e("checkkey", "" + data.getRef());
                    keylist.add(data.getKey()); //adding keys to different list - path key
                }

                putDataIntoBottomRecyclerView(DataView.this, users);
                Log.e("listcheck", "" + users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void putDataIntoBottomRecyclerView(Context context, ArrayList<User> itemsList) {

        Log.e("Listsize_check", "" + itemsList.size());

        if (itemsList.size() == 0) {
            Toast.makeText(context, "No Data to Display", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Loading..", Toast.LENGTH_SHORT).show();
            adapter = new ViewAdapter(this, itemsList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            view_recycler.setLayoutManager(layoutManager);

            view_recycler.setAdapter(adapter);

            adapter.setOnItemClickListner(new ViewAdapter.onItemClickListner() {
                @Override
                public void onCardClick(int position) {
                    User dta = itemsList.get(position);
                    String mkey = keylist.get(position); // get the key of position
                    Log.e("username ", "" + dta.getUsername());
                    Log.e("email ", "" + dta.getEmail());
                    Log.e("pathkey  ", "" +mkey);

                    Intent intent = new Intent(DataView.this, EditView.class);
                    intent.putExtra("username",dta.getUsername());
                    intent.putExtra("email",dta.getEmail());
                    intent.putExtra("key",mkey);
                    startActivity(intent);
                }
            });


        }
    }

    public void showPopup(View view){

        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.edit:
                Toast.makeText(this, "Edit clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.delete:
                Toast.makeText(this, "delete clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}