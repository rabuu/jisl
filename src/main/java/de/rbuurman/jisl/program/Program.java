package de.rbuurman.jisl.program;

import java.util.LinkedList;
import java.util.Queue;

public class Program {
    private Queue<ProgramElement> elements = new LinkedList<ProgramElement>();

    public void add(ProgramElement element) {
        this.elements.add(element);
    }

    public ProgramElement poll() {
        return this.elements.poll();
    }

    public Object[] toArray() {
        return this.elements.toArray();
    }
}
