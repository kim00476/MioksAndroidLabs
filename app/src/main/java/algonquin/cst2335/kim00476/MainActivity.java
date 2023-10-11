package algonquin.cst2335.kim00476;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import algonquin.cst2335.kim00476.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d( TAG, "Message");
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        binding.emailField.setText(prefs.getString("LoginEmail", ""));

        binding.loginButton.setOnClickListener(click -> {
            // do this when clicked
            SharedPreferences.Editor editor = prefs.edit();
            Intent newPage = new Intent(MainActivity.this, SecondActivity.class);

            String userInput = binding.emailField.getText().toString();
            newPage.putExtra("LoginEmail", userInput);
            startActivity(newPage); // go to new page

            editor.putString("LoginEmail", userInput); //go to disk
            editor.apply();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w( "MainActivity", "In onStart()-The application is now visible on screen." );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( "MainActivity", "In onResume()-The application is now responding to user input." );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "In onPause()-The application no longer responds to user input." );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w("MainActivity", "In onStop()-The application is no longer visible.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("MainActivity", "In onDestroy()-Any memory used by the application is freed.");
    }
}