package compilador;

import ast.*;

public class Generador {
	/*
	 * Ilustracion de la disposicion de la memoria en este ambiente de ejecucion
	 * para el lenguaje Tiny
	 * 
	 * |t1 |<- mp (Maxima posicion de memoria de la TM |t1 |<- desplazamientoTmp
	 * (tope actual) |free| |free| |... | |x | |y |<- gp
	 */

	/*
	 * desplazamientoTmp es una variable inicializada en 0 y empleada como el
	 * desplazamiento de la siguiente localidad temporal disponible desde la
	 * parte superior o tope de la memoria (la que apunta el registro MP).
	 * 
	 * - Se decrementa (desplazamientoTmp--) despues de cada almacenamiento y
	 * 
	 * - Se incrementa (desplazamientoTmp++) despues de cada eliminacion/carga
	 * en otra variable de un valor de la pila.
	 * 
	 * Pudiendose ver como el apuntador hacia el tope de la pila temporal y las
	 * llamadas a la funcion emitirRM corresponden a una inserccion y extraccion
	 * de esta pila
	 */
	private static int desplazamientoTmp = 0;
	private static TablaSimbolos tablaSimbolos = null;
	private static int inicio=-1;

	public static void setTablaSimbolos(TablaSimbolos tabla) {
		tablaSimbolos = tabla;
	}

