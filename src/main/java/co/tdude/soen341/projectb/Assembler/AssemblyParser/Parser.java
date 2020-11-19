package co.tdude.soen341.projectb.Assembler.AssemblyParser;

import co.tdude.soen341.projectb.Assembler.AssemblyUnit;
import co.tdude.soen341.projectb.Environment.Environment;
import co.tdude.soen341.projectb.Lexer.ILexer;
import co.tdude.soen341.projectb.Lexer.Tokens.CommentToken;
import co.tdude.soen341.projectb.Lexer.Tokens.LabelToken;
import co.tdude.soen341.projectb.Lexer.Tokens.Token;
import co.tdude.soen341.projectb.Lexer.Tokens.TokenType;
import co.tdude.soen341.projectb.Node.Instruction;
import co.tdude.soen341.projectb.Node.LineStatement;
import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

import java.io.File;
import java.util.ArrayList;

/**
 * Parses the assembly file and extracts a list of LineStatements.
 */
public class Parser implements IParser {

    /**
     * Lexer used to extract tokens from assembly file.
     */
    private ILexer _lexer;
    /**
     * Stores sequential tokens extracted from assembly file.
     */
    private Token _token;

    /**
     * Assembly source file.
     */
    private File _sourceFile;

    /**
     *
     */
    //private IReportable errorReporter;

    /**
     *
     */
    private ArrayList<LineStatement> _assemblyUnit;

    /**
     * SymbolTable that stores labels and their hex equivalents.
     */
    //private ISymbolTable _labelTable;

    /**
     * Symbol table that stores opcodes and their hex equivalents.
     */
    //private ISymbolTable _keywordTable;

    /**
     * Constructor to instantiate a Parser object.
     * @param env Environment object that supplies the instantiated assembly file, lexer object, and symbol tables.
     */
    public Parser(Environment env) {
        _assemblyUnit = new ArrayList<>();
        _lexer = env.getLexer();
        _sourceFile = env.getSourceFile();
        //this.errorReporter = env.getErrorReporter(); TODO ONCE ERREPORTER IS IN
        //_labelTable = env.getSymbolTable();
        //_keywordTable = env.getSymbolTable();

        nextToken(); // prime
        // address = 0; Don't know what this is for
    }

    protected void expect(TokenType expected, TokenType test) {
        if (expected != test) {
            error("The Token Type " + expected.name() + " was expected, but we got a " + test.name());
        }
    }

    // Record the error: <t> expected, found <_token> at <_token>.position
    protected void expect(String t) {
        //errorReporter.record( _Error.create(t+" expected", Lexer.getPosition()) );
    }

    protected void error(String t) {
        //errorReporter.record( _Error.create(t, Lexer.getPosition()) );
    }

    private class SyntaxError extends Exception {}

    /**
     *
     * @return
     */
    public AssemblyUnit parse() {
        System.out.println("Parsing an Assembler...");

        while (_token.getType() != TokenType.EOF) {
            _assemblyUnit.add(parseLineStmt());
            nextToken();
        }

        return new AssemblyUnit(_assemblyUnit);
    }

    //---------------------------------------------------------------------------------
    // Parse a 1 Byte no Operand Mnemonic
    private Instruction parseInherent() {
        return new Instruction(_token.getValue(), null);
    }

    //---------------------------------------------------------------------------------
//    private Instruction parseImmediate() {
//        // your code...
//    } TODO: Sprint 2

    //---------------------------------------------------------------------------------
//    private Instruction parseRelative() {
//        // your code...
//    } TODO: Sprint 2

    // -------------------------------------------------------------------
    // A line statement:
    //   - could be empty (only a EOL);
    //   - could have a single comment start at BOL or after a label, label/inst, or label/dir;
    //   - could have a label only, etc.
    //
    // LineStatement = [Label] [Instruction | Directive ] [Comment] EOL .
    //
    private LineStatement parseLineStmt() {
        LabelToken   label = null;
        Instruction  inst = null;
        CommentToken comment = null;

        System.out.println("Parsing a Line Statement...");

        // Test if EOL first
        if (_token.getType() == TokenType.EOL) {
            return new LineStatement(null, null, null);
        }

        if (_token.getType() == TokenType.COMMENT) {
            return new LineStatement(null, null, (CommentToken) _token);
        }

        if (SymbolTable.isMnemonicRegistered(_token.getValue())) {
            // If registered, then mnemonic
            // Right now, we can assume that no operand.
            //_lexer.getToken();
            inst = parseInherent();
            return new LineStatement(null, inst, null);
        }
        else {
            //Error e1 = new Error();
            //e1.generatemsg(Error.err_type.INCORRECT, lexer.getPosition(), token);
            //erReporter.record(e1);
            // If not registered, then label
            // TODO Label processing
        }

        return null;
    }

    /**
     * Loads the next _token to parse into the _token member
     */
    protected void nextToken() {
        _token = _lexer.getToken();
    }
}
