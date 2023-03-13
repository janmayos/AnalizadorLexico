/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico;

/**
 *
 * @author janma
 */
public class Digito {
    
    private char[] digitos = {'0','1','2','3','4','5','6','7','8','9'};
    public Digito() {
        this.digito="";
    }
    
    
    
    public boolean isdigit(char c){
        for (char digito : this.digitos) {
            if(c == digito)
                return true;
        }
        return false;
    }
    
    public void addChar(char c){
        this.digito = this.digito + c;
    }

    @Override
    public String toString() {
        return digito;
    }
    
    public void clear(){
        this.digito = "";
    }
    
    
    
    
    
    
    
    
}
