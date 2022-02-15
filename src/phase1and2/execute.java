/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phase1and2;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static phase1and2.loadProcess.mem;
import static phase1and2.start.R;
import static phase1and2.start.ps;

/**
 *
 * @author Huda
 */
public class execute {
    // this class fetches one instruction and executes it

    public static SpecialPurposeRegisters spr;
    instructions ins = new instructions();
    public static GeneralPurposeRegisters gpr;
    frame[] codePageTable;

    public void executeProcess(process p) {
        gpr = p.getPCB().getGPR();
        spr = p.getPCB().getSPR();
        codePageTable = p.getPCB().getCodePageTable();
        //System.out.println(p.getCode()[0]);
        //System.out.println("codePageTable" + codePageTable);
        fetch(p);
    }

    public void fetch(process p) {
        int dataRangeStart = -1;
        int dataRangeEnd = -1;
        if (p.getPCB().getDataPageTable().length != 0) {
            dataRangeStart = p.getPCB().getDataPageTable()[0].startingAddress;
            dataRangeEnd = p.getPCB().getDataPageTable()[p.getPCB().getDataPageTable().length - 1].endingAddress;
        }
        int codeRangeStart = p.getPCB().getCodePageTable()[0].startingAddress;
        int codeRangeEnd = p.getPCB().getCodePageTable()[p.getPCB().getCodePageTable().length - 1].endingAddress;
        System.out.println(p.getPCB().getProcess_file_name() + " ");
        int pc = spr.programCounter;
        System.out.println("pc " + pc);
        int pointer = p.getPointer();
        System.out.println("pointer " + pointer);
        // System.out.println("opcode coming " + p.getCode()[pointer]);
        int index = pc / 128;
        int pcMap = p.getPCB().getCodePageTable()[index].startingAddress + pc;
        //System.out.println("pcMap " + pcMap);
        int dataCounter = spr.dataCounter;
        index = dataCounter / 128;
        int dcMap = 0;
        if (p.getPCB().getDataPageTable().length != 0) {
            dcMap = p.getPCB().getDataPageTable()[index].startingAddress;
        }
        //if index > array length - 1, throw error. Will do this while doing errors.
        byte opcode = mem.mainMemory[pcMap];
        System.out.println("opcode " + opcode);
        if (opcode == 22 || opcode == 23 || opcode == 24 || opcode == 25 || opcode == 26 || opcode == 27 || opcode == 28) {
            registerRegister(opcode, mem.mainMemory[pcMap + 1], mem.mainMemory[pcMap + 2]);
            p.getPCB().UpdateProgramCounter(pc + 3);
            p.setPointer(pointer + 3);
            //System.out.println(opcode + " Register register");
            p.getPCB().updateGPR(gpr);
            p.getPCB().updateSPR(spr);
        } else if (opcode == 48 || opcode == 49 || opcode == 50 || opcode == 51 || opcode == 52 || opcode == 53 || opcode == 54
                || opcode == 55 || opcode == 56 || opcode == 57 || opcode == 58 || opcode == 59 || opcode == 60 || opcode == 61) {
            //System.out.println(" first from memory " + mem.mainMemory[pcMap + 2]);
            int firstByte = mem.mainMemory[pcMap + 2] & 0xFF;
            //System.out.println("first " + firstByte);
            String st = String.format("%02X", firstByte);
            //System.out.println("st " + st);
            int secondByte = mem.mainMemory[pcMap + 3] & 0xFF;
            //System.out.println("second " + secondByte);
            String st2 = String.format("%02X", secondByte);
            String hex = st + "" + st2;
            //System.out.println("hex " + hex);
            int concat = Integer.parseInt(hex, 16);
            short immediate = (short) (concat & 0xFFFF);
            //System.out.println("immediate " + immediate);
            if (opcode == 55 || opcode == 56 || opcode == 57 || opcode == 58 || opcode == 59 || opcode == 60 || opcode == 61) {
                if (immediate < codeRangeStart || immediate > codeRangeEnd) {
                    //invalid code access                     
                    //remove from queue
                    if (ps.find(p)) {
                        ps.pop();
                    } else {
                        R.removeNode(p);
                    }
                }
            } else {
                registerImmediate(opcode, mem.mainMemory[pcMap + 1], immediate);
                p.getPCB().UpdateProgramCounter(pc + 4);
                p.setPointer(pointer + 4);
                //System.out.println(opcode + " Register Immediate");
                p.getPCB().updateGPR(gpr);
                p.getPCB().updateSPR(spr);
            }
        } else if (opcode == 81 || opcode == 82) {
            int firstByte = mem.mainMemory[pcMap + 2] & 0xFF;
            String st = String.format("%02X", firstByte);
            int secondByte = mem.mainMemory[pcMap + 3] & 0xFF;
            String st2 = String.format("%02X", secondByte);
            String hex = st + "" + st2;
            int concat = Integer.parseInt(hex, 16);
            short immediate = (short) (concat & 0xFFFF);
            if (opcode == 81) {
                if (immediate < dataRangeStart || immediate > dataRangeEnd) {
                    //invalid data access
                    if (ps.find(p)) {
                        ps.pop();
                    } else {
                        R.removeNode(p);
                    }
                } else {
                    memoryInstructions(opcode, mem.mainMemory[pcMap + 1], immediate);
                }
                //System.out.println(opcode + " ");
            }
            if (opcode == 82) {
                if (immediate < dataRangeStart || immediate > dataRangeEnd) {
                    //invalid data access
                    if (ps.find(p)) {
                        ps.pop();
                    } else {
                        R.removeNode(p);
                    }
                    return;
                }
                memoryInstructions(opcode, mem.mainMemory[dcMap], immediate);
                p.getPCB().UpdateDataCounter(dataCounter++);
                System.out.println(opcode + " ");
            }
            p.getPCB().UpdateProgramCounter(pc + 4);
            p.setPointer(pointer + 4);
            p.getPCB().updateGPR(gpr);
            p.getPCB().updateSPR(spr);
        } else if (opcode == 113 || opcode == 114 || opcode == 115 || opcode == 116 || opcode == 117 || opcode == 118 || opcode == 119 || opcode == 120) {
            singleOperand(opcode, mem.mainMemory[pcMap + 1]);
            spr.programCounter = spr.programCounter + 2;
            p.setPointer(pointer + 2);
            System.out.println(opcode + " ");
            p.getPCB().updateGPR(gpr);
            p.getPCB().updateSPR(spr);
        } else if (opcode == -15 || opcode == -14 || opcode == -13) {
            if (opcode == -13) {
                if (ps.find(p)) {
                    ps.pop();
                } else {
                    R.removeNode(p);
                }
            }
            noOperand(opcode);
            spr.programCounter = spr.programCounter + 1;
            p.setPointer(pointer + 1);
            System.out.println(opcode + " ");
            p.getPCB().updateGPR(gpr);
            p.getPCB().updateSPR(spr);
        } else {
            System.out.println("Invalid opcode");
            System.out.println(opcode + " ");
            if (ps.find(p)) {
                ps.pop();
            } else {
                System.out.println(p+" this is the process we want to remove");
                R.removeNode(p);
                System.out.println(R);
            }
        }

        if (spr.codeCounter == spr.programCounter) {
            if (ps.find(p)) {
                ps.pop();
            } else {
                R.removeNode(p);
            }
        }
    }

