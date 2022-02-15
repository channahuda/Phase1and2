/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phase1and2;

/**
 *
 * @author Huda
 */
public class codePageTable {

    frame[] pageTable;

    codePageTable(double codeSize) {

        //1st 128 bytes of code will be stored in frame 1 and so on
        pageTable = new frame[(int) Math.ceil(codeSize / 128)];
        
    }
}
