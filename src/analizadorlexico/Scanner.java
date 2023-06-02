package analizadorlexico;

import static analizadorlexico.TipoToken.ASTERISCO;
import static analizadorlexico.TipoToken.COMILLA_DOBLE;
import static analizadorlexico.TipoToken.DELIM_ESPACIO;
import static analizadorlexico.TipoToken.DIAGONAL;
import static analizadorlexico.TipoToken.EXCLAMACION;
import static analizadorlexico.TipoToken.IGUAL;
import static analizadorlexico.TipoToken.LETRA;
import static analizadorlexico.TipoToken.MAS;
import static analizadorlexico.TipoToken.MAYOR_QUE;
import static analizadorlexico.TipoToken.MENOR_QUE;
import static analizadorlexico.TipoToken.MENOS;
import static analizadorlexico.TipoToken.NUMERO;
import static analizadorlexico.TipoToken.PUNTO;
import static analizadorlexico.TipoToken.SALTO_LINEA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;
    private static final Map<String, TipoToken> simbolosLenguaje;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("y", TipoToken.Y);
        palabrasReservadas.put("clase", TipoToken.CLASE);
        palabrasReservadas.put("ademas", TipoToken.ADEMAS);
        palabrasReservadas.put("falso", TipoToken.FALSO);
        palabrasReservadas.put("para", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUN); //definir funciones
        palabrasReservadas.put("si", TipoToken.SI);
        palabrasReservadas.put("nulo", TipoToken.NULO);
        palabrasReservadas.put("o", TipoToken.O);
        palabrasReservadas.put("imprimir", TipoToken.IMPRIMIR);
        palabrasReservadas.put("retornar", TipoToken.RETORNAR);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("este", TipoToken.ESTE);
        palabrasReservadas.put("verdadero", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VAR); //definir variables
        palabrasReservadas.put("mientras", TipoToken.MIENTRAS);
        simbolosLenguaje = new HashMap<>();
        simbolosLenguaje.put("=", TipoToken.IGUAL);
        simbolosLenguaje.put("(", TipoToken.PARENTESIS_ABRE);
        simbolosLenguaje.put(")", TipoToken.PARENTESIS_CIERRA);
        simbolosLenguaje.put("{", TipoToken.LLAVE_ABRE);
        simbolosLenguaje.put("}", TipoToken.LLAVE_CIERRA);
        simbolosLenguaje.put(",", TipoToken.COMA);
        simbolosLenguaje.put(".", TipoToken.PUNTO);
        simbolosLenguaje.put(";", TipoToken.PUNTO_COMA);
        simbolosLenguaje.put("*", TipoToken.ASTERISCO);
        simbolosLenguaje.put("/", TipoToken.DIAGONAL);
        simbolosLenguaje.put("!", TipoToken.EXCLAMACION);
        simbolosLenguaje.put("+", TipoToken.MAS);
        simbolosLenguaje.put("-", TipoToken.MENOS);
        simbolosLenguaje.put("*", TipoToken.ASTERISCO);
        simbolosLenguaje.put("_", TipoToken.GUION_BAJO);
        simbolosLenguaje.put("'", TipoToken.COMILLA_SIMPLE);
        simbolosLenguaje.put("" + '"', TipoToken.COMILLA_DOBLE);
        simbolosLenguaje.put("\n", TipoToken.SALTO_LINEA);
        simbolosLenguaje.put("\\", TipoToken.DIAGONAL_INVERTIDA);
        simbolosLenguaje.put(":", TipoToken.DOS_PUNTOS);
        simbolosLenguaje.put("&", TipoToken.AMPERSON);
    }

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        //Aquí va el corazón del scanner.

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        reconocerToken();
        tokens.add(new Token(TipoToken.EOF, "", null, linea + 1));

        return tokens;
    }

    Token reconocerToken() {
        int estado = 0;
        int numerolinea = 1;
        TipoCaracter generarTipo = new TipoCaracter();
        Token token = new Token();

        for (int i = 0; i <= source.length(); i++) {
            if (i != source.length()) {
                generarTipo.setCaracter(source.charAt(i));
                if (generarTipo.isEOF() ){//|| (int) source.charAt(i) == 13) {
                    
                    estado = 0;
                    continue;
                }else if((int)source.charAt(i) == 13){
                    continue;
                }else{
                    System.out.println("aqui");
                }
            } else {
                generarTipo.setFinalCaracter();
            }
            //System.out.println(generarTipo.getCaracter());  Caracter que recorre
            //System.out.println(generarTipo.getTipoCaracter() + "  " + source.charAt(i) + " " + (int)source.charAt(i));

            switch (estado) {
                case 0:
                    token.setLinea(numerolinea);
                    token.addLexema(generarTipo.getCaracter());
                    token.setLiteral(generarTipo.getCaracter()); //Funcionalidad no encontrada aun

                    if (generarTipo.getTipoCaracter() == null) {
                        //Caracter no reconocido no toma en cuenta
                        token.limpiarToken();
                        estado = 0;
                        continue;
                    }
                    switch (generarTipo.getTipoCaracter()) {
                        case MENOR_QUE:
                            estado = 1;
                            break;
                        case IGUAL:
                            estado = 5;

                            break;
                        case MAYOR_QUE:
                            estado = 6;
                            break;
                        case LETRA:
                            estado = 10;
                            break;
                        case NUMERO:
                            estado = 13;
                            break;
                        case DELIM_ESPACIO:
                            estado = 23;
                            break;
                        case EXCLAMACION:
                            estado = 24;
                            break;
                        case COMILLA_DOBLE:
                            estado = 25;
                            break;
                        case DIAGONAL:
                            estado = 26;
                            break;
                        default:

                            if (this.simbolosLenguaje.containsKey(token.getLexema()) && !generarTipo.isEOF()) {
                                token.setTipo(this.simbolosLenguaje.get(token.getLexema()));
                                if (token.getTipo() == SALTO_LINEA) {
                                    numerolinea++;
                                    System.out.println("Es salto de linea");
                                    //System.exit(1);
                                } else {
                                    tokens.add(new Token(token));
                                }
                            } else {
                                if (!generarTipo.isEOF()) {
                                    System.out.println("No hay match de operador");
                                    estado = 0;
                                    break;
                                }
                            }

                            token.limpiarToken();
                            estado = 0;
                            //System.exit(1);
                            break;
                    }
                    break;
                case 1:
                    switch (generarTipo.getTipoCaracter()) {
                        case IGUAL:
                            estado = 2;
                            token.addLexema(generarTipo.getCaracter());
                            token.setTipo(TipoToken.LE);
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            break;
                        case MAYOR_QUE:
                            estado = 3;
                            token.addLexema(generarTipo.getCaracter());
                            token.setTipo(TipoToken.NE);
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;

                            break;
                        default:
                            estado = 4;
                            token.setLiteral(token.getLexema());
                            token.setTipo(TipoToken.LT);
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            i--;
                            break;
                    }
                    break;
                case 5:
                    switch (generarTipo.getTipoCaracter()) {
                        case IGUAL:
                            token.addLexema(generarTipo.getCaracter());
                            token.setTipo(TipoToken.IGUAL_COMPARAR);
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            break;
                        default:
                            token.setLiteral(token.getLexema());
                            token.setTipo(TipoToken.EQ);
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            i--;
                            break;
                    }
                    break;
                case 6:
                    switch (generarTipo.getTipoCaracter()) {
                        case IGUAL:
                            estado = 7;
                            token.addLexema(generarTipo.getCaracter());
                            token.setTipo(TipoToken.GE);
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            break;

                        default:
                            estado = 8;
                            token.setLiteral(token.getLexema());
                            token.setTipo(TipoToken.GT);
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            i--;
                            break;
                    }
                    break;
                case 10:
                    switch (generarTipo.getTipoCaracter()) {
                        case LETRA:
                        case NUMERO:
                            token.addLexema(generarTipo.getCaracter());
                            estado = 10;
                            break;

                        default:
                            estado = 11;
                            token.setLiteral(token.getLexema());
                            if (this.palabrasReservadas.containsKey(token.getLexema().toLowerCase())) {
                                token.setTipo(this.palabrasReservadas.get(token.getLexema().toLowerCase()));
                            } else {
                                token.setTipo(TipoToken.IDENTIFICADOR);
                            }
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            i--;
                            break;
                    }
                    break;
                case 13:
                    switch (generarTipo.getTipoCaracter()) {
                        case NUMERO:
                            token.addLexema(generarTipo.getCaracter());
                            estado = 13;
                            break;
                        case PUNTO:
                            token.addLexema(generarTipo.getCaracter());
                            estado = 14;
                            break;
                        default:
                            estado = 20;
                            token.setTipo(TipoToken.DIGITO);
                            try {
                                token.setLiteral(Integer.valueOf(token.getLexema()));
                            } catch (Exception e) {
                                token.setLiteral(Long.valueOf(token.getLexema()));
                            }

                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            i--;
                            break;
                    }
                    break;
                case 14:
                    switch (generarTipo.getTipoCaracter()) {
                        case NUMERO:
                            token.addLexema(generarTipo.getCaracter());  //Que pasa si no hay digito se genera token?
                            estado = 15;
                            break;
                    }
                    break;
                case 15:
                    switch (generarTipo.getTipoCaracter()) {
                        case NUMERO:
                            token.addLexema(generarTipo.getCaracter());
                            estado = 15;
                            break;
                        case LETRA:
                            if (generarTipo.getCaracter() == 'E') {
                                token.addLexema(generarTipo.getCaracter());
                                estado = 16;
                                break;
                            }
                        default:
                            estado = 21;
                            token.setTipo(TipoToken.DIGITO_FLOTANTE);
                            try {
                                token.setLiteral(Float.valueOf(token.getLexema()));
                            } catch (Exception e) {
                                token.setLiteral(Double.valueOf(token.getLexema()));
                            }

                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            i--;
                            break;

                    }
                    break;
                case 16:
                    switch (generarTipo.getTipoCaracter()) {
                        case NUMERO:
                            token.addLexema(generarTipo.getCaracter());
                            estado = 18;
                            break;
                        case MAS:
                        case MENOS:
                            token.addLexema(generarTipo.getCaracter());
                            estado = 17;
                            break;
                    }
                    break;
                case 17:
                    switch (generarTipo.getTipoCaracter()) {
                        case NUMERO:
                            token.addLexema(generarTipo.getCaracter());
                            estado = 18;
                            break;
                    }
                    break;
                case 18:
                    switch (generarTipo.getTipoCaracter()) {
                        case NUMERO:
                            token.addLexema(generarTipo.getCaracter());
                            estado = 18;
                            break;
                        default:
                            estado = 19;
                            token.setTipo(TipoToken.DIGITO_EXPONENTE);
                            token.setLiteral(token.getLexema());
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            i--;
                            break;
                    }
                    break;
                case 23:
                    switch (generarTipo.getTipoCaracter()) {
                        case DELIM_ESPACIO:
                            //El espacio no genera token
                            //token.addLexema(generarTipo.getCaracter());
                            estado = 23;
                            break;
                        default:
                            estado = 24;
                            //El espacio o tabulación no es valido para la generación de tokens
                            //token.setTipo(TipoToken.DELIM_ESPACIO);
                            //token.setLiteral(token.getLexema());
                            //tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            i--;
                            break;
                    }
                    break;
                case 24:
                    switch (generarTipo.getTipoCaracter()) {
                        case IGUAL:
                            token.addLexema(generarTipo.getCaracter());
                            token.setTipo(TipoToken.NO_IGUAL);
                            token.setLiteral(token.getLexema());
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            break;
                        default:
                            token.setTipo(TipoToken.EXCLAMACION);
                            token.setLiteral(token.getLexema());
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            i--;
                            break;
                    }
                    break;
                case 25:
                    switch (generarTipo.getTipoCaracter()) {
                        case COMILLA_DOBLE:
                            token.addLexema(generarTipo.getCaracter());
                            token.setTipo(TipoToken.CADENA);
                            token.setLiteral(token.getLexema());
                            token.setLexema(token.getLexema().replace('"' + "", ""));
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            break;
                        default:
                            token.addLexema(generarTipo.getCaracter());
                            estado = 25;
                            break;
                    }
                    break;
                case 26:
                    switch (generarTipo.getTipoCaracter()) {
                        case DIAGONAL:
                            estado = 27;
                            break;
                        case ASTERISCO:
                            estado = 28;
                            break;
                        default:

                            token.setLiteral(token.getLexema());
                            token.setTipo(TipoToken.DIAGONAL);
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            i--;
                            break;
                    }
                    break;
                case 27: // 
                    token.limpiarToken();
                    switch (generarTipo.getTipoCaracter()) {
                        case SALTO_LINEA:
                            estado = 0;
                            break;
                    }

                    break;
                case 28:
                    switch (generarTipo.getTipoCaracter()) {
                        case ASTERISCO:
                            estado = 29;
                            break;
                        default:
                            token.limpiarToken();
                            estado = 28;
                            break;
                    }
                    break;
                case 29:
                    switch (generarTipo.getTipoCaracter()) {
                        case DIAGONAL:
                            token.limpiarToken();
                            estado = 0;
                            break;
                        default:
                            token.limpiarToken();
                            estado = 28;

                            break;
                    }
                    break;
                default:
                    if (generarTipo.isEOF()) {
                        System.out.println("No hay match de transición estado: " + estado + " token" + token.toString() + "eof: " + generarTipo.isEOF());
                    }
                    break;
            }
            //System.out.println(generarTipo.isEOF());
            if (generarTipo.isEOF()) {
                break;
            }
        }
        //if(ban){
        //     tokens.add(new Token(TipoToken.NUMERO, digito.toString(), null, linea));
        //   ban = false;
        //}
        return null;
    }

}

/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */
