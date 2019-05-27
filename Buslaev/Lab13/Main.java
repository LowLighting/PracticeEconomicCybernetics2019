public class Main
{
    public static native String Find();
    static
    {
        System.load("C:\\Users\\scoob\\Desktop\\Лабы по проге\\Учебная практика(2)\\lab13\\DLL\\x64\\Debug\\Dll.dll");
    }

    public static void main(String[] args) {
        System.out.println(Find());
    }
}

