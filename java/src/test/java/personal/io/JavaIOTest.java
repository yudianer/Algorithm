package personal.io;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author malujia
 * @create 10-31-2019 下午6:11
 **/

public class JavaIOTest {
    @Test
    public void channelTest() throws IOException {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("/home/maujia/personal/learning/java/src/test/java/personal/java/test.txt", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        String content = "MJMJMJMJMJMJMJMJMJMJ";
        byteBuffer.put(content.getBytes());
        FileChannel fileChannel = file.getChannel();
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()){
            fileChannel.write(byteBuffer);
        }

    }

    @Test
    public void nioTest() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        int readyChannels = selector.selectNow();
        if (readyChannels != 0){
            for (SelectionKey selectionKey:selector.selectedKeys()) {
                if (selectionKey.isAcceptable()){}
                selector.selectedKeys().remove(selectionKey);
            }
        }

    }
}
