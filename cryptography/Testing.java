package SICT_4309.cryptography;

public class Testing
{
    public static void main(String[] args)
    {
        RSA user1 = new RSA();
        RSA user2 = new RSA();
        RSA user3 = new RSA();
        RSA user4 = new RSA();

        // test1: user1 want to send a "Sharaf Qeshta" to user2

        String message = "Sharaf Qeshta";
        // we need to encrypt the message using user2 publicKey
        message = user2.encrypt(message);
        // when user2 receive the encrypted message he will
        // decrypt it using his own private key
        System.out.println("Cipher: " + message);
        System.out.println("user1 try to decrypt the cipher: " + user1.decrypt(message));
        System.out.println("user2 try to decrypt the cipher: " + user2.decrypt(message));
        System.out.println("user3 try to decrypt the cipher: " + user3.decrypt(message));
        System.out.println("user4 try to decrypt the cipher: " + user4.decrypt(message));

        /*
        * output =>
        * Cipher: ؁⦻ؐᴆؐڙ⢀⍍ʞٖ⦻ቧؐ
        * user1 try to decrypt the cipher: ޻ォٝᏨٝ儻㫜䵑䒋᫄ォปٝ
        * user2 try to decrypt the cipher: Sharaf Qeshta
        * user3 try to decrypt the cipher: ㈃ᮏ㊂⏗㊂∨b⭞⇆㧌ᮏ㪨㊂
        * user4 try to decrypt the cipher: ⽵ᆌ᨞ᶋ᨞墻߾㚉䝏䪭ᆌ㚊᨞
        * */


        // test2: user3 want to send "information security Course" to user4

        String message2 = "Information Security Course";
        message2 = user4.encrypt(message2);

        System.out.println("Cipher: " + message2);
        System.out.println("user1 try to decrypt the cipher: " + user1.decrypt(message2));
        System.out.println("user2 try to decrypt the cipher: " + user2.decrypt(message2));
        System.out.println("user3 try to decrypt the cipher: " + user3.decrypt(message2));
        System.out.println("user4 try to decrypt the cipher: " + user4.decrypt(message2));

        /*
        * output =>
        * Cipher: 䒎Ȥᛚ㳊⹚ᆡ቙䧈ཱུ㳊Ȥ໴㯬㚶㏝㬭⹚ཱུ䧈䭹໴ᗻ㳊㬭⹚ᱠ㚶
        * user1 try to decrypt the cipher: Ԥ䝿㇖ਥ䯭Þ➪ᑑ偎ਥ䝿䉖⡾୮㕛ᯓ䯭偎ᑑḌ䉖㚎ਥᯓ䯭⵹୮
        * user2 try to decrypt the cipher: ⯠㜝ⱑ䲿͊䁒ᱡ⎫╞䲿㜝⯇⤰↕ࢭ〇͊╞⎫୆⯇䚣䲿〇͊傲↕
        * user3 try to decrypt the cipher: ๰኿ԺಣᎶᯮᚮξᄠಣ኿Ꮢ୻ඕ௚ᔏᎶᄠξǝᏒᡁಣᔏᎶłඕ
        * user4 try to decrypt the cipher: Information Security Course
        * */
    }
}
