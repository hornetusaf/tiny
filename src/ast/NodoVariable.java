package ast;

public class NodoVariable extends NodoBase{
	
	private String id;
	private tipoOp TipoOperador;
	private NodoBase partev;
	private tipoDato tipo;
	private Integer tam;

	public NodoVariable(String id,tipoOp to,NodoBase partev) {
		super();//si
		this.id = id;
		this.TipoOperador=to;
		this.partev= partev;		
	}
	public NodoVariable(String id,Integer tam,tipoOp to,NodoBase partev) {
		super();//si
		this.id = id;
		this.tam=tam;
		this.TipoOperador=to;
		this.partev= partev;		
	}
	public NodoVariable(tipoDato tipo,NodoBase partev) {
		super();
		this.tipo = tipo;
		this.partev=partev;
	}	
	
	public NodoVariable(String id,tipoOp to) {
		super();
		this.id = id;
		this.TipoOperador=to;
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
		this.partev = null;
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

	public NodoBase getPartev() {
		return partev;
	}
	
	public void setPartev(NodoBase partev) {
		this.partev = partev;
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
