package co.tdude.soen341.projectb.Lexer.Tokens;

public class EOFToken extends Token {
    public EOFToken() {
        super("EOF");
        this.type = TokenType.EOF;
    }
}
