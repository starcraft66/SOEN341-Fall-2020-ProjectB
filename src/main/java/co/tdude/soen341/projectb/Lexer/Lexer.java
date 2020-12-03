package co.tdude.soen341.projectb.Lexer;

import co.tdude.soen341.projectb.ErrorReporter.Error;
import co.tdude.soen341.projectb.ErrorReporter.ErrorReporter;
import co.tdude.soen341.projectb.Lexer.Tokens.*;
import co.tdude.soen341.projectb.Reader.IReader;
import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

import java.io.IOException;
import java.util.Objects;

/**
 * Lexical Analyzer (Lexer) used to extract tokens from an assembly file.
 */
public class Lexer implements ILexer {
    /**
     * SymbolTable that holds opcodes and their hex equivalences.
     */
    private SymbolTable<MnemonicToken> _keywordTable;

    /**
     * Current line position within the lexer
     */
    private int _curLinePos;

    /**
     * Current column position within the lexer
     */
    private int _curColPos;

    /**
     * Current character being read from the assembly file.
     */
    private char _ch;

    /**
     * Used to read characters
     */
    private IReader _reader;

    private ErrorReporter ereport;

    /**
     * The purpose of the _lexeme is to store the contents of the token, while the getToken function returns
     * the "Type" of the _lexeme.
     */
    private String _lexeme;

    /**
     * Constructor for a Lexer object.
     * @param reader Used to read individual characters in the assembly file.
     */
    public Lexer(IReader reader) {

        _keywordTable = new SymbolTable<>();

        InitKeywordTable();

        ereport = new ErrorReporter();

        _reader = reader;
        _curLinePos = 1;
        _curColPos = 0;
        read(); // Prime the first character;
    }

    private void InitKeywordTable() {
        // Inherent
        _keywordTable.put("halt", new MnemonicToken("halt", 0x00, false, 0) );
        _keywordTable.put("pop", new MnemonicToken("pop", 0x01, false, 0));
        _keywordTable.put("dup", new MnemonicToken("dup", 0x02, false, 0));
        _keywordTable.put("exit", new MnemonicToken("exit", 0x03, false, 0));
        _keywordTable.put("ret", new MnemonicToken("ret", 0x04, false, 0));
        _keywordTable.put("not", new MnemonicToken("not", 0x0C, false, 0));
        _keywordTable.put("and", new MnemonicToken("and", 0x0D, false, 0));
        _keywordTable.put("or", new MnemonicToken("or", 0x0E, false, 0));
        _keywordTable.put("xor", new MnemonicToken("xor", 0x0F, false, 0));
        _keywordTable.put("neg", new MnemonicToken("neg", 0x10, false, 0));
        _keywordTable.put("inc", new MnemonicToken("inc", 0x11, false, 0));
        _keywordTable.put("dec", new MnemonicToken("dec", 0x12, false, 0));
        _keywordTable.put("add", new MnemonicToken("add", 0x13, false, 0));
        _keywordTable.put("sub", new MnemonicToken("sub", 0x14, false, 0));
        _keywordTable.put("mul", new MnemonicToken("mul", 0x15, false, 0));
        _keywordTable.put("div", new MnemonicToken("div", 0x16, false, 0));
        _keywordTable.put("rem", new MnemonicToken("rem", 0x17, false, 0));
        _keywordTable.put("shl", new MnemonicToken("shl", 0x18, false, 0));
        _keywordTable.put("shr", new MnemonicToken("shr", 0x19, false, 0));
        _keywordTable.put("teq", new MnemonicToken("teq", 0x1A, false, 0));
        _keywordTable.put("tne", new MnemonicToken("tne", 0x1B, false, 0));
        _keywordTable.put("tlt", new MnemonicToken("tlt", 0x1C, false, 0));
        _keywordTable.put("tgt", new MnemonicToken("tgt", 0x1D, false, 0));
        _keywordTable.put("tle", new MnemonicToken("tle", 0x1E, false, 0));
        _keywordTable.put("tge", new MnemonicToken("tge", 0x1F, false, 0));

        // Immediate
        _keywordTable.put("br.i5", new MnemonicToken("br.i5", 0x30, true, 5));
        _keywordTable.put("brf.i5", new MnemonicToken("brf.i5", 0x50, true, 5));
        _keywordTable.put("enter.u5", new MnemonicToken("enter.u5", 0x70, false, 5));
        _keywordTable.put("ldc.i3", new MnemonicToken("ldc.i3", 0x90, true, 3));
        _keywordTable.put("addv.u3", new MnemonicToken("addv.u3", 0x98, false, 3));
        _keywordTable.put("ldv.u3", new MnemonicToken("ldv.u3", 0xA0, false, 3));
        _keywordTable.put("stv.u3", new MnemonicToken("stv.u3", 0xA8, false, 3));

        // Relative
        _keywordTable.put("lda.i16", new MnemonicToken("lda.i16", 0xD5, true, 16));
        _keywordTable.put("call.i16", new MnemonicToken("call.i16", 0xE7, true, 16));
        _keywordTable.put("trap", new MnemonicToken("trap", 0xFF, true, 8));

        // Directive
        _keywordTable.put(".cstring", new DirectiveToken(".cstring", 0, false, 0));
    }


