/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

/**
 *
 * @author CASA
 */
public class SConstant extends Symbol{
    public static String value; 

    public SConstant(String _value) {
        value = _value;
    }

    @Override
    public String toString() {
        return value;
    }
    
    
    
    
    
}
