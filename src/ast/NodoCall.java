package ast;
public class NodoCall extends NodoBase {
 
	private String nombre;
 	private NodoBase ExI;
 	

	public NodoCall(NodoBase exI, NodoBase exD, String nombreFuncion) {
 		super();
		this.ExI = exI;
		this.setHermanoDerecha(exD);
		this.nombre=nombreFuncion;
		
 	}
	
	public NodoCall(NodoBase exI, NodoBase exD) {
		super();		
		this.ExI = exI;
		this.setHermanoDerecha(exD);
		this.nombre=null;
	}	
	
	public NodoCall(String nombreFuncion) {
 		super();		
		this.nombre=nombreFuncion;
		this.ExI = null;
		this.setHermanoDerecha(null);
 	}
	
 	public NodoCall() {
 		super();
		this.ExI = null;
		this.setHermanoDerecha(null);
		this.nombre=null;
 	}
	
 	public NodoBase getExI() {
		return ExI;
	}
		 		
 	public void setExI(NodoBase exI) {
		ExI = exI;
 	}

	public String getNombreFuncion() {
		return nombre;
	}

	public void setNombreFuncion(String nombreFuncion) {
		nombre = nombreFuncion;
	}	
}