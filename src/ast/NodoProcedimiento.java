package ast;

public class NodoProcedimiento extends NodoBase {
	
<<<<<<< HEAD
	public NodoProcedimiento()
	{
		
=======
	private String id;
	private NodoBase partev;
	private NodoBase cuerpo;
	private tipoFuncion tipof;

	public  NodoProcedimiento(String id,NodoBase partev,NodoBase cuerpo,tipoFuncion tipof){
		super();
		this.id = id;
		this.partev = partev;
		this.cuerpo = cuerpo;
		this.tipof= tipof;
	}
	
	public NodoProcedimiento() {
		super();
		this.id = null;
		this.partev = null;
		this.tipof= null;
		this.cuerpo = null;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public tipoFuncion getTipo() {
		return tipof;
	}

	public void setTipo(tipoFuncion tipof) {
		this.tipof = tipof;
	}

	public NodoBase getPartev() {
		return partev;
	}
	
	public void setPartev(NodoBase partev) {
		this.partev = partev;
	}	
	public NodoBase getCuerpo() {
		return cuerpo;
	}
	
	public void setCuerpo(NodoBase cuerpo) {
		this.cuerpo = cuerpo;
>>>>>>> Silverio
	}
}
