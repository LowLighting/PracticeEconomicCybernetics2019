import java.io.BufferedReader;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class MailPerson extends UnicastRemoteObject implements Mud.MailClientInterface{
    private String name;
    private PrintWriter out;
    private ArrayList<String> letters;
    MailPerson(String name, PrintWriter out, BufferedReader in) throws RemoteException {
        this.name = name;
        this.out = out;
        letters = new ArrayList<>();
    }
    @Override
    public String getName() throws RemoteException {
        return name;
    }
    @Override
    public String showMessage() throws RemoteException{
        StringBuilder res= new StringBuilder((name + "'s messages:\n"));
       for(String i:letters){
           res.append(i).append("\n");
       }
       return res.toString();
    }
    @Override
    public void talk(String text)throws RemoteException {

        out.print(text);
        out.flush();
    }
    @Override
    public void addLetter(String letter) throws RemoteException {
        letters.add(letter);
    }
    @Override
    public String toString() {
        return "{" + name + "}";
    }
}
