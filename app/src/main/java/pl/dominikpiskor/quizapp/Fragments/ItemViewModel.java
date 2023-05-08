package pl.dominikpiskor.quizapp.Fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The class responsible for transferring data between fragments and activities
 */
public class ItemViewModel extends ViewModel {

    private final MutableLiveData<String> selectedItem = new MutableLiveData<String>();

    //==============================================================================================

    public void setData(String item) {
        selectedItem.setValue(item);
    }

    //==============================================================================================

    public LiveData<String> getSelectedItem() {
        return selectedItem;
    }
}
