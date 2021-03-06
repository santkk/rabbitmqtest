package in.test.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class WorkQueueProducer {
	private static final String Q_NAME = "first";

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory cfac = new ConnectionFactory();
		cfac.setHost("localhost");
		Connection conn = cfac.newConnection();
		Channel channel = conn.createChannel();
		channel.queueDeclare(Q_NAME, false, false, false, null);

		channel.queueDeclare(Q_NAME, false, false, false, null);
		int count = Integer.parseInt(args[0]);
		String message = "";
		for (int i = 0; i < count; i++) {
			message = "task..." + i;
			channel.basicPublish("", Q_NAME, null, message.getBytes());
			System.out.println("sent " + message);
		}

		channel.close();
		conn.close();
	}
}
