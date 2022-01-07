package data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;

import java.util.List;

// A basic API to access the data (can be replaced if needed by another implementation)
public class BasicRepository  implements IRepository {

    private ClientList list;

    public BasicRepository(ClientList l) {
        list = l;
    }

    @Override
    public Task<Void> insertClient(Client client) {
        list.add(client);
        return null;
    }


    @Override
    public void deleteClient(Client client) {
        list.remove(client);
    }


    @Override
    public void deleteAllClients() {
        list.removeAll(list);
    }

    // shows how to transform a regular list to a LiveData list

    @Override
    public LiveData<List<Client>> getAllClients() {
        return new MutableLiveData<>(list);
    }

}