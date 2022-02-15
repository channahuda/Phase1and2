/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phase1and2;

import java.io.FileWriter;
import java.io.IOException;
import static phase1and2.loadProcess.mem;

/**
 *
 * @author Huda
 */
public class memoryDump {
    
    memoryDump() throws IOException {
        FileWriter myWriter = new FileWriter("Errors.txt");
        //myWriter.write("Files in Java might be tricky, but it is fun enough!");
        for (int i = 0; i < mem.mainMemory.length; i++) {
            myWriter.write(mem.mainMemory[i]);
        }
        myWriter.close();
    }

    /*public void memoryDump(PCB p) throws IOException {
        FileWriter myWriter = new FileWriter("Errors.txt");
        myWriter.write("Process Name: " + p.getProcess_file_name());
        myWriter.write("\n");
        myWriter.write("Process ID: " + p.getProcess_id());
        myWriter.write("\n");
        for (int i = 0; i < 16; i++) {
            myWriter.write("GPR " + i + ": " + p.getGPR().generalPurposeRegisters[i]);
            // }
            // myWriter.write("--------------------");

        }*/
}
