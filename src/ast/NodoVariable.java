package ast;

public class NodoVariable extends NodoBase{
	
	private String id;
	private tipoOp TipoOperador;
	private tipoDato tipo;
	private Integer tam;
	
	public NodoVariable( tipoDato tipo) {
		super();//si
		this.tipo=tipo;
	}
	
	public NodoVariable(String id,tipoOp to) {
		super();//si
		this.id = id;
		this.TipoOperador=to;		
	}
	public NodoVariable(tipoDato td, String id,tipoOp to) {
		super();//si
		this.tipo= td;
		this.id = id;
		this.TipoOperador=to;		
	}
	public NodoVariable(tipoDato td, String id) {
		super();//si
		this.tipo= td;
		this.id = id;
	}
	public NodoVariable(String id,Integer tam,tipoOp to) {
		super();//si
		this.id = id;
		this.tam=tam;
		this.TipoOperador=to;		
	}

	
	public NodoVariable() {
		super();
		this.id = "";
		this.tipo = null;
		this.setTam(1);
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

	public tipoOp getTipoOperador() {
		return TipoOperador;
	}
	public void setTipoOperador(tipoOp tipoOperador) {
		TipoOperador = tipoOperador;
	}
	public Integer getTam() {
		return tam;
	}
	public void setTam(Integer tam) {
		this.tam = tam;
	}	
}