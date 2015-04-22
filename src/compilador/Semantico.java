package compilador;
import java.awt.peer.SystemTrayPeer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import ast.*;

public class Semantico extends TablaSimbolos{
	private HashMap<String, RegistroSimbolo> tabla;
	public int cont=0;
	public String nombrefcall=null;
	List<String> envio = new ArrayList<String>();
	public Semantico(HashMap<String, RegistroSimbolo> tab) {
		super();
		tabla = tab;
	}
	
	public void RecorrerOperacion(NodoBase operador,String id_asignacion,String id_funcion,boolean llamado, tipoOp to,boolean func) {			
	//List<String> lista_parametro=new ArrayList<String>();
	//List<String> lista_envio=new ArrayList<String>();
		//System.out.println("fln flkneklevn");
		
	while(operador != null)
	{
		envio.clear();
		//System.out.println(operador);
		if(operador instanceof NodoProcedimiento){
			
			RecorrerOperacion(((NodoProcedimiento)operador).getPartev(), id_asignacion, ((NodoProcedimiento)operador).getId(),llamado,to,true);	
			RecorrerOperacion(((NodoProcedimiento)operador).getCuerpo(), id_asignacion, ((NodoProcedimiento)operador).getId(),llamado,to,false);
		}
		else if(operador instanceof NodoVariable){
			
			//if(((NodoVariable)operador).getPartev()!=null)
			//RecorrerOperacion(((NodoVariable)operador).getPartev(), id_asignacion, id_funcion,llamado,to,func);
		}
		else if(operador instanceof NodoCall){
			//System.out.println("entro a nodo call "+((NodoCall)operador).getEx());
			//envio.clear();
			
			if(((NodoCall)operador).getNombreFuncion()!=null){
				
				nombrefcall=((NodoCall)operador).getNombreFuncion();
			}
			if(((NodoCall)operador).getExI() instanceof NodoIdentificador){
				RegistroSimbolo s = BuscarSimbolo(id_funcion+"."+((NodoIdentificador)((NodoCall)operador).getExI()).getNombre());
					if(s!=null)
						if(s.isInicializado()==false){
							System.out.println("variable: "+((NodoIdentificador)((NodoCall)operador).getExI()).getNombre() +" no inicializada");
						}
			}
			ValidarCall(nombrefcall,((NodoCall)operador).getExI(),id_funcion);
			RecorrerOperacion(((NodoCall)operador).getExD(), null, id_funcion,true,to,func);
			RecorrerOperacion(((NodoCall)operador).getExI(), null, id_funcion,true,to,func);
		}
		else if(operador instanceof NodoAsignacion){
			
			
						RecorrerOperacion(((NodoAsignacion)operador).getExpresion(), ((NodoAsignacion)operador).getIdentificador(), id_funcion,llamado,to,func);	
						//if(((NodoIdentificador)((NodoAsignacion)operador).getExpresion()).getNombre() != null)
						//System.out.println("aquii"+((NodoIdentificador)((NodoAsignacion)operador).getExpresion()).getNombre());
						
		}
		else  if (operador instanceof  NodoIf){
			
			RecorrerOperacion(((NodoIf)operador).getPrueba(),id_asignacion,id_funcion,llamado, to,func);
			RecorrerOperacion(((NodoIf)operador).getParteThen(),id_asignacion,id_funcion,llamado, to,func);
	    	if(((NodoIf)operador).getParteElse()!=null){
	    		RecorrerOperacion(((NodoIf)operador).getParteElse(),id_asignacion,id_funcion,llamado, to,func);
	    	}
	    }
		else if(operador instanceof NodoOperacion)
		{
			 if(((NodoOperacion)operador).getOpDerecho() instanceof NodoIdentificador || ((NodoOperacion)operador).getOpIzquierdo() instanceof NodoIdentificador){
				 String op = ((NodoOperacion)operador).getOperacion().toString();
				 RegistroSimbolo s = BuscarSimbolo(id_funcion+"."+((NodoIdentificador)((NodoOperacion)operador).getOpDerecho()).getNombre());
				 RegistroSimbolo s2 = BuscarSimbolo(id_funcion+"."+((NodoIdentificador)((NodoOperacion)operador).getOpIzquierdo()).getNombre());
				 if(s!=null){
					 if(s.isInicializado()==false){
						 System.out.println("error: variable "+((NodoIdentificador)((NodoOperacion)operador).getOpDerecho()).getNombre()+ " no inicializada");
					 }
					 if( s.getTipo() == tipoDato.INT ){
				 		if(op=="and" || op=="or"){
				 		System.out.println("error 2");
				 		}
				 	} else  if( s.getTipo() == tipoDato.BOOLEAN ){
				 		if(op=="mayor" || op=="menor"|| op=="mayor_igual"|| op=="diferente" || op=="igual" ||op=="menor_igual" ||
								op=="mas" || op=="menos" || op=="entre" || op=="por"
								){
				 		System.out.println("error: "+ id_funcion+"."+((NodoIdentificador)((NodoOperacion)operador).getOpDerecho()).getNombre() +" es booleano ");
				 		}
				 	}
					 
				 }
				 if(s2!=null){
					 if(s2.isInicializado()==false){
						 System.out.println("error: variable "+((NodoIdentificador)((NodoOperacion)operador).getOpIzquierdo()).getNombre()+ " no inicializada");
					 }
					 
					 
					 if( s2.getTipo() == tipoDato.INT ){
				 		if(op=="and" || op=="or"){
				 		System.out.println("error 2");
				 		}
				 	} else  if( s2.getTipo() == tipoDato.BOOLEAN ){
				 		if(op=="mayor" || op=="menor"|| op=="mayor_igual"|| op=="diferente" || op=="igual" ||op=="menor_igual" ||
								op=="mas" || op=="menos" || op=="entre" || op=="por"
								){
				 		System.out.println("error: "+ id_funcion+"."+((NodoIdentificador)((NodoOperacion)operador).getOpIzquierdo()).getNombre() +" es booleano ");
				 		}
				 	}
				 }
				//System.out.println(s2.getTipo());
			}
			 if(((NodoOperacion)operador).getOpDerecho() instanceof NodoValor || ((NodoOperacion)operador).getOpIzquierdo() instanceof NodoValor){
				 String op = ((NodoOperacion)operador).getOperacion().toString();
				 if( ((NodoValor)((NodoOperacion)operador).getOpDerecho()).getValor() instanceof Integer){
					 if(op=="and" || op=="or"){
					 		System.out.println("error 2");
					 		}
				 }else if (((NodoValor)((NodoOperacion)operador).getOpIzquierdo()).getValor() instanceof Integer){
					 if(op=="and" || op=="or"){
					 		System.out.println("error 2");
					 		}
					 
				 }
				 if( ((NodoValor)((NodoOperacion)operador).getOpDerecho()).get_Valor() instanceof Boolean ){
					 if(op=="mayor" || op=="menor"|| op=="mayor_igual"|| op=="diferente" || op=="igual" ||op=="menor_igual" ||
								op=="mas" || op=="menos" || op=="entre" || op=="por"
								){
				 		System.out.println("error: "+((NodoValor)((NodoOperacion)operador).getOpDerecho()).get_Valor()+" es de tipo boolean");
				 
					 }
				 }else if (((NodoValor)((NodoOperacion)operador).getOpIzquierdo()).get_Valor() instanceof Boolean){
					 if(op=="mayor" || op=="menor"|| op=="mayor_igual"|| op=="diferente" || op=="igual" ||op=="menor_igual" ||
								op=="mas" || op=="menos" || op=="entre" || op=="por"
								){
				 		System.out.println("error: "+((NodoValor)((NodoOperacion)operador).getOpIzquierdo()).get_Valor()+" es de tipo boolean");
				 
					 }
					 
				 }
			} 			
			RecorrerOperacion(((NodoOperacion)operador).getOpIzquierdo(),id_asignacion,id_funcion,llamado,((NodoOperacion)operador).getOperacion(),func);
			if(((NodoOperacion)operador).getOpDerecho()!=null)
				RecorrerOperacion(((NodoOperacion)operador).getOpDerecho(),id_asignacion,id_funcion,llamado,((NodoOperacion)operador).getOperacion(),func);
			
			
		}
		else if(operador instanceof NodoReturn){
			RegistroSimbolo s = BuscarSimbolo(id_funcion);
			//System.out.println("aquiii"+s.getRetorno().toString());
			if(s!=null && s.getRetorno().toString()!="INT" && s.getRetorno().toString()!="BOOLEAN"){
				System.out.println("Para retornar la funcion debe ser de tipo INT o BOOLEAN");
			}
			RecorrerOperacion(((NodoReturn)operador).getId(),null,id_funcion,llamado,to,func);
		}
		
		else if(operador instanceof NodoIdentificador)
		{
			//System.out.println(((NodoIdentificador)((NodoOperacion)operador).getOpDerecho()).getNombre());
			if(id_asignacion!=null){
				RegistroSimbolo s = BuscarSimbolo(id_funcion+"."+id_asignacion);
				RegistroSimbolo s2 = BuscarSimbolo(id_funcion+"."+((NodoIdentificador)operador).getNombre());
				if(s2!=null)
				if(s2.isInicializado()==false){
					System.out.println("error variable: "+ ((NodoIdentificador)operador).getNombre()+" no esta inicializada");
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
				if(s3!=null && s2!=null){
					if(s2.getTipo().toString()!=s3.getRetorno().toString() && s3.getRetorno().toString()!="VOID"){
						System.out.println("tipo de retorno debe ser de tipo "+s3.getRetorno());	
					}	
				}	
			}
			
		}
		else if (operador instanceof NodoValor){
			//System.out.println(((NodoValor)operador).getValor());
			
			
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
							System.out.println("variable "+id_asignacion+" es tipo ENTERO");											
							//System.exit(1);
						}
					}
					else if(s1.getTipo()==tipoDato.BOOLEAN) {
						if(!(((NodoValor)operador).get_Valor() instanceof Boolean))
							System.out.println("variable "+id_asignacion+" es tipo BOOLEAN");
						//System.exit(1); 
							
					}
			}
			/*if(s!=null)
			if(s.getTipo()==tipoDato.INT)
			{
				if(!(((NodoValor)operador).getValor() instanceof Integer)){					
					System.out.println("variable "+id_asignacion+" debe ser de tipo ENTERO");											
				}
			}
			else {
				if(!(((NodoValor)operador).get_Valor() instanceof Boolean))
					System.out.println("variable "+id_asignacion+" debe ser de tipo BOOLEAN");
			}*/
			
		}
		operador = operador.getHermanoDerecha();								
	}
}

	public tipoDato BuscarSimbolo(int dir){		
		for (Iterator<String> it = tabla.keySet().iterator(); it.hasNext();) {
			String s = (String) it.next();
			RegistroSimbolo x = BuscarSimbolo(s);
			int dir_otro=-1;
			if(x!=null)
				dir_otro = x.getDireccionMemoria();
			if (dir_otro!=-1)
				if( dir_otro==dir){
					return BuscarSimbolo(s).getTipo();			
				}				
		}
		return null;
	}
	public RegistroSimbolo BuscarSimbolo(String Identificador){
		RegistroSimbolo simbolo=(RegistroSimbolo)tabla.get(Identificador);
		return simbolo;
	}
	// true es nuevo no existe se insertara, false ya existe NO se vuelve a
	public void ImprimirClaves2() {
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



	public String GetVariable_ts(int memoria) {
		for (Iterator<String> it = tabla.keySet().iterator(); it.hasNext();) {
			String s = (String) it.next();
			if(memoria == BuscarSimbolo(s).getDireccionMemoria()){
				return s;
			}
		}
		return null;
	}
	
	public void ValidarCall(String NombreF,NodoBase call_exp,String funcion_actual){

		//while(call_exp!=null){
			
			 if(call_exp instanceof NodoIdentificador){				
				RegistroSimbolo s = BuscarSimbolo(funcion_actual+"."+((NodoIdentificador)call_exp).getNombre().toString());
				String variable_main =  ((NodoIdentificador)call_exp).getNombre().toString();
				String tipo_dato_variable_main=null;
				int dirf=-1;
				int tam = -1;
				tipoFuncion tipo_dato_funcion  =null;
				
				if (s!=null && NombreF!=null ) {
					tipo_dato_variable_main= s.getTipo().toString();					
					dirf=BuscarSimbolo(NombreF).getDireccionMemoria();
					tam=BuscarSimbolo(NombreF).getTamano();
					cont++;
					int x=dirf+cont;
					String var_parm_id = GetVariable_ts(x);//aqui vamos bien
					String tipo_dato_variable_param = BuscarSimbolo(var_parm_id).getTipo().toString();
			
							if(x<=(dirf+tam)){
								if(tipo_dato_variable_main==tipo_dato_variable_param){
									//System.out.println("variable "+variable_main+" de tipo "+tipo_dato_variable_main);
									//System.out.println("variable parametro "+cont+ var_parm_id+ " de tipo " +tipo_dato_variable_param);
									if(cont == tam)
										cont = 0;
								}
								else{
									System.out.println("*****ERROR******");
									System.out.println("variable en el main "+variable_main+" de tipo "+tipo_dato_variable_main);
									System.out.println("variable parametro "+ var_parm_id+ " de tipo " +tipo_dato_variable_param);
									if(cont == tam)
										cont = 0;
								}	
							} //	if(x<=(dirf+tam))

				}// (s!=null && NombreF!=null )
				
			} else if(call_exp instanceof NodoValor){			
				String variable_main;
				String tipo_dato_variable_main;
				if(((NodoValor)call_exp).get_Valor() instanceof Boolean){
					variable_main =  ((NodoValor)call_exp).get_Valor().toString();
					tipo_dato_variable_main	= "BOOLEAN";
				}
				else{
					variable_main =  ((NodoValor)call_exp).getValor().toString();
					tipo_dato_variable_main	= "INT";
					}
				int dirf=-1;
				int tam = -1;
				tipoFuncion tipo_dato_funcion  =null;
				if ( NombreF!=null ){					
					dirf=BuscarSimbolo(NombreF).getDireccionMemoria();
					tam=BuscarSimbolo(NombreF).getTamano();
					cont++;
					int x=dirf+cont;
					String var_parm_id = GetVariable_ts(x);//aqui vamos bien
					String tipo_dato_variable_param = BuscarSimbolo(var_parm_id).getTipo().toString();		
					if(x<=(dirf+tam)){
						if(tipo_dato_variable_main==tipo_dato_variable_param){
							if(cont == tam)
								cont = 0;
							}//tipo_dato_variable_main==tipo_dato_variable_param
							else{
								System.out.println("*****ERROR******");
								System.out.println("variable en el main "+variable_main+" de tipo "+tipo_dato_variable_main);
								System.out.println("variable parametro "+ var_parm_id+ " de tipo " +tipo_dato_variable_param);
								if(cont == tam)
									cont = 0;
								}
							} //x<=(dirf+tam)
					}// NombreF!=null 
			} //call_exp instanceof NodoValor
			 
		
	}

	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	



