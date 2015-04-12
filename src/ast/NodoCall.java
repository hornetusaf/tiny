package ast;

public class NodoCall extends NodoBase {

	private NodoBase ExI;
	private NodoBase ExD;
	
	public NodoCall(NodoBase exI, NodoBase exD) {
		super();
		ExI = exI;
		ExD = exD;
	}
	public NodoCall(NodoBase exI) {
		super();
		ExI = exI;
		ExD = null;
	}
	public NodoCall() {
		super();
		ExI = null;
		ExD = null;
	}
	public NodoBase getExI() {
		return ExI;
	}
	public void setExI(NodoBase exI) {
		ExI = exI;
	}
	public NodoBase getExD() {
		return ExD;
	}
	public void setExD(NodoBase exD) {
		ExD = exD;
	}
	
	
}
