package co.tdude.soen341.projectb.Node;

/**
 * Representation of an assembly instruction (mnemonic + opcode).
 */
public class Instruction {

    /**
     * The mnemonic for a given LineStatement.
     */
    String mnemonic;

    /**
     * The operand for a given LineStatement.
     */
    String operand;

    /**
     * Constructor used to create an Instruction object.
     * @param value The mnemonic.
     * @param operand The operand.
     */
    public Instruction(String value, Object operand) { // Replace operand with the operand class once it's in
        mnemonic = value;
    }

    /**
     * Convert the mnemonic to string representation.
     */
    @Override
    public String toString() {
        return mnemonic;
    }
}
