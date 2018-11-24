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
public class SemanticStack implements Stack {
    
    private ArrayList<SemanticRegister> storedItems = new ArrayList<>();
   
    
    private static SemanticStack stack;
    
    
    public SemanticStack() {}
    
    public static SemanticStack getInstance() {
        if (stack.equals(null)) {
            stack = new SemanticStack();
        }
        return stack;
    }

    @Override
    public boolean push(SemanticRegister value) {
        boolean v = false;
        if (storedItems.isEmpty()) {
            storedItems.add(value);
            v = true;
        } else {
            storedItems.add(value);
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
    public SemanticRegister search(SR_Name descrp) {
        SemanticRegister sr = null;
        for (int i = 0; i < storedItems.size(); i++) {
            SemanticRegister sr1 = storedItems.get(i);
            if (sr1.getDescrp().equals(this))
                sr = sr1; 
            
        }
        return sr;
    }

    @Override
    public void clear() {
        this.storedItems.clear();
    }

    @Override
    public boolean isEmpty() {
       return this.storedItems.isEmpty();
    }
    
}
