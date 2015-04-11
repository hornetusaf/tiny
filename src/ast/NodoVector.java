package ast;

public class NodoVector extends NodoBase{

	private String id;
	private String tipo;
	private Integer tamaño;
	
	public NodoVector()	{
		super();
		this.id = null;
		this.tipo = null;
		this.tamaño = 0;		
	}
	
	public NodoVector(String id, String tipo, Integer tamaño)	{
		super();
		this.id = id;
		this.tipo = tipo;
		this.tamaño = tamaño;		
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

	public int getTamaño() {
		return tamaño;
	}

	public void setTamaño(Integer tamaño) {
		this.tamaño = tamaño;
	}	
}
