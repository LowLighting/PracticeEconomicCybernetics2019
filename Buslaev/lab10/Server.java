package pack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

public class Server implements Serializable, ServerFunctions
{
    TreeMap<String, ServerClient> clients;
    @Override
    public String[] getCurrentClients() throws RemoteException
    {
        Object [] mas=clients.keySet().toArray();
        String[] rez = new String[mas.length];
        for (int i=0; i<rez.length; i++)
            rez[i]=(String)mas[i];
        return rez;
    }

    @Override
    public Message RecieveMessage(String client,int id) throws RemoteException
    {
        return  clients.get(client).getMessage(id);
    }

    @Override
    public void RemoveMessage(String client,int id) throws RemoteException
    {
        clients.get(client).deleteMessage(id);
    }

    @Override
    public String CheckClient(String Name, String Password)
    {
        if (clients.containsKey(Name))
        {
            if (clients.get(Name).getPassword().compareTo(Password)==0)
                return "";
            else
                return "Пароль введён неверно";
        }
        else
            return  "Пользователя с таким именем ящика нет";
    }

    @Override
    public boolean AddClient(String client, String password) throws RemoteException
    {
        if (clients.containsKey(client))
            return false;
        clients.put(client,new ServerClient(password));
        return true;
    }

    @Override
    public boolean ChangePassword(String client, String old_password, String new_password)
    {
        if (clients.get(client).getPassword().compareTo(old_password)!=0)
            return false;
        clients.get(client).setPassword(new_password);
        return true;
    }

    public Server()
    {
        File datafile=new File("clients.data");
        if (datafile.exists())
        {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(datafile));
                clients=(TreeMap<String, ServerClient>)ois.readObject();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
            clients = new TreeMap<>();
    }

    public static void main(String[] args)
    {
        Server server = new Server();
        try
        {
            ServerFunctions stub = (ServerFunctions) UnicastRemoteObject.exportObject(server,2222);
            Registry reg = LocateRegistry.createRegistry(2222);
            reg.bind("Server", stub);
            while (true)
            {
                Scanner end = new Scanner(System.in);
                if (end.nextLine().compareTo("q")==0)
                {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("clients.data")));
                    oos.writeObject(server.clients);
                    oos.close();
                    System.exit(0);
                }
            }
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
        catch (AlreadyBoundException e)
        {
            e.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void RemoveSendedMessage(String client, int id) throws RemoteException 
    {
         clients.get(client).deleteSendedMessage(id);
    }

    @Override
    public boolean ExistClient(String client) throws RemoteException {
      return clients.containsKey(client);
    }

    @Override
    public boolean SendMessage(File f, String text, String getter, String sender, Date date, byte [] mas) throws RemoteException {
       if (!clients.containsKey(getter))
            return false;
        int id=clients.get(getter).addMessage(new Message(f,text,getter,sender,date,mas));
        int id2=clients.get(sender).addSendMessage(new Message(f,text,getter,sender,date,mas));
        try
        {
            Registry reg = LocateRegistry.getRegistry(2222);
            ClientFunctions stub1= (ClientFunctions) reg.lookup(getter);
            stub1.AddMessage1(sender,date,id);
            stub1.AddSendMessage1(getter, date, id);
                                return true;
        }
        catch (NotBoundException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Message RecieveSendMessage(String client, int id) throws RemoteException {
        return clients.get(client).getSended_messages().get(id);
    }
}
