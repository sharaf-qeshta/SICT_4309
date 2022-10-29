package SICT_4309.blockchain;

import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.Random;

public class RSA
{
    /**
     * the PrimeNumbers.dat contains the first 3 million prime numbers
     * i generated it previously while i`am solving the exercises
     * in a book called introduction to java programming and data structures
     * using sieve of eratosthenes algorithm
     * if you want to see the code that outputs this file
     * here it is:
     *
     * https://github.com/sharaf-qeshta/Introduction-to-Java-Programming-and-Data-Structures-Comprehensive-Version-Eleventh-Edition-Global-/tree/main/Chapter_22/Problem%2408/SieveOfEratosthenes
     * */
    private static final String PRIMES_FILE_PATH = "src/SICT_4309/blockchain/PrimeNumbers.dat";

    /**
     * will be used to store the value of n
     * as the book author say in chapter 12 n must be
     * at least of length of 100 digits
     * but for simplicity i used the first 25-50 primes numbers
     * so we can make it faster in this case n will
     * be of length 5 digits
     * the book suggest the 100 digits to make it harder to guess
     * and encode any character but since we are only use the ascii
     * characters 5 digits length for n will be enough
     * */
    private final int n;

    private int publicKey, privateKey;


    public RSA(int n, int publicKey, int privateKey)
    {
        this.n = n;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * generate private and public keys for
     * the current object
     * */
    public RSA()
    {
        int[] primes = getRandomPrimes(); // get two random primes
        int p = primes[0], q = primes[1];

        n = p * q; // evaluating n

        int Φ = (p - 1) * (q - 1);
        // the value of the public key must
        // be relatively prime with Φ(n)
        // that is have no common factor other than one
        for (publicKey = 2; publicKey < Φ; publicKey++)
            if (gcd(publicKey, Φ) == 1)
                break;

        // here we find private key
        // such that
        // privateKey * publicKey = 1 % Φ(n)
        for (int i = 0; i <= 9; i++)
        {
            int x = 1 + (i * Φ);
            if (x % publicKey == 0)
            {
                privateKey = x / publicKey;
                break;
            }
        }
    }

    /**
     * return the public key
     * */
    public Entry<Integer, Integer> getPublicKey()
    {
        return new Entry<>(publicKey, n);
    }


    /**
     * return the private key
     * */
    public Entry<Integer, Integer> getPrivateKey()
    {
        return new Entry<>(privateKey, n);
    }


    /**
     * this method get random
     * primes from the file PrimeNumbers.dat
     * since each number stored in 8 bits
     * in the binary files
     * multiplying 8 by N (which in our case a random number between 25 and 50)
     * and make the file seek 8N the next read will give us the Nth number in the file
     * */
    private int[] getRandomPrimes()
    {
        int[] primes = new int[2];
        try (RandomAccessFile file = new RandomAccessFile(PRIMES_FILE_PATH, "r"))
        {
            Random random = new Random();
            int r1 = random.nextInt(25) + 25; // 25 ---> 50

            file.seek(r1 * 8);
            primes[0] = (int) file.readLong();

            int r2 = random.nextInt(25) + 25; // 25 --> 50
            while (r1 == r2)
                r2 = random.nextInt(25) + 25;
            file.seek(r2 * 8);
            primes[1] = (int) file.readLong();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return primes;
    }


    /**
     * return the cipher for p
     * which is p^publicKey % n
     * */
    private BigInteger encrypt(int p)
    {
        BigInteger cipher = new BigInteger(p + "");
        return cipher.pow(publicKey).mod(new BigInteger(n + ""));
    }

    /**
     * a static method do the same
     * as the previous method
     * */
    private static BigInteger encrypt(int p, Entry<Integer, Integer> publicKey)
    {
        BigInteger cipher = new BigInteger(p + "");
        return cipher.pow(publicKey.getKey()).mod(new BigInteger(publicKey.getN() + ""));
    }

    /**
     * trying to decrypt the cipher
     * c which obtained using
     * c^privateKey % n
     * */
    private BigInteger decrypt(int c)
    {
        BigInteger plain = new BigInteger(c + "");
        return plain.pow(privateKey).mod(new BigInteger(n + ""));
    }

    /**
     * a static method do the same
     * as the previous method
     * */
    private static BigInteger decrypt(int c, Entry<Integer, Integer> privateKey)
    {
        BigInteger plain = new BigInteger(c + "");
        return plain.pow(privateKey.getKey()).mod(new BigInteger(privateKey.getN() + ""));
    }

    /**
     * encrypt a character
     * by getting it`s ascii
     * integer representation
     * */
    private char encrypt(char p)
    {
        BigInteger cipher = encrypt((int) p);
        return (char) Integer.parseInt(cipher.toString());
    }

    /**
     * a static method do the same
     * as the previous method
     * */
    private static char encrypt(char p, Entry<Integer, Integer> publicKey)
    {
        BigInteger cipher = encrypt((int) p, publicKey);
        return (char) Integer.parseInt(cipher.toString());
    }

    /**
     * decrypt a character
     * by getting it`s ascii
     * integer representation
     * */
    private char decrypt(char c)
    {
        BigInteger cipher = decrypt((int) c);
        return (char) Integer.parseInt(cipher.toString());
    }

    /**
     * a static method do the same
     * as the previous method
     * */
    private static char decrypt(char c, Entry<Integer, Integer> privateKey)
    {
        BigInteger cipher = decrypt((int) c, privateKey);
        return (char) Integer.parseInt(cipher.toString());
    }

    /**
     * encrypt plain
     * by iterating through the plain text
     * and encrypt each character
     * */
    public String encrypt(String plain)
    {
        String cipherText = "";
        for (int i = 0; i < plain.length(); i++)
            cipherText += encrypt(plain.charAt(i));
        return cipherText;
    }


    /**
     * a static method do the same
     * as the previous method
     * */
    public static String encrypt(String plain, Entry<Integer, Integer> publicKey)
    {
        String cipherText = "";
        for (int i = 0; i < plain.length(); i++)
            cipherText += encrypt(plain.charAt(i), publicKey);
        return cipherText;
    }

    /**
     * decrypt cipher
     * by iterating through the cipher text
     * and decrypt each character
     * */
    public String decrypt(String cipher)
    {
        String plainText = "";
        for (int i = 0; i < cipher.length(); i++)
            plainText += decrypt(cipher.charAt(i));
        return plainText;
    }

    /**
     * a static method do the same
     * as the previous method
     * */
    public static String decrypt(String cipher, Entry<Integer, Integer> privateKey)
    {
        String plainText = "";
        for (int i = 0; i < cipher.length(); i++)
            plainText += decrypt(cipher.charAt(i), privateKey);
        return plainText;
    }

    /**
     * a recursive method used
     * to get the greatest common divisor
     * between two integers i and j
     * we use it in the process
     * of generating the private and the public keys
     * */
    private static int gcd(int i, int j)
    {
        if (i == 0)
            return j;
        else
            return gcd(j % i, i);
    }
}
