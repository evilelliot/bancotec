import java.io.*;
import java.util.Random;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;  
import java.util.Scanner;
class Accounts {
    private int ID;
    private int CLABE;
    private String ownerInitials;
    private String ownerName;
    private int ownerAge;
    private String ownerNIP;
    private double balance;
    // instance of logger
    private Logger log = new Logger();
    // consults only query
    Accounts(int ID, String ownerName, String ownerNIP){
        this.ID = ID;
        this.ownerName = ownerName;
        this.ownerNIP = ownerNIP;
        this.balance = 10.0;
    }
    Accounts(String ownerName, int ownerAge, String ownerNIP){
        // set the owner name
        this.ownerName = ownerName.toLowerCase();
        this.ownerAge = ownerAge;
        // now look for the account exitence
        String f = "./users/" + this.getInitials() + "_"+ ownerAge +".txt";
        String data;
        System.out.println(f);
        File account = new File(f);
        // if the account already exist, pass this procedure
        if(!account.exists()){   
            this.ID = this.getRandomNumber();
            this.CLABE = this.getRandomNumber();
            this.ownerNIP = this.encryptNIP(ownerNIP.toLowerCase());
            this.ownerInitials = this.getInitials();
            
            this.storeAccount(false);

            data = this.ID + "@" + this.ownerInitials;
            log.createEntry("newaccount", data);
        }else{
            System.out.println("La cuenta ya existe.");
        }     
    }
    // security functions
    private String encryptNIP(String nip){
        Encrypt encrypt = new Encrypt();

        return encrypt.encrypt(nip, "sha256");

    }
    private boolean validateNIP(String NIP){
        String fNIP = this.getInformationFromFile(4);
        
        if(NIP.equals(fNIP)){
            return true;
        }else{
            return false;
        }
    }
    // storage functions
    private void storeAccount(boolean rw){
        // storage order
        // ID, owner clabe,  ownerage , ownername, owner encrypted nip, balance
        String filename = "./users/" + this.getInitials() + "_" + this.ID + ".txt";
        try {
            FileWriter storage = new FileWriter(filename, false);
            if(rw == true){
                storage.write(this.ID + "-" + this.CLABE + "-" + this.ownerAge + "-" + this.ownerName + "-" + this.encryptNIP(this.ownerNIP) + "-" + this.balance);    
            }
            storage.write(this.ID + "-" + this.CLABE + "-" + this.ownerAge + "-" + this.ownerName + "-" + this.ownerNIP + "-" + this.balance);
            storage.close();
        } catch (IOException e) {
            System.out.println("Ocurrio un error: leer más.");
            e.printStackTrace();
        }

    }
    // getters
    public String getInitials(){
        String[] exploded = this.ownerName.split(" ");
        String initials = "";
        for(int i = 0; i <= exploded.length - 1; i++){            
            initials = initials +  exploded[i].charAt(0);
        }
        return initials.toLowerCase();
    }
    private int getRandomNumber(){
        Random rand = new Random();
        return 100000 + rand.nextInt(900000);
    }
    private String getInformationFromFile(int index){
        /**
         * Information indexes:
         * 0 = ID
         * 1 = CLABE
         * 2 = owner age
         * 3 = owner name
         * 4 = encrypted nip
         * 5 = balance
         */
        String file = "./users/" + this.getInitials() + "_"+ this.ID  +".txt";
        try{
            File f = new File(file);
            Scanner reader = new Scanner(f);
            reader.useDelimiter("\\Z");
            String information = reader.next();
            String[] inArray = information.split("-");

            if(index > 5){
                //System.out.println("No existe dicho indice de información.");
                return null;
            }else{
                //System.out.println(inArray[index]);
                return inArray[index].toString();
            }
            
        }catch(FileNotFoundException e){
            System.out.print(e);
            return null;
        }   
    }
    public void showInformation(String nip){
    

        String data;
        System.out.println(this.getInformationFromFile(4));
        System.out.println(this.encryptNIP(nip));
        
        nip = this.encryptNIP(nip);
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
        System.out.println(nip.equals(this.getInformationFromFile(4)));
        if(nip.equals(this.getInformationFromFile(4))){
            System.out.println("*****************************");
            System.out.println("*****************************");
            System.out.println("ID: " + this.getInformationFromFile(0));
            System.out.println("Cuentahabiente: " + this.getInformationFromFile(3));
            System.out.println("balance: " + this.getInformationFromFile(5));
            System.out.println("*****************************");
            System.out.println("*****************************");

            data = this.getInformationFromFile(0) + "@" + this.getInitials();
            log.createEntry("consult", data);
        }else{
            System.out.println("Permiso denegado.");
            
            data = "forbidden action for: " + this.ID;
            log.createEntry("consult", data);
        }

    }
    // setters
    public void updatebalance(double cantidad, boolean tipo, String NIP){
        System.out.println(this.getInformationFromFile(4));
        if(this.encryptNIP(NIP).equals(this.getInformationFromFile(4))){
            if(tipo == true){
                String aux = getInformationFromFile(5);
                this.balance = Double.parseDouble(aux) + cantidad;

                //System.out.println(this.balance);
                this.storeAccount(true);
                System.out.println("Se han depositado: " + cantidad +  " a la cuenta " + this.ID);
            }else{
                if(cantidad > this.balance){  
                    System.out.println("No tienes balance suficiente");
                }else{
                    String aux = getInformationFromFile(5);
                    this.balance = Double.parseDouble(aux) - cantidad;

                    //System.out.println(this.balance);
                    this.storeAccount(true);
                    System.out.println("Se han retirado: " + cantidad +  " de la cuenta " + this.ID);
                    
                }
            }
        }else{
            System.out.println("No eres el propietario de esta cuenta");
        }
        
    }
}