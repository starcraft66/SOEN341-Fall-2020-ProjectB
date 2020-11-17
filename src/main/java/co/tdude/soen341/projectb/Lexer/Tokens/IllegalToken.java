package co.tdude.soen341.projectb.Lexer.Tokens;

public class IllegalToken extends Token {
    public IllegalToken() {
        super("ILLEGAL");
        type = TokenType.ILLEGAL_CHAR;
    }
}
