package DBClass;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBPet {
    private DatabaseReference dbRef;

    public DBPet(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbRef=db.getReference(Pet.class.getSimpleName());
    }

    public Task<Void> add(Pet p){
        return dbRef.push().setValue(p);
    }
}
