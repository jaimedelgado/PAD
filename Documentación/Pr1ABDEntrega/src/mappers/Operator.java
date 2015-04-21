package mappers;


/**
 * Representa un operador de comparación. Se utiliza en QueryCondition.
 * 
 * @author Manuel Montenegro
 *
 */
public enum Operator {
	EQ(" = "), LE(" <= "), LT(" < "), GE(" >= "), GT(" > "), NEQ(" != "), LIKE(" % ");
	
	private String representacion;
	
	private Operator(String string){
		representacion = string;
	}
	
	/*
	 * Sería conveniente añadir un atributo a cada enum con la representación
	 * de cada operador (en forma de cadena)
	 */
	
	@Override
	public String toString() {
		return representacion;
	}
}
