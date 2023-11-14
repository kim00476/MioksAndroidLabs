package algonquin.cst2335.kim00476;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.kim00476.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {
    //inflate an XML layout for this Fragment
//    @Nullable
    ChatMessage selected;
    public MessageDetailsFragment(ChatMessage m){
        selected = m;

    }
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.messageId.setText(selected.message);
        binding.timeId.setText(selected.timeSent);
//        binding.sendId.setText(selected.m ?"selected selected.sentOrReceive?"True":"False");
        binding.databaseId.setText(Long.toString(selected.id));
//        binding.databaseId.setText("Id is " + selected.id);
        //set textViews
        return binding.getRoot();
    }
}
