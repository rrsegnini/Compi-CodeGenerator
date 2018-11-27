/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

/**
 *
 * @author CASA
 */
public class SVariable extends Symbol{
    public String type;
    
    public SVariable(String type){
        this.type = type;
    }
    
    @Override
    public String toString(){
        return type;
    }
}
