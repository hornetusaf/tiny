package compilador;

import java.util.*;

import ast.*;
public class TablaSimbolos {
	private HashMap<String, RegistroSimbolo> tabla;
	private int direccion; // Contador de las localidades de memoria asignadas a
	private int parametros;

	public TablaSimbolos() {
		super();
		tabla = new HashMap<String, RegistroSimbolo>();
		direccion = 0;		
	}

	public void cargarTabla(NodoBase raiz,String id) {

		while (raiz != null) {
			//System.out.println(raiz);
			if (raiz instanceof NodoCall){
				RegistroSimbolo s=BuscarSimbolo((((NodoCall)raiz).getNombreFuncion()));
				//System.out.println("aquiii "+id+".@"+((NodoAsignacion)raiz).getIdentificador());
				if(s!=null ){
					
				}
				else{
						System.out.println("funcion no declarada "+(((NodoCall)raiz).getNombreFuncion()));
				}
				cargarTabla(((NodoCall)raiz).getEx(), id);
				
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
						RecorrerOperacion(((NodoProcedimiento) raiz), null, ((NodoProcedimiento) raiz).getId(),false,null);
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
				RegistroSimbolo s2=BuscarSimbolo(id+".@"+((NodoAsignacion)raiz).getIdentificador());
				//System.out.println("aquiii "+id+".@"+((NodoAsignacion)raiz).getIdentificador()+" s: "+s);
				
				if(s!=null){
					boolean x= ValidarInicializacion(((NodoAsignacion)raiz).getExpresion(),id);
					if(s.isInicializado()!=true && x==true){
						s.setInicializado(x);
						//System.out.println("aquiii cambia a true");
					}
				}
				else if(s2!=null){
					boolean x= ValidarInicializacion(((NodoAsignacion)raiz).getExpresion(),id);
					if(s2.isInicializado()!=true && x==true){
						s2.setInicializado(x);
						//System.out.println("aquiii cambia a true");
					}
				}
				else{
					RegistroSimbolo sb=BuscarSimbolo(id+".@"+((NodoAsignacion)raiz).getIdentificador());
					RegistroSimbolo sb2=BuscarSimbolo(id+"."+((NodoAsignacion)raiz).getIdentificador());
					if(sb!=null){
						//sb.setInicializado(true);
					}else
						System.out.println("Variable no declarada "+id+"."+((NodoAsignacion)raiz).getIdentificador());
				}
				cargarTabla(((NodoAsignacion) raiz).getExpresion(),id);
				RecorrerOperacion(((NodoAsignacion)raiz).getExpresion(),((NodoAsignacion)raiz).getIdentificador(),null,false,null);
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
					//s.setInicializado(true);
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
		while(a!=null)
		{
			InsertarVariable(id,a.getTipo(),a.getId(),a.getTam());
			a=(NodoVariable)a.getPartev();		
		}		
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
							+ " retorno activo "
							+ BuscarSimbolo(s).isVarReturn());
						
		}
	}
	public int getDireccion(String Clave) {
		return BuscarSimbolo(Clave).getDireccionMemoria();
	}
	
public boolean ValidarOperacion (NodoBase operando_1, NodoBase operando_2 ){
		
		if ( (((NodoVariable)operando_1).getTipo()) == ((NodoVariable)operando_2).getTipo())
		{
			System.out.println("tamos claros");
			return true;
		}
		else
		{
			System.out.println("Incompativilidad de tipos entre los operandos");
			return false;
		}
	}
public boolean ValidarFunciones (boolean externo, String tipoe){
	System.out.println("********ERRORES************");
	List<String> listadof =  new ArrayList<>();
	List<String> listadov =  new ArrayList<>();
	for (Iterator<String> it = tabla.keySet().iterator(); it.hasNext();) {
		String s = (String) it.next();
		String[] parts = s.split("[.]");
		if(parts.length==1) //comprabacion de que la variable sea una funcion
			listadof.add(s);
		if(parts.length==2)
			if(!parts[0].contains("@"))
					listadov.add(s);
				
	}
	
	if(externo){
		String tipo;
		for(String f : listadof){
			tipo=BuscarSimbolo(f).getRetorno().toString();
			if(tipo==tipoe)
				return true;
		}
		return false;
	}
	else { 
	boolean tiene_retorno=false;
	boolean retorno_correcto=false;
	for(String f : listadof){
		tiene_retorno=false;
		retorno_correcto=false;
		for (String v : listadov) {
			String[] parts = v.split("[.]");
			if(parts[0].equals(f)) //reviso que correspondaa la funcion
				if(BuscarSimbolo(v).isVarReturn()) //que tenga retorno
				{
					tiene_retorno=true;
					if(BuscarSimbolo(v).getTipo().toString()== BuscarSimbolo(f).getRetorno().toString()){
						retorno_correcto = true;
					//	System.out.println("funcion: "+f +" tipo: "+BuscarSimbolo(f).getRetorno());
					//	System.out.println("variable retornada: "+v+ " tipo "+BuscarSimbolo(v).getTipo() );
						break;
					}
				}
		}
		if(!tiene_retorno){
			System.out.println("ERROR la funcion "+f +" es de tipo "+BuscarSimbolo(f).getRetorno().toString()+" y no tiene retorno"); 
			return false;
		}
		if(!retorno_correcto){
			System.out.println("ERROR la funcion  "+ f+" es de tipo "+BuscarSimbolo(f).getRetorno().toString()+" y no retorna el tipo de dato correcto");
			return false;
		}
	}
	if(tiene_retorno && retorno_correcto)
		return true;
	else
		return false;
	}
}
/*
public boolean ValidarRetorno (NodoBase funcion, String id ){
	String fun=((NodoProcedimiento)funcion).getId();		
	if ( ((NodoProcedimiento)funcion).getTipo() == ((NodoIdentificador)var).ge() )
		return true;
	else
	{
		System.out.println("Incompatibilidad de tipos con la funcion, no se puede resolver");
		return false;
	}
}
*/
	


public boolean ValidarInicializacion (NodoBase raiz,String id){
	boolean retornar=false,retornar2=false;
	while(raiz!=null){
		if(raiz instanceof NodoProcedimiento){
			System.out.println("validando proc");
			retornar=ValidarInicializacion(((NodoProcedimiento)raiz).getCuerpo(),((NodoProcedimiento)raiz).getId());
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
			
			System.out.println(id);
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
						System.out.println("aquiii"+simb2.getIdentificador());
						return simb2.isInicializado();
						}
					else
						return false;		
				}
		}
		
