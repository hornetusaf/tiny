package ast;

public class NodoVector extends NodoBase{

	private String id;
	private String tipo;
	private Integer tama�o;
	
	public NodoVector()	{
		super();
		this.id = null;
		this.tipo = null;
		this.tama�o = 0;		
	}
	
	public NodoVector(String id, String tipo, Integer tama�o)	{
		super();
		this.id = id;
		this.tipo = tipo;
		this.tama�o = tama�o;		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTama�o() {
		return tama�o;
	}

	public void setTama�o(Integer tama�o) {
		this.tama�o = tama�o;
	}	
}
