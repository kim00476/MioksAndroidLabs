package algonquin.cst2335.kim00476;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {

    //only 1 function: return the DAO obj

    public abstract ChatMessageDAO cmDAO(); //name doesn't matter
}
