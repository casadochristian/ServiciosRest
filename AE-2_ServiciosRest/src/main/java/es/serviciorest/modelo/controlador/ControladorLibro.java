package es.serviciorest.modelo.controlador;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.serviciorest.modelo.entidad.Libro;
import es.serviciorest.modelo.persistencia.DaoLibro;

//Creamos un CRUD en el controlador usando el daoLibro que contiene nuestra base de datos en memoria
@RestController
public class ControladorLibro {
	
	//Usamos la anotacion autowired para hacer la inyeccion de dependencia dentro del contexto
	//de Spring. Esto es posible porque DaoLibro tiene la anotacion @Component
	@Autowired
	private DaoLibro daoLibro;
	
	/**
	 * ALTA - Configuramos un endpoint para dar de alta un libro mediante el método POST. La información
	 * viajará a través de JSON. Será nuestro servidor el que asigne el ID para que no haya ID repetidas.
	 * 
	 * @param l el libro que vamos a dar de alta
	 * @return la persona creada junto con el codigo 201 CREATED
	 */
	@PostMapping(path="libros",consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Libro> altaLibro(@RequestBody Libro l) {
		System.out.println("altaLibro: objeto Libro: " + l);
		daoLibro.altaLibro(l);
		return new ResponseEntity<Libro>(l, HttpStatus.CREATED);//201 CREATED
	}
	
	/**
	 * BAJA - Configuramos un endpoint para dar de baja un libro mediante el método DELETE. 
	 * Obtenemos el id del libro mediante @PathVariable junto con la anotación Path, metiendo el id entre llaves
	 * 
	 * @param id del libro que queremos eliminar de la lista
	 * @return si todo va bien devolvemos el codigo 200 OK y el libro que hemos borrado
	 * en caso de no encontrar la persona devolveremos un 404 NOT FOUND
	 */
	@DeleteMapping(path="libros/{id}")
	public ResponseEntity<Libro> borrarLibro(@PathVariable("id") int id) {
		System.out.println("ID a borrar: " + id);
		Libro l = daoLibro.bajaLibro(id);
		if(l != null) {
			return new ResponseEntity<Libro>(l, HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
	
	/**
	 * MODIFICAR - Configuramos un endpoint para modificar un libro mediante el método PUT. La información
	 * viajará a través de JSON. Usamos @PathVariable para obtener la id del libro a modificar y @RequestBody
	 * para la estructura del libro
	 * 
	 * @param id del libro que queremos modificr
	 * @param l el libro con la onformación que vamos a modificar
	 * @return si todo va bien devolvemos el libro modificado y un codigo 200 OK, en caso de que la id del
	 * libro a modificar no exista, devolvemos un 404 NOT FOUND
	 */
	@PutMapping(path="libros/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Libro> modificarPersona(
			@PathVariable("id") int id, 
			@RequestBody Libro l) {
		System.out.println("ID a modificar: " + id);
		System.out.println("Datos a modificar: " + l);
		l.setId(id);
		Libro lmodificar = daoLibro.modificarLibro(l);
		if(lmodificar != null) {
			return new ResponseEntity<Libro>(l, HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
	
	/**
	 * OBTENER - Configuramos un endpoint para obtener un libro a través de si id mediante el método GET. 
     * la información viajará a través de JSON. Usamos @PathVariable para recoger la id
	 * 
	 * @param id del libro que queremos consultar
	 * @return el libro que queremos consultar junto con un codigo 200 OK en caso de encontrar el libro. En caso
	 * contrario devolvemos un 404 NOT FOUND
	 */
	@GetMapping(path="libros/{id}",produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<Libro> obtenerLibro(@PathVariable("id") int id) {
		System.out.println("Buscando libro con id: " + id);
		Libro l = daoLibro.obtenerLibro(id);
		if(l != null) {
			return new ResponseEntity<Libro>(l, HttpStatus.OK);//200 OK
		}else {
			return new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);//404 NOT FOUND
		}
	}
	
	/**
	 * LISTAR - Configuramos un endpoint para obtener la lista completa de los libros almacenados en el dao mediante el método GET. 
     * la información viajará a traves de JSON
	 * 
	 * @return la lista completa de libros que tengamos en memoria en nuestro dao
	 */
	@GetMapping(path="libros",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Libro>> listarLibros() {
		List<Libro> listarLibros = daoLibro.listarLibros();
		
		System.out.println(listarLibros);
		return new ResponseEntity<List<Libro>>(listarLibros,HttpStatus.OK);//200 OK
	}
}
