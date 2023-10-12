package algonquin.cst2335.kim00476;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import algonquin.cst2335.kim00476.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySecondBinding binding = ActivitySecondBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Intent newPage = getIntent();
        String userInput = newPage.getStringExtra("LoginEmail");
        binding.WelcomeMessage.setText("Welcome back " + userInput);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);  //this is file.

        String phoneFile = prefs.getString("tel:", " ");
        binding.editTextPhone.setText(phoneFile);

//        File mySandbox = getFilesDir();
//        String path = mySandbox.getAbsolutePath();
        File path =  getFilesDir();

        File file = new File( path, "Picture.png");

        if(path.exists())
        {
            Bitmap theImage = BitmapFactory.decodeFile( file.getAbsolutePath());
            binding.imageView.setImageBitmap(theImage);
        }
//        else {
//            binding.imageView.setImageResource(android.R.drawable.ic_menu_camera);
//
//        }

        binding.button.setOnClickListener(click -> {

            String phoneNumber = binding.editTextPhone.getText().toString();

            SharedPreferences.Editor editor = prefs.edit(); //editor for writing

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));

            startActivity(intent);
//            finish();
            editor.putString("tel:", phoneNumber); //this goes disk
            editor.apply();
        });

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            binding.imageView.setImageBitmap(thumbnail);

//                            FileOutputStream fOut = null;

                            try{ FileOutputStream fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                                fOut.flush();
                                fOut.close();

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        binding.button2.setOnClickListener(click ->  {

        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
//            startActivity(cameraIntent);
            cameraResult.launch(cameraIntent);
        }
        else{
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, 0);
            }
        });
    }
}