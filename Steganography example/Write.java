package SICT_4309;

import java.io.*;

/**
 * the reader of the file non_secret.xFormat will
 * see a group of objects for employees and will not notice
 * that we hide information in these objects
 * */
public class Write
{
    public static final String FILE_PATH
            = "src/SICT_4309/file_to_be_hidden.txt";

    public static void main(String[] args)
    {
        // read the bytes from the file
        byte[] bytes = null;
        try(FileInputStream inputStream
                    = new FileInputStream(new File(FILE_PATH)))
        {
            bytes = inputStream.readAllBytes();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        // write bytes to non_secret.xFormat while hiding each
        // byte in a single Employee object
        if (bytes != null)
        {
            try(ObjectOutputStream file = new ObjectOutputStream
                    (new FileOutputStream(new File("non_secret.xFormat"))))
            {
                int i = 0;
                for (byte x : bytes)
                    file.writeObject(new Employee(i++ + "", x));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
