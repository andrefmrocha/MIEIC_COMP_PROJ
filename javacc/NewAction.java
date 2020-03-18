public class NewAction implements Action{
    private final String classIdentifier;

    NewAction(String classIdentifier){
        this.classIdentifier = classIdentifier;
    }

    @Override
    public int process(SimpleNode node){
        return 0;
    }
}