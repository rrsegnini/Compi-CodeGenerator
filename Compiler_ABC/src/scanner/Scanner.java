package scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import src.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import parser.*;
import semantic.*;
import java_cup.runtime.Symbol;
/**
 *
 * @author CASA
 */
public class Scanner {
    
    // objeto tabla de tokens
//    public static TokenTable table = new TokenTable();
    
    private static void printScannedTokens(ScannerABC analizadorJFlex){
        try{
            while(true){
                    // Obtener el token analizado y mostrar su información
                    Symbol token = analizadorJFlex.next_token();
                    if (!analizadorJFlex.existenTokens())
                    break;
                    System.out.println(sym.terminalNames[token.sym]  + " " + token.value.toString());
                    //Object result = p.parse().value; 
            }
        }catch(Exception e){}
            
    }
    
    private static void printLexicalErrors(ScannerABC analizadorJFlex){
        try{
            while(true){
                    // Obtener el token analizado y mostrar su información
                    Symbol token = analizadorJFlex.next_token();
                    if (!analizadorJFlex.existenTokens())
                    break;
            }
            
        }catch(Exception e){}
            
    }
    public static void main(String [ ] args){

        try{
            File file = new File("src/tests/PruebaFunciones.txt");

            String path = file.getAbsolutePath();
            String archivo = path;
            // Se trata de leer el archivo y analizarlo en la clase que se ha creado con JFlex
            BufferedReader buffer = new BufferedReader(new FileReader(archivo));
            ScannerABC analizadorJFlex = new ScannerABC(buffer);
            parser p = new parser(analizadorJFlex); 
            printScannedTokens(analizadorJFlex);
            printLexicalErrors(analizadorJFlex);
            
            
            buffer = new BufferedReader(new FileReader(archivo));
            analizadorJFlex = new ScannerABC(buffer);
            p = new parser(analizadorJFlex);
            
            // creando buffered writer 
            BufferedWriter writer = new BufferedWriter(new FileWriter("assembler_code.txt"));
            SemanticActions.setWriter(writer);
            
            Object result = p.parse().value;
            SymbolTable ST = new SymbolTable();
            System.out.println(ST.toString());
            if (ST.symbolExists("b")){
                System.out.println("Existe :)");
            }
            
            /*ST.put("ID", new SFunction("INT"));
            if (ST.symbolExists("ID")){
                System.out.println(ST.getSymbol("ID"));
            }*/
            
            writer.close();
            
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }
}
