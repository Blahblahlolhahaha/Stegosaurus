package steganography;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Scanner;

public class Encrypt{
    public static void encode_me() {
        while(true){
            try {
                Scanner scan  = new Scanner(System.in);
                System.out.print("Input your message here: ");
                String message = scan.nextLine();
                message = message.concat("::::::END");
                System.out.print("Input absolute path of the image you want to hide your image in: ");
                String path = scan.nextLine();
                scan.close();
                System.out.println("\nNow the program will encode your message. This might take a while.....");
                byte[] message_bytes = message.getBytes();
                String full = new String();
                for (int i = 0; i<message_bytes.length;i++){
                    String bytee = Integer.toBinaryString((int) message_bytes[i] + 128);
                    String zero = "0";
                    while (bytee.length() < 8){
                        bytee = zero.concat(bytee);
                    }
                    full = full.concat(bytee);
                }
                char[] full_bits = full.toCharArray();
                
                BufferedImage picture = ImageIO.read(new File(path));
                byte[] pixels = (byte[])picture.getRaster().getDataElements(0, 0, picture.getWidth(), picture.getHeight(), null);
                String[] imagecode = new String[pixels.length];
                for (int i = 0; i<pixels.length;i++){
                    String bytee = Integer.toBinaryString((int) pixels[i] + 128);
                    String zero = "0";
                    while (bytee.length() < 8){
                        bytee = zero.concat(bytee);
                    }
                    imagecode[i] = bytee;
                }
                
                byte[] encrypted = new byte[pixels.length];
                for (int i = 0; i < pixels.length; i++){
                    try{
                        byte bits = (byte) (Integer.parseInt((imagecode[i].substring(0, 7) + full_bits[i]), 2) - 128);
                        encrypted[i] = bits;
                    } catch(Exception e){
                        byte bits = (byte) (Integer.parseInt(imagecode[i], 2) - 128);
                        encrypted[i] = bits;
                    }
                    
                }
                BufferedImage results = new BufferedImage(picture.getWidth(),picture.getHeight(),ColorSpace.TYPE_RGB);
                results.getRaster().setDataElements(0,0,picture.getWidth(),picture.getHeight(), encrypted);
                ImageIO.write(results,"png",new File(System.currentTimeMillis() + ".png"));
                System.out.println("Success!");
                break;
            } catch (IOException e) {
                //TODO: handle exception
                System.out.println("Picture does not exist!");
            }
        }

        
    }
}