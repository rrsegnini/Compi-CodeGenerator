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
public class SProcedure {
    public static ArrayList<ArrayList<String>> parameters;
    
    public SProcedure(ArrayList<ArrayList<String>> _parameters){
        this.parameters = _parameters;
    }
    
    

    @Override
    public String toString() {
        if (parameters != null){
            return "Parameters: " + parameters.toString() + "\n";
        }else{
            return "Parameters: " + "No parameters" + "\n";           
        }
    }
    
}
