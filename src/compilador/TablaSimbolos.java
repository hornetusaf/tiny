package compilador;

import java.util.*;


import ast.NodoAsignacion;
import ast.NodoBase;
import ast.NodoEscribir;
import ast.NodoIdentificador;
import ast.NodoIf;
import ast.NodoOperacion;
import ast.NodoProcedimiento;
import ast.NodoRepeat;
import ast.NodoReturn;
import ast.NodoValor;
import ast.NodoVariable;
import ast.NodoFor;
import ast.NodoLeer;
import ast.NodoCall;
import ast.tipoDato;
import ast.tipoFuncion;
import ast.*;
public class TablaSimbolos {
	private HashMap<String, RegistroSimbolo> tabla;
	private int direccion; // Contador de las localidades de memoria asignadas a
							// la tabla
	private int parametros;

	public TablaSimbolos() {
		super();
		tabla = new HashMap<String, RegistroSimbolo>();
		direccion = 0;		
	}

	public void cargarTabla(NodoBase raiz,String id) {

		while (raiz != null) {
			//System.out.println(raiz);
			/*if (raiz instanceof NodoCall){
				RegistroSimbolo s=BuscarSimbolo((((NodoCall)raiz).getNombreFuncion()));
				//System.out.println("aquiii "+id+".@"+((NodoAsignacion)raiz).getIdentificador());
				if(s!=null ){
					
				}
				else{
						System.out.println("funcion no declarada "+id+"."+(((NodoCall)raiz).getNombreFuncion()));
				}
				
			}
			else*/ if (raiz instanceof NodoProcedimiento) {
															
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
				//System.out.println("aquiii "+id+".@"+((NodoAsignacion)raiz).getIdentificador());
				if(s!=null ){
					boolean x= ValidarInicializacion(((NodoAsignacion)raiz).getExpresion(),id);
					//System.out.println(x);
					if(s.isInicializado()!=true && x==true){
						s.setInicializado(x);
						//System.out.println("aquiii cambia a true");
					}
				}
				else{
					RegistroSimbolo sb=BuscarSimbolo(id+".@"+((NodoAsignacion)raiz).getIdentificador());
					if(sb!=null ){
						//sb.setInicializado(true);
					}else
						System.out.println("Variable no declarada "+id+"."+((NodoAsignacion)raiz).getIdentificador());
				}
				cargarTabla(((NodoAsignacion) raiz).getExpresion(),id);
				RecorrerOperacion(((NodoAsignacion)raiz).getExpresion(),null,((NodoAsignacion)raiz).getIdentificador());
			}
			
			if (raiz instanceof  NodoIf){
		    	//System.out.println("**Prueba IF**");
		    	cargarTabla(((NodoIf)raiz).getPrueba(),id);
		    	//System.out.println("**Then IF**");
		    	cargarTabla(((NodoIf)raiz).getParteThen(),id);
		    	if(((NodoIf)raiz).getParteElse()!=null){
		    		//System.out.println("**Else IF**");
		    		cargarTabla(((NodoIf)raiz).getParteElse(),id);
		    	}
		    }
			
			if (raiz instanceof  NodoOperacion){
				//System.out.println("entro nodo op");
				cargarTabla(((NodoOperacion)raiz).getOpIzquierdo(),id);
				cargarTabla(((NodoOperacion)raiz).getOpDerecho(),id);
			}
			if(raiz instanceof  NodoIdentificador){
				RegistroSimbolo s=BuscarSimbolo(id+"."+((NodoIdentificador)raiz).getNombre());
				//System.out.println("aquiii "+id+".@"+((NodoAsignacion)raiz).getIdentificador());
				if(s!=null ){
					//s.setInicializado(true);
				}
				else{
						System.out.println("Variable no declarada "+id+"."+((NodoIdentificador)raiz).getNombre());
				}
			}
			if(raiz instanceof  NodoEscribir){
				cargarTabla(((NodoEscribir)raiz).getExpresion(),id);				
			}
			if(raiz instanceof NodoLeer){
				RegistroSimbolo s=BuscarSimbolo(id+"."+(((NodoLeer)raiz).getIdentificador()));
				//System.out.println("aquiii "+id+".@"+((NodoAsignacion)raiz).getIdentificador());
				if(s!=null ){
					//s.setInicializado(true);
				}
				else{
						System.out.println("Variable no declarada "+id+"."+(((NodoLeer)raiz).getIdentificador()));
				}
			}
			if(raiz instanceof NodoReturn){
				RegistroSimbolo s=BuscarSimbolo(id+"."+(((NodoReturn)raiz).getId()));
				//System.out.println("aquiii "+id+".@"+((NodoAsignacion)raiz).getIdentificador());
				if(s!=null ){
					//s.setInicializado(true);
				}
				else{
						System.out.println("Variable no declarada "+id+"."+(((NodoReturn)raiz).getId()));
				}
				
			}
			
			
			
			raiz = raiz.getHermanoDerecha();
		}
	}	
	public void RecorrerFuncion(String id,NodoProcedimiento proc){
		NodoVariable a=(NodoVariable)proc.getPartev();
		while(a!=null)
		{
			InsertarVariable(id,a.getTipo(), "@"+a.getId(),a.getTam());
			a=(NodoVariable)a.getPartev();		
		}		
		NodoBase raiz = proc.getCuerpo();		
		cargarTabla(raiz, id);	
	}
	
