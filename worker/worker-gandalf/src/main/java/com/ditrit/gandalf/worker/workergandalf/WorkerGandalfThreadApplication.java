package com.ditrit.gandalf.worker.workergandalf;

import com.ditrit.gandalf.worker.workergandalf.thread.WorkerGandalfThread;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class WorkerGandalfThreadApplication {

	public static void main(String[] args) {
		WorkerGandalfThread workerGandalfThread = new WorkerGandalfThread();
		workerGandalfThread.start();
	}
}
