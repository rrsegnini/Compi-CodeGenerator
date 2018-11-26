/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danielalvarado
 */
public class SemanticActions {
    private static SemanticStack stack = new SemanticStack();
    private static String dir = "assembler_code.txt";
    private static BufferedWriter writer;
    
    
    public SemanticActions(BufferedWriter _writer) {
        this.stack = SemanticStack.getInstance();
        writer = _writer;
    }
    
   
    ////////////////////////////////////////////////////////////////////////////
    //Functions used for Translation of Declarations (Variables)
    ////////////////////////////////////////////////////////////////////////////
    
    public static void rememberType(String _token) {
        SemanticRegister SR_Type = new SemanticRegister(SR_Name.TYPE,_token);
        stack.push(SR_Type); 
    }
    
    public static void rememberVar(String _token) {
        SemanticRegister SR_Id = new SemanticRegister(SR_Name.ID,_token);
        stack.push(SR_Id);
    }
    
    public static void insertVarST() {
        /// MAL ES QUE NO SE COMO HACER PARA INSERTARLA CORRECTAMENTE
        // VAR TIENE UN VALUE, EL VALUE ES EL TOKEN. ES DECIR SI ES VAR A ES A
        // ESO ES LO QUE ESTA EN EL REGISTRO SEMANTICO
        // PERO ENTONCES TENGO QUE GUARDAR EL VALOR
        // ENTONCES NO SE QUE TENGO QUE MANDARLE AL PUT.
        // SI TECNICAMENTE EL SYMBOL YA TIENE LA VARIABLE EN SI, ES DECIR EL 
        // PERDON POR LA MASYUSCULA FUE SIN QUERER Y CUANDO ME DI CUENTA NO IBA A 
        // BORRARLO Y A VOLVERLO A ESCRIBID XD JAJAJAJA
        
        //SemanticRegister SR_Type = stack.search(SR_Name.TYPE);
        SemanticRegister SR_Type = stack.pop();
        while (stack.top() != null) {
            SemanticRegister SR_DataObject = stack.pop();
            SVariable var = new SVariable(SR_Type.getToken());
            SymbolTable ts = new SymbolTable();
            ts.put(SR_DataObject.getToken(), var);
            
        }
        
    }
    
    public static void insertConstTS(String _value) {
        SemanticRegister constantSR = stack.pop();
        SConstant constant = new SConstant(_value);
        SymbolTable ts = new SymbolTable();
        ts.put(constantSR.getToken(), constant);

    }
    
