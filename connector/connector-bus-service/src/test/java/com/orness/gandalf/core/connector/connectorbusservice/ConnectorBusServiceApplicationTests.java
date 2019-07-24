package com.orness.gandalf.core.connector.connectorbusservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectorBusServiceApplicationTests {

	private PrintStream sysOut;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		sysOut = System.out;
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void revertStreams() {
		System.setOut(sysOut);
	}

	@Test
	public void test() {

	}

}
