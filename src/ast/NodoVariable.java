package ast;

public class NodoVariable extends NodoBase{
	
	private String id;
	private tipoOp TipoOperador;	
	private tipoDato tipo;
	private Integer tam;
	
	
	public NodoVariable(String id,tipoOp to,NodoBase partev) {
		super();//si
		this.id = id;
		this.TipoOperador=to;
		this.tam=0;
		this.setHermanoDerecha(partev);		
	}
	public NodoVariable(tipoDato td, String id,tipoOp to,NodoBase partev) {
		super();//si
		this.tipo= td;
		this.id = id;
		this.TipoOperador=to;
		this.tam=0;
		this.setHermanoDerecha(partev);		
	}
	public NodoVariable(tipoDato td, String id) {
		super();//si
		this.tipo= td;
		this.id = id;
		this.tam=0;
	}
	public NodoVariable(String id,Integer tam,tipoOp to,NodoBase partev) {
		super();//si
		this.id = id;
		this.tam=tam;
		this.TipoOperador=to;
		this.setHermanoDerecha(partev);
	}
	public NodoVariable(tipoDato tipo,NodoBase partev) {
		super();
		this.tipo = tipo;
		this.setHermanoDerecha(partev);
		this.tam=0;
	}	
	
	public NodoVariable(String id,tipoOp to) {
		super();
		this.id = id;
		this.TipoOperador=to;
		this.tam=0;
	}
	
	public NodoVariable(String id,Integer tam,tipoOp to) {
		super();
		this.id = id;
		this.setTam(tam);
		this.TipoOperador=to;
	}
	
	public NodoVariable() {
		super();
		this.id = "";
		this.setHermanoDerecha(null);
		this.tipo = null;
		this.setTam(0);
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
		return getHermanoDerecha();
	}
	
	public void setPartev(NodoBase partev) {
		this.setHermanoDerecha(partev);
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