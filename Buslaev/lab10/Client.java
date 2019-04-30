package pack;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements ClientFunctions, Serializable
{
    String Name;
    ServerFunctions stub;
    public String getName() {
        return Name;
    }
   MainForm main_form=null;
    public void setName(String Name) {
        this.Name = Name;
    }
    ArrayList<MessageAtForm> messages = new ArrayList<MessageAtForm>();
    ArrayList<MessageAtForm> send_messages = new ArrayList<MessageAtForm>();
    @Override
    public void AddMessage(MessageAtForm message) throws RemoteException
    {
        messages.add(message);
        if (main_form!=null)
            main_form.addAnnounce();
    }
    void MainFunction()
    {
          Registry reg; 
        try {
            reg = LocateRegistry.getRegistry(2222);
            stub = (ServerFunctions) reg.lookup("Server");
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        new AccessDialog(null,true,this).setVisible(true);
        main_form=new MainForm(this);
        main_form.setVisible(true);
        while (true) {}
    }
    public static void main(String[] args) {
        Client c = new Client();
        c.MainFunction();
    }
    @Override
    public void AddSendMessage(MessageAtForm message) throws RemoteException {
         send_messages.add(message);
    }

    @Override
    public void AddSendMessage1(String sender, Date date, int id) throws RemoteException {
         send_messages.add(new MessageAtForm(sender, date, id));
    }

    @Override
    public void AddMessage1(String sender, Date date, int id) throws RemoteException {
          messages.add(new MessageAtForm(sender, date, id));
        if (main_form!=null)
            main_form.addAnnounce();
    }
}
