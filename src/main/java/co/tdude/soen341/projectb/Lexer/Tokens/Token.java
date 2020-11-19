package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Stores token values, which are LineStatements items that are parsed within an assembly file.
 */
abstract public class Token {
    /**
     * The string representation of the token that was parsed.
     */
    private String value;

    /**
     * The type of the token, chosen from a list of available enum types.
     */
    protected TokenType type;

    /**
     * Constructor used to create a Token object.
     * @param lexeme
     */
    public Token(String lexeme) {
        value = lexeme;
    }

    /**
     * Gets the string value of the token.
     * @return Value of the token.
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the token type.
     * @return Enum token type.
     */
    public TokenType getType() {
        return type;
    }
}
