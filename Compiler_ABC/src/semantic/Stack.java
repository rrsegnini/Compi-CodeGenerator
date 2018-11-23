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
public interface Stack<RS> {
    
    boolean push(RS value);
    
    RS pop();
    
    RS search();
    
    boolean contains();
    
    void clear();
    
    boolean isEmpty();
}
