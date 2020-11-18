package co.tdude.soen341.projectb.Node;

import co.tdude.soen341.projectb.Lexer.Tokens.*;

public class LineStatement extends Node {
    LabelToken label;
    Instruction inst;
    CommentToken comment;
    public LineStatement(LabelToken label, Instruction inst, CommentToken comment) {
        this.label = label;
        this.inst = inst;
        this.comment = comment;
    }

    public Instruction getInst() {
        return inst;
    }
}
