/**
 * Author: Alberto Ocaranza
 * Description: esta clase convierte una string a un hash MD5.
 * Utiliza messagedigest para convertir el contenido en int de una string
 * a un hash encriptado.
 */
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;  
class Encrypt {
    
    
    public static void main(String[] args){
        // Probando el método.
        String data = "Montserrat";
        System.out.println("Original: " + data);
        System.out.println("Hashed: " + encrypt(data, "sha256"));
        System.out.println("Hashed: " + encrypt(data, "sha1"));
        System.out.println("Hashed: " + encrypt(data, "MD5"));
        // Doble vuelta (más seguro)
        System.out.println("2X Hashed: " +  encrypt(encrypt(data, "MD5"), "sha1"));
    }    
    static public String encrypt(String data, String algorithm){
        try{
            MessageDigest mdConvert = MessageDigest.getInstance(algorithm);
            byte[] messageDigest = mdConvert.digest(data.getBytes());
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
   
}
