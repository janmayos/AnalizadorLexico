package analizadorlexico;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author janma
 */
public class TipoCaracter {

    private char caracter;
    private boolean EOF;

    public TipoCaracter() {
        this.EOF = false;
    }

    public TipoToken getTipoCaracter() {
        //System.out.println(caracter);

        if (!isEOF()) {
            String caracterString = getCaracter() + "";
            //System.out.println(caracterString);
            if (caracterString.matches("[a-zA-Z]")) {
                return TipoToken.LETRA;
            } else if (caracterString.matches("[0-9]")) {
                return TipoToken.NUMERO;
            }
            switch (caracterString) {
                case "<":
                    return TipoToken.MENOR_QUE;
                case ">":
                    return TipoToken.MAYOR_QUE;
                case "=":
                    return TipoToken.IGUAL;
                case ".":
                    return TipoToken.PUNTO;
                case "+":
                    return TipoToken.MAS;
                case "-":
                    return TipoToken.MENOS;
                case " ":
                    return TipoToken.DELIM_ESPACIO;
                case "(":
                    return TipoToken.PARENTESIS_ABRE;
                case ")":
                    return TipoToken.PARENTESIS_CIERRA;
                case "{":
                    return TipoToken.LLAVE_ABRE;
                case "}":
                    return TipoToken.LLAVE_CIERRA;
                case ",":
                    return TipoToken.COMA;
                case ";":
                    return TipoToken.PUNTO_COMA;
                case "*":
                    return TipoToken.ASTERISCO;
                case "/":
                    return TipoToken.DIAGONAL;
                case "!":
                    return TipoToken.EXCLAMACION;
                case "_":
                    return TipoToken.GUION_BAJO;

                default:
                    System.out.println("Caracter no reconocido:'" + caracterString + "'");
                    return null;
            }

        }
        /*
      






!=
=
==
<
<=
>
>=*/
        return TipoToken.EOF;
    }

    /*public static void main(String[] args) {
        TipoCaracter tipo = new TipoCaracter('p');
        System.out.println(tipo.getTipoCaracter());
        
    }*/
    public Character getCaracter() {
        return caracter;
    }

    public void setCaracter(char caracter) {
        this.caracter = caracter;
    }

    public boolean isEOF() {
        return EOF;
    }

    public void setEOF(boolean EOF) {
        this.EOF = EOF;
    }

    void setFinalCaracter() {
        this.EOF = true;
    }

}
