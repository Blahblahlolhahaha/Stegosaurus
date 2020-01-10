package steganography;

public class Main{
    public static void main(String[] args){
        if(args.length != 1){
            System.out.println(" Usage: java stegonography.Stego <encode/decode>");
        }
        else if(!(args[0].equals("encode") || args[0].equals("decode"))){
            
            System.out.println(" Usagee: java stegonography.Stego <encode/decode>");
            System.out.println("Usagee: java stegonography.Stego <encode/decode>");
        }
        else{
            if (args[0].equals("encode")){
                Encrypt.encode_me();
            }
            else{
                Decrypt.crackme();
            }
        }
    }
}