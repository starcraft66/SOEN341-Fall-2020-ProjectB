package co.tdude.soen341.projectb.Node;

import co.tdude.soen341.projectb.Lexer.Tokens.MnemonicToken;
import co.tdude.soen341.projectb.Lexer.Tokens.OperandToken;
import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

/**
 * Representation of an assembly instruction (mnemonic + opcode).
 */
public class Instruction {

    private Boolean isRelative = false;

    private MnemonicToken mt;
    
    private OperandToken ot;

    public Instruction(MnemonicToken mt, OperandToken ot) {
        this.mt = mt;
        this.ot = ot;
        setRelative();
    }

    private void setRelative() {
        if (mt.getOpsize() >= 8) {
            isRelative = true;
        }
    }

    public Boolean getRelative() {
        return isRelative;
    }

    /**
     * Checks if the operand needs resolving
     * @return
     */
    public boolean isResolved() {
        if (ot != null) {
            // If the operand exists, return if it's been resolved
            return ot.isResolved();
        } else {
            // A no-op instruction is always resolved
            return true;
        }
    }

    /**
     * Should be called in the context of if (!inst.isResolved()) inst.resolve()
     * @return if the instruction was successfully resolved
     */
    public boolean resolve(int currentAddr, SymbolTable<Integer> labelTable) {
        if (ot != null) {
            return ot.resolve(currentAddr, labelTable);
        } else {
            return true;
        }
    }

    public String toString() {
        String out = mt.getValue();
        if (ot != null) {
            out += " " + ot.getValue();
        }
        return out;
    }

    public MnemonicToken getMnemonic() {
        return mt;
    }

    public OperandToken getOperand() {
        return ot;
    }
}
