package pack;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable
{
    File f;

    public File getF() {
        return f;
    }

    public String getText() {
        return text;
    }
    String text;


  
    String getter;
    String sender;
    Date date;
    byte[] mas;

    public byte[] getMas() {
        return mas;
    }

    public Message(File f, String text, String getter, String sender, Date date, byte[] mas) {
        this.f = f;
        this.text = text;
        this.getter = getter;
        this.sender = sender;
        this.date = date;
        this.mas = mas;
    }
    public String getGetter()
    {
        return getter;
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