    public static void insertFunctionST() {
        
        ArrayList parameters = new ArrayList<>();
        SemanticRegister SR_DataObject = null;
        SemanticRegister SR_ParameterType = null;
        SemanticRegister SR_ReturnType = stack.pop();
        
        while (stack.top() != null) {
            ArrayList parameter = new ArrayList<String>();
            
            SR_DataObject = stack.pop();
            if (stack.top() != null){                
                SR_ParameterType = stack.pop();
                parameter.add(SR_DataObject.getToken());
                parameter.add(SR_ParameterType.getToken());
                parameters.add(parameter);             
            }else{
                SFunction function = new SFunction(SR_ReturnType.getToken(),
                                        parameters);
                SymbolTable ST = new SymbolTable();
                ST.put(SR_DataObject.getToken(), function);
            }
            
            
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
        
        String resultStr = "ax";
        
        //verify types in SYMBOL'S TABLE
        
        /*
        Los DO son de tipo constante los 2?{
        Si: calcular el resultado //constant Folding Ej: 4+5
        Crear RS_DO de tipo constante NO: generar el código para la operación
        Crear RS_DO de tipo dirección con el lugar donde quedo el resultado,
        puede ser una variable temporal o un registro
        */
        
        if (SR_DO1 != null && SR_DO2 != null){
            // Executes Constant Folding
            if (SR_DO1.getType().equals(ValueType.CONST) &&  
                    SR_DO2.getType().equals(ValueType.CONST) ) {
                    int res = SemanticActions.constFoldint(SR_DO2.getToken(), SR_DO1.getToken(),
                            SR_OP.getToken());
                    resultStr = Integer.toString(res);
            } else {
                //GENERATES CODE FOR EXP
                //generate code where SR_DO2 is the dividend and SR_DO1 is the divisor in div
                SemanticActions.generateEvalCode(SR_DO2.getToken(), SR_DO1.getToken(), SR_OP.getToken());
                
            }
            SemanticRegister SR_Type = new SemanticRegister(SR_Name.DATA_OBJECT,resultStr);
            stack.push(SR_Type);
        }
    
    }
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //------------------------------------------------------------------------//
    
    ////////////////////////////////////////////////////////////////////////////
    //Functions used for Var Assignment
    ////////////////////////////////////////////////////////////////////////////
    
    public static void assignVa() {
        SemanticRegister SR_DO1_var = stack.pop();
        SemanticRegister SR_OP = stack.pop();
        SemanticRegister SR_DO2_value2beAssign = stack.pop();
          
        // a = 2
        // a -> var
        // 2 -> value2beAssign
        String value2beAssign = SR_DO2_value2beAssign.getToken();
        String var = SR_DO1_var.getToken();
        
        try {
            SemanticActions.assignmentCode(var, value2beAssign);
        } catch (IOException ex) {
            Logger.getLogger(SemanticActions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //------------------------------------------------------------------------//
    
    
    //------------------------------------------------------------------------//
    
    ////////////////////////////////////////////////////////////////////////////
    //Functions used for Translation of IF 
    ////////////////////////////////////////////////////////////////////////////
    
    public static void startIf(int _counter) {
        SemanticRegister SR_IF = new SemanticRegister(SR_Name.IF,"if");
        SR_IF.setLabel1("else_label" + Integer.toString(_counter));
        SR_IF.setLabel2("exit_label" + Integer.toString(_counter));
        stack.push(SR_IF);
        
    }
    
    
    public static void evalIf() {
        //Usa este metodo aca generateIfCode(String var1, String var2, String op ,String label)
        SemanticRegister SR_IF = stack.search(SR_Name.IF);
        String elseLabel = SR_IF.getLabel1();
        SemanticRegister SR_DO1 = stack.pop();
        SemanticRegister SR_OP = stack.pop();
        SemanticRegister SR_DO2 = stack.pop();
        SemanticActions.generateCondCode(SR_DO1.getToken(), SR_DO2.getToken(), SR_OP.getToken(), elseLabel);
    
    }
    
    public static void startElse() {
        try {
            SemanticRegister SR_IF = stack.search(SR_Name.IF);
            //writes the jmp to the exit label
            writer.write("JMP"+SR_IF.getLabel2() +"\n");
            // writes the else label
            writer.write(SR_IF.getLabel1() +":"+"\n");
            
        }catch (Exception e) {
        }
    }
    
    public static void endIf() {
        try {
            SemanticRegister SR_IF = stack.search(SR_Name.IF);
            // writes the else label
            writer.write(SR_IF.getLabel2() +":"+"\n");
            
        }catch (Exception e) {
        }
        
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //------------------------------------------------------------------------//
    
    
    ////////////////////////////////////////////////////////////////////////////
    //Functions used for WHILE Assignment
    ////////////////////////////////////////////////////////////////////////////
    
    public static void startWhile(int _counter) {
        SemanticRegister SR_WHILE = new SemanticRegister(SR_Name.WHILE,"while");
        SR_WHILE.setLabel1("while_label" + Integer.toString(_counter));
        SR_WHILE.setLabel2("exit_label" + Integer.toString(_counter));
        
         try {
            writer.write(SR_WHILE.getLabel1() +":"+"\n");
            
        }catch (Exception e) {
        }
        
        stack.push(SR_WHILE);
    }

    public static void evalWhile() {
        SemanticRegister SR_WHILE = stack.search(SR_Name.WHILE);
        String exitLabel = SR_WHILE.getLabel2();
        SemanticRegister SR_DO1 = stack.pop();
        SemanticRegister SR_OP = stack.pop();
        SemanticRegister SR_DO2 = stack.pop();
        SemanticActions.generateCondCode(SR_DO1.getToken(), SR_DO2.getToken(), SR_OP.getToken(), exitLabel);
    }
    
    public static void endWhile() {
        try {
            SemanticRegister SR_IF = stack.search(SR_Name.IF);
            //writes the jump to the start_while label
            writer.write("JMP"+SR_IF.getLabel1() +"\n");
            // writes the e label
            writer.write(SR_IF.getLabel2() +":"+"\n");
            
        }catch (Exception e) {
            
        }
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    //------------------------------------------------------------------------//
    
 
    public static int constFoldint(String c1, String c2, String op) {
        int res = 0;
        switch (op) {
                case "+": 
                    res = Integer.parseInt(c1) + Integer.parseInt(c2);
                    break;
                case "-": 
                    res = Integer.parseInt(c1) - Integer.parseInt(c2);
                    break;
                case "DIV": 
                    res = Integer.parseInt(c1) / Integer.parseInt(c2);
                    break;
                case "/":
                    res = Integer.parseInt(c1) / Integer.parseInt(c2);
                    break;
                case "mod":
                    res = Integer.parseInt(c1) % Integer.parseInt(c2);
                    break;
                case "*":
                    res = Integer.parseInt(c1) * Integer.parseInt(c2);
                    break;

            }       
        return res;
    }
    
    public static void generateEvalCode(String var1, String var2, String op) {
        try {
            switch (op) {
                case "+": 
                    SemanticActions.addCode(var1, var2);
                    break;
                case "-": 
                    SemanticActions.subCode(var1, var2);
                    break;
                case "DIV": 
                    SemanticActions.divCode(var1, var2);
                    break;
                case "/":
                    SemanticActions.divCode(var1, var2);
                    break;
                case "mod":
                    SemanticActions.modCode(var1, var2);
                    break;
                case "*":
                    SemanticActions.multCode(var1, var2);
                    break;

            }
        } catch (Exception e) {}
    }
    
    public static void assignmentCode(String var, String res) throws IOException {
        writer.write("mov " + var + "," + res  +"\n");
    }
    
    
    public static void addCode(String var1, String var2) throws IOException {
        writer.write("mov " + "ax," + var1  +"\n");
        writer.write("add " + "ax," + var2  +"\n");   
    }
    public static void subCode(String var1, String var2) throws IOException  {
        writer.write("mov " + "ax," + var1  +"\n");
        writer.write("sub " + "ax," + var2  +"\n"); 
    }
    public static void multCode(String var1, String var2) throws IOException  {
        writer.write("mov " + "ax," + var1  +"\n");
        writer.write("mul " + var2  +"\n"); 
    }
    public static void divCode(String dividend, String divisor) throws IOException  {
        writer.write("mov " + "dx,0" +"\n");
        writer.write("mov " + "ax,," + dividend  +"\n");
        writer.write("mov " + "cx," + divisor  +"\n"); 
        writer.write("div " + "cx" +"\n"); 
        
    }
    public static void modCode(String dividend, String divisor) throws IOException  {
        writer.write("mov " + "dx,0" +"\n");
        writer.write("mov " + "ax,," + dividend +"\n" );
        writer.write("mov " + "cx," + divisor  +"\n"); 
        writer.write("div " + "cx" +"\n"); 
        writer.write("mov " + "ax,dx"  +"\n");
    }
    
  
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    
    public static void generateCondCode(String var1, String var2, String op ,String label) {
        try {
            writer.write("cmp " + var1 + "," + var2 +"\n");
            switch (op) {
                case "=": 
                    writer.write("JNE" + label +"\n");
                    break;
                case ">": 
                    writer.write("JNGE" + label +"\n");
                    break;
                case "<": 
                    writer.write("JNLE" + label +"\n");        
                    break;
                case "!=":
                    writer.write("JE" + label +"\n");
                    break;
                case "<=":
                    writer.write("JNE" + label +"\n");
                    break;
                case ">=":
                    writer.write("JNG" + label +"\n");
                    break;

            }
        } catch (Exception e) {}
    }
    
    
    
    
}
