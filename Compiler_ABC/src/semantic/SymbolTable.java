/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import java.util.Hashtable;
import parser.sym;

/**
 *
 * @author CASA
 */
public class SymbolTable implements ISymbolTable{
    public boolean symbolExists(String identifier){
        //boolean symbolExists = ST.get(identifier) != null;
        return ST.containsKey(identifier);
    }
    
    public Symbol getSymbol(String identifier){
        return (Symbol)ST.get(identifier);
    }
    
    public void put(String identifier, Symbol type){
        ST.put(identifier, type);
    }

    @Override
    public String toString() {
        return ST.toString();
        
        /*String toStringRet = "";   
        java.util.Set<String> keys = ST.keySet();
        for(String key: keys){           
            
            toStringRet += key.toString() + "\n";
        }
        return toStringRet;*/
    }
    
    
    
}
