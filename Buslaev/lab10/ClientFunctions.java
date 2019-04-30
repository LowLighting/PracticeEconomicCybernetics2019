package pack;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface ClientFunctions extends Remote
{
    void AddMessage(MessageAtForm message) throws RemoteException;
    void AddMessage1(String sender, Date date, int id) throws RemoteException;
        void AddSendMessage(MessageAtForm message) throws RemoteException;
        void AddSendMessage1(String sender, Date date, int id) throws RemoteException;
}
