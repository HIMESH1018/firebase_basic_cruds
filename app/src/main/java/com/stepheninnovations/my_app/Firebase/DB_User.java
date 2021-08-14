package com.stepheninnovations.my_app.Firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.stepheninnovations.my_app.User;

public class DB_User {

    private DatabaseReference databaseReference;
    public DB_User()
    {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());

    }

    public Task<Void> add(User user){


        return databaseReference.push().setValue(user);
    }

    public Query get(){

        return databaseReference.orderByKey();
    }
}
