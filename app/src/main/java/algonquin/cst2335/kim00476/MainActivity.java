package algonquin.cst2335.kim00476;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        et = findViewById(R.id.passwordText);
        btn = findViewById(R.id.loginBtn);

        btn.setOnClickListener(click -> {
            String userInput = et.getText().toString();
            char[] passwordChars = userInput.toCharArray();

            if (checkPasswordComplexity(userInput)) {
                tv.setText("You shall not pass!");
            } else if (containsSpecialCharacter(passwordChars)){
                tv.setText("You shall not pass!");
            } else {
                tv.setText("Your password meets the requirements.");

//                if (!checkPasswordComplexity(password)) {
//                    Toast.makeText(this, "Password must have at least one upper case letter, one lower case letter, and one digit.", Toast.LENGTH_SHORT).show();
//                }
//
//                if (!containsSpecialCharacter(passwordChars)) {
//                    Toast.makeText(this, "Password must contain at least one special character.", Toast.LENGTH_SHORT).show();
//                }
            }
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
                foundLowerCase = true;
            } else if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else {
                return false;
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
                return true;
            default:
                return false;
        }
    }
}
