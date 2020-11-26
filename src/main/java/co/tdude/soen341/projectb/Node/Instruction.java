package co.tdude.soen341.projectb.Node;

/**
 * Representation of an assembly instruction (mnemonic + opcode).
 */
public class Instruction {

    /**
     * The mnemonic for a given LineStatement.
     */
    private String _mnemonic;

    /**
     * The operand for a given LineStatement.
     */
    private String _operand;

    /**
     * Constructor used to create an Instruction object.
     * @param opcode The mnemonic.
     * @param operand The operand.
     */
    public Instruction(String opcode, String operand) { // Replace operand with the operand class once it's in
        _mnemonic = opcode;
        _operand = operand;
    }

    /**
     * Convert the mnemonic to string representation.
     */
    public String get_mnemonic() {
        return _mnemonic;
    }

    public String get_operand() {
        return _operand;
    }
}
