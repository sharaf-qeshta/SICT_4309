package SICT_4309.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Block
{
    private final long timestamp;
    ArrayList<Transaction> transactions;
    private String previousHash;
    private String hash;
    private int nonce;

    public Block(ArrayList<Transaction> transactions, String previousHash)
    {
        this.timestamp = System.currentTimeMillis();
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.hash = calculateHash();
        this.nonce = 0;
    }

    public String calculateHash()
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
            byte[] hash = md.digest((timestamp + transactions.toString()
                    + previousHash + nonce)
                    .getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        }
        catch (Exception exception)
        {
            // to make compiler happy
            return timestamp + transactions.toString()
                    + previousHash + nonce;
        }
    }

    public static String bytesToHex(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public String getHash()
    {
        return hash;
    }

    public ArrayList<Transaction> getTransactions()
    {
        return transactions;
    }

    public String getPreviousHash()
    {
        return previousHash;
    }

    public void mineBlock(int difficulty)
    {
        String filledWithZero = "";
        for (int i = 0; i < difficulty; i++)
            filledWithZero += "0";
        while (hash.substring(0, difficulty).equals(filledWithZero))
        {
            this.hash = calculateHash();
            nonce++;
        }
        System.out.println("Block Mined");
    }

    public void setPreviousHash(String previousHash)
    {
        this.previousHash = previousHash;
        // by default modify the block data
        // tend recalculating the hash
        this.hash = calculateHash();
    }

    public boolean hasValidTransactions()
    {
        for (Transaction transaction: transactions)
        {
            try
            {
                if (!transaction.isValid())
                    return false;
            }
            catch (Exception exception)
            {
                return false;
            }
        }
        return true;
    }

    public String toString()
    {
        return  "\n{\n\"Timestamp\": " + timestamp + "\n"
                + "\"Transactions\": " + transactions + "\n"
                + "\"Hash\": \"" + hash + "\"\n"
                + "\"Previous Hash\": \"" + previousHash + "\"\n}\n";
    }
}
