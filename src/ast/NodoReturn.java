package ast;

public class NodoReturn extends NodoBase {
	private NodoBase id;	
	
	public NodoReturn(NodoBase id) {
		super();
		this.id = id;		
	}
	public NodoReturn() {
		super();
		this.id = null;		
	}
	public NodoBase getId() {
		return id;
	}
	public void setId(NodoBase id) {
		this.id = id;
	}
}
	