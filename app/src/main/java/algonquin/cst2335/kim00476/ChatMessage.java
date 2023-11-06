package algonquin.cst2335.kim00476;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity //mapping variables to columns
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)//the database will increment them for us
    @ColumnInfo(name="id")
    long id;

    @ColumnInfo(name="messageColumn")
    public String message;

    @ColumnInfo(name="timeSent")
    public String timeSent ;

    @ColumnInfo(name="isSentButton")
    public boolean isSentButton;

    public ChatMessage(){ }
    public ChatMessage(String m, String t, boolean sent)
    {
        this.message = m;
        this.timeSent = t;
        this.isSentButton = sent;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }
}
