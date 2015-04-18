package ast;

public class NodoLeer extends NodoBase {
	private String id;
	private Integer pos;

	public NodoLeer(String identificador) {
		super();
		this.id = identificador;
		this.setPos(0);
	}
	public NodoLeer(String identificador,Integer pos) {
		super();
		this.id = identificador;
		this.setPos(pos);
	}

	public NodoLeer() {
		super();
		id="";
	}

	public String getIdentificador() {
		return id;
	}

	public void setExpresion(String identificador) {
		this.id = identificador;
	}
	public Integer getPos() {
		return pos;
	}
	public void setPos(Integer pos) {
		this.pos = pos;
	}

}
