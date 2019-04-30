package pack;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeMap;

public class ServerClient implements Serializable
{
    String password;
    TreeMap<Integer, Message> messages = new TreeMap<Integer, Message>();
    TreeMap<Integer, Message> sended_messages = new TreeMap<Integer, Message>();
 ArrayList<Integer> send_deleted_id = new ArrayList<Integer>();
    public TreeMap<Integer, Message> getSended_messages() {
        return sended_messages;
    }
    ArrayList<Integer> deleted_id = new ArrayList<Integer>();
    int id=0, send_id=0;
    public TreeMap<Integer, Message> getMessageMap()
    {
        return  messages;
    }
    public Message[] getMessages()
    {
        return (Message[]) messages.values().toArray();
    }
    public int addMessage(Message msg)
    {
        if (deleted_id.isEmpty())
        {
            messages.put(id++, msg);
            return  id-1;
        }
        else
        {
            int index=deleted_id.get(0);
            deleted_id.remove(0);
            messages.put(index, msg);
            return index;
        }
    }
    public int addSendMessage(Message msg)
    {
         if (send_deleted_id.isEmpty())
        {
            sended_messages.put(send_id++, msg);
            return  send_id-1;
        }
        else
        {
            int index=send_deleted_id.get(0);
            send_deleted_id.remove(0);
            sended_messages.put(index, msg);
            return index;
        }
    }
    public Message getMessage(int index)
    {
        return messages.get(index);
    }
    public void deleteMessage(int index)
    {
        messages.remove(index);
        deleted_id.add(index);
    }
      public void deleteSendedMessage(int index)
    {
        sended_messages.remove(index);
        send_deleted_id.add(index);
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public ServerClient(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }
}
