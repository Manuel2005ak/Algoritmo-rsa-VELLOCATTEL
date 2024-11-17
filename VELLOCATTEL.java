import java.math.BigInteger;
import java.util.Random;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class VELLOCATTEL {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("inserisci il messaggio da cifrare: ");
        String message = scanner.nextLine();
        int length = 2048;
        Random r = new Random();

        BigInteger p = BigInteger.probablePrime(length / 2, r);
        BigInteger q = BigInteger.probablePrime(length / 2, r);
        BigInteger n = p.multiply(q);
        BigInteger v = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger e;
        do {
            e = new BigInteger(length / 2, r);
        } while ((e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(v) >= 0) || !e.gcd(v).equals(BigInteger.ONE)); 

        BigInteger d = e.modInverse(v);
 
        System.out.println("chiave pubblica (n=" +  n.toString(16) + " ,e = " + e.toString(16) + " )");
        System.out.println("chiave privata (n=" + n.toString(16) + " ,d =" + d.toString(16) + " )");

        BigInteger encryptedMessage = cifra(message, e, n);
        System.out.println("messaggio cifrato: " + encryptedMessage.toString(16));

        String decryptedMessage = decifra(encryptedMessage, d, n);
        System.out.println("messaggio decifrato: " + decryptedMessage);
        scanner.close();
    }

    public static BigInteger cifra(String message, BigInteger e, BigInteger n) {
        BigInteger messageBytes = new BigInteger(message.getBytes(StandardCharsets.UTF_8));
        return messageBytes.modPow(e, n);
    }

    public static String decifra(BigInteger encryptedMessage, BigInteger d, BigInteger n) {
        BigInteger decryptedMessageBytes = encryptedMessage.modPow(d, n);
        return new String(decryptedMessageBytes.toByteArray(), StandardCharsets.UTF_8);
    }
}