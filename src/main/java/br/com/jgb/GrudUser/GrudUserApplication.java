package br.com.jgb.GrudUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class GrudUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrudUserApplication.class, args);
	}

}