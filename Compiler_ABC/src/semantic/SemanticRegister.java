/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

/**
 *
 * @author danielalvarado
 */
public class SemanticRegister {
    
    String descrp;
    String valor;
    String type;

    /**
     * Constructor used for WHILE IF 
     * @param descrp SR name 
     * @param valor  SR token being stored 
     */
    public SemanticRegister(String descrp, String valor) {
        this.descrp = descrp;
        this.valor = valor;
    }
       
    /**
     * Constructor used for SR_DO (constants and variables)
     * @param descrp SR name
     * @param valor  SR token being stored
     * @param type   SR type of SR_DO
     */
    public SemanticRegister(String descrp, String valor, String type) {
        this.descrp = descrp;
        this.valor = valor;
        this.type = type;
    }

    
    
    
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    

    public String getDescrp() {
        return descrp;
    }

    public void setDescrp(String descrp) {
        this.descrp = descrp;
    }
    
    
    
}
