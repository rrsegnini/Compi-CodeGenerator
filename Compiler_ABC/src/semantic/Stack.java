/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

/**
 *
 * @author danielalvarado
 * @param <RS> Object stored in the stack (Semantic Register)
 */
public interface Stack<SR> {
    
    boolean push(SR value);
    
    SR pop();
    
    SR search();
    
    boolean contains();
    
    void clear();
    
    boolean isEmpty();
}