    public void registerRegister(byte opcode, byte R1, byte R2) {
        spr.programCounter = spr.programCounter + 3;
        switch (opcode) {
            case (22):
                ins.MOV(R1, R2);
                break;
            case (23):
                ins.ADD(R1, R2);
                break;
            case (24):
                ins.SUB(R1, R2);
                break;
            case (25):
                ins.MUL(R1, R2);
                break;
            case (26):
                ins.DIV(R1, R2);
                break;
            case (27):
                ins.AND(R1, R2);
                break;
            case (28):
                ins.OR(R1, R2);
                break;
        }
    }

    public void registerImmediate(byte opcode, byte R1, short immediate) {
        switch (opcode) {
            case (48):
                ins.MOVI(R1, immediate);
                System.out.println(spr.programCounter);
                spr.programCounter = spr.programCounter + 4;
                System.out.println(spr.programCounter);
                break;
            case (49):
                ins.ADDI(R1, immediate);
                spr.programCounter = spr.programCounter + 4;
                break;
            case (50):
                ins.SUBI(R1, immediate);
                spr.programCounter = spr.programCounter + 4;
                break;
            case (51):
                ins.MULI(R1, immediate);
                spr.programCounter = spr.programCounter + 4;
                break;
            case (52):
                ins.DIVI(R1, immediate);
                spr.programCounter = spr.programCounter + 4;
                break;
            case (53):
                ins.ANDI(R1, immediate);
                spr.programCounter = spr.programCounter + 4;
                break;
            case (54):
                ins.ORI(R1, immediate);
                spr.programCounter = spr.programCounter + 4;
            case (55):
                ins.BZ(R1, immediate);
                spr.programCounter = spr.programCounter + 4;
                break;
            case (56):
                ins.BZ(R1, immediate);
                spr.programCounter = spr.programCounter + 4;
                break;
            case (57):
                spr.programCounter = spr.programCounter + 4;
                break;
            case (58):
                ins.BS(R1, immediate);
                spr.programCounter = spr.programCounter + 4;
                break;
            case (59):
                ins.JUMP(R1, immediate);
                spr.programCounter = spr.programCounter + 4;
                break;
            case (60):
                ins.CALL(R1, immediate);
                spr.programCounter = spr.programCounter + 4;
                break;
        }
    }

    public void memoryInstructions(byte opcode, byte R1, short immediate) {
        spr.programCounter = spr.programCounter + 4;
        switch (opcode) {
            case (81):
                ins.loadWord(R1, immediate);
                break;
            case (82):
                ins.storeWord(R1, immediate);
                spr.dataCounter++;
                break;
        }
    }

    public void singleOperand(byte opcode, byte R1) {
        spr.programCounter = spr.programCounter + 2;
        switch (opcode) {
            case (113):
                ins.SHL(R1);
                break;
            case (114):
                ins.SHR(R1);
                break;
            case (115):
                ins.RTL(R1);
                break;
            case (116):
                ins.RTR(R1);
                break;
            case (117):
                ins.INC(R1);
                break;
            case (118):
                ins.DEC(R1);
                break;
            case (119):
                ins.PUSH(R1);
                break;
            case (120):
                ins.POP(R1);
                break;
        }
    }

    public void noOperand(byte opcode) {
        spr.programCounter = spr.programCounter + 1;
        switch (opcode) {
            case (-15):
                ins.RETURN();
                break;
            case (-14):
                ins.NOOP();
                break;
            case (-13):
                ins.END();
                break;
        }
    }
}
