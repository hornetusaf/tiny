package compilador;

import java.util.*;

import ast.*;
public class TablaSimbolos {
	private HashMap<String, RegistroSimbolo> tabla;
	private int direccion; // Contador de las localidades de memoria asignadas a

	public TablaSimbolos() {
		super();
		tabla = new HashMap<String, RegistroSimbolo>();
		direccion = 1;		
	}

	public void cargarTabla(NodoBase raiz,String id) {

		while (raiz != null) {
			//System.out.println(raiz);
			if (raiz instanceof NodoCall){
				
			}
			else if (raiz instanceof NodoProcedimiento) {
															
					RegistroSimbolo simbolo;
					if (tabla.containsKey(((NodoProcedimiento) raiz).getId())) {
						System.out.println("Funcion ya declarada "+((NodoProcedimiento) raiz).getId());
					} else {
						simbolo = new RegistroSimbolo(((NodoProcedimiento)raiz).getTipo(), direccion++);					
						tabla.put(((NodoProcedimiento) raiz).getId(), simbolo);
						RecorrerFuncion(((NodoProcedimiento) raiz).getId(),((NodoProcedimiento) raiz));
					}														
			}
			else if (raiz instanceof NodoVariable) {
				InsertarVariable(id,((NodoVariable) raiz).getTipo(),((NodoVariable) raiz).getId(),((NodoVariable) raiz).getTam());
			}
			else if (raiz instanceof NodoFor) {
				cargarTabla(((NodoFor) raiz).getAsi(),id);
				cargarTabla(((NodoFor) raiz).getExp(),id);
				cargarTabla(((NodoFor) raiz).getAsf(),id);
				if(((NodoFor) raiz).getCuerpo()!=null){
					cargarTabla(((NodoFor) raiz).getCuerpo(),id);
				}else
					System.out.println("ciclo infinito");
			} else if (raiz instanceof NodoRepeat) {
				cargarTabla(((NodoRepeat) raiz).getCuerpo(),id);
				cargarTabla(((NodoRepeat) raiz).getPrueba(),id);
			}
			
			else if (raiz instanceof NodoAsignacion)
			{
				RegistroSimbolo s=BuscarSimbolo(id+"."+((NodoAsignacion)raiz).getIdentificador());												
				if(s!=null){
					boolean x= ValidarInicializacion(((NodoAsignacion)raiz).getExpresion(),id);
					if(s.isInicializado()!=true && x==true){
						s.setInicializado(x);
						//System.out.println("aquiii cambia a true");
					}
				}				
				else{					
					System.out.println("Variable no declarada "+id+"."+((NodoAsignacion)raiz).getIdentificador());
				}
				cargarTabla(((NodoAsignacion) raiz).getExpresion(),id);			
			}
			
			else if (raiz instanceof  NodoIf){
		    	//System.out.println("**Prueba IF**");
		    	cargarTabla(((NodoIf)raiz).getPrueba(),id);
		    	//System.out.println("**Then IF**");
		    	cargarTabla(((NodoIf)raiz).getParteThen(),id);
		    	if(((NodoIf)raiz).getParteElse()!=null){
		    		//System.out.println("**Else IF**");
		    		cargarTabla(((NodoIf)raiz).getParteElse(),id);
		    	}
		    }
			
			else if (raiz instanceof  NodoOperacion){
				//System.out.println("entro nodo op");
				cargarTabla(((NodoOperacion)raiz).getOpIzquierdo(),id);
				cargarTabla(((NodoOperacion)raiz).getOpDerecho(),id);
				//RecorrerOperacion(((NodoOperacion)raiz).getOpIzquierdo(),null,((NodoAsignacion)raiz).getIdentificador());
			}
			else if(raiz instanceof  NodoIdentificador){
				RegistroSimbolo s=BuscarSimbolo(id+"."+((NodoIdentificador)raiz).getNombre());
				//System.out.println("aquiii "+id+".@"+((NodoAsignacion)raiz).getIdentificador());
				if(s!=null ){
					//s.setInicializado(true);
				}
				else{
						System.out.println("Variable no declarada "+id+"."+((NodoIdentificador)raiz).getNombre());
				}
			}
			else if(raiz instanceof  NodoEscribir){
				cargarTabla(((NodoEscribir)raiz).getExpresion(),id);				
			}
			else if(raiz instanceof NodoLeer){
				RegistroSimbolo s=BuscarSimbolo(id+"."+(((NodoLeer)raiz).getIdentificador()));
				//System.out.println("aquiii "+id+".@"+((NodoAsignacion)raiz).getIdentificador());
				if(s!=null ){
					s.setInicializado(true);
				}
				else{
						System.out.println("Variable no declarada "+id+"."+(((NodoLeer)raiz).getIdentificador()));
				}
			}
			else if(raiz instanceof NodoReturn){
				cargarTabla(((NodoReturn)raiz).getId(),id);
			
			}							
			raiz = raiz.getHermanoDerecha();
		}
	}	
	public void RecorrerFuncion(String id,NodoProcedimiento proc){
		NodoVariable a=(NodoVariable)proc.getPartev();
		int cont=0;
		while(a!=null)
		{
			cont++;
			InsertarVariable(id,a.getTipo(),a.getId(),a.getTam());
			a=(NodoVariable)a.getPartev();					
		}
		RegistroSimbolo s=BuscarSimbolo(id);
		s.setTamano(cont);
		NodoBase raiz = proc.getCuerpo();	
		cargarTabla(raiz, id);	
	}
	
