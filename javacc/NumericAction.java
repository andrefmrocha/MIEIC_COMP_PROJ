public class NumericAction implements Action{
    private final int integer;

    NumericAction(int integer){
        this.integer = integer;
    }

    @Override
    public int process(SimpleNode node){
        return 0;
    }
}