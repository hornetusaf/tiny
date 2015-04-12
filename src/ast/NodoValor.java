package ast;

public class NodoValor extends NodoBase {
	private Integer valor;
	private boolean _valor;

	public NodoValor(Integer valor) {
		super();
		this.valor = valor;
	}
	public NodoValor(boolean valor) {
		super();
		this._valor = valor;
	}

	public NodoValor() {
		super();
	}
	
	public boolean get_Valor() {
		return _valor;
	}
	public Integer getValor() {
		return valor;
	}

}
