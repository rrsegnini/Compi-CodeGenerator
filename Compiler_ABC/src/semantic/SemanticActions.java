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
        file = Paths.get("assembler_code.txt");
        //Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //Functions used for Translation of Declarations (Variables)
    ////////////////////////////////////////////////////////////////////////////
    
    public static void rememberType(String _token) {
        SemanticRegister SR_Type = new SemanticRegister(SR_Name.TYPE,_token);
        stack.push(SR_Type); 
    }
    
    public static void rememberID(String _token) {
        SemanticRegister SR_Id = new SemanticRegister(SR_Name.ID,_token);
        stack.push(SR_Id);
    }
    
    public static void insertVarST() {
        SemanticRegister SR_Type = stack.search(SR_Name.TYPE);
        while (stack.top() != SR_Type) {
            SemanticRegister SR_DataObject = stack.pop();
            
        }
        
        
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //------------------------------------------------------------------------//
    
    ////////////////////////////////////////////////////////////////////////////
    //Functions used for Translation of Expressions
    ////////////////////////////////////////////////////////////////////////////
    
    public static void rememberConst(String _token) {
        SemanticRegister SR_Const = new SemanticRegister(SR_Name.DATA_OBJECT,_token,ValueType.CONST);
        stack.push(SR_Const); 
    }
    
    public static void rememberVariable(String _token) {
        // VERIFY IN SYMBOL'S TABLE
        SemanticRegister SR_Var = new SemanticRegister(SR_Name.DATA_OBJECT,_token,ValueType.VAR);
        stack.push(SR_Var);
    }
    
    public static void rememberOperator(String _token) {
        SemanticRegister SR_Op = new SemanticRegister(SR_Name.OPERATOR,_token);
        stack.push(SR_Op);
    }
    
    public static void binaryEvaluation() {
        SemanticRegister SR_DO1 = stack.pop();
        SemanticRegister SR_OP = stack.pop();
        SemanticRegister SR_DO2 = stack.pop();

        //verify types in SYMBOL'S TABLE
        
        /*
        Los DO son de tipo constante los 2?{
        Si: calcular el resultado //constant Folding Ej: 4+5
        Crear RS_DO de tipo constante NO: generar el código para la operación
        Crear RS_DO de tipo dirección con el lugar donde quedo el resultado,
        puede ser una variable temporal o un registro
        */
        
        if (SR_DO1.getType().equals(ValueType.CONST) &&  
                SR_DO2.getType().equals(ValueType.CONST) ) {
        
        } else {
        
        }
       
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
    
    public static void startIf(int _counter) {}
    
    public static void evalIf() {}
    
    public static void startElse() {}
    
    public static void endIf() {}
    
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //------------------------------------------------------------------------//
    
    
}
