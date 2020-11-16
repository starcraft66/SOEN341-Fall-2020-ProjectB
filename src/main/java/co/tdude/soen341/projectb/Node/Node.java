package co.tdude.soen341.projectb.Node;

public abstract class Node {
    private Label _label;
    private Instruction _instruction;
    private Comment _comment;

    public Label get_label() {
        return _label;
    }

    public Instruction get_instruction() {
        return _instruction;
    }

    public Comment get_comment() {
        return _comment;
    }
}
