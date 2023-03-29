package analizadorlexico;

import static analizadorlexico.TipoToken.IGUAL;
import static analizadorlexico.TipoToken.LETRA;
import static analizadorlexico.TipoToken.MAS;
import static analizadorlexico.TipoToken.MAYOR_QUE;
import static analizadorlexico.TipoToken.MENOR_QUE;
import static analizadorlexico.TipoToken.MENOS;
import static analizadorlexico.TipoToken.NUMERO;
import static analizadorlexico.TipoToken.PUNTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;

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
        TipoCaracter generarTipo = new TipoCaracter();
        Token token = new Token();
        for (int i = 0; i <= source.length(); i++) {
            if (i != source.length()) {
                generarTipo.setCaracter(source.charAt(i));
            } else {
                generarTipo.setFinalCaracter();
            }
            switch (estado) {
                case 0:
                    token.setLinea(i);
                    token.addLexema(generarTipo.getCaracter());
                    token.setLiteral(generarTipo.getCaracter()); //Funcionalidad no encontrada aun
                    //System.out.println(generarTipo.getTipoCaracter());
                    switch (generarTipo.getTipoCaracter()) {
                        case MENOR_QUE:
                            estado = 1;
                            break;
                        case IGUAL:
                            estado = 5;
                            token.setTipo(TipoToken.EQ);
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
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
                        default:
                            token.limpiarToken();
                            System.out.println("No hay match de operador");
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
                            token.setTipo(TipoToken.LT);
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
                            token.setLiteral(Integer.valueOf(token.getLexema()));
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
                            token.setLiteral(Float.valueOf(token.getLexema()));
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
                default:
                    System.out.println("No hay match de transición");
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
