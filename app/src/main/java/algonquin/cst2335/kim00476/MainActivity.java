package algonquin.cst2335.kim00476;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.kim00476.databinding.ActivityMainBinding;

/**
 * This class represents the password checking app.
 * @author Miok Kim
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    protected RequestQueue queue = null;

    protected String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this part goes at the top of the onCreate function
        queue = Volley.newRequestQueue(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getForecast.setOnClickListener(click -> {

            //get the forecast          replace " " with +
//            String cityName = null;
            try {

               cityName = binding.editText.getText().toString();

                String stringURL ="https://api.openweathermap.org/data/2.5/weather?q= "
                        + URLEncoder.encode(cityName, "UTF-8")
                        +"&appid=7e943c97096a9784391a981c4d878b22&units=metric";

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                        (response) -> { /*this gets called if the server responded*/
                            try {
                                JSONObject coord = response.getJSONObject("coord");
                                JSONArray weatherArray= response.getJSONArray("weather");
                                int vis = response.getInt("visibility");
                                String name = response.getString( "name" );
                                JSONObject position0 = weatherArray.getJSONObject(0);

                                String description = position0.getString("description");

                                JSONObject mainObject = response.getJSONObject("main");
                                double current = mainObject.getDouble("temp");
                                double min = mainObject.getDouble("temp_min");
                                double max = mainObject.getDouble("temp_max");
                                int humidity = mainObject.getInt("humidity");

                                runOnUiThread( (  )  -> {
                                            binding.temp.setText("The temperature is:" + current);
                                            binding.temp.setVisibility(View.VISIBLE);

                                            binding.minTemp.setText("The minimum temperature is:" + min);
                                            binding.minTemp.setVisibility(View.VISIBLE);

                                            binding.maxTemp.setText("The maximum temperature is:" + max);
                                            binding.maxTemp.setVisibility(View.VISIBLE);

                                            binding.humitidy.setText("The humidity is:" + humidity);
                                            binding.humitidy.setVisibility(View.VISIBLE);

                                            binding.description.setText("The dexcription is:" + description);
                                            binding.description.setVisibility(View.VISIBLE);

                                        });

//                                String iconName = weather.getJSONObject(0).getString("icon");
//                                JSONArray weatherArray =  response.getJSONArray("weather");
//                                JSONObject position0 = weatherArray.getJSONObject(0);

//                               for(int i = 0; i < weatherArray.length(); i++)
//                                {
//                                    JSONObject thisObj = weatherArray.getJSONObject(i);
//                                    iconName = thisObj.getString("icon");
//                                }
                                // now that we have the iconName
                                // 2nd query for the image



                                String iconName = weatherArray.getJSONObject(0).getString("icon");

                                String imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";
                                String pathname = getFilesDir() + "/" + iconName + ".png";
                                File file = new File(pathname);
                                if (file.exists()) {
                                    binding.icon.setImageBitmap(BitmapFactory.decodeFile(pathname));
                                } else {
                                    ImageRequest imgReq = new ImageRequest(imageUrl,
                                            (Response.Listener<Bitmap>) bitmap -> {
                                                FileOutputStream fOut = null;
                                                try {
                                                    fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);

                                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                                    fOut.flush();
                                                    fOut.close();

                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                } catch (IOException e) {
                                                    throw new RuntimeException(e);
                                                }
                                                binding.icon.setImageBitmap(bitmap);
                                                binding.icon.setVisibility(View.VISIBLE); //visible for image

                                            }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                            (error) -> {
                                                int i = 0;
                                            });
                                    queue.add(imgReq); //fetches from the server

                                }
                            } catch (JSONException e){
                                throw new RuntimeException(e);
                            }
                        },
                        (error) -> {  /*this gets called if there was an error or no response*/
                        int i = 0;
                        });
                        queue.add(request); //fetches from the server
                    } catch (UnsupportedEncodingException e) {
                     throw new RuntimeException(e);
                }
                });


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
            }


//    /**
//     * @param pw The String object that we are checking
//     * @return Returns true if the password meets the function.
//     */
//    boolean checkPasswordComplexity(String pw) {
//
//        boolean foundUpperCase = false;
//        boolean foundLowerCase = false;
//        boolean foundNumber = false;
//
//        for (int i = 0; i < pw.length(); i++) {
//            char c = pw.charAt(i);
//            if (Character.isLowerCase(c)) {
//                Toast.makeText(this, "Your password are only Lower Case.", Toast.LENGTH_SHORT).show();
//                foundLowerCase = true;
//            } else if (Character.isUpperCase(c)) {
//                Toast.makeText(this, "Your password are only Upper Case.", Toast.LENGTH_SHORT).show();
//                foundUpperCase = true;
//            } else if (Character.isDigit(c)) {
//                Toast.makeText(this, "Your password are only Number Case.", Toast.LENGTH_SHORT).show();
//                foundNumber = true;
//
//            }
//        }
//        return foundLowerCase && foundUpperCase && foundNumber;
//    }
//
//    /**
//     * Check if the password contains special characters.
//     * @param pwChars The character array of the password.
//     * @return Returns true if the password contains at least one special character.
//     */
//    boolean containsSpecialCharacter(char[] pwChars) {
//        for (char c : pwChars) {
//            if (isSpecialCharacter(c)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * check characters.
//     * @param c The character to be checked.
//     * @return Returns true if the character is a special character.
//     */
//    boolean isSpecialCharacter(char c) {
//        switch (c){
//            case '!':
//            case '@':
//            case '#':
//            case '$':
//            case '%':
//            case '^':
//            case '&':
//            case '+':
//            case '-':
//            case '=':
//            case '(':
//            case ')':
//            case '*':
//            case '_':
//                Toast.makeText(this, "Your password are only Special Characters.", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return false;
//        }
    }

