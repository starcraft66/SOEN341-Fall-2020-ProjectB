package co.tdude.soen341.projectb.Lexer;

import co.tdude.soen341.projectb.Lexer.Tokens.Token;

/**
 * Lexical Analyzer (Lexer) used to extract tokens from an assembly file.
 */
public interface ILexer {
    /**
     * Scan the next token. Mark position on entry in case of error.
     * @return The token.
     */
    Token getToken();
    String getPosition();
}
