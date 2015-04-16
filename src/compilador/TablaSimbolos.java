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
import ast.NodoVariable;
import ast.NodoFor;
import ast.NodoLeer;
import ast.NodoCall;
import ast.tipoDato;
import ast.tipoFuncion;

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
			if (raiz instanceof NodoProcedimiento) {
															
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
			else if (raiz instanceof NodoIf) {				
				cargarTabla(((NodoIf) raiz).getParteThen(),id);
				if (((NodoIf) raiz).getParteElse() != null) {
					cargarTabla(((NodoIf) raiz).getParteElse(),id);
				}
			} else if (raiz instanceof NodoFor) {
				cargarTabla(((NodoFor) raiz).getCuerpo(),id);
			} else if (raiz instanceof NodoRepeat) {
				cargarTabla(((NodoRepeat) raiz).getCuerpo(),id);
			}
			else if (raiz instanceof NodoAsignacion)
			{
				RegistroSimbolo s=BuscarSimbolo(id+"."+((NodoAsignacion)raiz).getIdentificador());
				if(s!=null){
					s.setInicializado(true);
				}
				else{
					System.out.println("Variable no declarada "+id+"."+((NodoAsignacion)raiz).getIdentificador());
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
		if (tabla.containsKey(id+"."+identificador) || tabla.containsKey(id+".@"+identificador)) {
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
							+ BuscarSimbolo(s).getTamano());
						
		}
	}

	public int getDireccion(String Clave) {
		return BuscarSimbolo(Clave).getDireccionMemoria();
	}

	/*
	 * TODO: 1. Crear lista con las lineas de codigo donde la variable es usada.
	 */
}
