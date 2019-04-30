package pack;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface ServerFunctions extends Remote
{
    String[] getCurrentClients() throws RemoteException;
    Message RecieveMessage(String client,int id) throws  RemoteException;
    Message RecieveSendMessage(String client,int id) throws  RemoteException;
    void RemoveMessage(String client, int id) throws RemoteException;
    void RemoveSendedMessage(String client, int id) throws RemoteException;
    String CheckClient(String Name, String Password) throws RemoteException;
    boolean ExistClient(String client) throws RemoteException;
    boolean SendMessage(File f, String text, String getter, String sender, Date date, byte[] mas) throws RemoteException;
    boolean AddClient(String client, String password) throws RemoteException;
    boolean ChangePassword(String client, String old_password, String new_password) throws RemoteException;
}
