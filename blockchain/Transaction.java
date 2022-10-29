package SICT_4309.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Transaction
{
    private final String toAddress;
    private final String fromAddress;
    int amount;
    private String signature;
    private Entry<Integer, Integer> publicKey;

    public Transaction(String toAddress,
                       String fromAddress, int amount)
    {
        this.toAddress = toAddress;
        this.fromAddress = fromAddress;
        this.amount = amount;
    }

    public String getToAddress()
    {
        return toAddress;
    }

    public String getFromAddress()
    {
        return fromAddress;
    }

    public int getAmount()
    {
        return amount;
    }

    public String calculateHash()
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
            byte[] hash = md.digest((fromAddress + toAddress + amount)
                    .getBytes(StandardCharsets.UTF_8));
            return Block.bytesToHex(hash);
        }
        catch (Exception exception)
        {
            // to make compiler happy
            return fromAddress + toAddress + amount;
        }
    }

    public void signTransaction(RSA rsa) throws Exception
    {
        // fromAddress is the public key
        if (rsa.getPublicKey().getKey() != Integer.parseInt(fromAddress))
            throw new Exception("You cannot sign transaction for other wallets");

        String hash = calculateHash();
        this.signature = RSA.encrypt(hash, rsa.getPrivateKey());
        this.publicKey = rsa.getPublicKey();
    }

    public boolean isValid() throws Exception
    {
        if (fromAddress == null)
            return true;

        if (signature == null || publicKey == null)
            return false;

        if (signature.length() == 0)
            throw new Exception("No signature in this transaction");

        String plain = RSA.decrypt(signature, publicKey);
        return plain.equals(calculateHash());
    }
}
