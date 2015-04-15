package ast;

public class NodoDeclaracion extends NodoBase {

    private NodoIdentificador variable;    
    private tipoDato tipo;

    public NodoDeclaracion() {
    }

    public NodoDeclaracion(NodoBase nombre, tipoDato  tipo) {
        this.variable = (NodoIdentificador)nombre;
        this.tipo = tipo;
    }

    public NodoIdentificador getVariable() {
        return variable;
    }

    public tipoDato getTipo() {
        return tipo;
    }
}