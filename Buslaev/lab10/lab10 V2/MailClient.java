import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.Naming;

public class MailClient {
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {
        try {
            String hostname = "localhost";
            String username = "qwe";

            Mud.MailServerInterface server = (Mud.MailServerInterface) Naming.lookup("rmi://" + hostname
                    + ":" + MailServer.PORT + "/" + username);
            System.out.println("Вы посоеденены к MailServerInterface!");
            runUser(username, server);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("Usage: java FreeTimeClient <host> <user>");
            System.exit(1);
        }

    }
    private static void runUser(String username, Mud.MailServerInterface server) throws IOException, InterruptedException {

        String currentPersonName = welcome(username, server);
        Mud.MailClientInterface curname=server.getPerson(currentPersonName);
        String cmd;
        System.out.println(help);
        for (; ; ) {

            try {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {
                }

                cmd = getLine(">> ");

                switch (cmd) {
                    case "s":
                        sendMesssage(server, currentPersonName);
                        break;
                    case "sh":
                        showMessage(server,currentPersonName);
                        break;
                    case "u":
                        showClients(server);
                        break;
                    case "help":
                    case "h":
                        System.out.println(help);
                        break;

                    case "quit":
                    case "q":
                        delClient(server,currentPersonName);
                        System.out.println("Пока!");
                        System.out.flush();
                        System.exit(0);

                    default:
                        System.out.println("Неизвестная комманда.  Попробуйте 'Помощь'.\n");

                }

            } catch (Exception e) {
                System.out.println("Синтаксическая илимная ошибка:");
                System.out.println(e);
                System.out.println("Попробуйте 'Помощь'.");
            }
        }

    }
    private static String welcome(String username, Mud.MailServerInterface server) throws IOException, InterruptedException {
        Mud.MailClientInterface currentPerson = null;
        do {
            String cmd = getLine("[" + username + "]:" + "Хотите войти или зарегистрироваться?\n" +
                    "\tr : зарегистрироваться\n" +
                    "\tl : войти\n" +
                    ">> ");

            String name = getLine(">> Введите свой ник: ");

            switch (cmd) {
                case "r": {
                    PrintWriter out = new PrintWriter(System.out);
                    MailPerson current = new MailPerson(name, out, in);

                    if (server.addPerson(current)) {
                        System.out.println(">> Вы успешно зарегестрированы как \"" + name + "\"");
                        currentPerson = server.getPerson(name);
                    } else {
                        System.out.println(">> Ник \"" + name + "\" уже используется.");
                    }
                    break;
                }
                case "l": {
                    currentPerson = server.getPerson(name);
                    if (currentPerson != null) {
                        System.out.println(">> Вы вошли как \"" + currentPerson.getName() + "\".");
                    } else {
                        System.out.println(">> Нет пользователей с таким ником.");
                    }
                    break;
                }
                default: {
                    System.out.println(">> Неправильный ключ + \"" + cmd + "\"");
                    currentPerson = null;
                    break;
                }
            }
        } while (currentPerson == null);
        return currentPerson.getName();
    }

    private static String getLine(String prompt) throws InterruptedException {
        String line = null;
        do {
            try {
                System.out.print(prompt);
                System.out.flush();

                line = in.readLine();
                if (line != null) line = line.trim();
            } catch (Exception ignored) {
            }
        } while ((line == null) || (line.length() == 0));

        return line;
    }
    private  static  void delClient(Mud.MailServerInterface server, String personName)throws IOException, InterruptedException{
        Mud.MailClientInterface person = server.getPerson(personName);
        server.delClient(person);
    }
    private static void showClients(Mud.MailServerInterface server)throws IOException, InterruptedException{
        System.out.println(server.getPersons());
    }
    private static void showMessage(Mud.MailServerInterface server, String personName)throws IOException, InterruptedException{

        String secondPersonName = getLine(">> Чьи сообщения отобразить?\n" +
                ">> ");

        Mud.MailClientInterface person = server.getPerson(personName);
        Mud.MailClientInterface secondPerson = server.getPerson(secondPersonName);

        if (secondPerson != null) {
            System.out.println(secondPerson.showMessage());
        }
        else {
            System.out.println(">> Нет пользователей с таким ником.\n");
        }

        System.out.flush();

    }
    private static void sendMesssage(Mud.MailServerInterface server, String personName) throws IOException, InterruptedException {

        String secondPersonName = getLine(">> Кому хотите написать сообщение?\n" +
                ">> ");

        Mud.MailClientInterface person = server.getPerson(personName);
        Mud.MailClientInterface secondPerson = server.getPerson(secondPersonName);

        if (secondPerson != null) {
            String message=personName+":  "+getLine(">> Введите сообщение\n" + ">> ");
            secondPerson.talk(message);
            secondPerson.addLetter(message);
        }
        else {
            System.out.println(">> Нет пользователей с таким ником.\n");
        }

        System.out.flush();
    }
    private static final String help = "Команды:\n" +
            "s : отправить сообщение\n" +
            "sh: отобразить сообщение\n" +
            "u: отобразить клиентов\n"+
            "help | h: показать это сообщение\n" +
            "q : выйти\n";
}
