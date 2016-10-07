package br.com.stanchese.reservatorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class ReservatorioApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ReservatorioApplication.class);
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ReservatorioApplication.class, args);
	} 
}
