/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author danielalvarado
 */
public class SemanticActions {
    private static SemanticStack stack;
    private static Path file;
    
    
    public SemanticActions() {
        this.stack = SemanticStack.getInstance();
    }
    
    
    public static void openFile() {
        this.file = Paths.get("assembler_code.txt");
        //Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //Functions used for Translation of Declarations
    ////////////////////////////////////////////////////////////////////////////
    
    public static void rememberType(String _token) {
        SemanticRegister SR_Type = new SemanticRegister(SR_Name.TYPE,_token);
        stack.push(SR_Type); 
    }
    
    public static void rememberID(String _token) {
        SemanticRegister SR_Id = new SemanticRegister(SR_Name.ID,_token);
        stack.push(SR_Id);
    }
    
    public static void insertST() {
        SemanticRegister SR_Type = this.stack.search(SR_Name.TYPE);
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
    
    public void startIf(int _counter) {}
    
    public void evalIf() {}
    
    public void startElse() {}
    
    public void endIf() {}
    
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //------------------------------------------------------------------------//
    
    
}
