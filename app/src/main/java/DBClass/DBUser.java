/*package DBClass;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBUser {
    private DatabaseReference dbRef;

    public DBUser(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbRef=db.getReference(User.class.getSimpleName());
    }

    public Task<Void> add(User p){
        return dbRef.push().setValue(p);
    }
}*/
