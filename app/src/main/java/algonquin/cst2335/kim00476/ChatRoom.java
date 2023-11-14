package algonquin.cst2335.kim00476;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.kim00476.data.ChatRoomViewModel;
import algonquin.cst2335.kim00476.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.kim00476.databinding.ReceiveRowLayoutBinding;
import algonquin.cst2335.kim00476.databinding.SentRowLayoutBinding;

public class ChatRoom extends AppCompatActivity {
    ArrayList<ChatMessage> messages; // in the beginning, there are no messages
    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;
    ChatMessageDAO mDao; //Declare the dao here
    RecyclerView.Adapter<MyRowHolder> myAdapter; //to hold the object below


    //    RecyclerView.Adapter<MyRowHolder> myAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        //get the data from the ViewModel

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        chatModel.selectedMessage.observe(this, (newMessageValue ) ->{

//            String message = newMessageValue.message;
//            String time = newMessageValue.timeSent;

            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx= fMgr.beginTransaction();
            tx.addToBackStack(" ");
            tx.add(R.id.fragmentLocation , chatFragment); // first is the FrameLayout id
            tx.commit();

        });
        //load messages from the database                                                           //can be any name
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();

        //intialize the variable
        mDao = db.cmDAO(); //get a DAO object to interact with the database
        // if ChatMessageDAO mDao = db.cmDAO(); local variable and garbage collector, delete

        // load all messages from database //background thread
//        Executor thread = Executors.newSingleThreadExecutor();
//        thread.execute(() -> {
//
//            List<ChatMessage> fromDatabase = mDao.getAllMessages(); //return a List
//            messages.addAll(fromDatabase); // this adds all messages from the database
//
//        });

        //end of loading from database

        // might be null
        if (messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<>());

            Executor thread2 = Executors.newSingleThreadExecutor();
            thread2.execute(() ->
            {
                messages.addAll(mDao.getAllMessages()); //Once you get the data from database

                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter)); //You can then load the RecyclerView
            });
        }

        setContentView(binding.getRoot());
        binding.addButton.setOnClickListener(cli -> {
            String userMessage = binding.userMessages.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(userMessage, currentDateAndTime, true);
            messages.add(chatMessage);
//            myAdapter.notifyItemInserted(messages.size()-1 );  //redraw the screen
            binding.userMessages.setText(""); // remove the text in EditText
            // tell the recycle view that there is new data
            myAdapter.notifyDataSetChanged();  //redraw the screen

            // add to database on another thread
            Executor thread1 = Executors.newSingleThreadExecutor();
            // this is on a background thread
            thread1.execute(() -> {
                chatMessage.id = mDao.insertMessage(chatMessage); /*this runs in another thread*/
                Log.d("TAG", "The id created is :" + chatMessage.id);
            }); //the body of run()

            ; //get the ID from the database
        });


        binding.receiveButton.setOnClickListener(cl -> {
            String userMessage = binding.userMessages.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(userMessage, currentDateAndTime, false);
            messages.add(chatMessage);

//            myAdapter.notifyItemInserted(messages.size()-1 );  //redraw the screen
            binding.userMessages.setText(""); // remove the text in EditText
            // tell the recycle view that there is new data
            myAdapter.notifyDataSetChanged();  //redraw the screen

            // add to database on another thread
            Executor thread1 = Executors.newSingleThreadExecutor();
            // this is on a background thread
            thread1.execute(() -> {
                chatMessage.id = mDao.insertMessage(chatMessage); /*this runs in another thread*/
                Log.d("TAG", "The id created is :" + chatMessage.id);
            }); //the body of run()
        });

        //will draw the recycle view
        binding.recycleView.setAdapter(
                myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
                    @Override
                    public int getItemViewType(int position) {
                        //determine which layout to load at row position

                        ChatMessage message = messages.get(position);
                        return message.isSentButton() ? 0 : 1;

//                        if(messages.charAt(0) == '1') // first letter
//                            return 0;
//                            //charAt(0) == '2'
//                        else
//                            return 1;

//                if(position <5) // for the first 5 rows
//                if(position % 2 == 1) // for the odd rows
//                {
//                    return 0;
//                }
//                else
//                      return 1; // for even rows
                    }

                    @NonNull
                    @Override                                               //which layout to load
                    public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        //viewType will either be 0 or 1
                        if (viewType == 0) {
                            //1)load a XML layout
                            SentRowLayoutBinding binding =                          //parent is incase matchparent
                                    SentRowLayoutBinding.inflate(getLayoutInflater(), parent, false);

                            //2) call our Constructor below
                            return new MyRowHolder(binding.getRoot());//getRoot returns a ConstrainLayout with TextViews inside
                        } else { //viewType == 1
                            ReceiveRowLayoutBinding binding =                          //parent is incase matchparent
                                    ReceiveRowLayoutBinding.inflate(getLayoutInflater(), parent, false);

                            //2) call our Constructor below
                            return new MyRowHolder(binding.getRoot());//getRoot returns a ConstrainLayout with TextViews inside

                        }
                    }


                    @Override                                                   //row number
                    public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                        ChatMessage chatMessage = messages.get(position);

                        //where you overwrite the default text
//                holder.messageView.setText(messages.get(position));
                        holder.messageText.setText(chatMessage.getMessage());
                        holder.timeText.setText(chatMessage.getTimeSent()); //???
                    }

                    // returns the number of rows to draw
                    @Override
                    public int getItemCount() {
                        return messages.size(); //show as many rows as items in the array
                    }
                });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        //where you overwrite the default text
        public TextView messageText;
        public TextView timeText;

        // use find view by id
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                ChatMessage m= messages.get(position);

                chatModel.selectedMessage.postValue(m);

                /*no alert dialog now * /
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );

                builder.setMessage("Do you want to delete this message:" + messageText.getText());
                builder.setTitle("Question");
                builder.setNegativeButton("No", (dialog, cl) -> {  });
                builder.setPositiveButton("Yes", (dialog, cl) -> {
                    /*is yes is clicked* /
               Executor thread1 = Executors.newSingleThreadExecutor();
                // this is on a background thread
               thread1.execute(() -> {
                    //delete form database
                    mDao.deleteMessage(m); //which chat message to delete?

                    });
                    messages.remove(position); //remove form the array list row: 0
                    myAdapter.notifyDataSetChanged();

                    //give feedback : anything on screen
                  /* Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", ck -> {

                                Executor thread2 = Executors.newSingleThreadExecutor();
                                // this is on a background thread
                                thread2.execute((  ) -> {

                                    mDao.insertMessage(m);
                                });
                                messages.add(position,m);
                                myAdapter.notifyDataSetChanged();
                            })
                            .show();

                });

                builder.create().show(); // this has to be last */
                });
                //all you do here is theRootConstraintLayout.findViewById
                messageText = itemView.findViewById(R.id.textView2);
                timeText = itemView.findViewById(R.id.textView3);

//            });

        }
    }
}
