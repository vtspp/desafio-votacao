package br.com.cooperativismo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class PautaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PautaApplication.class, args);
	}

}