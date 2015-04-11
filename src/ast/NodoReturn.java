package ast;

public class NodoReturn extends NodoBase {
	private String id;
	private boolean _bool;
	
	public NodoReturn(String id, boolean _bool) {
		super();
		this.id = id;
		this._bool = _bool;
	}
	public NodoReturn() {
		super();
		this.id = "";
		this._bool = false;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean is_bool() {
		return _bool;
	}
	public void set_bool(boolean _bool) {
		this._bool = _bool;
	}
	

}