    /**
     * Gets the current column and line position being parsed in the assembly file.
     * @return A String with the current column and line position.
     */
    public String getPosition() {
        return _curLinePos + ":" + _curColPos;
    }

    /**
     * Read the next character from the assembly file.
     */
    private char read() {
        try {
            _ch = _reader.read();
        } catch (IOException e) {
            Error e1=new Error();
            e1.generatemsg(Error.err_type.IOERROR,null,null);
            ereport.record(e1);
            System.exit(1);
        }
        return _ch;
    }





    /*
     * The scanX() family of functions will progress along the file using read() searching for whitespace to
     * terminate the _lexeme. It builds up the _lexeme in the `_lexeme` member variable with string concatenation
     * because I'm lazy. It finally performs some final validation to ensure that the token is in the expected format
     * (e.g. a number does not have any non-number elements in it)
     */

    /**
     * Scans and returns an integer (No negative numbers allowed)
     * @return
     */
    private Token scanNumber() {
        while (!Character.isWhitespace(_ch) && _ch != '\0') {
            _curColPos++;
            _lexeme += _ch;
            _ch = read();
        }
        try {
            Integer.parseInt(_lexeme);
            return new NumberToken(_lexeme);
        } catch (NumberFormatException e) {
            Error e1=new Error();
            e1.generatemsg(Error.err_type.NUMBERFORMAT, null, null);
           ereport.record(e1);
            return new IllegalToken();
        }
    }

    /**
     * Iterates through the read alphabetical characters and tries to identify whether it is a mnemonic token or identifier token.
     * @return A mnemonic token or identifier token, depending on the the string of characters read.
     */
    private Token scanIdentifier() {
        while (!Character.isWhitespace(_ch) && _ch != '\0') {
            _curColPos++;
            _lexeme += _ch;
            if (!(Character.isAlphabetic(_ch) || Character.isDigit(_ch) || _ch == '.')) {
                Error e1=new Error();
                e1.generatemsg(Error.err_type.NONIDENT,getPosition(),null);
                ereport.record(e1);
            }
            else {
                _ch = read();
            }
        }

        // TODO: As per the current logic in the above while loop, the '.' can appear anywhere within the mnemonic.
        // We only want it to appear after alphabetic characters are scanned.
        // Write logic to search for it after the mnemonic is scanned.

        // This returns the MnemonicToken if it was found in the keyword table.
        return Objects.requireNonNullElseGet(_keywordTable.get(_lexeme), () -> new LabelToken(_lexeme));
    }

    /**
     * Scans a directive when encountered
     * @return a new DirectiveToken instance
     */
    private Token scanDirective() {
        _lexeme += _ch;
        _ch = read();

        while (!Character.isWhitespace(_ch) && _ch != '\0') {
            if (Character.isAlphabetic(_ch)) {
                _curColPos++;
                _lexeme += _ch;

                _ch = read();
            }
            else {
               Error e1=new Error();
               e1.generatemsg(Error.err_type.DIRECTIVE, getPosition(), null);
               ereport.record(e1);
            }
        }

        return new DirectiveToken(_lexeme, 0, false, 0);
    }

    /**
     * Scans a String when encountered
     * @return a new StringToken instance
     */
    private Token scanString() {
        _ch = read();

        while (_ch != '"') {
            _curColPos++;
            _lexeme += _ch;

            _ch = read();
        }

        if (_ch == '"') {
            _ch = read();

            while (_ch == ' ' || _ch == '\t') {
                _ch = read();
            }
        }

        return new StringToken(_lexeme);
    }

    private Token scanComment() {
        while (_ch != '\n' && _ch != '\r' && _ch != '\0') { // Not endline or EOF
            _lexeme += _ch;
            _ch = read();
        }
        return new CommentToken(_lexeme);
    }

    /**
     * Scan the next token. Mark position on entry in case of error.
     * @return The token.
     */
    public Token getToken() {
        _lexeme = "";

        //_ch = read(); This was removed because it advanced past the End of Word. It is the responsibility of the
        // various scanX() functions to read() until their word is done

        // Mark position (after skipping blanks)
        //curlinePos = linePos;
        //curcolPos = colPos;

        // skip whitespaces
        while (_ch == ' ' || _ch == '\t') {
            _ch = read();
        }

        switch ( _ch ) {
            // As his usage of '-1' doesn't work in java chars, we'll send a null byte from _reader.read if the EOF
            // was reached.
            case '\0':
                return new EOFToken();

            case '\r':
                if (read() != '\n') {
                    Error e1=new Error();
                    e1.generatemsg(Error.err_type.MISSINGNEWLINE,getPosition(),null);
                    ereport.record(e1);
                }
            case '\n':
                // I think this works to carriage return on to the next line of source
                _curColPos = 1;
                _curLinePos++;
                read(); // Prime the next line
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

            case '.':
                return scanDirective();

            case '-':
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
                return scanNumber();

            case ';':
                return scanComment();

            case '"':
               return scanString();

            default:
            {  Error e1=new Error();
                e1.generatemsg(Error.err_type.ILLEGALTOKEN,getPosition(),null);
                ereport.record(e1);
                read();
                return new IllegalToken();}
        }
    }
}
