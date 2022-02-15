/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phase1and2;

import java.io.IOException;

/**
 *
 * @author Huda
 */
public class start {

    public static RoundRobin R = new RoundRobin();
    public static PriorityS ps = new PriorityS();

    public static void readfile() throws IOException {

        loadProcess p = new loadProcess();
        p.readFile("flags");
        p.readFile("large0");
        p.readFile("noop");
        p.readFile("p5");
        p.readFile("power");
        p.readFile("sfull");
        Scheduling obj = new Scheduling();
        obj.Dispatcher();
        memoryDump dump = new memoryDump();
    }
}
