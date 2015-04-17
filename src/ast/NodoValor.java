package ast;

public class NodoValor extends NodoBase {
	private Integer valor;
	private Boolean _valor;

	public NodoValor(Integer valor) {
		super();
		this.valor = valor;
	}
	public NodoValor(Boolean valor) {
		super();
		this._valor = valor;
	}

	public NodoValor() {
		super();
	}
	
	public Boolean get_Valor() {
		return _valor;
	}
	public Integer getValor() {
		return valor;
	}

}
