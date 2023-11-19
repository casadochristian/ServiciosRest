package es.serviciorest.cliente;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import es.serviciorest.cliente.entidad.Libro;
import es.serviciorest.cliente.servicio.ServicioProxyLibro;

@SpringBootApplication
public class Ae2ServiciosRestClienteApplication implements CommandLineRunner {
	
	//Inyectamos el objeto necesario para acceder al servicio REST
	@Autowired
	private ServicioProxyLibro spl;
	
	//Inyectamos nuestro propio contexto para poder acceder al mismo
	@Autowired
	private ApplicationContext context;
	
	//Damos de alta el método que nos hará posible hacer las peticiones HTTP a nuestro servicio REST
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(Ae2ServiciosRestClienteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("****** Arrancando el cliente REST ******");		
        //Creamos un do while infinito hasta que nos pulsen la opción de salir de la aplicación
		boolean continuar = true;        
		do {
			//declaramos las variables que necesitamos y creamos el menú
			Scanner sc = new Scanner(System.in);
			int id;
			String titulo ="";
	        String editorial = "";
	        double nota;
	        
            System.out.println("-----------------------------");
            System.out.println("Elige una opción: ");
            System.out.println("1. Dar de alta un libro: ");
            System.out.println("2. Dar de baja un libro por ID: ");
            System.out.println("3. Modificar un libro por ID: ");
            System.out.println("4. Obtener un libro por ID: ");
            System.out.println("5. Listar todos los libros: ");
            System.out.println("6. Salir");
            System.out.println("-----------------------------");                        
            
            //Creamos un switch con las opciones del menú y recogemos por escaner la información necesaria en las variables correspondientes
            String opcion = sc.nextLine();
            switch(opcion) {
            case "1":
            	id= 0;
            	System.out.println("Dando de alta un nuevo libro");
            	System.out.println("Introduce el título");
            	titulo = sc.nextLine();
            	System.out.println("Introduce la editorial");
            	editorial = sc.nextLine();
            	System.out.println("Introduce la nota");
            	nota = sc.nextDouble();
            	
            	Libro nuevoLibro = new Libro(id, titulo, editorial, nota);
            	spl.alta(nuevoLibro);
            	System.out.println("Libro añadido");
            	break;
            case "2":
            	System.out.println("Dando de baja un libro por ID");
            	System.out.println("Introduce el ID del libro a eliminar: ");
            	id = sc.nextInt();
            	spl.baja(id);
            	System.out.println("El libro con el id " + id + " ha sido eliminado");
            	break;
            case "3":
            	System.out.println("Modificando un libro por ID");
            	System.out.println("Introduce el ID del libro a modificar");
            	id = sc.nextInt();
            	sc.nextLine();
            	System.out.println("Introduce el nuevo título:");
            	titulo = sc.nextLine();
            	System.out.println("Introduce la nueva editorial:");
            	editorial = sc.nextLine();
            	System.out.println("Introduce la nueva nota:");
            	nota = sc.nextDouble();
            	
            	Libro libroModificado = new Libro(id, titulo, editorial, nota);
            	spl.modificar(libroModificado);
            	System.out.println("El libro con el id " + id + " ha sido modificado");
            	break;
            case "4":
            	System.out.println("Obteniendo un libro por ID");
            	System.out.println("Introduce el ID del libro a obtener");
            	id = sc.nextInt();
            	System.out.println("Mostrando el libro con el id " + id);
            	System.out.println(spl.obtener(id));
            	break;
            case "5":
            	System.out.println("Mostrando la lista de todos los libros");
            	List<Libro> listaLibros = spl.listar();
            	
            	listaLibros.forEach((v) -> System.out.println(v));
            	break;
            case "6":
            	System.out.println("Saliendo de la aplicacion");
            	continuar = false;
            	break;
            default:
            	System.out.println("Opción no válida. Introduce una opción entre el 1 y el 6");
            	}
		}while(continuar);
		
		//solo podemos salir del bucle si nos pulsan la opción de salir, de manera que una vez que salgamos del bucle finalizamos la aplicación
		pararAplicacion();		
	}
	
	//Método que finaliza nuestra aplicación
	public void pararAplicacion() {				
		SpringApplication.exit(context, () -> 0);
	}

}
