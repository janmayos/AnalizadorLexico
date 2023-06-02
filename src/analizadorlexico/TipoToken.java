package analizadorlexico;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    NUMERO,DIGITO,DIGITO_FLOTANTE,DIGITO_EXPONENTE,LETRA,CADENA,IDENTIFICADOR,
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)
    PUNTO, MENOS, MAS,PARENTESIS_ABRE, PARENTESIS_CIERRA, LLAVE_CIERRA, LLAVE_ABRE, COMA, PUNTO_COMA, ASTERISCO, DIAGONAL,
    EXCLAMACION,NO_IGUAL,
    IGUAL,IGUAL_COMPARAR, GUION_BAJO,
    COMILLA_SIMPLE, COMILLA_DOBLE,
    // Operaciones
    LE, NE, LT, EQ, GE, GT,
    MENOR_QUE,MAYOR_QUE,
    // Palabras clave:
    Y, CLASE,ADEMAS,FALSO,PARA,FUN,SI,NULO,O,IMPRIMIR,RETORNAR,SUPER,ESTE,VERDADERO,VAR,MIENTRAS,
    //DELIM
     DELIM_ESPACIO,
    // Final de cadena
    EOF, SALTO_LINEA, DIAGONAL_INVERTIDA, DOS_PUNTOS, AMPERSON,
    //Sin definir
    SIN_DEFINIR
    
}
