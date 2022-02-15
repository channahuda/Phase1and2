/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phase1and2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author Huda
 */
public class loadProcess {

    Path path;
    byte[] bytes;
    Byte[] data;
    Byte[] code;
    public static Scheduling obj;
    public static memory mem = new memory();

    public void readFile(String filename) throws IOException {
        path = Paths.get(filename);
        bytes = Files.readAllBytes(path);
        // System.out.println(bytes.length);
        int priority = bytes[0];
        short temp = (short) (bytes[1] * 256);
        int pID = (short) (temp + bytes[2]);
        temp = (short) (bytes[3] * 256);
        double dataSize = (short) (temp + bytes[4]);
        data = new Byte[(int) dataSize];
        int bytesPointer = 8;
        System.out.println("datasize " + dataSize);
        for (int i = 0; i < dataSize; i++) {
            data[i] = bytes[bytesPointer];
            bytesPointer++;
            //System.out.println(data[i]);
        }
        double codeSize = bytes.length - dataSize - 8;
        code = new Byte[(int) codeSize];
        for (int i = 0; i < codeSize; i++) {
            code[i] = bytes[bytesPointer];
            // System.out.print(code[i] + " ");
            bytesPointer++;
        }
        //System.out.println(bytesPointer);
        dataPageTable dataPage = new dataPageTable(dataSize);
        int dataIterations = (int) Math.ceil(dataSize / 128);
        //System.out.println("dataIterations " + dataIterations);
        int Check = 0;
        int l = 0;
        for (int k = 0; k < dataIterations; k++) {
            while (mem.check[l] == true) {
                l++;
            }
            dataPage.pageTable[k] = new frame(l * 128, (l * 128 + 127));
            //    System.out.println(filename + " dataPage " + k + " " + l * 128 + " " + (l * 128 + 127));
            mem.check[l] = true;
            for (int i = l * 128; i < l * 128 + 128 && Check < dataSize; i++) {
                //System.out.println("i " + i);
                //System.out.println("Check " + Check);
                mem.mainMemory[i] = data[Check];
                Check++;
            }
        }
        //System.out.println("------------------------------");
        //System.out.println(filename + " code size " + codeSize);
        Check = 0;
        codePageTable codePage = new codePageTable(codeSize);
        int codeIterations = (int) Math.ceil(codeSize / 128);
        //System.out.println("code iterations " + codeIterations);
        int j = 0;
        for (int k = 0; k < codeIterations; k++) {
            while (mem.check[j] == true) {
                j++;
            }
            codePage.pageTable[k] = new frame(j * 128, (j * 128 + 127));
            //System.out.println(filename + " codePage " + k + " " + j * 128 + " " + (j * 128 + 127));
            mem.check[j] = true;
            for (int i = j * 128; i < j * 128 + 128 && Check < codeSize; i++) {

                mem.mainMemory[i] = code[Check];
                //System.out.println("i " + i);
                //System.out.println("check " + Check);
                Check++;

            }
        }
        // System.out.println("------------------------------");
        // System.out.println("code page " + codePage.pageTable);
        process p = new process(priority, pID, dataSize, codeSize, filename, data, code, dataPage.pageTable, codePage.pageTable);
        // System.out.println("----------------------");
        //execute e = new execute();
        //e.executeProcess(p);
        //e.executeProcess(p);
        //e.executeProcess(p);
        //e.executeProcess(p);
        //System.out.println(pID);
        obj = new Scheduling(p, priority);

    }
}
