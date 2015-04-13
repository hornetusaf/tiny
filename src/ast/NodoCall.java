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
		this.ExI = exI;
		this.ExD = exD;
		this.nombre="";
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
		return ExI;
	}
	
	public void setExI(NodoBase exI) {
		this.ExI = exI;
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
