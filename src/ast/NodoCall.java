package ast;
public class NodoCall extends NodoBase {
 
	private String nombre;
 	private NodoBase ExI;
 	private NodoBase ExD;
 	

	public NodoCall(NodoBase exI, NodoBase exD, String nombreFuncion) {
 		super();
		this.ExI = exI;
		this.ExD = exD;
		this.nombre=nombreFuncion;
 	}
	
	public NodoCall(NodoBase exI, NodoBase exD) {
		super();
		this.setHermanoDerecha(exI);
		this.ExD = exD;
		this.nombre=null;
	}	
	
	public NodoCall(String nombreFuncion) {
 		super();
		this.ExI = null;
		this.ExD = null;
		this.nombre=nombreFuncion;
 	}
	
 	public NodoCall() {
 		super();
		this.ExI = null;
		this.ExD = null;
		this.nombre="";
 	}
	
 	public NodoBase getExI() {
		return getHermanoDerecha();
	}
	
	public void setExI(NodoBase partev) {
		this.setHermanoDerecha(partev);
	}
	
 	public NodoBase getExD() {
 		return ExD;
 	}
	
 	public void setExD(NodoBase exD) {
		this.ExD = exD;
 	}

	public String getNombreFuncion() {
		return nombre;
	}

	public void setNombreFuncion(String nombreFuncion) {
		this.nombre = nombreFuncion;
	}	
}