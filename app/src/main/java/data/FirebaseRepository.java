package data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseRepository implements IRepository  {

    //plus besoin de DAO etant donne qu on
    //se sert directement de l api de firebase

    private DatabaseReference databaseReference;
    private myLiveData<List<Client>> allClients= new myLiveData<List<Client>>();


    public FirebaseRepository(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Client.class.getSimpleName());

        //on recupere la liste de clients
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Client> clientList=new ArrayList<Client>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Client client = dataSnapshot1.getValue(Client.class);
                    clientList.add(client);
                }

                setClients(clientList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public Task<Void> insertClient(Client client) {
        return databaseReference.push().setValue(client);
    }

    @Override
    public void deleteClient(Client client) {

    }

    @Override
    public void deleteAllClients() {

    }

    @Override
    public LiveData<List<Client>> getAllClients() {
        return allClients;
    }

    public void setClients(List<Client> clients){
        allClients.setTheData(clients);
    }
}
