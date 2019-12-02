package personal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("/home/maujia/personal/learning/java/src/main/java/personal/java/test.txt", "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(file.length());
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        FileChannel fileChannel = file.getChannel();
        int bytesRead = fileChannel.read(byteBuffer);
        System.out.println(bytesRead);
        while (bytesRead != -1){
            System.out.println("byte read: " + bytesRead);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()){
                System.out.println((char)byteBuffer.get());
            }
            byteBuffer.clear();
            bytesRead = fileChannel.read(byteBuffer);
        }
        HashMap hashMap = new HashMap();
    }

}
