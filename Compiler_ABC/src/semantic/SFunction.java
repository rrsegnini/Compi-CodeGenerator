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
    public static String returnType;
    public static ArrayList<ArrayList<String>> parameters;

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
            return "Parameters: " + parameters.toString() + "\n" +
                    "Return type: " + returnType;
        }else{
            return "Parameters: " + "No parameters" + "\n" +
                    "Return type: " + returnType;           
        }
    }
    
    
    
}