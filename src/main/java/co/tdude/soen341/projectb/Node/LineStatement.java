package co.tdude.soen341.projectb.Node;

import co.tdude.soen341.projectb.Lexer.Tokens.*;

/**
 * Represents a line statement within the assembly file.
 */
public class LineStatement extends Node {
    /**
     * The label for a given line statement
     */
    private LabelToken label;

    /**
     * The instruction for a given line statement
     */
    private Instruction inst;

    /**
     * The comment for a given line statement
     */
    private CommentToken comment;

    /**
     * Constructor used to create a LineStatement object.
     * @param label Label for the line statement.
     * @param inst Instruction for the line statement.
     * @param comment Comment for the line statement.
     */
    public LineStatement(LabelToken label, Instruction inst, CommentToken comment) {
        this.label = label;
        this.inst = inst;
        this.comment = comment;
    }

    /**
     * Gets the instruction object for the line statement.
     * @return An Instruction object.
     */
    public Instruction getInst() {
        return inst;
    }

    public String toString() {
        String out = "";
        if (label != null) {
            out += label.getValue() + " ";
        }
        if (inst != null) {
            out += inst.toString();
        }
        if (comment != null) {
            out += " ;" + comment.getValue();
        }
        return out;
    }

    public LabelToken getLabel() {
        return label;
    }
}
