package in.test.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class TestConsumer {
	private static final String Q_NAME = "first";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory cfac = new ConnectionFactory();
		cfac.setHost("localhost");
		Connection conn = cfac.newConnection();
		Channel channel = conn.createChannel();

		channel.queueDeclare(Q_NAME, false, false, false, null);

		System.out.println("waiting for message");

		Consumer testConsumer = new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String msg = new String(body, "UTF-8");

				System.out.println("Received '" + msg + "'");
			}

		};

		channel.basicConsume(Q_NAME, true, testConsumer);

	}

}
