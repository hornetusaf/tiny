package ast;

public class NodoVector extends NodoBase{

	private String id;
	private tipoDato tipo;
	private Integer tam;
	
	public NodoVector()	{
		super();
		this.id = null;
		this.tipo = null;
		this.tam = 0;		
	}
	
	public NodoVector(String id, tipoDato tipo, Integer tam)	{
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

	public tipoDato getTipo() {
		return tipo;
	}

	public void setTipo(tipoDato tipo) {
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
