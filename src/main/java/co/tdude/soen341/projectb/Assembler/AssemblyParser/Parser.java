package co.tdude.soen341.projectb.Assembler.AssemblyParser;

import co.tdude.soen341.projectb.Assembler.AssemblyUnit;
import co.tdude.soen341.projectb.Environment.Environment;
import co.tdude.soen341.projectb.ErrorReporter.Error;
import co.tdude.soen341.projectb.ErrorReporter.IReportable;
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
import java.util.logging.Logger;

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
    private IReportable errorReporter;

    /**
     * The list of LineStatements that comprise the AssumblyUnit.
     */
    private ArrayList<LineStatement> _assemblyUnit;

    /**
     * The path to the output listing file
     */
    private String _listingFilePath;

    /**
     * The path to the output binary file
     */
    private final String _binaryFilePath;

    /**
     * Constructor to instantiate a Parser object.
     * @param env Environment object that supplies the instantiated assembly file, lexer object, and symbol tables.
     * @param listingFilePath The path for the output file name (and listing with .lst suffix)
     */
    public Parser(Environment env, String listingFilePath, String binaryFilePath) {
        _assemblyUnit = new ArrayList<>();
        _lexer = env.getLexer();
        _sourceFile = env.getSourceFile();
        this.errorReporter = env.getErrorReporter();
        //_labelTable = env.getSymbolTable();
        //_keywordTable = env.getSymbolTable();
        _listingFilePath = listingFilePath;
        _binaryFilePath = binaryFilePath;

        nextToken();
    }



    /**
     * Parses the assembly file for tokens, creates LineStatements, and adds them to a list.
     * @return The assembly unit that is comprised of all the LineStatements.
     */
    public AssemblyUnit parse() {
        Logger.getLogger("").fine("Parsing an Assembler...");

        while (_token.getType() != TokenType.EOF) {
            _assemblyUnit.add(parseLineStmt());
            nextToken();
        }

        return new AssemblyUnit(_assemblyUnit, _listingFilePath, _binaryFilePath);
    }

    /**
     * Parse a 1 Byte no Operand Mnemonic
     */
    private Instruction parseInherent() {
        return new Instruction(_token.getValue(), null);
    }

    /*private Instruction parseImmediate() {
        if(_token.getType() == TokenType.EOL){

        }
        if (_token.getType() == TokenType.MNEMONIC){
            // I would need to be able to 'see' the next token  if order to be able to create a whole Instruction instance
            if (_token.getType() == TokenType.NUMBER){

            }
        }
    }*/

    //---------------------------------------------------------------------------------
//    private Instruction parseRelative() {
//        // your code...
//    } TODO: Sprint 2

    /**
     * Creates a LineStatement object depending on the type of token being parsed.
     * @return A new LineStatement object.
     */
    private LineStatement parseLineStmt() {
        LabelToken   label = null;
        Instruction  inst = null;
        CommentToken comment = null;

        Logger.getLogger("").fine("Parsing a Line Statement...");

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
            Error e1 = new Error();
            e1.generatemsg(Error.err_type.INCORRECT, _lexer.getPosition(), _token);
            errorReporter.record(e1);
            //If not registered, then label
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
