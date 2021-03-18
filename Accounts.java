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
    // instance of logger
    private Logger log = new Logger();
    Accounts(){

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
            
            this.storeAccount();

            data = this.ID + "@" + this.ownerInitials;
            log.createEntry("newaccount", data);
        }else{
            System.out.println("La cuenta ya existe.");
        }     
    }
    // security functions
    private String encryptNIP(String nip){
        try{
            MessageDigest mdConvert = MessageDigest.getInstance("MD5");
            byte[] messageDigest = mdConvert.digest(nip.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);

            String hashedText = number.toString(16);
            while(hashedText.length() < 32){
                hashedText = "0" + hashedText;
            }
            return hashedText;
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

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
    private void storeAccount(){
        // storage order
        // ID, owner clabe,  ownerage , ownername, owner encrypted nip
        String filename = "./users/" + this.ownerInitials + "_" + this.ownerAge + ".txt";
        try {
            FileWriter storage = new FileWriter(filename);
            storage.write(this.ID + "-" + this.CLABE + "-" + this.ownerAge + "-" + this.ownerName + "-" + this.ownerNIP);
            storage.close();
        } catch (IOException e) {
            System.out.println("Ocurrio un error: leer más.");
            e.printStackTrace();
        }

    }
    // getters
    private String getInitials(){
        String[] exploded = this.ownerName.split(" ");
        String initials = "";
        for(int i = 0; i <= exploded.length - 1; i++){            
            initials = initials +  exploded[i].charAt(0);
        }
        return initials;
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
         */
        String file = "./users/" + this.getInitials() + "_"+ ownerAge  +".txt";
        try{
            File f = new File(file);
            Scanner reader = new Scanner(f);
            reader.useDelimiter("\\Z");
            String information = reader.next();
            String[] inArray = information.split("-");

            if(index > 4){
                //System.out.println("No existe dicho indice de información.");
                return null;
            }else{
                //System.out.println(inArray[index]);
                return inArray[index];
            }
            
        }catch(FileNotFoundException e){
            System.out.print(e);
            return null;
        }   
    }
    public void showInformation(String nip, String userInitials){
        String data;
        if(this.validateNIP(this.encryptNIP(nip)) && userInitials == this.getInitials()){
            System.out.println("ID: ");
            System.out.println(this.getInformationFromFile(0));
            System.out.println("Cuentahabiente: ");
            System.out.println(this.getInformationFromFile(3));

            data = this.getInformationFromFile(0) + "@" + this.getInitials();
            log.createEntry("consult", data);
        }else{
            System.out.println("Permiso denegado.");
            data = "forbidden action for: " + this.ID;
            log.createEntry("consult", data);
        }

    }
}

