package analizadorlexico;

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

    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens(){
        //Aquí va el corazón del scanner.

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        reconocerToken();
        tokens.add(new Token(TipoToken.EOF, "", null, linea));

        return tokens;
    }
    
    Token reconocerToken(){
        Digito digito = new Digito();
        boolean ban = false;
        System.out.println(this.source);
        for (int i = 0; i < source.length(); i++){
            char c = source.charAt(i);        
    
            
            System.out.println(c);
            if(digito.isdigit(c)){
                
                digito.addChar(c);
            }else{
                ban=true;
                
            }
           
        }
         if(ban){
                tokens.add(new Token(TipoToken.NUMERO, digito.toString(), null, linea));
                ban = false;
            }
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