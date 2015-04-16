package compilador;

import ast.NodoProcedimiento;
import ast.tipoDato;
import ast.tipoFuncion;

public class RegistroSimbolo {
	
	private tipoDato tipo;
	private String identificador;	
	private int DireccionMemoria;	
	private int tamano;
	private tipoFuncion Retorno;
	private boolean Inicializado;
		
	
	public RegistroSimbolo(tipoDato tipo,int direccionMemoria,int tam) {
		super();
		this.tipo = tipo;
		this.identificador = identificador;
		this.DireccionMemoria = direccionMemoria;
		this.tamano=tam;
		this.setInicializado(false);
		/*0-n cantidad parametros
		 *-1 variables
		 * */
	}
	public RegistroSimbolo(tipoFuncion retorno,int direccionMemoria) {
			super();
			this.setRetorno(retorno);
			this.identificador = identificador;
			this.DireccionMemoria = direccionMemoria;
			this.setInicializado(true);
			/*0-n cantidad parametros
			 *-1 variables
			 * */
		}
		
	public String getIdentificador() {
		return identificador;
	}
	
	public int getDireccionMemoria() {
		return DireccionMemoria;
	}

	public void setDireccionMemoria(int direccionMemoria) {
		DireccionMemoria = direccionMemoria;
	}

	public tipoDato getTipo() {
		return tipo;
	}

	public void setTipo(tipoDato tipo) {
		this.tipo = tipo;
	}
	public int getTamano() {
		return tamano;
	}
	public void setTamano(int tamano) {
		this.tamano = tamano;
	}
	public tipoFuncion getRetorno() {
		return Retorno;
	}
	public void setRetorno(tipoFuncion retorno) {
		Retorno = retorno;
	}
	public boolean isInicializado() {
		return Inicializado;
	}
	public void setInicializado(boolean inicializado) {
		Inicializado = inicializado;
	}
	
}
