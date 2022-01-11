package calculadora.IASHandyman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.text.ParseException;

@SpringBootApplication

public class CalculadoraApplication {

	public static void main(String[] args) throws ParseException {

		SpringApplication.run(CalculadoraApplication.class, args);
	}

}
