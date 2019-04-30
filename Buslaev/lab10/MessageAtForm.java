package pack;

import java.io.Serializable;
import java.util.Date;

public class MessageAtForm implements Serializable
{
    String sender;
    Date date;
    int id;
    boolean read=true;
    public int getId()
    {
        return id;
    }

    public MessageAtForm(String sender, Date date, int id)
    {
        this.sender = sender;
        this.date = date;
        this.id = id;
    }

    public String getSender()
    {
        return sender;
    }

    public Date getDate()
    {
        return date;
    }
}
