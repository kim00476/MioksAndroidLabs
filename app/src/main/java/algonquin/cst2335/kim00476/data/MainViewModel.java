package algonquin.cst2335.kim00476.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    //list data to save off-screen
    public MutableLiveData<String> editString = new MutableLiveData<>();

    public MutableLiveData<Boolean> isSelected = new MutableLiveData<>(false);

}
