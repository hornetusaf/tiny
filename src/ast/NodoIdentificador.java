package ast;

public class NodoIdentificador extends NodoBase {
	private String nombre;
	private Integer pos;

	public NodoIdentificador(String nombre) {
		super();
		this.nombre = nombre;
		this.setPos(0);
	}
	public NodoIdentificador(String nombre,Integer pos) {
		super();
		this.nombre = nombre;
		this.setPos(pos);
	}

	public NodoIdentificador() {
		super();
	}

	public String getNombre() {
		return nombre;
	}
	public Integer getPos() {
		return pos;
	}
	public void setPos(Integer pos) {
		this.pos = pos;
	}

}
