package co.tdude.soen341.projectb.Lexer.Tokens;

/**
 * Enumerates the different types of possible tokens that will be parsed within the assembly file.
 */
public enum TokenType {
    EOF, EOL, NUMBER, COMMENT, IDENT, ILLEGAL_CHAR, MNEMONIC, DIRECTIVE, STRING
}
