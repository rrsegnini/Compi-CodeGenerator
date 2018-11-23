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
    
    private SR_Name descrp;
    private String value;
    private ValueType type;
    private String label1;
    private String label2;

    /**
     * Constructor used for WHILE IF 
     * @param descrp SR name 
     * @param value  SR token being stored 
     */
    public SemanticRegister(SR_Name descrp, String value) {
        this.descrp = descrp;
        this.value = value;
    }
       
    /**
     * Constructor used for SR_DO (constants and variables)
     * @param descrp SR name
     * @param value  SR token being stored
     * @param type   SR type of SR_DO
     */
    public SemanticRegister(SR_Name descrp, String value, ValueType type) {
        this.descrp = descrp;
        this.value = value;
        this.type = type;
    }

    
    public String getValue() {
        return value;
    }

    public void setValue(String valor) {
        this.value = valor;
    }

    public ValueType getType() {
        return type;
    }

    public void setType(ValueType type) {
        this.type = type;
    }
    

    public SR_Name getDescrp() {
        return descrp;
    }

    public void setDescrp(SR_Name descrp) {
        this.descrp = descrp;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }
    
    
    
}
