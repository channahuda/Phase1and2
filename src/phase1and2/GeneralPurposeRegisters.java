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
import java.util.BitSet;

class GeneralPurposeRegisters {

    //register[] generalPurposeRegisters;
    short[] generalPurposeRegisters;

    GeneralPurposeRegisters() {
        //R0 is at index 1, R1 is at index 2 and so on
        //  generalPurposeRegisters = new register[16];
        generalPurposeRegisters = new short[16];
    }

}

class SpecialPurposeRegisters {
    //range of int is from -2,147,483,647 to 2,147,483,647
    //range of short is -32,768 to 32,767
    //memory addresses should be from 0 to 65535

    final short zeroRegister = 0; //zero register
    int codeBase;
    int codeLimit;
    int codeCounter;
    int dataBase;
    int dataLimit;
    int dataCounter;
    int stackBase;
    int stackLimit;
    int stackCounter;
    int programCounter = 0;
    int InstructionRegister;
    BitSet flag;
    // There are six more registers that we can declare according to our use

    SpecialPurposeRegisters() {
        //initially, code base and code counter will be the same
        //initially, data base and data counter will be the same
        //stack is a LIFO structure so we start filiing it by the stack limit.
        //bitSet makes an array of bits
        flag = new BitSet(16);
        stackBase = 65487;
        stackLimit = 65485;
        stackCounter = stackLimit;
        //if stackcounter = stackbase stackoverflow
    }

}

class memory {

    // one frame is of 128 bytes
    // 512 frames can be made in the memory
    byte[] mainMemory;
    boolean[] check;

    memory() {
        mainMemory = new byte[65536];
        check = new boolean[511];
    }
}
