package steganography;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Scanner;

public class Decrypt {
    public static void crackme() {
        while (true) {
            try {
                Scanner hey = new Scanner(System.in);
                System.out.print("Input absolute file path of file you would like to decrypt: ");
                String filename = hey.nextLine();
                hey.close();
                System.out.println("Now the program will decode your program. This might take a while.....");
                BufferedImage cipher = ImageIO.read(new File(filename));
                String decoded = getmessage(cipher);
                if (decoded.equals("")) {
                    System.out.println("This picture contains no secret message!");
                    break;
                } else {
                    System.out.println("The secret message is ' " + decoded + " '");
                    break;
                }

            } catch (IOException e) {
                System.out.println("Image file does not exist!");
            }
        }
    }

    public static String getmessage(BufferedImage picture) {
        int end = 0;
        int y = 0;
        Boolean stegoed = false;
        byte[] all_bits = (byte[]) picture.getRaster().getDataElements(0, 0, picture.getWidth(), picture.getHeight(),
                null);
        String[] binary = new String[all_bits.length];
        byte[] mama = new byte[all_bits.length];
        byte[] message = new byte[all_bits.length];
        for (int i = 0; i < all_bits.length; i++) {
            String bytee = Integer.toBinaryString(((int) all_bits[i] + 128));
            String zero = "0";
            while (bytee.length() < 8) {
                bytee = zero.concat(bytee);
            }
            binary[i] = bytee;
        }
        for (int i = 0; i < all_bits.length; i++) {
            String the_end = "::::::END";
            byte[] idk = the_end.getBytes();
            String current_byte = "";
            current_byte = "";
            while (current_byte.length() < 8) {
                current_byte = current_byte.concat(binary[y].substring(7));
                y += 1;
            }
            mama[i] = (byte) (Integer.parseInt(current_byte, 2) - 128);
            if (i > idk.length) {
                byte[] check_end = { mama[i - 9], mama[i - 8], mama[i - 7], mama[i - 6], mama[i - 5], mama[i - 4],
                        mama[i - 3], mama[i - 2], mama[i - 1] };
                if (new String(check_end).equals(the_end)) {
                    end = i - 9;
                    stegoed = true;
                    break;
                }
            }

        }
        if (stegoed) {
            for (int i = 0; i < end; i++) {
                message[i] = mama[i];
            }
            return new String(message);
        } else {
            return "";
        }
    }
}