	public static void generarCodigoObjeto(NodoBase raiz) {
		System.out.println();
		System.out.println();
		System.out
				.println("------ CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
		System.out.println();
		System.out.println();
		generarPreludioEstandar();
		generar(raiz, "@");
		/* Genero el codigo de finalizacion de ejecucion del codigo */
		UtGen.emitirComentario("Fin de la ejecucion.");
		UtGen.emitirRO("HALT", 0, 0, 0, "");		
		System.out.println("Inciio de linea "+inicio);
		System.out.println();
		System.out
				.println("------ FIN DEL CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
	}

	// Funcion principal de generacion de codigo
	// prerequisito: Fijar la tabla de simbolos antes de generar el codigo
	// objeto
	private static void generar(NodoBase nodo, String ambito) {
		if (tablaSimbolos != null) {			
			if(inicio==-1 && ambito.compareTo("@")==0)
				inicio=UtGen.Linea();
			
			if (nodo instanceof NodoIf) {
				generarIf(nodo, ambito);
			} else if (nodo instanceof NodoRepeat) {
				generarRepeat(nodo, ambito);
			} else if (nodo instanceof NodoAsignacion) {
				generarAsignacion(nodo, ambito);
			} else if (nodo instanceof NodoLeer) {
				generarLeer(nodo, ambito);
			} else if (nodo instanceof NodoEscribir) {
				generarEscribir(nodo, ambito);
			} else if (nodo instanceof NodoValor) {
				generarValor(nodo, ambito);
			} else if (nodo instanceof NodoVariable) {
				generarVariable(nodo, ambito);
			} else if (nodo instanceof NodoIdentificador) {
				generarIdentificador(nodo, ambito);
			} else if (nodo instanceof NodoOperacion) {
				generarOperacion(nodo, ambito);
			}else if (nodo instanceof NodoProcedimiento) {
				inicio=-1;
				generar(((NodoProcedimiento) nodo).getCuerpo(),((NodoProcedimiento) nodo).getId());
			}else if (nodo instanceof NodoFor) {
				generarFor(nodo, ambito);
			}

			else {

				System.out.println("BUG: Tipo de nodo a generar desconocido "+ nodo.toString());
			}
			/*
			 * Si el hijo de extrema izquierda tiene hermano a la derecha lo
			 * genero tambien
			 */
			if (nodo.TieneHermano())
				generar(nodo.getHermanoDerecha(), ambito);
		} else
			System.out
					.println("ERROR: por favor fije la tabla de simbolos a usar antes de generar codigo objeto!!!");
	}
	
	private static void generarIf(NodoBase nodo, String ambito) {
		NodoIf n = (NodoIf) nodo;
		int localidadSaltoElse, localidadSaltoEnd, localidadActual;
		if (UtGen.debug)
			UtGen.emitirComentario("-> if");
		/* Genero el codigo para la parte de prueba del IF */
		generar(n.getPrueba(), ambito);
		localidadSaltoElse = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el else debe estar aqui");
		/* Genero la parte THEN */
		generar(n.getParteThen(), ambito);
		localidadSaltoEnd = UtGen.emitirSalto(1);
		UtGen.emitirComentario("If: el salto hacia el final debe estar aqui");
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoElse);
		UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual,"if: jmp hacia else");
		UtGen.restaurarRespaldo();
		/* Genero la parte ELSE */
		if (n.getParteElse() != null) {
			generar(n.getParteElse(), ambito);
		}
		// igualmente debo generar la sentencia que reserve en
		// localidadSaltoEnd al finalizar la ejecucion de un true
		localidadActual = UtGen.emitirSalto(0);
		UtGen.cargarRespaldo(localidadSaltoEnd);
		UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadActual,"if: jmp hacia el final");
		UtGen.restaurarRespaldo();
		if (UtGen.debug)
			UtGen.emitirComentario("<- if");
	}

	private static void generarRepeat(NodoBase nodo, String ambito) {
		NodoRepeat n = (NodoRepeat) nodo;
		int localidadSaltoInicio;
		if (UtGen.debug)
			UtGen.emitirComentario("-> repeat");
		localidadSaltoInicio = UtGen.emitirSalto(0);
		UtGen.emitirComentario("repeat: el salto hacia el final (luego del cuerpo) del repeat debe estar aqui");
		/* Genero el cuerpo del repeat */
		generar(n.getCuerpo(), ambito);
		/* Genero el codigo de la prueba del repeat */
		generar(n.getPrueba(), ambito);
		//System.out.println((((NodoOperacion) n.getPrueba()).getOperacion()).toString());
		UtGen.emitirRM_Abs("JNE", UtGen.AC, localidadSaltoInicio,"repeat: jmp hacia el inicio del cuerpo");
		if (UtGen.debug)
			UtGen.emitirComentario("<- repeat");
	}
	private static void generarFor(NodoBase nodo, String ambito) {
		NodoFor n = (NodoFor) nodo;
		int localidadSaltoInicio;
		if (UtGen.debug)
			UtGen.emitirComentario("-> for");
		generar(n.getAsi(), ambito);
		localidadSaltoInicio = UtGen.emitirSalto(0);		
		generar(n.getCuerpo(), ambito);
		generar(n.getAsf(), ambito);		
		generar(n.getExp(), ambito);
		UtGen.emitirRM_Abs("JNE", UtGen.AC, localidadSaltoInicio,"repeat: jmp hacia el inicio del cuerpo");
		if (UtGen.debug)
			UtGen.emitirComentario("<- for");
	}


	private static void generarAsignacion(NodoBase nodo, String ambito) {
		NodoAsignacion n = (NodoAsignacion) nodo;
		int direccion;
		if (UtGen.debug)
			UtGen.emitirComentario("-> asignacion");
		/* Genero el codigo para la expresion a la derecha de la asignacion */
		generar(n.getExpresion(), ambito);
		/* Ahora almaceno el valor resultante */
		RegistroSimbolo s = tablaSimbolos.BuscarSimbolo(ambito + "."
				+ n.getIdentificador());
		if (s.getTamano() > n.getPos() || s.getTamano() == 0) {
			direccion = s.getDireccionMemoria() + n.getPos();
			UtGen.emitirRM(
					"ST",
					UtGen.AC,
					direccion,
					UtGen.GP,
					"asignacion: almaceno el valor para el id "
							+ n.getIdentificador());
			if (UtGen.debug)
				UtGen.emitirComentario("<- asignacion");
		} else {
			System.out.println("Error: Identificador " + ambito + "."
					+ n.getIdentificador() + " Posicion Maxima "
					+ (s.getTamano() - 1));
		}
	}

	private static void generarLeer(NodoBase nodo, String ambito) {
		NodoLeer n = (NodoLeer) nodo;
		int direccion;
		if (UtGen.debug)
			UtGen.emitirComentario("-> leer");
		RegistroSimbolo s = tablaSimbolos.BuscarSimbolo(ambito + "."
				+ n.getIdentificador());
		if (s.getTamano() > n.getPos() || s.getTamano() == 0) {
			UtGen.emitirRO("IN", UtGen.AC, 0, 0, "leer: lee un valor entero ");
			direccion = s.getDireccionMemoria() + n.getPos();
			UtGen.emitirRM(
					"ST",
					UtGen.AC,
					direccion,
					UtGen.GP,
					"leer: almaceno el valor entero leido en el id "
							+ n.getIdentificador());
			if (UtGen.debug)
				UtGen.emitirComentario("<- leer");
		} else {
			System.out.println("Error: Identificador " + ambito + "."
					+ n.getIdentificador() + " Posicion Maxima "
					+ (s.getTamano() - 1));
		}
	}

	private static void generarEscribir(NodoBase nodo, String ambito) {
		NodoEscribir n = (NodoEscribir) nodo;
		if (UtGen.debug)
			UtGen.emitirComentario("-> escribir");
		/* Genero el codigo de la expresion que va a ser escrita en pantalla */
		generar(n.getExpresion(), ambito);
		/* Ahora genero la salida */
		UtGen.emitirRO("OUT", UtGen.AC, 0, 0,
				"escribir: genero la salida de la expresion");
		if (UtGen.debug)
			UtGen.emitirComentario("<- escribir");
	}

	private static void generarValor(NodoBase nodo, String ambito) {
		NodoValor n = (NodoValor) nodo;
		if (UtGen.debug)
			UtGen.emitirComentario("-> constante");
		UtGen.emitirRM("LDC", UtGen.AC, n.getValor(), 0, "cargar constante: "
				+ n.getValor().toString());
		if (UtGen.debug)
			UtGen.emitirComentario("<- constante");
	}

	private static void generarVariable(NodoBase nodo, String ambito) {
		NodoVariable n = (NodoVariable) nodo;
		int direccion;
		if (UtGen.debug)
			UtGen.emitirComentario("-> Declaracion de Variable");
		direccion = tablaSimbolos.getDireccion(ambito + "." + n.getId());
		UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP,
				"cargar valor de Variable: " +ambito+"."+n.getId());
		if (UtGen.debug)
			UtGen.emitirComentario("<-Fin Declaracion de Variable");
	}

	private static void generarIdentificador(NodoBase nodo, String ambito) {
		NodoIdentificador n = (NodoIdentificador) nodo;
		int direccion;
		if (UtGen.debug)
			UtGen.emitirComentario("->Variable");
		{
			RegistroSimbolo s = tablaSimbolos.BuscarSimbolo(ambito + "."
					+ n.getNombre());
			if (s.getTamano() > n.getPos() || s.getTamano() == 0) {
				direccion = s.getDireccionMemoria() + n.getPos();
				UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP,
						"cargar valor de Variable: "+ambito+"."+ n.getNombre());
				if (UtGen.debug)
					UtGen.emitirComentario("<- Variable");
			} else {
				System.out.println("Error: Variable " + ambito + "."
						+ n.getNombre() + " Posicion Maxima "
						+ (s.getTamano() - 1));
			}
		}
	}

	private static void generarOperacion(NodoBase nodo, String ambito) {
		NodoOperacion n = (NodoOperacion) nodo;
		if (UtGen.debug)
			UtGen.emitirComentario("-> Operacion: " + n.getOperacion());

		generar(n.getOpIzquierdo(), ambito);
		/*
		 * Almaceno en la pseudo pila de valor temporales el valor de la
		 * operacion izquierda
		 */
		UtGen.emitirRM("ST", UtGen.AC, desplazamientoTmp--, UtGen.MP,
				"op: push en la pila tmp el resultado expresion izquierda");
		/* Genero la expresion derecha de la operacion */

		generar(n.getOpDerecho(), ambito);
		/* Ahora cargo/saco de la pila el valor izquierdo */
		UtGen.emitirRM("LD", UtGen.AC1, ++desplazamientoTmp, UtGen.MP,
				"op: pop o cargo de la pila el valor izquierdo en AC1");
		switch (n.getOperacion()) {
		case mas:
			UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC, "op: +");
			break;
		case menos:
			UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: -");
			break;
		case por:
			UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC, "op: *");
			break;
		case entre:
			UtGen.emitirRO("DIV", UtGen.AC, UtGen.AC1, UtGen.AC, "op: /");
			break;
		case menor:
			UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <");
			UtGen.emitirRM("JLT", UtGen.AC, 2, UtGen.PC,"voy dos instrucciones mas alla if verdadero (AC<0)");
			UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
			UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC,"Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
			UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC,"caso de verdadero (AC=1)");
			break;
		case mayor:
			UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >");
			UtGen.emitirRM("JGT", UtGen.AC, 2, UtGen.PC,"voy dos instrucciones mas alla if verdadero (AC<0)");
			UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
			UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC,"Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
			UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC,"caso de verdadero (AC=1)");
			break;
		case menor_igual:
			UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >");
			UtGen.emitirRM("JLE", UtGen.AC, 2, UtGen.PC,"voy dos instrucciones mas alla if verdadero (AC<0)");
			UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
			UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC,"Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
			UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC,"caso de verdadero (AC=1)");
			break;
		case mayor_igual:
			UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >");
			UtGen.emitirRM("JGE", UtGen.AC, 2, UtGen.PC,"voy dos instrucciones mas alla if verdadero (AC<0)");
			UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
			UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC,"Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
			UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC,"caso de verdadero (AC=1)");
			break;
		case diferente:
			UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >");
			UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC,"voy dos instrucciones mas alla if verdadero (AC<0)");
			UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
			UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC,"Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
			UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC,"caso de verdadero (AC=1)");
			break;
		case igual:
			UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: ==");
			UtGen.emitirRM("JEQ", UtGen.AC, 2, UtGen.PC,"voy dos instrucciones mas alla if verdadero (AC==0)");
			UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
			UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC,"Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
			UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC,"caso de verdadero (AC=1)");
			break;			
		case and:		
			generarOperacion(((NodoOperacion)nodo).getOpIzquierdo(), ambito);
			UtGen.emitirRM("LDC", UtGen.R4, 0, UtGen.AC,"Cargar 0");
			UtGen.emitirRO("ADD", UtGen.R2, UtGen.R4, UtGen.AC, "Guarda EI");
			generarOperacion(((NodoOperacion)nodo).getOpDerecho(), ambito);
			UtGen.emitirRM("LDC", UtGen.R4, 0, UtGen.AC,"Cargar 0");
			UtGen.emitirRO("ADD", UtGen.R3, UtGen.R4, UtGen.AC, "Guarda ED");
			
			UtGen.emitirRO("MUL", UtGen.AC, UtGen.R2, UtGen.R3, "AND");
			
			UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC,"voy dos instrucciones mas alla if verdadero (AC==0)");
			UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
			UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC,"Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
			UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC,"caso de verdadero (AC=1)");
			
			
			break;
		case or:
			generarOperacion(((NodoOperacion)nodo).getOpIzquierdo(), ambito);
			UtGen.emitirRM("LDC", UtGen.R4, 0, UtGen.AC,"Cargar 0");
			UtGen.emitirRO("ADD", UtGen.R2, UtGen.R4, UtGen.AC, "Guarda EI");
			generarOperacion(((NodoOperacion)nodo).getOpDerecho(), ambito);
			UtGen.emitirRM("LDC", UtGen.R4, 0, UtGen.AC,"Cargar 0");
			UtGen.emitirRO("ADD", UtGen.R3, UtGen.R4, UtGen.AC, "Guarda ED");
			
			UtGen.emitirRO("ADD", UtGen.AC, UtGen.R2, UtGen.R3, "AND");
			
			UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC,"voy dos instrucciones mas alla if verdadero (AC==0)");
			UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
			UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC,"Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
			UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC,"caso de verdadero (AC=1)");
			break;
		default:
			UtGen.emitirComentario("BUG: tipo de operacion desconocida");
		}
		if (UtGen.debug)
			UtGen.emitirComentario("<- Operacion: " + n.getOperacion());
	}

	// TODO: enviar preludio a archivo de salida, obtener antes su nombre
	private static void generarPreludioEstandar() {
		UtGen.emitirComentario("Compilacion TINY para el codigo objeto TM");
		UtGen.emitirComentario("Archivo: " + Compilador.archivo);
		/* Genero inicializaciones del preludio estandar */
		/* Todos los registros en tiny comienzan en cero */
		UtGen.emitirComentario("Preludio estandar:");
		UtGen.emitirRM("LD", UtGen.MP, 0, UtGen.AC,
				"cargar la maxima direccion desde la localidad 0");
		UtGen.emitirRM("ST", UtGen.AC, 0, UtGen.AC,
				"limpio el registro de la localidad 0");
	}

}
