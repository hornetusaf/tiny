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
	List<String> envio = new ArrayList<String>();
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
		envio.clear();
		System.out.println(operador);
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
				RecorrerOperacion(((NodoOperacion)operador).getOpDerecho(),id_asignacion,id_funcion,llamado,((NodoOperacion)operador).getOperacion(),func);
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
		 				
	public void ValidarCall(String NombreF,NodoBase call_exp,String funcion_actual){
		//List<String> envio = new ArrayList<String>();	
		List<String> param = new ArrayList<String>();
		
		//while(call_exp!=null){
			
			if(call_exp instanceof NodoOperacion){
				System.out.println("entro nodo op");
				//ValidarCall(NombreF,((NodoOperacion)call_exp).getOpIzquierdo());
				//ValidarCall(NombreF,((NodoOperacion)call_exp).getOpDerecho());
				
			}else if(call_exp instanceof NodoIdentificador){
				envio.add(((NodoIdentificador)call_exp).getNombre());
				System.out.println("entro a nodo id"+((NodoIdentificador)call_exp).getNombre());
				// s = BuscarSimbolo(NombreF+"."+(NodoIdentificador)call_exp).getNombre());
				//RegistroSimbolo s ;
				RegistroSimbolo s =BuscarSimbolo(funcion_actual+"."+((NodoIdentificador)call_exp).getNombre());
				tipoDato td=null;
				int dir=-1;
				int tam = -1;
				if (s!=null && NombreF!=null ) {
						td= s.getTipo();					
						dir=BuscarSimbolo(NombreF).getDireccionMemoria();
						 tam=BuscarSimbolo(NombreF).getTamano();				
				}


				tipoDato comp=BuscarSimbolo(dir);
				cont++;
				int x=dir+cont;
				if(x<tam){
					if(BuscarSimbolo(x)!=comp)
					{

						System.out.println("variable "+((NodoIdentificador)call_exp).getNombre()+" de tipo "+s);
						System.out.println("funcion "+NombreF+ "de tipo" +td);
					}
					else
					{
						System.out.println("variable "+((NodoIdentificador)call_exp).getNombre()+" de tipo "+s);
						System.out.println("funcion "+NombreF+ "de tipo" +td);
					}
					
				}
				/*while((tam-dir)!=0){
					dir=dir+cont;
				}*/
				if(comp==null){
					System.out.println("tipo de dato invalido en la funcion "+NombreF);
				}
				
				
				
				
			}else if(call_exp instanceof NodoValor){
				envio.add(((NodoValor)call_exp).getValor().toString());
				System.out.println("entro a nodo id"+((NodoValor)call_exp).getValor());
				
				
			}
			//call_exp=call_exp.getHermanoDerecha();
	//	}
		
		
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
