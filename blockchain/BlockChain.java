package SICT_4309.blockchain;

import java.util.ArrayList;

public class BlockChain
{
    ArrayList<Block> chain = new ArrayList<>();
    private final ArrayList<Transaction> pendingTransactions = new ArrayList<>();
    private int miningReward = 100;
    private int difficulty;

    public BlockChain(int difficulty)
    {
        this.difficulty = difficulty;
        chain.add(getGenesisBlock());
    }

    public Block getGenesisBlock()
    {
        return new Block(new ArrayList<>(), "");
    }

    public Block getLastBlock()
    {
        return chain.get(chain.size()-1);
    }


    public void minePendingTransactions(String miningRewardAddress)
    {
        Transaction transaction = new Transaction
                (miningRewardAddress, null , miningReward);
        pendingTransactions.add(transaction);
        // java use pass by reference for objects
        // so we need to clone the pendingTransaction
        Block block = new Block(
                (ArrayList<Transaction>) pendingTransactions.clone(),
                getLastBlock().getHash());
        block.mineBlock(difficulty);
        chain.add(block);
        pendingTransactions.clear();
    }

    public void addTransaction(Transaction transaction) throws Exception
    {
        if (transaction.getToAddress() == null
                || transaction.getFromAddress() == null)
            throw new Exception("Transaction must include from and to address");
        if (!transaction.isValid())
            throw new Exception("cannot add invalid transaction to chain");
        pendingTransactions.add(transaction);
    }


    public int getBalanceOfAddress(String address)
    {
        int balance = 0;
        for (Block block: chain)
        {
            for (Transaction transaction : block.getTransactions())
            {
                if (transaction.getFromAddress() == null)
                {
                    if (transaction.getToAddress().equals(address))
                        balance += transaction.getAmount();
                    continue;
                }
                if (transaction.getFromAddress().equals(address))
                    balance -= transaction.getAmount();
                else if (transaction.getToAddress().equals(address))
                    balance += transaction.getAmount();
            }
        }
        return balance;
    }

    public boolean isValidChain()
    {
        for (int i = 1; i < chain.size(); i++)
        {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i-1);

            if (!currentBlock.hasValidTransactions())
                return false;


            if (!currentBlock.getHash()
                    .equals(currentBlock.calculateHash()))
                return false;

            if (!currentBlock.getPreviousHash()
                    .equals(previousBlock.getHash()))
                return false;
        }
        return true;
    }

    public String toString()
    {
        return chain.toString();
    }


}
