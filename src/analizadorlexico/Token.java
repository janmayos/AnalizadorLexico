package analizadorlexico;

public class Token {

    private TipoToken tipo;
    private String lexema;
    private Object literal;
    private int linea;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = linea;
    }
    
    public Token(Token token2) {
        this.tipo = token2.getTipo();
        this.lexema = token2.getLexema();
        this.literal = token2.getLiteral();
        this.linea = token2.getLinea();
    }

    public Token() {
        this.tipo = null;
        this.lexema = "";
        this.literal = null;
        this.linea = -1;
    }
    
    public void limpiarToken(){
        this.tipo = null;
        this.lexema = "";
        this.literal = null;
        this.linea = -1;
    }

    public String toString() {
        return "Tipo: "+tipo + "\tLexema: " + lexema + "\tLiteral: " + literal + "\tLinea: "+linea;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public void setTipo(TipoToken tipo) {
        this.tipo = tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public void addLexema(char caracter) {
        
        this.lexema = lexema + caracter;
        
    }

      public void setLexema(String caracter) {
        
        this.lexema = caracter;
        
    }
    public Object getLiteral() {
        return literal;
    }

    public void setLiteral(Object literal) {
        this.literal = literal;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }
}
