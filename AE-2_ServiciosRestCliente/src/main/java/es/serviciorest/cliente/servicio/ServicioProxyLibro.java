package es.serviciorest.cliente.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import es.serviciorest.cliente.entidad.Libro;

@Service
public class ServicioProxyLibro {
	
	//La URL base del servicio REST de libros
		public static final String URL = "http://localhost:8080/libros/";
		
	//Inyectamos el objeto de tipo RestTemplate que nos ayudará
	//a hacer las peticiones HTTP al servicio REST
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * Método que da de alta un libro en el servicio REST
	 * 
	 * @param l el libro que vamos a dar de alta
	 * @return el libro con el id actualizado que se ha dado de alta en el
	 * servicio REST. Null en caso de que no se haya podido dar de alta
	 */
	public Libro alta(Libro l){
		try {
			ResponseEntity<Libro> re = restTemplate.postForEntity(URL, l, Libro.class);
			System.out.println("alta -> Codigo de respuesta " + re.getStatusCode());
			return re.getBody();
		} catch (HttpClientErrorException e) {
			System.out.println("alta -> El libro NO se ha dado de alta, id: " + l);
		    System.out.println("alta -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
	}
	
	/** 
	 * Método que borra un libro en el servicio REST
	 *  
	 * @param id el id del libro que queremos borrar.
	 * @return true en caso de que se haya podido borrar el libro. 
	 * false en caso contrario.
	 */
	public boolean baja(int id){
		try {
			restTemplate.delete(URL + id);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("borrar -> El libro no se ha borrado, id: " + id);
		    System.out.println("borrar -> Codigo de respuesta: " + e.getStatusCode());
		    return false;
		}
	}
	
	/** 
	 * Método que modifica un libro en el servicio REST
	 * 
	 * @param l el libro que queremos modificar, no se pedirá el id porque 
	 * lo asignará el servidor para evitar id duplicados
	 * @return true en caso de que se haya podido modificar la persona. 
	 * false en caso contrario.
	 */
	public boolean modificar(Libro l){
		try {
			restTemplate.put(URL + l.getId(), l, Libro.class);
			return true;
		} catch (HttpClientErrorException e) {
			System.out.println("modificar -> El libro no se ha modificado, id: " + l.getId());
		    System.out.println("modificar -> Codigo de respuesta: " + e.getStatusCode());
		    return false;
		}
	}	
	
	/**
	 * Método que obtiene un libro a partir de un id
	 * En caso de que el id no exista arrojaria una excepción que se captura
	 * para sacar el codigo de respuesta
	 * 
	 * @param id del libro que queremos obtener
	 * @return retorna el libro que estamos buscando, null en caso de que el libro
	 * no se encuentre en el servidor (devuelva 404) o haya habido algún
	 * otro error.
	 */
	public Libro obtener(int id){
		try {
			ResponseEntity<Libro> re = restTemplate.getForEntity(URL + id, Libro.class);
			HttpStatus hs= re.getStatusCode();
			if(hs == HttpStatus.OK) {
				return re.getBody();
			}else {
				System.out.println("obtener -> Respuesta no contemplada");
				return null;
			}
		}catch (HttpClientErrorException e) {//Errores 4XX
			System.out.println("obtener -> El libro NO se ha encontrado, id: " + id);
		    System.out.println("obtener -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
	}
	
	/**
	 * Metodo que devuelve todos los libros
	 * 
	 * @return el listado de todos los libros de la lista
	 */
	public List<Libro> listar(){		
		try {
			ResponseEntity<Libro[]> re =
					  restTemplate.getForEntity(URL,Libro[].class);
			Libro[] arrayLibros = re.getBody();
			return Arrays.asList(arrayLibros);//convertimos el array en un ArrayList
		} catch (HttpClientErrorException e) {
			System.out.println("listar -> Error al obtener la lista de libros");
		    System.out.println("listar -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
	}

}
