import Accounts;
import java.util.Scanner;
class Bancotec{
    static private Scanner input = new Scanner(System.in).useDelimiter("\n");
    public static void main(String[] args){
        
        char option;
        int ID;
        String name = "";
        int age;
        String userInitials;
        String NIP;
        Accounts person;
        double amount;
        do{
            menu();
            option = input.next().charAt(0);
            if(option == 'n'){
                System.out.println("Ingresa el nombre del cuentahabiente");
                name = input.next();
                System.out.println("Ingresa la edad del cuentahabiente");
                age = input.nextInt();
                System.out.println("Ingresa un NIP nuevo");
                NIP = input.next();

                person = new Accounts(name, age, NIP);
            }else if(option == 'c'){
                System.out.println("Ingresa tu ID: ");
                ID = input.nextInt();
                System.out.println("Ingresa el nombre del cuentahabiente");
                name = input.next();
                System.out.println("Ingresa tu nip: ");
                NIP = input.next();
                Accounts consult = new Accounts(ID, name, NIP);

                consult.showInformation(NIP);
            }else if(option == 'd'){
                System.out.println("Ingresa tu ID: ");
                ID = input.nextInt();
                System.out.println("Ingresa el nombre del cuentahabiente");
                name = input.next();
                System.out.println("Ingresa tu nip: ");
                NIP = input.next();
                
                System.out.println("Cantidad a depositar: ");
                amount = input.nextDouble();

                Accounts deposit = new Accounts(ID, name, NIP);

                deposit.updatebalance(amount, true, NIP);
            }
        }while(option != 'e');
    }
    static private void menu(){
        System.out.println("Sistema de cajero BANTEC");
        System.out.println("*****************************");
        System.out.println("*****************************");
        System.out.println("Opciones: ");
        System.out.println("n) Nueva cuenta ");
        System.out.println("c) Consultar estado de cuenta ");
        System.out.println("d) Depositar a cuenta ");
        System.out.println("r) Retirar de cuenta ");
        System.out.println("t) E-transfer ");
        System.out.println("e) Salir ");
        System.out.print("> ");
    }
}