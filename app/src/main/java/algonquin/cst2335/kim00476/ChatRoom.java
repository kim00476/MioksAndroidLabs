package algonquin.cst2335.kim00476;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.kim00476.data.ChatMessage;
import algonquin.cst2335.kim00476.data.ChatRoomViewModel;
import algonquin.cst2335.kim00476.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.kim00476.databinding.ReceiveRowLayoutBinding;
import algonquin.cst2335.kim00476.databinding.SentRowLayoutBinding;

public class ChatRoom extends AppCompatActivity {

    ArrayList<ChatMessage> messages; // in the beginning, there are no messages
    ActivityChatRoomBinding binding;

    ChatRoomViewModel chatModel ;
    RecyclerView.Adapter<MyRowHolder> myAdapter; //to hold the object below
//    RecyclerView.Adapter<MyRowHolder> myAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        //get the data from the ViewModel

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
//        messages = chatModel.messages.getValue();

        // might be null
        if(messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }

        setContentView(binding.getRoot());
        binding.addButton.setOnClickListener(cli -> {
            String  userMessage = binding.userMessages.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(userMessage, currentDateAndTime, true);
            messages.add(chatMessage);

//            myAdapter.notifyItemInserted(messages.size()-1 );  //redraw the screen
            binding.userMessages.setText(""); // remove the text in EditText
            // tell the recycle view that there is new data
            myAdapter.notifyDataSetChanged();  //redraw the screen
        });

        binding.receiveButton.setOnClickListener(cl ->{
            String  userMessage = binding.userMessages.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage chatMessage = new ChatMessage(userMessage, currentDateAndTime, false);
            messages.add(chatMessage);

//            myAdapter.notifyItemInserted(messages.size()-1 );  //redraw the screen
            binding.userMessages.setText(""); // remove the text in EditText
            // tell the recycle view that there is new data
            myAdapter.notifyDataSetChanged();  //redraw the screen
        });

        //will draw the recycle view
        binding.recycleView.setAdapter(
                myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
                    @Override
                    public int getItemViewType(int position){
                        //determine which layout to load at row position

                        ChatMessage message = messages.get(position);
                    return message.isSentButton() ? 0 :1;

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
                if(viewType == 0) {
                    //1)load a XML layout
                    SentRowLayoutBinding binding =                          //parent is incase matchparent
                            SentRowLayoutBinding.inflate(getLayoutInflater(), parent, false);

                    //2) call our Constructor below
                    return new MyRowHolder(binding.getRoot());//getRoot returns a ConstrainLayout with TextViews inside
                }
                else { //viewType == 1
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
            //all you do here is theRootConstraintLayout.findViewById
            messageText = itemView.findViewById(R.id.textView2);
            timeText = itemView.findViewById(R.id.textView3);
        }
    }
}