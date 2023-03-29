package analizadorlexico;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    NUMERO,LETRA,CADENA,IDENTIFICADOR,
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)

    // Operaciones
    LE, NE, LT, EQ, GE, GT,
    MENOR_QUE,IGUAL,MAYOR_QUE,
    // Palabras clave:
    Y, CLASE,

    // Final de cadena
    EOF
    
    
}
