package ast;
public class NodoCall extends NodoBase {
 
	private String nombre;
 	private NodoBase ExI;

	public NodoCall(NodoBase exI, String nombreFuncion) {
 		super();
		this.ExI = exI;
		this.nombre=nombreFuncion;
 	}
	
	
 	public NodoBase getExI() {
 		return ExI;
 	}
	
 	public void setExI(NodoBase exI) {
		this.ExI = exI;
 	}

	public String getNombreFuncion() {
		return nombre;
	}

	public void setNombreFuncion(String nombreFuncion) {
		this.nombre = nombreFuncion;
	}	
}