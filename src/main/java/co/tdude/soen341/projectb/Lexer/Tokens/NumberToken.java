package co.tdude.soen341.projectb.Lexer.Tokens;

public class NumberToken extends Token {
    private int number;

    public NumberToken(String lexeme) {
        super(lexeme);
        type = TokenType.NUMBER;
        // Should be guaranteed that getValue will return a valid int in the number subclass
        number = Integer.parseInt(getValue());
    }
}
