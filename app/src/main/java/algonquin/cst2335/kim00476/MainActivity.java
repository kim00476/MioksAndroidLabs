package algonquin.cst2335.kim00476;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * This class represents the password checking app.
 * @author Miok Kim
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /** This holds the text at the centre of the screen */
    TextView tv = null;
    /** This holds the text at the middle of the screen */
    EditText et = null;
    /** This holds the text at the bottom of the screen */
    Button btn = null;
    protected String cityName;

    protected RequestQueue queue = null;

//    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = ActivityMainBinding.inflate( getLayoutInflater() );

        binding.getForecast.setOnClickListener(click -> {
                    cityName = binding.editText.getText().toString();
                    String stringURL ="";

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                            (response -> { },
                            (error -> {  });
                    queue.add(request);


//        tv = findViewById(R.id.textView);
//        et = findViewById(R.id.passwordText);
//        btn = findViewById(R.id.loginBtn);
//
//        btn.setOnClickListener(click -> {
//            String userInput = et.getText().toString();
//            char[] passwordChars = userInput.toCharArray();
//
//            if (checkPasswordComplexity(userInput) && containsSpecialCharacter(passwordChars)) {
//                tv.setText("Your password meets the requirements.");
//            } else {
//                tv.setText("You shall not pass!");
//            }

        });
    }

    /**
     * @param pw The String object that we are checking
     * @return Returns true if the password meets the function.
     */
    boolean checkPasswordComplexity(String pw) {

        boolean foundUpperCase = false;
        boolean foundLowerCase = false;
        boolean foundNumber = false;

        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            if (Character.isLowerCase(c)) {
                Toast.makeText(this, "Your password are only Lower Case.", Toast.LENGTH_SHORT).show();
                foundLowerCase = true;
            } else if (Character.isUpperCase(c)) {
                Toast.makeText(this, "Your password are only Upper Case.", Toast.LENGTH_SHORT).show();
                foundUpperCase = true;
            } else if (Character.isDigit(c)) {
                Toast.makeText(this, "Your password are only Number Case.", Toast.LENGTH_SHORT).show();
                foundNumber = true;

            }
        }
        return foundLowerCase && foundUpperCase && foundNumber;
    }

    /**
     * Check if the password contains special characters.
     * @param pwChars The character array of the password.
     * @return Returns true if the password contains at least one special character.
     */
    boolean containsSpecialCharacter(char[] pwChars) {
        for (char c : pwChars) {
            if (isSpecialCharacter(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * check characters.
     * @param c The character to be checked.
     * @return Returns true if the character is a special character.
     */
    boolean isSpecialCharacter(char c) {
        switch (c){
            case '!':
            case '@':
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '+':
            case '-':
            case '=':
            case '(':
            case ')':
            case '*':
            case '_':
                Toast.makeText(this, "Your password are only Special Characters.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}