	public void InsertarVariable(String id,tipoDato tipo,String identificador,int tam){
		RegistroSimbolo simbolo;
		if (tabla.containsKey(id+"."+identificador)) {
			System.out.println("Variable ya declarada "+id+"."+identificador);
		} else {			
			simbolo = new RegistroSimbolo(tipo,direccion++,tam);
			tabla.put(id+"."+identificador, simbolo);
			if(tam>0)
				direccion+=tam-1;
			
		}		
	}
	public RegistroSimbolo BuscarSimbolo(String Identificador){
		RegistroSimbolo simbolo=(RegistroSimbolo)tabla.get(Identificador);
		return simbolo;
		
	}

	// true es nuevo no existe se insertara, false ya existe NO se vuelve a
	public void ImprimirClaves() {
		System.out.println("*** Tabla de Simbolos ***");
		for (Iterator<String> it = tabla.keySet().iterator(); it.hasNext();) {
			String s = (String) it.next();
			System.out
					.println("Consegui Key: " + s + " con direccion: "
							+ BuscarSimbolo(s).getDireccionMemoria()
							+ " Tipo:  "
							+ BuscarSimbolo(s).getTipo()
							+ " Tamano: "
							+ BuscarSimbolo(s).getTamano()
							+ " Inicializado "
							+ BuscarSimbolo(s).isInicializado()
							+ " retorno activo "	);
						
		}
	}
	public int getDireccion(String Clave) {
		return BuscarSimbolo(Clave).getDireccionMemoria();
	}
	



public boolean ValidarInicializacion (NodoBase raiz,String id){
	boolean retornar=false,retornar2=false;
	while(raiz!=null){
		if(raiz instanceof NodoProcedimiento){
			//System.out.println("validando proc");
			retornar=ValidarInicializacion(((NodoProcedimiento)raiz).getCuerpo(),((NodoProcedimiento)raiz).getId());
		}
		else if(raiz instanceof NodoCall){
			RegistroSimbolo s =BuscarSimbolo(((NodoCall)raiz).getNombreFuncion());
			if(s!=null){
			retornar=true;
			retornar2=true;
			//System.out.println("entra ");
			}
			else{
			retornar=false;
			retornar2=false;
			}
		}
		else if(raiz instanceof NodoOperacion){
			//System.out.println("validando");
			retornar=ValidarInicializacion(((NodoOperacion)raiz).getOpIzquierdo(), id);
			if(((NodoOperacion)raiz).getOpDerecho()!=null)
				retornar2=ValidarInicializacion(((NodoOperacion)raiz).getOpDerecho(), id);
			else
				retornar2=true;
		}
		else if(raiz instanceof NodoValor){
			
			//System.out.println(id);
				return true;
				
			
		}
		else if(raiz instanceof NodoIdentificador){
			String s;
			//if(id==null)
				s = ((NodoIdentificador)raiz).getNombre();
				//System.out.println("ID"+((NodoIdentificador)raiz).getNombre()+" es: "+BuscarSimbolo("@."+s).isInicializado());
				//System.out.println(id);
				if(id=="@"){
					return BuscarSimbolo("@."+s).isInicializado();
				}
				else{
						//System.out.println("buscarr "+id+".@"+s);
					RegistroSimbolo simb2 = BuscarSimbolo(id+"."+s);
					
					if (simb2!=null){						
						}
					else
						return false;		
				}
		}
		
		raiz = raiz.getHermanoDerecha();
	}	
		return (retornar && retornar2);
}

public HashMap<String, RegistroSimbolo> getabla(){
	//System.out.println(tabla.get("f").getTamano());
	return tabla;
}
	/*
	 * TODO: 1. Crear lista con las lineas de codigo donde la variable es usada.
	 */
}