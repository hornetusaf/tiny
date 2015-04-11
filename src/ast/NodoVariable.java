package ast;

public class NodoVariable extends NodoBase{
	
	private String id;
	private NodoBase partev;
	private tipoDato tipo;

	public NodoVariable(String id,tipoDato tipo) {
		super();
		this.id = id;
		this.partev = null;
		this.tipo= tipo;
	}
	public NodoVariable(NodoBase partev,tipoDato tipo) {
		super();
		this.id = null;
		this.partev = partev;
		this.tipo = tipo;
	}
	
	public NodoVariable(String id, NodoBase partev,tipoDato tipo) {
		super();
		this.id = id;
		this.partev = partev;
		this.tipo = tipo;
	}
	
	public NodoVariable() {
		super();
		this.id = null;
		this.partev = null;
		this.tipo = null;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public tipoDato getTipo() {
		return tipo;
	}

	public void setTipo(tipoDato tipo) {
		this.tipo = tipo;
	}

	public NodoBase getPartev() {
		return partev;
	}
	
	public void setPartev(NodoBase partev) {
		this.partev = partev;
	}	
}
