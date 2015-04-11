package ast;

public class NodoVector extends NodoBase{

	private String id;
	private String tipo;
	private Integer tam;
	
	public NodoVector()	{
		super();
		this.id = null;
		this.tipo = null;
		this.tam = 0;		
	}
	
	public NodoVector(String id, String tipo, Integer tam)	{
		super();
		this.id = id;
		this.tipo = tipo;
		this.tam = tam;		
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

	public int getTam() {
		return tam;
	}

<<<<<<< HEAD
	public void setTam(Integer tamaño) {
=======
	public void setTam(Integer tam) {
>>>>>>> origin/master
		this.tam = tam;
	}	
}
