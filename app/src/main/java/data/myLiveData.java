package data;

import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class myLiveData<T> extends MutableLiveData<T> {

    public void setTheData(T value){
        setValue(value);
    }
}
