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
        SemanticRegister SR_Id = new SemanticRegister(SR_Name.ID,_token);
        stack.push(SR_Id);
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
        SemanticRegister SR_Const = new SemanticRegister(SR_Name.DATA_OBJECT,_token,ValueType.CONST);
        stack.push(SR_Const); 
    }
    
    public void rememberVariable(String _token) {
        // VERIFY IN SYMBOL'S TABLE
        SemanticRegister SR_Var = new SemanticRegister(SR_Name.DATA_OBJECT,_token,ValueType.VAR);
        stack.push(SR_Var);
    }
    
    public void rememberOperator(String _token) {
        SemanticRegister SR_Op = new SemanticRegister(SR_Name.OPERATOR,_token);
        stack.push(SR_Op);
    }
    
    public void binaryEvaluation() {
        SemanticRegister SR_DO1 = this.stack.pop();
        SemanticRegister SR_OP = this.stack.pop();
        SemanticRegister SR_DO2 = this.stack.pop();

        //verify types in SYMBOL'S TABLE
        
        /*
        Los DO son de tipo constante los 2?{
        Si: calcular el resultado //constant Folding Ej: 4+5
        Crear RS_DO de tipo constante NO: generar el código para la operación
        Crear RS_DO de tipo dirección con el lugar donde quedo el resultado,
        puede ser una variable temporal o un registro
        */
       
        //generate code

        SemanticRegister SR_Type = new SemanticRegister(SR_Name.DATA_OBJECT,"rx");
        stack.push(SR_Type);
    
    }
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //------------------------------------------------------------------------//
    
    ////////////////////////////////////////////////////////////////////////////
    //Functions used for Translation of IF 
    ////////////////////////////////////////////////////////////////////////////
    
    public void startIf() {}
    
    public void evalIf() {}
    
    public void startElse() {}
    
    public void endIf() {}
    
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //------------------------------------------------------------------------//
    
    
}
