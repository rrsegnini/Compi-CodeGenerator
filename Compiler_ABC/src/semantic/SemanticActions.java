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
public class SemanticActions {
    SemanticStack<SemanticRegister> stack;
    
    
    public SemanticActions() {
        this.stack = new SemanticStack<SemanticRegister>();
    }
    
    public SemanticActions(SemanticStack<SemanticRegister> _stack) {
        this.stack = _stack;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //Functions used for Translation of Declarations
    ////////////////////////////////////////////////////////////////////////////
    
    public void rememberType(String _token) {
        SemanticRegister SR_Type = new SemanticRegister(SR_Name.TYPE,_token);
        stack.push(SR_Type);
    
    }
    
    public void rememberID() {}
    
    public void insertST() {}
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    
}
