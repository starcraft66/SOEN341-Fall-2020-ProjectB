package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Token used to identify a number/operand within an assembly file.
 */
public class NumberToken extends Token {
    /**
     * The value of the number/operand.
     */
    private int number;

    /**
     * Constructor used to create a NumberToken object.
     */
    public NumberToken(String lexeme) {
        super(lexeme);
        type = TokenType.NUMBER;
        // Should be guaranteed that getValue will return a valid int in the number subclass
        number = Integer.parseInt(getValue());
    }

    public int getNumber() {
        return number;
    }
}
