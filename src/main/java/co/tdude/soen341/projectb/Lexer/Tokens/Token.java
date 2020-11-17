package co.tdude.soen341.projectb.Lexer.Tokens;

abstract public class Token {
    private String value;
    protected TokenType type; // Exists to branch behaviour based on Type

    public String getValue() {
        return value;
    }
    public TokenType getType() {
        return type;
    }

    Token(String lexeme) {
        value = lexeme;
    }
}
