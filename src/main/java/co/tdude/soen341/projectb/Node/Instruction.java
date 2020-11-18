package co.tdude.soen341.projectb.Node;

public class Instruction {

    String mnemonic;
    String operand;
    public Instruction(String value, Object operand) { // Replace operand with the operand class once it's in
        mnemonic = value;
    }

    @Override
    public String toString() {
        return mnemonic;
    }
}
