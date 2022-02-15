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
public class PCB {

    private int process_id;
    private int process_priority;
    private double code_size;
    private double data_size;
    private String process_file_name;
    private GeneralPurposeRegisters gpr = new GeneralPurposeRegisters();
    private SpecialPurposeRegisters spr = new SpecialPurposeRegisters();
    private int execution_time = 0;
    private int waiting_time = 0;
    private frame[] dataPageTable;
    private frame[] codepageTable;

    public PCB(int pid, int pp, double cs, double ds, String pfn, frame[] dataPageTable, frame[] codePageTable) {
        process_id = pid;
        process_priority = pp;
        code_size = cs;
        data_size = ds;
        process_file_name = pfn;
        this.dataPageTable = dataPageTable;
        this.codepageTable = codePageTable;
        //execution_time=ex;
        //waiting_time=wt;
    }

    public int getProcess_id() {
        return process_id;
    }

    public int getProcess_priority() {
        return process_priority;
    }

    public void setProcess_priority(int i) {
        process_priority = i;
    }

    public double getCode_size() {
        return code_size;
    }

    public double getData_size() {
        return data_size;
    }

    public String getProcess_file_name() {
        return process_file_name;
    }

    public int getExecution_time() {
        return execution_time;
    }

    public void setExecution_time(int execution_time) {
        this.execution_time = execution_time;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public GeneralPurposeRegisters getGPR() {
        return gpr;
    }

    public SpecialPurposeRegisters getSPR() {
        return spr;
    }

    public void updateGPR(GeneralPurposeRegisters gpr) {
        this.gpr = gpr;
    }

    public void updateSPR(SpecialPurposeRegisters spr) {
        this.spr = spr;
    }

    public void UpdateProgramCounter(int value) {
        spr.programCounter = value;
    }

    public frame[] getCodePageTable() {
        //System.out.println("code page " + codepageTable);
        return codepageTable;
    }

    public frame[] getDataPageTable() {
        return dataPageTable;
    }

    public void UpdateDataCounter(int value) {
        spr.dataCounter = value;
    }

}
