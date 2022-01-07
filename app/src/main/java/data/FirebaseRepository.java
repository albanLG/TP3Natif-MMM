package data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.tp2_mmm.SecondFragment;
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
    private LiveData<List<Client>> allClients=new MutableLiveData<>(Arrays.asList(
            new Client("hello","pute","salope","gpag","pgiqjg")
    ));
    //private LiveData<List<Client>> allClients;

    public FirebaseRepository(Application application){
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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
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
        //allClients=new MutableLiveData<>(clients);

        allClients=new MutableLiveData<>(Arrays.asList(
                new Client("hello","pute","salope","gpag","pgiqjg"),
                new Client("sheje","pute","salope","gpag","pgiqjg")
        ));

        /*List<Client> data=allClients.getValue();
        data.removeAll(data);
        data.addAll(clients);*/

        synchronized(allClients) {
            allClients.notify();
            System.out.println(allClients.getValue().size());
        }

    }
}
