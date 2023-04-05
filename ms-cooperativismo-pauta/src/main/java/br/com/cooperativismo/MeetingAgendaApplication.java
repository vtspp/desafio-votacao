package br.com.cooperativismo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class MeetingAgendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingAgendaApplication.class, args);
	}

}