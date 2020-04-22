package javamm;

import javamm.parser.SimpleNode;

public class SemanticsException{
    private final String error;
    private final SimpleNode node;
    public SemanticsException(String error, SimpleNode node){
        this.error = error;
        this.node = node;
    }

    public String toString() {
        return error;
    }

    public String getError() {
        return error;
    }

    public SimpleNode getNode() {
        return node;
    }
}
