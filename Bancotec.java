import Accounts;
import java.util.Scanner;
class Bancotec{
    static private Scanner input = new Scanner(System.in).useDelimiter("\n");
    public static void main(String[] args){
        
        char option;
        String name = "";
        int age;
        String userInitials;
        String NIP;
        Accounts person;
        do{
            System.out.println("Sistema de cajero BANTEC");
            option = input.next().charAt(0);
            if(option == 'n'){
                System.out.println("Ingresa el nombre del cuentahabiente");
                name = input.next();
                System.out.println("Ingresa la edad del cuentahabiente");
                age = input.nextInt();
                System.out.println("Ingresa un NIP nuevo");
                NIP = input.next();

                person = new Accounts(name, age, NIP);
            }
        }while(option != 'e');
    }
}