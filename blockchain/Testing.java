package SICT_4309.blockchain;

public class Testing
{
    public static void main(String[] args) throws Exception
    {
        BlockChain blockChain = new BlockChain(2);
        RSA user1 = new RSA();
        RSA user2 = new RSA();

        // user1 want to send 10 coins to user2
        Transaction transaction1 = new Transaction(
                user2.getPublicKey().getKey() + "",
                user1.getPublicKey().getKey()
                        + "",
                10);

        // user2 want to send 5 coins to user1
        Transaction transaction2 = new Transaction(
                user1.getPublicKey().getKey() + "",
                user2.getPublicKey().getKey()
                        + "",
                5);

        // user2 want to send 20 coins to user1
        Transaction transaction3 = new Transaction(
                user1.getPublicKey().getKey() + "",
                user2.getPublicKey().getKey()
                        + "",
                20);

        // user1 sign the transaction using his private key
        transaction1.signTransaction(user1);

        // user2 sign the transaction using his private key
        transaction2.signTransaction(user2);

        // user2 sign the transaction using his private key
        transaction3.signTransaction(user2);

        // add the transactions to the current block
        blockChain.addTransaction(transaction1);
        blockChain.addTransaction(transaction2);
        blockChain.addTransaction(transaction3);

        System.out.println("Start the miner...");

        // user1 mines a block he gets 100 coins
        blockChain.minePendingTransactions(user1.getPublicKey().getKey()+ "");

        // user2 mines another block and gets a 100 coins
        blockChain.minePendingTransactions(user2.getPublicKey().getKey()+ "");

        // user1 again mines another block and he gets another 100 coins
        blockChain.minePendingTransactions(user1.getPublicKey().getKey()+ "");


        System.out.println("Balance of user 1 is "
                + blockChain
                .getBalanceOfAddress(user1.getPublicKey().getKey()+"")); // Balance of user 1 is 215

        System.out.println("Balance of user 2 is "
                + blockChain
                .getBalanceOfAddress(user2.getPublicKey().getKey()+"")); // Balance of user 2 is 85



        System.out.println("Is the blockchain valid? " + blockChain.isValidChain()); // Is the blockchain valid? true

        /*
         * [
         * {
         * "Timestamp": 1667052029473
         * "Transactions": []
         * "Hash": "801d984de22511a05d2f4c0603e9809ab88b01ed90c25fd94dc6ae7b14ccd481"
         * "Previous Hash": ""
         * }
         * ,
         * {
         * "Timestamp": 1667052030070
         * "Transactions": [SICT_4309.blockchain.Transaction@7506e922, SICT_4309.blockchain.Transaction@4ee285c6, SICT_4309.blockchain.Transaction@621be5d1, SICT_4309.blockchain.Transaction@573fd745]
         * "Hash": "6c366cf4b6e66d68ee44453271c70ff49d693a05d05ad95f864bc6553f54c29a"
         * "Previous Hash": "801d984de22511a05d2f4c0603e9809ab88b01ed90c25fd94dc6ae7b14ccd481"
         * }
         * ,
         * {
         * "Timestamp": 1667052030073
         * "Transactions": [SICT_4309.blockchain.Transaction@4f2410ac]
         * "Hash": "d66a2b2fb6ce9d635e117af5ef1d8bb560f980ce9a19cd9d51962f7a34486a6c"
         * "Previous Hash": "6c366cf4b6e66d68ee44453271c70ff49d693a05d05ad95f864bc6553f54c29a"
         * }
         * ,
         * {
         * "Timestamp": 1667052030075
         * "Transactions": [SICT_4309.blockchain.Transaction@722c41f4]
         * "Hash": "8f14804082a3b5687aacc21397a9993ea4781e52dbb5159d2a3ed8f7eda8bab6"
         * "Previous Hash": "d66a2b2fb6ce9d635e117af5ef1d8bb560f980ce9a19cd9d51962f7a34486a6c"
         * }
         * ]
         * */
        System.out.println(blockChain.chain);

        blockChain.chain.get(1).transactions.get(0).amount = 120;
        System.out.println("Is the blockchain valid? " + blockChain.isValidChain()); // Is the blockchain valid? false
    }
}
