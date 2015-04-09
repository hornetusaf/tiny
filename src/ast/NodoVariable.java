package ast;

public class NodoVariable extends NodoBase{
	
	private String id;
	private NodoBase partev;	

	public NodoVariable(String id) {
		super();
		this.id = id;
		this.partev = null;
	}
	public NodoVariable(NodoBase partev) {
		super();
		this.id = null;
		this.partev = partev;
	}
	
	public NodoVariable(String id, NodoBase partev) {
		super();
		this.id = id;
		this.partev = partev;
	}
	
	public NodoVariable() {
		super();
		this.id = null;
		this.partev = null;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public NodoBase getPartev() {
		return partev;
	}
	
	public void setPartev(NodoBase partev) {
		this.partev = partev;
	}	
}
