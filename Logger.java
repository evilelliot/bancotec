import java.io.*;
import java.sql.Timestamp;
class Logger {
    public boolean createEntry(String type, String data){
        Timestamp time = new Timestamp(System.currentTimeMillis());
        String filename = "./logs.txt";
        try {
            FileWriter storage = new FileWriter(filename, true);
            storage.write("\r\n");
            storage.write(type + "-" + time + "-" + data);

            storage.close();

            return true;
        } catch (IOException e) {
            System.out.println("Ocurrio un error: leer más.");
            e.printStackTrace();

            return false;
        }
    }
}
