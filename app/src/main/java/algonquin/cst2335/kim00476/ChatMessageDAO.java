package algonquin.cst2335.kim00476;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
@Dao
public interface ChatMessageDAO {

    @Insert //id                        //@Entity
    public long insertMessage(ChatMessage m); //for inserting, long is the new id

    @Query("Select * from ChatMessage")//the SQL search
    public List<ChatMessage> getAllMessages(); //for query

    @Delete //number of rows deleted
    void deleteMessage(ChatMessage m); //delete this message by id

}
