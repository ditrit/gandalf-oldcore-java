package com.orness.core.grpcjavaclient;

import com.orness.core.connectorkafkaservice.domain.ConnectorKafkaServiceGrpc;
import com.orness.core.connectorkafkaservice.domain.MessageRequest;
import com.orness.core.connectorkafkaservice.domain.MessageResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class GrpcJavaClientApplication {

	private static ManagedChannel channel;
	private static ConnectorKafkaServiceGrpc.ConnectorKafkaServiceBlockingStub stub;

	public static void main(String[] args) {
		StartClient();
		MessageClient("TOTO");

	}

	public static void StartClient() {
		channel = ManagedChannelBuilder.forAddress("localhost", 9005)
				.usePlaintext()
				.build();

		stub = ConnectorKafkaServiceGrpc.newBlockingStub(channel);
	}

	private static void StopClient() {
		channel.shutdown();
	}

	private static void MessageClient(String message) {
		Iterator<MessageResponse> messageResponseIterator;
		//REQUEST 0
		MessageRequest messageRequest = BuildMessageRequest("id_0", "name_0", "topic_0", "content_0");
		//CALL RPC 0
		messageResponseIterator = stub.messageStream(messageRequest);

		//REQUEST 0
		messageRequest = BuildMessageRequest("id_1", "name_1", "topic_1", "content_1");
		//CALL RPC 0
		messageResponseIterator = stub.messageStream(messageRequest);

		//PRINT
		while (messageResponseIterator.hasNext()) {
			System.out.println("Response received from server:\n" + messageResponseIterator.next());

		}
	}

	private static MessageRequest BuildMessageRequest(String id, String name, String topic, String content) {
		return MessageRequest.newBuilder().setId(id).setName(name).setTopic(topic).setContent(content).build();
	}
}
