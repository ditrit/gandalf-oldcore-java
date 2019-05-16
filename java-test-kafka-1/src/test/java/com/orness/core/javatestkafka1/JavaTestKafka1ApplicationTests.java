package com.orness.core.javatestkafka1;

import com.orness.core.messagekafkamodule.domain.MessageKafka;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaTestKafka1ApplicationTests {

	private static MessageKafka messageKafka = new MessageKafka();



	@Test
	public void contextLoads() {
	}

}
