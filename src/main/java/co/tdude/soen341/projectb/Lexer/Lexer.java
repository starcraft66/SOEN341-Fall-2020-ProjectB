package co.tdude.soen341.projectb.Lexer;

import co.tdude.soen341.projectb.Lexer.Tokens.*;
import co.tdude.soen341.projectb.SymbolTable.*;
import co.tdude.soen341.projectb.Reader.*;

import java.io.IOException;

public class Lexer implements ILexer {
    /** Create a lexer that scans the given character stream. */
    private SymbolTable keywordTable;
    private int linePos, colPos, curlinePos, curcolPos;
    private char ch;
    // The purpose of the lexeme is to store the contents of the token, while the getToken function returns the
    // "Type" of the lexeme.

    IReader reader;
    private String lexeme;

    public Lexer(IReader reader, ISymbolTable keywordTable) {

        this.reader = reader;

        // Enter all mnemonics as keywords in the symbol table...

        // One byte inherent ops
        // It feels weird to handle them here, but that's what the prof says
        keywordTable.registerSymbol("halt", 0x00);
        keywordTable.registerSymbol("pop", 0x01);
        keywordTable.registerSymbol("dup", 0x02);
        keywordTable.registerSymbol("exit", 0x03);
        keywordTable.registerSymbol("ret", 0x04);
        keywordTable.registerSymbol("not", 0x0C);
        keywordTable.registerSymbol("and", 0x0D);
        keywordTable.registerSymbol("or", 0x0E);
        keywordTable.registerSymbol("xor", 0x0F);
        keywordTable.registerSymbol("neg", 0x10);
        keywordTable.registerSymbol("inc", 0x11);
        keywordTable.registerSymbol("dec", 0x12);
        keywordTable.registerSymbol("add", 0x13);
        keywordTable.registerSymbol("sub", 0x14);
        keywordTable.registerSymbol("mul", 0x15);
        keywordTable.registerSymbol("div", 0x16);
        keywordTable.registerSymbol("rem", 0x17);
        keywordTable.registerSymbol("shl", 0x18);
        keywordTable.registerSymbol("shr", 0x19);
        keywordTable.registerSymbol("teq", 0x1A);
        keywordTable.registerSymbol("tne", 0x1B);
        keywordTable.registerSymbol("tlt", 0x1C);
        keywordTable.registerSymbol("tgt", 0x1D);
        keywordTable.registerSymbol("tle", 0x1E);
        keywordTable.registerSymbol("tge", 0x1F);

        linePos = 1;
        colPos = 0;
        curlinePos = linePos;
        curcolPos = colPos;

        read(); // prime
    }

    public String getPosition() {
        return curlinePos + ":" + curcolPos;
    }

    /* Read the next character. */
//    TODO: bring in a reader class to make this work.
    private char read() {
        colPos++;
        try {
            ch = reader.read();
        } catch (IOException e) {
            // IO EXCEPTION!
            System.exit(1);
        }
        return ch;
    }

    private void error(String t) {
//         errorReporter.record( _Error.create(t, getPosition()) ); TODO: Bring in the Error Reporter
//         Shouldn't this exit in some way?
    }

    // The scanX() family of functions will progress along the file using read() searching for whitespace to
    // terminate the lexeme. It builds up the lexeme in the `lexeme` member variable with string concatenation
    // because I'm lazy. It finally performs some final validation to ensure that the token is in the expected format
    // (e.g. a number does not have any non-number elements in it)
    private Token scanNumber() {
        while (!Character.isWhitespace(ch) && ch != '\0') {
            lexeme += ch;
            ch = read();
        }
        try {
            Integer.parseInt(lexeme);
        } catch (NumberFormatException e) {
            error(e.getMessage());
        }
        return new NumberToken(lexeme);
    }
    private Token scanIdentifier() {
        while (!Character.isWhitespace(ch) && ch != '\0') {
            lexeme += ch;
            if (!(Character.isAlphabetic(ch) || Character.isDigit(ch))) {
                error("The Identifier had a non-ident character in it");
            }
            ch = read();
        }
        try {
            keywordTable.get(lexeme);
            return new MnemonicToken(lexeme);
        } catch (ValueNotExist e) {
            return new IdentifierToken(lexeme);
        }
    }
//    private Token scanDirective() {
//        // TODO: Sprint 2
//    }
//    private Token scanString() {
//        // TODO: Sprint 2
//    }
    private Token scanComment() {
        while (ch != '\n' && ch != '\r' && ch != '\0') { // Not endline or EOF
            lexeme += ch;
            ch = read();
        }
        return new CommentToken(lexeme);
    }
    /**
     * Scan the next token. Mark position on entry in case of error.
     * @return   the token.
     */
    public Token getToken() {
        // skip whitespaces
        // "\n", "\r\n", "\n", or line comments are considered as EOL

        // your code...

        // Mark position (after skipping blanks)
        curlinePos = linePos;
        curcolPos = colPos;

        lexeme = "";

        switch ( ch ) {

            // As his usage of '-1' doesn't work in java chars, we'll send a null byte from reader.read if the EOF
            // was reached.
            case '\0':
                return new EOFToken();

            case '\r':
                if (read() != '\n') {
                    error("a \\r character must be followed by a \\n on dos architectures");
                }
            case '\n':
                // I think this works to carriage return on to the next line of source
                colPos = 0;
                linePos++;
                return new EOLToken();

            case 'a': case 'b': case 'c': case 'd': case 'e':
            case 'f': case 'g': case 'h': case 'i': case 'j':
            case 'k': case 'l': case 'm': case 'n': case 'o':
            case 'p': case 'q': case 'r': case 's': case 't':
            case 'u': case 'v': case 'w': case 'x': case 'y':
            case 'z':
            case 'A': case 'B': case 'C': case 'D': case 'E':
            case 'F': case 'G': case 'H': case 'I': case 'J':
            case 'K': case 'L': case 'M': case 'N': case 'O':
            case 'P': case 'Q': case 'R': case 'S': case 'T':
            case 'U': case 'V': case 'W': case 'X': case 'Y':
            case 'Z':
                return scanIdentifier();

//                case '.':  /* dot for directives as a first character */
//                    return scanDirective(); TODO: SPRINT 2

            case '-':
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
                return scanNumber();

            case ';':
                return scanComment();

//                case '"':
//                    return scanString(); // TODO: STRING STUFF

            default:
                read();
                error("Illegal Token Detected");
                return new IllegalToken();
        }
    }

    // No setter as Lexeme is only mutated inside Lexer
    public String getLexeme() {
        return lexeme;
    }
}