	public void InsertarVariable(String id,tipoDato tipo,String identificador,int tam){
		RegistroSimbolo simbolo;
		if (tabla.containsKey(id+"."+identificador) || tabla.containsKey(id+".@"+identificador) ) {
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
							+ BuscarSimbolo(s).isInicializado());
						
		}
	}

	public int getDireccion(String Clave) {
		return BuscarSimbolo(Clave).getDireccionMemoria();
	}

	public boolean ValidarInicializacion (NodoBase raiz,String id){
		boolean retornar=false,retornar2=false;
		while(raiz!=null){
			if(raiz instanceof NodoOperacion){
				//System.out.println("validando");
				retornar=ValidarInicializacion(((NodoOperacion)raiz).getOpIzquierdo(), id);
				if(((NodoOperacion)raiz).getOpDerecho()!=null)
					retornar2=ValidarInicializacion(((NodoOperacion)raiz).getOpDerecho(), id);
				else
					retornar2=true;
			}
			if(raiz instanceof NodoValor){
				//System.out.println("entro a nodovalor");
				return true;
			}
			if(raiz instanceof NodoIdentificador){
					String s = ((NodoIdentificador)raiz).getNombre();
					//System.out.println("ID"+((NodoIdentificador)raiz).getNombre()+" es: "+BuscarSimbolo("@."+s).isInicializado());
					return BuscarSimbolo("@."+s).isInicializado();
			}
			
			raiz = raiz.getHermanoDerecha();
		}
		
		return (retornar && retornar2);
		
		
	}
	
	
	private void RecorrerOperacion(NodoBase operador, tipoOp to,String id_asignacion) {			
				
		while(operador != null)
		{
			if(operador instanceof NodoOperacion)
			{
				
				RecorrerOperacion(((NodoOperacion)operador).getOpIzquierdo(),((NodoOperacion)operador).getOperacion(),id_asignacion);				
				if(((NodoOperacion)operador).getOpDerecho()!=null)
					RecorrerOperacion(((NodoOperacion)operador).getOpDerecho(),((NodoOperacion)operador).getOperacion(),id_asignacion);
			}
			else if(operador instanceof NodoIdentificador)
			{
				RegistroSimbolo s = BuscarSimbolo("@."+id_asignacion);
				NodoIdentificador a = (NodoIdentificador)operador;
				RegistroSimbolo s2 = BuscarSimbolo("@."+a.getNombre());
				if(s!=null && s2!=null)	
				if(s.getTipo()!=s2.getTipo()){
					System.out.println("tipo invalido "+s.getTipo()+" "+s2.getTipo());	
				}
			}
			operador = operador.getHermanoDerecha();								
		}
		
	}
		 				
	
	
	/*
	 * TODO: 1. Crear lista con las lineas de codigo donde la variable es usada.
	 */
}