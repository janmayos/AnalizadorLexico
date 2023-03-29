package analizadorlexico;

import static analizadorlexico.TipoToken.IGUAL;
import static analizadorlexico.TipoToken.MAYOR_QUE;
import static analizadorlexico.TipoToken.MENOR_QUE;
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
        /*palabrasReservadas.put("ademas", );
        palabrasReservadas.put("falso", );
        palabrasReservadas.put("para", );
        palabrasReservadas.put("fun", ); //definir funciones
        palabrasReservadas.put("si", );
        palabrasReservadas.put("nulo", );
        palabrasReservadas.put("o", );
        palabrasReservadas.put("imprimir", );
        palabrasReservadas.put("retornar", );
        palabrasReservadas.put("super", );
        palabrasReservadas.put("este", );
        palabrasReservadas.put("verdadero", );
        palabrasReservadas.put("var", ); //definir variables
        palabrasReservadas.put("mientras", );*/
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
        tokens.add(new Token(TipoToken.EOF, "", null, linea));

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
                    token.setLexema(generarTipo.getCaracter());
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
                            token.setLexema(generarTipo.getCaracter());
                            token.setTipo(TipoToken.LE);
                            tokens.add(new Token(token));
                            token.limpiarToken();
                            estado = 0;
                            break;
                        case MAYOR_QUE:
                            estado = 3;
                            token.setLexema(generarTipo.getCaracter());
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
                            token.setLexema(generarTipo.getCaracter());
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
                default:
                    System.out.println("No hay match de transición");
                    break;
            }
            System.out.println(generarTipo.isEOF());
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
