package analizadorlexico;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    NUMERO,CADENA,IDENTIFICADOR,
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    // Palabras clave:
    Y, CLASE,

    // Final de cadena
    EOF
}
