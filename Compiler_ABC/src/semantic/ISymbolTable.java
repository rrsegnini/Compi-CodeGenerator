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
    public static Hashtable GlobalsST = new Hashtable<String, Symbol>();
    public static Hashtable LocalsST = new Hashtable<String, Symbol>();
    public boolean symbolExists(String identifier);
    public boolean localSymbolExists(String identifier);
    public boolean globalSymbolExists(String identifier);
    //public Symbol getSymbol(String identifier);
    public Symbol getLocalSymbol(String identifier);
    public Symbol getGlobalSymbol(String identifier);
    public void putLocal(String identifier, Symbol type);
    public void putGlobal(String identifier, Symbol type);
    public void resetLocalsST();
}
