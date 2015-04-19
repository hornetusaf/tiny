package ast;

public class NodoReturn extends NodoBase {
	private String id;	
	private Integer valor;
	private Boolean _valor;
	
	public NodoReturn(String id) {
		super();
		this.id = id;		
		this.valor = null;
		this._valor = null;
	}
	public NodoReturn(Integer valor) {
		super();
		this.id = null;		
		this.valor = valor;
		this._valor = null;
	}
	public NodoReturn(Boolean _valor) {
		super();
		this.id = null;		
		this.valor = null;
		this._valor = _valor;
	}
	
	public NodoReturn() {
		super();
		this.id = null;		
		this.valor = null;
		this._valor = null;
	}
	
	public Integer getValor() {
		return valor;
	}
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	public Boolean get_valor() {
		return _valor;
	}
	public void set_valor(Boolean _valor) {
		this._valor = _valor;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}