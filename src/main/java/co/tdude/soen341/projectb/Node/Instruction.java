package co.tdude.soen341.projectb.Node;

import co.tdude.soen341.projectb.Lexer.Tokens.MnemonicToken;
import co.tdude.soen341.projectb.Lexer.Tokens.OperandToken;
import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

/**
 * Representation of an assembly instruction (mnemonic + opcode).
 */
public class Instruction {
    /**
     * Identifies if the instruction is relative.
     */
    private Boolean isRelative = false;

    /**
     * The mnemonic componenet of the instruction.
     */
    private MnemonicToken mt;

    /**
     * The operand componenet of the instruction.
     */
    private OperandToken ot;

    /**
     * Constructor used to generate an Instruction object.
     * @param mt
     * @param ot
     */
    public Instruction(MnemonicToken mt, OperandToken ot) {
        this.mt = mt;
        this.ot = ot;
        setRelative();
    }

    /**
     * Sets the status to relative instruction.
     */
    private void setRelative() {
        if (mt.getOpsize() >= 8) {
            isRelative = true;
        }
    }

    /**
     * Gets the relative instruction status.
     * @return
     */
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

    /**
     * Generates a printable string.
     * @return The string to be printed.
     */
    public String toString() {
        String out = mt.getValue();
        if (ot != null) {
            out += " " + ot.getValue();
        }
        return out;
    }

    /**
     * Gets the mnemonic token.
     * @return Mnemonic token for the instruction.
     */
    public MnemonicToken getMnemonic() {
        return mt;
    }

    /**
     * Gets the operand token.
     * @return Operand token for the instruction.
     */
    public OperandToken getOperand() {
        return ot;
    }
}
