package bank;


import java.util.Scanner;

public class SingletonScanner {
    private static Scanner scanner;

    private SingletonScanner() {
    }

    public static Scanner getInstance() {
        if (scanner == null) {
            synchronized (SingletonScanner.class) {
                if (scanner == null) {
                    scanner = new Scanner(System.in);
                }
            }
        }
        return scanner;
    }
}
