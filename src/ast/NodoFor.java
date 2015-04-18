package ast;

public class NodoFor extends NodoBase{

	private NodoBase asi;
	private NodoBase asf;
	private NodoBase exp;
	private NodoBase cuerpo;
	
	public NodoFor() {
		super();
		this.asi = null;
		this.asf = null;
		this.exp = null;
		this.cuerpo = null;	
	}

	public NodoFor(NodoBase asi, NodoBase exp, NodoBase asf, NodoBase cuerpo) {
		super();
		this.asi = asi;
		this.exp = exp;
		this.asf = asf;
		this.cuerpo = cuerpo;
		
	}

	public NodoBase getAsi() {
		return asi;
	}

	public void setAsi(NodoBase asi) {
		this.asi = asi;
	}
	
	public NodoBase getAsf() {
		return asf;
	}

	public void setAsf(NodoBase asf) {
		this.asf = asf;
	}
	
	public NodoBase getExp() {
		return exp;
	}

	public void setExp(NodoBase exp) {
		this.exp = exp;
	}
	
	public NodoBase getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(NodoBase cuerpo) {
		this.cuerpo = cuerpo;
	}
}