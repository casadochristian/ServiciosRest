package es.serviciorest.modelo.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import es.serviciorest.modelo.entidad.Libro;

@Component
public class DaoLibro {
	
	public List<Libro> listaLibros;
	//usamos un contador para el id para que no se repita al añadir un nuevo libro
	public int contador;
	
	//Creamos un Dao que contenga una lista con los 5 libros predeterminados que tendrá nuestro servicio REST	
	public DaoLibro() {
		System.out.println("DaoLibro -> Cargando la lista de libros");
		listaLibros = new ArrayList<Libro>();
		Libro l1 = new Libro(contador++, "Los Pilares de la Tierra", "Plaza & Janes", 9);// ID:0
		Libro l2 = new Libro(contador++, "Yo Mato", "Grijalbo", 6.8);// ID:1
		Libro l3 = new Libro(contador++, "La Catedral del Mar", "Grijalbo", 8.5);// ID:2
		Libro l4 = new Libro(contador++, "1984", "Austral", 9);// ID:3
		Libro l5 = new Libro(contador++, "Cien años de soledad", "Cátedra", 9.5);// ID:4
		listaLibros.add(l1);
		listaLibros.add(l2);
		listaLibros.add(l3);
		listaLibros.add(l4);
		listaLibros.add(l5);		
	}
	
	//Método que añade un libro y le da un id que continue la lista
	//@param l el libro que queremos añadir
		public void altaLibro(Libro l) {
			l.setId(contador++);
			listaLibros.add(l);
		}
		
	/**
	 * Método que borra un libro a partir de su ID
	 * 
	 * @param numero de id que equivale a la posicion a borrar
	 * @return devolvemos el libro que hayamos borrado o null en caso de que no exista
	 */
		
	public Libro bajaLibro(int posicion) {
		try {
			return listaLibros.remove(posicion);
		}catch (IndexOutOfBoundsException iobe) {
			System.out.println("delete -> El id del libro no existe");
			return null;
		}
	}
	
	/**
	 * Método que modifica un libro de la lista por ID
	 * 
	 * @param l contiene los datos que queremos modificar y con p.getID() seleccionamos el id que queremos modificar
	 * @return el libro modificado en caso de que exista o null que caso contrario
	 */
	
	public Libro modificarLibro(Libro l) {
		try {
			Libro lAux = listaLibros.get(l.getId());
			lAux.setTitulo(l.getTitulo());
			lAux.setEditorial(l.getEditorial());
			lAux.setNota(l.getNota());
			return lAux;
		}catch (IndexOutOfBoundsException iobe) {
			System.out.println("update -> El id del libro no existe");
			return null;
		}
	}
	
	/**
	 * Método que devuelve el libro solicitado mediante id
	 * 
	 * @param id contiene el id del libro que queremos obtener
	 * @return una lista con el libro al que corresponda la id demandada
	 */
	//Método que devuelve un libro a partir de su ID
	public Libro obtenerLibro(int id) {
		try {
			return listaLibros.get(id);
		}catch(IndexOutOfBoundsException iobe) {
			System.out.println("El id del libro no existe");
			return null;
		}
	}
	
	/**
	 * Método que devuelve todos los libros de la lista
	 * 
	 * @return la lista con los todos los libros
	 */
	public List<Libro> listarLibros(){
		return listaLibros;
	}
	
	
	


}
