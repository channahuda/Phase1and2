/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package phase1and2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/**
 *
 * @author Muhammad Usman
 */
public class process {

    // private codePageTable code_pt;
    //private dataPageTable data_pt;
    Byte[] data;
    Byte[] code;
    private PCB pcb;
    private int pointer = 0;

    public process(int priority, int process_id, double data_size, double code_size, String process_file_name, Byte[] data, Byte[] code,
            frame[] dataPageTable, frame[] codePageTable) {
        this.data = data;
        this.code = code;
        pcb = new PCB(process_id, priority, code_size, data_size, process_file_name, dataPageTable, codePageTable);
        // System.out.println("data page table " + dataPageTable);
        // System.out.println("code page table " + codePageTable);
        //System.out.println("priority " + priority);
        //System.out.println("code_size " + code_size);
        //System.out.println("process_file_name " + process_file_name);
        //System.out.println("Data");
        //for (int i = 0; i < data.length; i++) {
        //  System.out.print(data[i] + " ");
        //}
        //System.out.println();
        //System.out.println("Code");
        //for (int i = 0; i < code.length; i++) {
        //  System.out.print(code[i] + " ");
        //}
        //    System.out.println();
    }

    public PCB getPCB() {
        return pcb;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int value) {
        pointer = value;
    }

    public Byte[] getCode() {
        return code;
    }

    public String toString() {
        return "" + getPCB().getProcess_id();
    }

}
