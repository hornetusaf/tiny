package ast;

public class NodoValor extends NodoBase {
	private Integer valor;
	private Boolean _valor;

	public NodoValor(Integer valor) {
		super();
		this.valor = valor;
		this._valor=null;
	}
	public NodoValor(Boolean valor) {
		super();
		this._valor = valor;
		this.valor=null;
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
	public Integer getValorB() {
		if(_valor)
			return 1;
		return 0;
	}

}