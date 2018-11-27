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
    private static int count;


    public SemanticActions(BufferedWriter _writer) {
        this.stack = SemanticStack.getInstance();
        writer = _writer;
    }

    public static void setWriter(BufferedWriter writer) {
        SemanticActions.writer = writer;
    }

    public static void setCount(int count) {
        SemanticActions.count = count;
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

    public static void insertGlobalVarST(int line) {
        SemanticRegister SR_Type = stack.pop();
        while (stack.top() != null) {
            SemanticRegister SR_DataObject = stack.pop();
            SVariable var = new SVariable(SR_Type.getToken());
            SymbolTable ts = new SymbolTable();
            
            if (ts.symbolExists(SR_Type.getToken())) {
                System.err.println("ERROR EN LA LINEA: " + line + "LA VARIABLE " +SR_Type.getToken() + "FUE DECLARADA PREVIAMENTE" );
                //System.out.println("ERROR LA VARIABLE " +SR_Type.getToken() + "FUE DECLARADA PREVIAMENTE" );
            } else {
                ts.putGlobal(SR_DataObject.getToken(), var);
            }
            
            

        }

    }

    public static void insertLocalVarST(int line) {       
        SemanticRegister SR_Type = stack.pop();

        while (stack.top() != null) {
            SemanticRegister SR_DataObject = stack.pop();
            String token = SR_Type.getToken();
            SVariable var = new SVariable(token);
            SymbolTable ts = new SymbolTable();
            if (ts.symbolExists(SR_Type.getToken())) {
                System.err.println("ERROR EN LA LINEA " + line + ": " + SR_Type.getToken() + "FUE DECLARADA PREVIAMENTE" );
            } else {
                ts.putLocal(SR_DataObject.getToken(), var);
            }
            

        }

    }

    public static void insertGlobalConstTS(String _value,int line) {
        SemanticRegister constantSR = stack.pop();
        SConstant constant = new SConstant(_value);
        SymbolTable ts = new SymbolTable();
        if (ts.symbolExists(constantSR.getToken())) {
                System.err.println("ERROR EN LA LINEA " + line  + ": LA VARIABLE " +constantSR.getToken() + "FUE DECLARADA PREVIAMENTE" );
            } else {
                ts.putGlobal(constantSR.getToken(), constant);
        }
            
        

    }

    public static void insertLocalConstTS(String _value,int line) {
        SemanticRegister constantSR = stack.pop();
        SConstant constant = new SConstant(_value);
        SymbolTable ts = new SymbolTable();
        if (ts.symbolExists(constantSR.getToken())) {
                System.err.println("ERROR EN LA LINEA " +  line + ": LA VARIABLE " +constantSR.getToken() + "FUE DECLARADA PREVIAMENTE" );
            } else {
                ts.putLocal(constantSR.getToken(), constant);
        }

    }

    public static void insertFunctionST(int line) {

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
                if (ST.symbolExists(SR_DataObject.getToken())) {
                System.out.println("ERROR EN LA LINEA " + line +   ": LA VARIABLE " +SR_DataObject.getToken() + "FUE DECLARADA PREVIAMENTE" );
            } else {
                ST.putGlobal(SR_DataObject.getToken(), function);
        }

            }


        }

    }
    
    public static void functionIdError(String _value) {
        SymbolTable ts = new SymbolTable();
        if (!ts.symbolExists(_value)) {
                System.out.println("ERROR LA VARIABLE " + _value + " NO HA SIDO DECLARADA" );
        }
    
    }



    public static void insertProcedureST(int line) {

        ArrayList parameters = new ArrayList<>();
        SemanticRegister SR_DataObject = null;
        SemanticRegister SR_ParameterType = null;
        //SemanticRegister SR_ReturnType = stack.pop();

        while (stack.top() != null) {
            ArrayList parameter = new ArrayList<String>();

            SR_DataObject = stack.pop();
            if (stack.top() != null){
                SR_ParameterType = stack.pop();
                parameter.add(SR_DataObject.getToken());
                parameter.add(SR_ParameterType.getToken());
                parameters.add(parameter);
            }else{
                SProcedure procedure = new SProcedure(parameters);
                SymbolTable ST = new SymbolTable();
                if (ST.symbolExists(SR_DataObject.getToken())) {
                    System.out.println("ERROR LA VARIABLE " +SR_DataObject.getToken() + "FUE DECLARADA PREVIAMENTE" );
                } else {
                    ST.putGlobal(SR_DataObject.getToken(), procedure);
                    }
            }


        }

    }

    public static void resetLocalsST() {
        SymbolTable ST = new SymbolTable();
        ST.resetLocalsST();
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

    public static void rememberVariable(String _token,int line) {
        // VERIFY IN SYMBOL'S TABLE
        SymbolTable ST = new SymbolTable();
        if (!ST.symbolExists(_token)) {
            System.err.println("ERROR EN LA LINEA " + line + ": LA VARIABLE " + _token + " NO HA SIDO DECLARADA" );
        }

        SemanticRegister SR_Var = new SemanticRegister(SR_Name.DATA_OBJECT,_token,ValueType.VAR);
        stack.push(SR_Var);
    }

    public static void rememberOperator(String _token) {
        SemanticRegister SR_Op = new SemanticRegister(SR_Name.OPERATOR,_token);
        stack.push(SR_Op);
    }

    
    
    public static void binaryEvaluation() {
        SemanticActions.binaryEvaluation(false);
    }
    
    
    public static void binaryEvaluation(boolean _isAParameter) {
        if (_isAParameter) {
            
        }else {
 

            SemanticRegister SR_DO1 = stack.pop();
            SemanticRegister SR_OP = stack.pop();

            //Eval unaria i++
            if (stack.top().getDescrp().equals(SR_Name.DATA_OBJECT) && SR_OP.getDescrp().equals(SR_Name.OPERATOR)
                    && SR_DO1.getDescrp().equals(SR_Name.OPERATOR)    ) {
                SemanticRegister SR_DO = stack.pop();
                String op1 = SR_DO1.getToken();
                String op2 = SR_OP.getToken();
                String var1 =  SR_DO.getToken();

                SemanticActions.generateUnEvalCode( var1, op1, op2);

            } else {
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

                    // if :=
                    if (stack.top().getDescrp().equals(SR_Name.OPERATOR)) {
                        SemanticRegister SR_Object = new SemanticRegister(SR_Name.DATA_OBJECT,resultStr);
                        stack.push(SR_Object);
                        SemanticActions.assignVar();

                    } else {
                        SemanticRegister SR_Object = new SemanticRegister(SR_Name.DATA_OBJECT,resultStr);
                        stack.push(SR_Object);
                    }


                }



            }

        }
    }

    
    public static void parameterErrorVerification(String _token) {
        
    }


    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    //------------------------------------------------------------------------//

    ////////////////////////////////////////////////////////////////////////////
    //Functions used for Var Assignment
    ////////////////////////////////////////////////////////////////////////////

    public static void assignVar() {
        SemanticRegister SR_DO2_value2beAssign = stack.pop();
        SemanticRegister SR_OP = stack.pop();
        SemanticRegister SR_DO1_var = stack.pop();

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
        SR_IF.setLabel1("else_label" + Integer.toString(count));
        SR_IF.setLabel2("exit_label" + Integer.toString(count));
        stack.push(SR_IF);
        count++;

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
            stack.pop(); //Saca el SR_IF

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
        SR_WHILE.setLabel1("while_label" + Integer.toString(count));
        SR_WHILE.setLabel2("exit_label" + Integer.toString(count));
        count++;

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
        
        //System.out.println("EL STACK AL FINAL DEL WHILE: " + stack);

    }

    public static void endWhile() {
        try {
            SemanticRegister SR_IF = stack.search(SR_Name.WHILE);
            //writes the jump to the start_while label
            writer.write("JMP"+SR_IF.getLabel1() +"\n");
            // writes the e label
            writer.write(SR_IF.getLabel2() +":"+"\n");
            stack.pop(); //Saca la el SR_WHILE    
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

    public static void generateUnEvalCode(String var1, String op1, String op2) {
        try {
            switch (op1) {
                case "+":
                    SemanticActions.incCode(var1);
                    break;
                case "-":
                    SemanticActions.decCode(var1);
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


    public static void incCode(String var1) throws IOException {
        writer.write("INC " + var1);

    }

    public static void decCode(String var1) throws IOException  {
        writer.write("DEC " + var1);
    }






    public static void generateCondCode(String var1, String var2, String op ,String label) {
        try {
            writer.write("cmp " + var1 + "," + var2 +"\n");
            switch (op) {
                case "=":
                    writer.write("JNE " + label +"\n");
                    break;
                case ">":
                    writer.write("JNGE " + label +"\n");
                    break;
                case "<":
                    writer.write("JNLE " + label +"\n");
                    break;
                case "!=":
                    writer.write("JE " + label +"\n");
                    break;
                case "<=":
                    writer.write("JNE " + label +"\n");
                    break;
                case ">=":
                    writer.write("JNG " + label +"\n");
                    break;

            }
        } catch (Exception e) {}
    }




}
