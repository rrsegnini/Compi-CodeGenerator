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
        return LocalsST.containsKey(identifier) | GlobalsST.containsKey(identifier);
    }
    
    public boolean localSymbolExists(String identifier){
        //boolean symbolExists = ST.get(identifier) != null;
        return LocalsST.containsKey(identifier);
    }
    
    public boolean globalSymbolExists(String identifier){
        //boolean symbolExists = ST.get(identifier) != null;
        return GlobalsST.containsKey(identifier);
    }
    
    public Symbol getLocalSymbol(String identifier){
        return (Symbol)LocalsST.get(identifier);
    }
    
    public Symbol getGlobalSymbol(String identifier){
        return (Symbol)GlobalsST.get(identifier);
    }
    
    public void putLocal(String identifier, Symbol type){
        LocalsST.put(identifier, type);
    }
    
    public void putGlobal(String identifier, Symbol type){
        GlobalsST.put(identifier, type);
    }
    
    public void resetLocalsST(){
        if (!LocalsST.isEmpty()){
            System.out.println("TABLA DE SIMBOLOS LOCAL: " + LocalsST.toString());
        }
        LocalsST.clear();
    }

    @Override
    public String toString() {
        return GlobalsST.toString();
    }
    
    
    
}
