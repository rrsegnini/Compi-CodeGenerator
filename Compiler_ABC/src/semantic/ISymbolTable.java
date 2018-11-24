/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;
import java.util.Hashtable;
import parser.sym;
/**
 *
 * @author CASA
 */
public interface ISymbolTable {
    public static Hashtable ST = new Hashtable<String, Symbol>();
    public boolean symbolExists(String identifier);
    public Symbol getSymbol(String identifier);
}
