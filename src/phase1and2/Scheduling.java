/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package phase1and2;

/**
 *
 * @author Muhammad Usman
 */
import java.util.ArrayList;
/**
 *
 *
 */
import java.util.*;
import static phase1and2.start.R;
import static phase1and2.start.ps;

class Node<process> {

    process p;
    int priority;
    Node<process> prev, next;
}

class PriorityS {

    static Node<process> front, rear;

    public String toString() {
        Node<process> temp = ps.front;
        String str = "";
        while (temp != null) {
            str = str + temp.p.getPCB().getProcess_file_name() + " " + temp.p.getPCB().getProcess_id();
            temp = temp.next;
        }
        return str;
    }

    public boolean find(process p) {
        Node<process> temp = ps.front;
        while (temp != null) {
            if (temp.p == p) {
                return true;
            } else {
                temp = temp.next;
            }
        }
        return false;
    }

    /* void pop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    // Function to insert a new Node
    public void push(int n, process p) {
        Node<process> news = new Node();
        news.priority = n;
        news.p = p;

        // If linked list is empty
        if (front == null) {
            front = news;
            rear = news;
            news.next = null;
        } else {
            // If p is less than or equal front
            // node's priority, then insert at
            // the front.
            if (n <= (front).priority) {
                news.next = front;
                (front).prev = news.next;
                front = news;
            } // If p is more rear node's priority,
            // then insert after the rear.
            else if (n > (rear).priority) {
                news.next = null;
                (rear).next = news;
                news.prev = (rear).next;
                rear = news;
            } // Handle other cases
            else {

                // Find position where we need to
                // insert.
                Node<process> start = (front).next;
                while (start.priority < n) {
                    start = start.next;
                }
                (start.prev).next = news;
                news.next = start;
                news.prev = start.prev;
                start.prev = news.next;
            }
        }

    }

    // Return the value at rear
    //static int peek(Node fr) { return fr.info; }
    public boolean isEmpty() {
        return (front == null);
    }

    // Removes the element with the
    // least priority value form the list
    public process pop() {
        if (front == null) {
            return null;
        }
        Node<process> temp = front;
        front = temp.next;
        if (front != null) {
            front.prev = null;
        }
        return temp.p;
    }
}

class RNode<process> {

    process p;
    RNode previous;
    RNode next;

    public RNode(process p) {
        this.p = p;
    }
}

class RoundRobin {

    //Initially, heade and tail is set to null
    RNode<process> head, tail, counter = null;

    public String toString() {
        RNode<process> temp = R.head;
        String str = "";
        while (temp != null) {
            str = str + temp.p.getPCB().getProcess_file_name() + " " + temp.p.getPCB().getProcess_id();
            temp = temp.next;
        }
        return str;
    }

    public boolean find(process p) {
        RNode<process> temp = R.head;
        while (temp != null) {
            if (temp.p == p) {
                return true;
            } else {
                temp = temp.next;
            }
        }
        return false;
    }

    //add a node to the list  
    public void addNode(process p) {
        //Create a new node  
        RNode<process> newNode = new RNode(p);

        //if list is empty, head and tail points to newNode  
        if (head == null) {
            head = tail = newNode;
            //head's previous will be null  
            head.previous = null;
            //tail's next will be null  
            tail.next = null;
        } else {
            //add newNode to the end of list. tail->next set to newNode  
            tail.next = newNode;
            //newNode->previous set to tail  
            newNode.previous = tail;
            //newNode becomes new tail  
            tail = newNode;
            //tail's next point to null  
            tail.next = null;
        }
    }

    public void removeNode(process p) {
        RNode<process> temp = head;
        while (temp != null && temp.p != p) {
            temp = temp.next;
        }
        if (temp != null) {
            if (temp.previous == null && temp.next == null) {
                R = null;
            } else if (temp.next == null) {
                temp.previous.next = null;
                tail = temp.previous;
            }//p on tail
            else if (temp.previous == null) {
                head = temp.next;
                head.previous = null;
            } //p on head
            else {
                temp.next.previous = temp.previous;
                temp.previous.next = temp.next;
            }
        }
    }

}

public class Scheduling {

    execute exobj = new execute();

    public Scheduling() {

    }

    public Scheduling(process p, int priority) {
        if (priority >= 0 & priority <= 15) {
            ps.push(priority, p);

        } else if (priority >= 16 & priority <= 31) {
            R.addNode(p);
        }
        //System.out.println("hello");
    }

    public void Dispatcher() {
        System.out.println(R);
        System.out.println(ps);
        //while(ps!=null){
        Node<process> psnode = new Node(); //priority queue
        psnode = ps.front;
        while (psnode != null) {
            int i = 0;
            while (i < 6) {

                exobj.executeProcess(psnode.p);
                //AccountingInfo(psnode);
                i++;
            }
            System.out.println("abc");
            psnode = psnode.next;
            ps.pop();

        }
        //}
        while (R != null) { //roundrobin

            RNode<process> node = R.head;
            while (node != null) {
                for (int i = 0; i < 4; i++) {
                    if (node.p.getPointer() == node.p.getCode().length) {
                        R.removeNode(node.p);
                    }
                    exobj.executeProcess(node.p);
                    //System.out.println("hello");
                    //  AccountingInfo(node);

                }
                if (R == null) {
                    break;
                } else if (node.next == null) {
                    node = R.head;
                }

                node = node.next;

            }
        }
        //System.out.println("hellops");

    }

    static void AccountingInfo(RNode<process> node) {         //for roundrobin
        node.p.getPCB().setExecution_time(node.p.getPCB().getExecution_time() + 2);
        RNode<process> temp = R.head;
        while (temp != null) {
            if (temp.p == node.p) {
                continue;
            }
            temp.p.getPCB().setWaiting_time(temp.p.getPCB().getWaiting_time() + 2);
            temp = temp.next;
        }

    }

    static void AccountingInfo(Node<process> psnode) {        //for priority scheduling
        psnode.p.getPCB().setExecution_time(psnode.p.getPCB().getExecution_time() + 2);
        Node<process> temp = ps.front;
        while (temp != null) {
            if (temp.p == psnode.p) {
                continue;
            }
            temp.p.getPCB().setWaiting_time(temp.p.getPCB().getWaiting_time() + 2);
            temp = temp.next;
        }

    }
}
