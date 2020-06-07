package javamm.semantics;

import java.io.PrintWriter;

/**
 * Wrapper around the stack usage, to be calculated for each method
 */
public class StackUsage {

    private int stackUsage;
    private int maxStackUsage;

    public StackUsage() {
        this.maxStackUsage = 0;
        this.stackUsage = 0;
    }

    public void inc(int i) {
        stackUsage += i;
        updateMax();
    }

    public void dec(int i) {
        stackUsage -= i;
    }

    public void set(int i) {
        stackUsage = i;
        updateMax();
    }

    private void updateMax() {
        maxStackUsage = Math.max(stackUsage, maxStackUsage);
    }

    public int getStackUsage() {
        return stackUsage;
    }

    public int getMaxStackUsage() {
        return maxStackUsage;
    }

    public void popStack(PrintWriter writer) {
        for(int i = 0; i < stackUsage; i++)
            writer.println("  pop");
        stackUsage = 0;
    }

    public static void popStack(PrintWriter writer, int n) {
        for (int i = 0; i < n; i++)
            writer.println("  pop");
    }
}
