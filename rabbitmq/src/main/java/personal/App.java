package personal;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("");
        factory.setPort(1234);
        factory.setVirtualHost("/");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.txSelect();
        while (true){
            try {
                channel.basicPublish("", "", true,
                        new AMQP.BasicProperties().builder()
                                .contentType("text/plain")
                                .deliveryMode(2)
                                .priority(1)
                                .expiration("6000000").build(),
                        "".getBytes());
                channel.txCommit();
            }catch (Exception e){
                channel.txRollback();
            }
        }
    }
}
