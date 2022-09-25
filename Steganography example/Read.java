package SICT_4309;

import java.io.*;
import java.util.ArrayList;

/**
 * here we need to read the objects in the file non_secret.xFormat
 * and extract the id attribute so we can combine them to form
 * the hidden data
 * */
public class Read
{
    private static final String FILE_PATH = "src/SICT_4309/extracted_data.txt";

    public static void main(String[] args)
    {
        // read objects in non_secret.xFormat
        // and store the id attribute in list
        ArrayList<Byte> list = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream
                (new FileInputStream("non_secret.xFormat")))
        {
            while (true)
            {
                Employee employee = (Employee) inputStream.readObject();
                list.add(employee.id);
            }
        }
        catch (EOFException ignored)
        {
            // there`s no more objects
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        // write each byte in the list to the target file
        try(FileOutputStream outputStream
                    = new FileOutputStream(new File(FILE_PATH)))
        {
            for (byte x : list)
                outputStream.write(x);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
