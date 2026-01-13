package com.jvm.screenmatch.principal;

import com.jvm.screenmatch.model.DatosEpisodio;
import com.jvm.screenmatch.model.DatosSerie;
import com.jvm.screenmatch.model.DatosTemporadas;
import com.jvm.screenmatch.model.Episodio;
import com.jvm.screenmatch.service.ConsumoAPI;
import com.jvm.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE="https://omdbapi.com/?t=";
    private final String API_KEY="";  //coloca tu clave de api
    private ConvierteDatos conversor = new ConvierteDatos();



    public void muestraElMenu(){

        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");

        var nombreSerie = teclado.nextLine();
        var json=consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(" ", "+")+API_KEY );
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //Datos de todas las temporadas.
        List<DatosTemporadas> temporadas= new ArrayList<>();
        for(int i=1;i<= datos.totalDeTemporadas();i++){
            json = consumoApi.obtenerDatos(URL_BASE+nombreSerie.replace(" ", "+")+"&season="+i+API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }
//        temporadas.forEach(System.out::println);

        // mostrar solo el titulo de cada temporada
//        for(int i=0;i<datos.totalDeTemporadas();i++){
//            List<DatosEpisodio> episodiosTemporadas = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporadas.size(); j++) {
//                System.out.println(episodiosTemporadas.get(j).titulo());
//            }
//        }

        // Mejoria usando funciones Lambda
        // temporadas.forEach(t-> t.episodios().forEach( e -> System.out.println(e.titulo())));

        //Convertir todas las informaciones a una lista del tipo DatosEpisodio
//        List<DatosEpisodio> datosEpisodios= temporadas.stream()
//                .flatMap(t->t.episodios().stream())
//                .collect(Collectors.toList());


//        //Top 5 episodios
//        System.out.println("\nTop 5");
//        datosEpisodios.stream()
//                .filter(e->!e.evaluacion().equalsIgnoreCase("N/A"))
////                .peek(e->System.out.println("Primer filtro(N/A)"+e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
////                .peek(e->System.out.println("Segundo filtro ordenacion (M>m)"+e))
//                .map(e->e.titulo().toUpperCase())
////                .peek(e->System.out.println("Tercer filtro mayusculas "+e))
//                .limit(5)
//                .forEach(System.out::println);
//
//       // convirtiendo los datos a una lista del tipo Episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t->t.episodios().stream()
                .map(d->new Episodio(t.numero(),d)))
                .collect(Collectors.toList());
//        episodios.forEach(System.out::println);

        // busqueda de episodios apartir de x año
//        System.out.println("\nIndica el año a partir del cual deseas ver los episodios:");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();
//
//        LocalDate fechabsuqeda =  LocalDate.of(fecha,1,1);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e->e.getFechaDeLanzamiento()!= null && e.getFechaDeLanzamiento().isAfter(fechabsuqeda))
//                .forEach(e-> System.out.println(
//                        " Temporada "+e.getTemporada()+
//                                " Episodio "+e.getTitulo()+
//                                " Fecha de lanzamiento "+e.getFechaDeLanzamiento().format(formatter)
//
//                ));
//
//        // Busca episodios por dpedazo del titulo
//        //System.out.println("Por favor escribe el titulo del episodio que desea ver: ");
//        var pedazoTitulo = teclado.nextLine();
//        Optional<Episodio> episodioBuscardo = episodios.stream()
//                .filter(ep -> ep.getTitulo().contains(pedazoTitulo.toUpperCase()))
//                .findFirst();
//        if(episodioBuscardo.isPresent()){
//            System.out.println("Episodio encontrado");
//            System.out.println("Los datos son: "+ episodioBuscardo.get().getTitulo());
//        } else {
//            System.out.println("Episodio no encontrado");
//        }
        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e->e.getEvaluacion()>0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                            Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e->e.getEvaluacion()>0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("Media de las evaluaciones: "+ est.getAverage());
        System.out.println("Episodio mejor evaluado: "+ est.getMax());
        System.out.println("Episodio peor evaluado: "+ est.getMin());
    }
}
