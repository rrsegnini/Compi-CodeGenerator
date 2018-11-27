/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;
import java.util.ArrayList;
/**
 *
 * @author CASA
 */
public class SFunction extends Symbol{
    public String returnType;
    public ArrayList<ArrayList<String>> parameters;

    public SFunction(String _returnType) {
        this.returnType = _returnType;
    }
    
    public SFunction(String _returnType, 
            ArrayList<ArrayList<String>> _parameters){
        this.returnType = _returnType;
        this.parameters = _parameters;
        
    }
    
    

    @Override
    public String toString() {
        if (parameters != null){
            return "[Parameters: " + parameters.toString() + " " +
                    "Return type: " + returnType + "]";
        }else{
            return "[Parameters: " + "No parameters" + " " +
                    "Return type: " + returnType + "]";           
        }
    }
    
    
    
}
