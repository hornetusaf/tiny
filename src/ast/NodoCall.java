package ast;
public class NodoCall extends NodoBase {
 
	private String nombre;
 	private NodoBase Ex;
 	

	public NodoCall(NodoBase ex, String nombreFuncion) {
 		super();
		this.Ex = ex;
		this.nombre=nombreFuncion;
 	}
	
 	public NodoCall() {
 		super();
		this.Ex = null;
		this.nombre=null;
 	}
	
 	public NodoBase getEx() {
 		return Ex;
 	}
	
 	public void setEx(NodoBase ex) {
		this.Ex = ex;
 	}
	public String getNombreFuncion() {
		return nombre;
	}

	public void setNombreFuncion(String nombreFuncion) {
		this.nombre = nombreFuncion;
	}	
}
