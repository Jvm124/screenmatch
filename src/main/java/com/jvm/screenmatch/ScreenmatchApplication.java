package com.jvm.screenmatch;

import com.jvm.screenmatch.model.DatosEpisodio;
import com.jvm.screenmatch.model.DatosSerie;
import com.jvm.screenmatch.model.DatosTemporadas;
import com.jvm.screenmatch.principal.EjemploStreams;
import com.jvm.screenmatch.principal.Principal;
import com.jvm.screenmatch.service.ConsumoAPI;
import com.jvm.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal();
		principal.muestraElMenu();
//		EjemploStreams ejemploStreams = new EjemploStreams();
//		ejemploStreams.muestraEjemplo();

	}

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

}
