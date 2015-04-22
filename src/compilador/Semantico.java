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
	String funcion_anterior="";
	int par_ant=0;
	public Semantico(HashMap<String, RegistroSimbolo> tab) {
		super();
		tabla = tab;	
		//System.out.println(tab.BuscarSimbolo("funcion.a").getDireccionMemoria());
	}
	
	public void RecorrerOperacion(NodoBase operador,String id_asignacion,String id_funcion,boolean llamado, tipoOp to,boolean func) {			
	//List<String> lista_parametro=new ArrayList<String>();
	//List<String> lista_envio=new ArrayList<String>();
		//System.out.println("fln flkneklevn");
	while(operador != null)
	{
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
			 ValidarCall(((NodoCall)operador).getNombreFuncion(),((NodoCall)operador).getExD(),id_funcion);
			RecorrerOperacion(((NodoCall)operador).getExD(), null, id_funcion,true,to,func);
			
		}
		else if(operador instanceof NodoAsignacion){
						RecorrerOperacion(((NodoAsignacion)operador).getExpresion(), ((NodoAsignacion)operador).getIdentificador(), id_funcion,llamado,to,func);	
		}
		else if(operador instanceof NodoOperacion)
		{
			
			RecorrerOperacion(((NodoOperacion)operador).getOpIzquierdo(),id_asignacion,id_funcion,llamado,((NodoOperacion)operador).getOperacion(),func);
			if(((NodoOperacion)operador).getOpDerecho()!=null)
				{
				RecorrerOperacion(((NodoOperacion)operador).getOpDerecho(),id_asignacion,id_funcion,llamado,((NodoOperacion)operador).getOperacion(),func);
				}
		}
		else if(operador instanceof NodoReturn){
			
			RecorrerOperacion(((NodoReturn)operador).getId(),null,id_funcion,llamado,to,func);
		}
		else if(operador instanceof NodoIdentificador)
		{
			if(llamado==true){
				//System.out.println("entro a llamado"+id_funcion+((NodoIdentificador)operador).getNombre());
			
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
				if(s3!=null && s2!=null)
				if(s2.getTipo().toString()!=s3.getRetorno().toString()){
					
						System.out.println("tipo de retorno debe ser de tipo "+s3.getRetorno());	
					
					
				}
				
				
			}
			
			
		}
		else if (operador instanceof NodoValor){
			System.out.println(((NodoValor)operador).getValor());
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
	
	
}

	public RegistroSimbolo BuscarSimbolo(String Identificador){
		RegistroSimbolo simbolo=(RegistroSimbolo)tabla.get(Identificador);
		return simbolo;	
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
		int tam=BuscarSimbolo(NombreF).getTamano();
		int dirf=BuscarSimbolo(NombreF).getDireccionMemoria();
		if(funcion_anterior==""){
			funcion_anterior = NombreF;
			par_ant =tam;
		}
		else if( funcion_anterior!= NombreF)
		{
			funcion_anterior = NombreF;
			cont = 0;
		}
		if(tam==0 && call_exp!=null)
		{
			System.out.println("la funcion "+NombreF +" no permite recibir parámetros" );
			System.exit(1);
		}
		if(call_exp == null)
			{
			System.out.println("ERROR no se soportan parametros nulos en la funcion "+NombreF);
			System.exit(1);
			}
		
		//while(call_exp!=nul
			 if(call_exp instanceof NodoIdentificador){				
				RegistroSimbolo s = BuscarSimbolo(funcion_actual+"."+((NodoIdentificador)call_exp).getNombre().toString());
				if(s==null){
					System.out.println("Error Variable no existe "+((NodoIdentificador)call_exp).getNombre().toString());
					System.exit(1);
				}
				String variable_main =  ((NodoIdentificador)call_exp).getNombre().toString();
				if(!s.isInicializado()){
					System.out.println("Error la variable "+variable_main + " no puede ser usada sin ser inicializada ");
					System.exit(1);
				}
				
				String tipo_dato_variable_main=null;
				tipoFuncion tipo_dato_funcion  =null;
				
				if (s!=null && NombreF!=null ) {
					tipo_dato_variable_main= s.getTipo().toString();					
					cont++;
					int x=dirf+cont;
					if(cont>tam && ( funcion_anterior == NombreF))
						{
						System.out.println("ERROR cantidad de parametos superior a los soportados por la funcion "+NombreF);
						System.exit(1);
						}
					String var_parm_id = GetVariable_ts(x);//aqui vamos bien
					String tipo_dato_variable_param = BuscarSimbolo(var_parm_id).getTipo().toString();
			
							if(x<=(dirf+tam)){
								if(tipo_dato_variable_main!=tipo_dato_variable_param){
									System.out.println("*****ERROR******");
									System.out.println("variable en el main "+variable_main+" de tipo "+tipo_dato_variable_main);
									System.out.println("variable parametro "+cont+ var_parm_id+ "de tipo " +tipo_dato_variable_param);
									System.exit(1);
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
				tipoFuncion tipo_dato_funcion  = null;				
					cont++;
					int x=dirf+cont;
					if(cont>tam && ( funcion_anterior == NombreF))
						{
						System.out.println("ERROR cantidad de parametos superior a los soportados por la funcion "+NombreF);
						System.exit(1);
						}
					String var_parm_id = GetVariable_ts(x);//aqui vamos bien
					String tipo_dato_variable_param = BuscarSimbolo(var_parm_id).getTipo().toString();		
					if(x<=(dirf+tam)){
						if(tipo_dato_variable_main!=tipo_dato_variable_param){
								System.out.println("Error");
								System.out.println("Variable en el main "+variable_main+" de tipo "+tipo_dato_variable_main);
								System.out.println("variable parametro "+cont+ var_parm_id+ "de tipo " +tipo_dato_variable_param);
								System.exit(1);
								}
							} //x<=(dirf+tam)
			} //call_exp instanceof NodoValor
			 
	 else if(call_exp instanceof NodoCall){			
		String func_main =  ((NodoCall)call_exp).getNombreFuncion();
		String tipo_func_main = BuscarSimbolo(func_main).getRetorno().toString();
			cont++;
			int x=dirf+cont;
			if(cont>tam && ( funcion_anterior == NombreF))
				{
				System.out.println("ERROR cantidad de parametos superior a los soportados por la funcion "+NombreF);
				System.exit(1);
				}
			String var_parm_id = GetVariable_ts(x);//aqui vamos bien
			String tipo_dato_variable_param = BuscarSimbolo(var_parm_id).getTipo().toString();		
			if(x<=(dirf+tam)){
				if(tipo_func_main!=tipo_dato_variable_param){
						System.out.println("Error");
						System.out.println("La funcion  "+func_main+" es de tipo "+tipo_func_main);
						System.out.println("variable parametro "+cont+ var_parm_id+ "de tipo " +tipo_dato_variable_param);
						System.exit(1);
						}
					} //x<=(dirf+tam)
	} //call_exp instanceof NodoValor
			 
		
	}
	public tipoDato BuscarSimbolo(int dir){
		
		for (Iterator<String> it = tabla.keySet().iterator(); it.hasNext();) {
			String s = (String) it.next();
			if(BuscarSimbolo(s).getDireccionMemoria()==dir){
				 return BuscarSimbolo(s).getTipo();
			}
							
			
						
		}
		return null;
		
	}

}
