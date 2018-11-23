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
    
    public void rememberID(String _token) {
        SemanticRegister SR_Type = new SemanticRegister(SR_Name.ID,_token);
        stack.push(SR_Type);
    }
    
    public void insertST() {
    // TODO: 
    }
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //------------------------------------------------------------------------//
    
    ////////////////////////////////////////////////////////////////////////////
    //Functions used for Translation of Expressions
    ////////////////////////////////////////////////////////////////////////////
    
    public void rememberConst(String _token) {
        SemanticRegister SR_Type = new SemanticRegister(SR_Name.DATA_OBJECT,_token,ValueType.CONST);
        stack.push(SR_Type); 
    }
    
    public void rememberVariable(String _token) {
        // VERIFY IN SYMBOL'S TABLE
        SemanticRegister SR_Type = new SemanticRegister(SR_Name.DATA_OBJECT,_token,ValueType.VAR);
        stack.push(SR_Type);
    }
    
    public void rememberOperator(String _token) {
        SemanticRegister SR_Type = new SemanticRegister(SR_Name.OPERATOR,_token);
        stack.push(SR_Type);
    }
    
    public void binaryEvaluation() {
    SemanticRegister RS_DO1 = this.stack.pop();
    SemanticRegister RS_OP = this.stack.pop();
    SemanticRegister RS_DO2 = this.stack.pop();
    
    //verify types in SYMBOL'S TABLE
    
    //generate code
    
    }
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //------------------------------------------------------------------------//
    
    ////////////////////////////////////////////////////////////////////////////
    //Functions used for Translation of IF 
    ////////////////////////////////////////////////////////////////////////////
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    
}
