package algonquin.cst2335.kim00476.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;

import algonquin.cst2335.kim00476.R;
import algonquin.cst2335.kim00476.data.MainViewModel;
import algonquin.cst2335.kim00476.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainViewModel model;
    private ActivityMainBinding variableBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        TextView myText = variableBinding.textView;
        Button btn = variableBinding.myButton;
        EditText myEdit = variableBinding.myEditText;
        CheckBox cb = variableBinding.myCheckBox;
        Switch sw = variableBinding.mySwitch;
        RadioButton rb = variableBinding.myRadioButton;
        ImageView im = variableBinding.imageTest;
        ImageButton ib = variableBinding.imageBtn;

        btn.setOnClickListener(v -> {
            String editString = myEdit.getText().toString();
            model.editString.postValue(editString);
        });

        variableBinding.myCheckBox.setOnCheckedChangeListener( (btn1, isSelected) -> {
            model.isSelected.postValue(isSelected);
            Toast.makeText(getApplicationContext(), "The value is now: " + isSelected, Toast.LENGTH_SHORT).show();
        } );

        variableBinding.mySwitch.setOnCheckedChangeListener(  ( btn1, isSelected) -> {
            model.isSelected.postValue(isSelected);
            Toast.makeText(getApplicationContext(), "The value is now: " + isSelected, Toast.LENGTH_SHORT).show();
        } );

        variableBinding.myRadioButton.setOnCheckedChangeListener( ( btn1, isSeleted) -> {
            model.isSelected.postValue(isSeleted);
            Toast.makeText(this, "The value is now: " + isSeleted, Toast.LENGTH_SHORT).show();
        } );

        variableBinding.imageTest.setOnClickListener(clk -> {
            Toast.makeText(this, "The width = " + variableBinding.imageTest.getWidth() +" "+ "and height = " +variableBinding.imageTest.getHeight(), Toast.LENGTH_SHORT).show();
        });

        variableBinding.imageBtn.setOnClickListener(clk -> {
            Toast.makeText(this, "The width = " + variableBinding.imageBtn.getWidth() +" "+ "and height = " +variableBinding.imageBtn.getHeight(), Toast.LENGTH_SHORT).show();
        });

        model.isSelected.observe(this, selected -> {
            variableBinding.myCheckBox.setChecked(selected);
            variableBinding.myRadioButton.setChecked(selected);
            variableBinding.mySwitch.setChecked(selected);
        });

        model.editString.observe(this, editString -> {
            myText.setText("Your edit text has: " +  model.editString.getValue());
        });
    }
}