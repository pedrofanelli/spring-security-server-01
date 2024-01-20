package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

@RestController
public class MainController {

	@GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }
    
	/**
     * Hay 4 tipos de OPERACIONES para usar con Mono y Flux:
     * 1) Creation
     * 2) Combination
     * 3) Transformation
     * 4) Logic
     * 
     * Mono y Flux representan los Publishers, a los que luego ejecutamos como Subscribers
     * Pueden detenerse de su lado por no haber mas items/error o de nuestro lado con un Disposable
     * 
     * @return 
     */
    @GetMapping("/ciao")
    public String ciao() throws InterruptedException {
        //return "<a href=\"/oauth2/authorization/google\">Google</a>";
        
        // 1) CREATION
        //Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana"); //flux creado sin suscribers, para generar el flow hay que suscribirse
        //String[] fruits = new String[] {"Apple", "Orange", "Grape", "Banana"};
        //Flux<String> fruitFlux = Flux.fromArray(fruits);
        /*List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Orange");
        fruits.add("Grape");
        fruits.add("Banana");
        Flux<String> fruitFlux = Flux.fromIterable(fruits);*/
        
        //en el ejemplo, no hay operaciones intermediarias, la data fluye directamente del Flux al Subscriber
        //fruitFlux.subscribe(f -> System.out.println("Here's some fruit: "+f));
        
        //***
        
        // range() crea un Flux que emite un valor autoincremental
        //Flux<Integer> intervalFlux = Flux.range(1, 5);
        //intervalFlux.subscribe(interval -> System.out.println("Interval: "+interval));
        
        // interval() crea un Flux que emite un valor autoincremental por cierto tiempo, 5 veces, sino es infinito
        // EJEMPLO MUY BUENO! Porque el controller subscribirá pero seguirá y devolverá el value, mostando la vista
        //pero en un Thread paralelo se irán ejecutando cada item de forma independiente, ejecutando la lógica por el Consumer
        // Cada item que aparezca será manejado, hasta que el Flux se corte o suceda un error
        //Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);
        //intervalFlux.subscribe(x -> System.out.println("Intervalo por cada 1 segundo: "+x));
        // si queremos cancelar desde el lado del subscriber y no del publisher
        /*Flux<Long> infiniteFlux = Flux.interval(Duration.ofSeconds(1));
        Disposable disposable = infiniteFlux.subscribe(
                x -> System.out.println("Intervalo por cada 1 segundo: " + x),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Flux completed")
        );
        // Cancelling the subscription after 5 seconds
        Thread.sleep(8000);
        disposable.dispose();*/
        
        // 2) COMBINATION
        
        // Podemos mergear dos Flux en uno solo
        // primero saldra el de los characters, por el retraso de subscripcion de frutas, luego se intercalan
        // .delayElements() emite un item por cada 500 ms
        // .delaySubscription retarda el inicio 250 ms luego de la subscripcion
        /*Flux<String> characterFlux = Flux
                                        .just("Garfield", "Kojak", "Barbossa")
                                        .delayElements(Duration.ofMillis(500));
        Flux<String> foodFlux = Flux
                                    .just("Lasagna", "Lollipops", "Apples")
                                    .delaySubscription(Duration.ofMillis(250))
                                    .delayElements(Duration.ofMillis(500));

        Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux);
        mergedFlux.subscribe(x -> System.out.println("Item: "+x));
        */
        
        //como mergeWith() no asegura un funcionamiento perfecto, se usa zip()
        // creará tuples de items, por cada grupo de Flux que se zipeen
        // además, zip() es STATIC, a diferencia de mergeWith que es método de un Flux específico
        // el Flux creado tiene una alineación perfecta entre characters y comidas
        /*
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");
        Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);
        zippedFlux.subscribe(p -> System.out.println("Touple: "+p.getT1()+" - "+p.getT2()));
        */
        // incluso podemos, en lugar de usar Tuple2, usar una funcion nuestra
        /*
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");
        Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux, (c,f) -> c+" eats "+f);
        zippedFlux.subscribe(p -> System.out.println(p));
        */
        
        //***
        
        // eligiendo el Flux que emite el primer dato y nos quedamos con ese
        /*
        Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
                                    .delaySubscription(Duration.ofMillis(100));
        Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");
        Flux<String> firstFlux = Flux.firstWithSignal(slowFlux, fastFlux);
        firstFlux.subscribe(p -> System.out.println(p));
        */
        
        
        // 3) TRANSFORMATION + FILTERING
        
        // podemos skip() un numero determinado de entries/data
        /*Flux<String> countFlux = Flux.just(
                                "one", "two", "skip a few", "ninety nine", "one hundred")
                                .skip(3);
        countFlux.subscribe(p -> System.out.println(p));*/
        // o por un tiempo específico. Emitimos elementos por cada 1 segundo, y los primeros 4 no se toman
        /*Flux<String> countFlux = Flux.just(
                                "one", "two", "skip a few", "ninety nine", "one hundred")
                                .delayElements(Duration.ofSeconds(1))
                                .skip(Duration.ofSeconds(4));
        countFlux.subscribe(p -> System.out.println(p));*/
        // skip() puede ser visto como el OPUESTO a take()
        // skip saltea los primeros, take toma los primeros
        // take tambien tiene un modo por tiempo y no solo por numero de items
        
        //skip y take son vistos como operaciones de FILTROS tomando como criterio el numero de items o la duración
        
        //***
        
        //Otro tipo de filtro son los Predicate usando filter(), más customizado a nuestra necesidad
        /*Flux<String> nationalParkFlux = Flux.just("Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton")
                                            .filter(np -> !np.contains(" ")); //Predicate
        nationalParkFlux.subscribe(p -> System.out.println(p));*/
        
        // tambien tenemos .distinct() que evita elementos iguales
        /*Flux<String> animalFlux = Flux.just("dog", "cat", "bird", "dog", "bird", "anteater")
                                        .distinct();
        animalFlux.subscribe(p -> System.out.println(p));*/
        
        //***
        
        // Una de las mas comunes formas de filtro es cambiar el tipo de dato
        // para eso tenemos map() y flatMap()
        /*Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                                        .map(n -> {
                                            String[] split = n.split("\\s");
                                            return new Player(split[0], split[1]);
                                        } // es una Function
        );
        playerFlux.subscribe(p -> System.out.println("First: "+p.getFirstName()+" - Last: "+p.getLastName()));*/
        // lo importante es que map() funciona SINCRONICAMENTE, a medida que each item is published
        // si quisieramos que fuera asincrónico usamos flatMap()!
        // usa otro Flux como intermediario para la transformación y permitir que sea async
        // llega el dato, se toma, y se inicia el proceso de transformación
        // si se demora y aparece otro dato, se iniciará paralelamente ni bien llega
        // y se irán publisheando a medida que terminan
        /*Flux<Player> playerFlux = Flux.just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                                        .flatMap(n -> Mono.just(n) //async cada item
                                            .map(p -> { //manejo sync dentro del item
                                                String[] split = p.split("\\s");
                                                return new Player(split[0], split[1]);
                                            })
                                            .subscribeOn(Schedulers.parallel())//cada subscripcion en un Thread paralelo
                                        );
        playerFlux.subscribe(p -> System.out.println("First: "+p.getFirstName()+" - Last: "+p.getLastName()));*/
        // existen varios modelos para manejar CONCURRENCIA en Schedulers
        // .immediate, .single, .newSingle, .elastic, .parallel
        
        // sirve para manejo asyncrónico PERO no asegura el orden
        
        //***
        
        //Buffering data!
        // Podemos buffer data en bite-size chunks
        /*
        Flux<String> fruitFlux = Flux.just("apple", "orange", "banana", "kiwi", "strawberry");
        Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);
        bufferedFlux.subscribe(f -> System.out.println(f.get(0)));
        */
        // podemos combinarlo con flatMap para que cada coleccion se procese de forma paralela
        /*Flux.just("apple", "orange", "banana", "kiwi", "strawberry")
            .buffer(3)
            .flatMap(x ->
                Flux.fromIterable(x) //creamos Flux de List
                    .map(y -> y.toUpperCase())
                    .subscribeOn(Schedulers.parallel())
                    .log()//en cada sub-Flux
            ).subscribe();
        */
        
        //an even more interesting way of collectiong items emitted by a Flux is with collectMap()
        //toma los items y crea un Mono devolviendo un Map con los items. Crea un buffer con todos
        // sin embargo, collectMap() es sincronico, espera a recibir todo y genera un Map
        /*Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
        Mono<Map<Character, String>> animalMapMono = animalFlux.collectMap(a -> a.charAt(0));
        animalMapMono.subscribe(map -> {
            System.out.println("Size: "+map.size());
            if (map.get('a').equals("aardvark")) {
                System.out.println("AARDVARK!");
            }
            if (map.get('e').equals("eagle")) {
                System.out.println("EAGLE!");
            }
            if (map.get('k').equals("kangaroo")) {
                System.out.println("KANGAROO!");
            }
        });*/
        // collectMap genera un nuevo Mono que emite un HashMap
        // ejemplo formando un hashMap de forma ASYNC!
        /*
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5);
        flux.flatMap(value ->
                Mono.just(value)
                    .delayElement(Duration.ofSeconds(2)) //cada item durará 2 segundos
                    .map(mappedValue -> mappedValue  * 2) //duplicamos el valor de cada uno
                    .log() //para ver que lo hace en Threads paralelos
            )
            //collectMap es sync pero metemos async en cada item antes
            .collectMap(data -> data / 2)//key(value/2) = value // seteamos key
            .subscribe(result -> System.out.println("Result: " + result));*/
        //el camino seria: HashMap(Mono(Flux(Mono,Mono,Mono...)))
        // Tenemos un Flux que emite ITEM => eso se maneja con un flatMap, aplicando un filtro asyncronico
        // donde cada item se transforma en un Mono y será Suscripto por el flatMap
        // Ese Flux, con el filtro flatMap, devolverá los ITEM de forma ASYNC
        // esos ITEM serán tomados por collectMap, y al finalizar todos, formará un nuevo Mono
        // ese Mono es subscripto para iniciar toda la cadena
        
        //***
        
        // 4) LOGIC OPERATIONS
        
        // con all()/any() chekeamos que TODOS o NINGUNO de los items cumplan una condicion
        /*
        Flux<String> animalFlux = Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo");
        animalFlux.all(a -> a.contains("a")).log().subscribe(x -> System.out.println(x));
        animalFlux.all(a -> a.contains("k")).log().subscribe(x -> System.out.println(x));
        animalFlux.any(a -> a.contains("t")).log().subscribe(x -> System.out.println(x));
        */
        
    	
    	
        return "Project Reactor";
    }
    
    /*
    private static class Player {
        private final String firstName;
        private final String lastName;
        public Player(String name, String last) {
            this.firstName = name;
            this.lastName = last;
        }
        public String getFirstName() {
            return firstName;
        }
        public String getLastName() {
            return lastName;
        }
    }
   	*/
    	
    	
    	
    
}