		raiz = raiz.getHermanoDerecha();
	}	
		return (retornar && retornar2);
}
			
	
private void RecorrerOperacion(NodoBase operador,String id_asignacion,String id_funcion,boolean llamado, tipoOp to) {			
	List<String> lista_parametro=new ArrayList<String>();
	List<String> lista_envio=new ArrayList<String>();
	while(operador != null)
	{
		if(operador instanceof NodoProcedimiento){
			RecorrerOperacion(((NodoProcedimiento)operador).getPartev(), id_asignacion, ((NodoProcedimiento)operador).getId(),llamado,to);	
			RecorrerOperacion(((NodoProcedimiento)operador).getCuerpo(), id_asignacion, ((NodoProcedimiento)operador).getId(),llamado,to);
		}
		if(operador instanceof NodoVariable){
			RegistroSimbolo simb = BuscarSimbolo(((NodoVariable)operador).getId());
			if(simb!=null)
			lista_parametro.add(simb.getTipo().toString());
			RecorrerOperacion(((NodoVariable)operador).getPartev(), id_asignacion, id_funcion,llamado,to);
		}
		else if(operador instanceof NodoCall){
			RecorrerOperacion(((NodoCall)operador).getEx(), null, id_funcion,true,to);
		}
		else if(operador instanceof NodoAsignacion){
			//System.out.println("entro con "+ id_funcion );
			RecorrerOperacion(((NodoAsignacion)operador).getExpresion(), ((NodoAsignacion)operador).getIdentificador(), id_funcion,llamado,to);	
		}
		else if(operador instanceof NodoOperacion)
		{
			
			RecorrerOperacion(((NodoOperacion)operador).getOpIzquierdo(),id_asignacion,id_funcion,llamado,((NodoOperacion)operador).getOperacion());
			if(((NodoOperacion)operador).getOpDerecho()!=null)
				RecorrerOperacion(((NodoOperacion)operador).getOpDerecho(),id_asignacion,id_funcion,llamado,((NodoOperacion)operador).getOperacion());
		}
		else if(operador instanceof NodoReturn){
			
			RecorrerOperacion(((NodoReturn)operador).getId(),null,id_funcion,llamado,to);
		}
		else if(operador instanceof NodoIdentificador)
		{
			if(llamado==true){
				NodoIdentificador a = (NodoIdentificador)operador;
				RegistroSimbolo s2 = BuscarSimbolo(id_funcion+"."+a.getNombre());
				RegistroSimbolo s = BuscarSimbolo(a.getNombre());
				lista_parametro.add(s.getTipo().toString());
				if(s.getRetorno().toString()!=s2.getTipo().toString()){
					
				}
			}
			if(id_funcion==null){
				RegistroSimbolo s = BuscarSimbolo("@."+id_asignacion);
				NodoIdentificador a = (NodoIdentificador)operador;
				RegistroSimbolo s2 = BuscarSimbolo("@."+a.getNombre());
				if(s!=null && s2!=null){	
					if(s.getTipo()!=s2.getTipo()){
						System.out.println("tipo de variable invalido: "+id_asignacion+" es de tipo "+s.getTipo()+" "+ a.getNombre()+" es de tipo "+s2.getTipo());	
					}
				}
			}else{
				RegistroSimbolo s = BuscarSimbolo(id_funcion+"."+id_asignacion);
				NodoIdentificador a = (NodoIdentificador)operador;
				RegistroSimbolo s2 = BuscarSimbolo(id_funcion+"."+a.getNombre());
				RegistroSimbolo s3 = BuscarSimbolo(id_funcion);
				if(s!=null && s2!=null){	
					if(s.getTipo()!=s2.getTipo()){
						System.out.println("tipo de variable invalido: "+id_asignacion+" es de tipo "+s.getTipo()+" "+ a.getNombre()+" es de tipo "+s2.getTipo());	
					}
					
				}
				if(s2!=null && s3!=null){
					if(s2.getTipo()!=s3.getTipo()){
						System.out.println("tipo de retorno debe ser de tipo "+s3.getRetorno());	
					}
					
				}
				
				
			}
			
			
		}
		else if (operador instanceof NodoValor){
			RegistroSimbolo s = BuscarSimbolo("@."+id_asignacion);
			if (id_funcion!=null){
				if(id_asignacion==null){
					RegistroSimbolo s2 = BuscarSimbolo(id_funcion);
					if(s2!=null)
						if(s2.getRetorno()==tipoFuncion.INT)
						{
							if(!(((NodoValor)operador).getValor() instanceof Integer)){					
								System.out.println("retorno debe ser de tipo ENTERO");											
							}
						}
						else {
							if(!(((NodoValor)operador).get_Valor() instanceof Boolean))
								System.out.println("retorno debe ser de tipo BOOLEAN");
						}
				}
				RegistroSimbolo s1 = BuscarSimbolo(id_funcion+"."+id_asignacion);
				if(s1!=null)
					if(s1.getTipo()==tipoDato.INT)
					{
						if(!(((NodoValor)operador).getValor() instanceof Integer)){					
							System.out.println("variable "+id_asignacion+" debe ser de tipo ENTERO");											
						}
					}
					else {
						if(!(((NodoValor)operador).get_Valor() instanceof Boolean))
							System.out.println("variable "+id_asignacion+" debe ser de tipo BOOLEAN");
					}
			}
			if(s!=null)
			if(s.getTipo()==tipoDato.INT)
			{
				if(!(((NodoValor)operador).getValor() instanceof Integer)){					
					System.out.println("variable "+id_asignacion+" debe ser de tipo ENTERO");											
				}
			}
			else {
				if(!(((NodoValor)operador).get_Valor() instanceof Boolean))
					System.out.println("variable "+id_asignacion+" debe ser de tipo BOOLEAN");
			}
			
		}
		operador = operador.getHermanoDerecha();								
	}
	
	if(lista_envio.size()==lista_parametro.size()){
		for(int i=0;i<lista_envio.size();i++){
			if(lista_envio.get(i)!=lista_parametro.get(i)){
				System.out.println("parametros de diferentes tipos");
			}
		}
	}else{
		System.out.println("no hay suficientes parametros");
	}
}
		 				
	
	
	/*
	 * TODO: 1. Crear lista con las lineas de codigo donde la variable es usada.
	 */
}