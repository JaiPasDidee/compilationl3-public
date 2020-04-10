package ig;

import fg.*;
import nasm.*;
import util.graph.*;
import util.intset.*;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class Ig {
    public Graph graph;
    public FgSolution fgs;
    public int regNb;
    public Nasm nasm;
    public Node int2Node[];


    public Ig(FgSolution fgs) {
        this.fgs = fgs;
        this.graph = new Graph();
        this.nasm = fgs.nasm;
        this.regNb = this.nasm.getTempCounter();
        this.int2Node = new Node[regNb];
        build();
    }

    public void build() {
        for (NasmInst sommet : fgs.nasm.listeInst) {
            Map< NasmInst, IntSet> in = fgs.in;
            Set<NasmInst> key = in.keySet();

            for (NasmInst inst:key) {
                IntSet r = in.get(inst);
                for (int i = 0; i < r.getSize(); i++) {
                    for (int i2 = 0; i2 < r.getSize(); i2++) {
                        if(i != i2){
                            if(r.isMember(i) && r.isMember(i2)) {
                                Node from = int2Node[i];
                                Node to = int2Node[i2];
                                graph.addNOEdge(from, to);
                            }
                        }
                    }
                }
            }

            Map< NasmInst, IntSet> out = fgs.out;
            key = out.keySet();

            for (NasmInst inst:key) {
                IntSet r = out.get(inst);
                for (int i = 0; i < r.getSize(); i++) {
                    for (int i2 = 0; i2 < r.getSize(); i2++) {
                        if(i != i2){
                            if(r.isMember(i) && r.isMember(i2)) {
                                Node from = int2Node[i];
                                Node to = int2Node[i2];
                                graph.addNOEdge(from, to);
                            }
                        }
                    }
                }
            }
        }
    }

    public int[] getPrecoloredTemporaries() {
        return new int[3];

    }


    public void allocateRegisters() {
        int i = 3;
    }


    public void affiche(String baseFileName) {
        String fileName;
        PrintStream out = System.out;

        if (baseFileName != null) {
            try {
                baseFileName = baseFileName;
                fileName = baseFileName + ".ig";
                out = new PrintStream(fileName);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        for (int i = 0; i < regNb; i++) {
            Node n = this.int2Node[i];
            out.print(n + " : ( ");
            for (NodeList q = n.succ(); q != null; q = q.tail) {
                out.print(q.head.toString());
                out.print(" ");
            }
            out.println(")");
        }
    }
}
	    
    

    
    
