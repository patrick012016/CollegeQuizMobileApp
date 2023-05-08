package pl.dominikpiskor.quizapp.Fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModel extends ViewModel {

    private final MutableLiveData<String> selectedItem = new MutableLiveData<String>();

    //==============================================================================================

    /*
     * Klasa odpowiedzialna za przesyłanie danych pomiędzy fragmentami a aktywnościami
     */
    public void setData(String item) {
        selectedItem.setValue(item);
    }

    //==============================================================================================

    public LiveData<String> getSelectedItem() {
        return selectedItem;
    }
}