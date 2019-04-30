/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nanookreporter;

/**
 *
 * @author leggettr
 */
public class BlastMatchCriteria {
    double maxE;
    double minId;
    int minLength;
    
    public BlastMatchCriteria(double e, double i, int l) {    
        maxE = e;
        minId = i;
        minLength = l;
    }
    
    public double getMaxE() {
        return maxE;
    }
    
    public double getMinId() {
        return minId;
    }
    
    public int getMinLength() {
        return minLength;
    }
}
