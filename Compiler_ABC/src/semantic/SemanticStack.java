/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import java.util.ArrayList;

/**
 *
 * @author danielalvarado
 */
public class SemanticStack<SemanticRegister> implements Stack {
    
    ArrayList<SemanticRegister> storedItems = new ArrayList<>();
   

    @Override
    public boolean push(Object value) {
        boolean v = false;
        if (storedItems.isEmpty()) {
            storedItems.add((SemanticRegister) value);
      
            v = true;
        } else {
            storedItems.add((SemanticRegister) value);
     
        }
        
        return v;
    }

    @Override
    public SemanticRegister pop() {
        SemanticRegister semanticRegister = null;
        int size = storedItems.size();
        if (size != 0)
            semanticRegister =  storedItems.remove(size-1);
        
        return semanticRegister;
    }

    @Override
    public SemanticRegister search(String descrp) {
        
        for (int i = 0; i < storedItems.size(); i++) {
            
            SemanticRegister sr = storedItems.get(i); 
           
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
