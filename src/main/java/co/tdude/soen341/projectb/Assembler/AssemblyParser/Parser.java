package co.tdude.soen341.projectb.Assembler.AssemblyParser;

import co.tdude.soen341.projectb.Assembler.AssemblyUnit;
import co.tdude.soen341.projectb.Assembler.SourceFile;
import co.tdude.soen341.projectb.Environment.Environment;
import co.tdude.soen341.projectb.ErrorReporter.Error;
import co.tdude.soen341.projectb.ErrorReporter.IReportable;
import co.tdude.soen341.projectb.Lexer.ILexer;
import co.tdude.soen341.projectb.Lexer.Tokens.*;
import co.tdude.soen341.projectb.Node.Instruction;
import co.tdude.soen341.projectb.Node.LineStatement;

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
     * Used to log error reports when parsing errors occur.
     */
    private IReportable errorReporter;

    /**
     * The list of LineStatements that comprise the AssemblyUnit.
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
     * Holds temporary LineStatement object.
     */
    private LineStatement _parsedLineStatement;

    /**
     * Holds temporary Instruction object.
     */
    private Instruction _instruction;

    /**
     * Constructor to instantiate a Parser object.
     * @param env Environment object that supplies the instantiated assembly file, lexer object, and symbol tables.
     * //@param listingFilePath The path for the output file name (and listing with .lst suffix)
     */
    public Parser(Environment env) {
        _assemblyUnit = new ArrayList<>();
        _lexer = env.getLexer();
        _sourceFile = env.getSourceFile();
        this.errorReporter = env.getErrorReporter();
        _listingFilePath = SourceFile.getName();
        _binaryFilePath = SourceFile.getName();

        nextToken();
    }



    /**
     * Parses the assembly file for tokens, creates LineStatements, and adds them to a list.
     * @return The assembly unit that is comprised of all the LineStatements.
     */
    public AssemblyUnit parse() {
        Logger.getLogger("").fine("Parsing an Assembler...");

        while (_token.getType() != TokenType.EOF) {
            _parsedLineStatement = parseLineStmt();
            _instruction = _parsedLineStatement.getInst();

            if (_instruction != null && !(_instruction.getMnemonic() == null)) {
                _assemblyUnit.add(_parsedLineStatement);
            }

            nextToken();
        }

        return new AssemblyUnit(_assemblyUnit, _listingFilePath, _binaryFilePath);
    }

//    /**
//     * Parse a 1 Byte no Operand Mnemonic
//     */
//    private Instruction parseInherent() {
//        return new Instruction((MnemonicToken) _token, null);
//    }
//
//    private Instruction parseImmediate() {
//        return new Instruction(_prevTokenValue, _token.getValue());
//    }

    //---------------------------------------------------------------------------------
//    private Instruction parseRelative() {
//        // your code...
//    } TODO: Sprint 2
    void assertMnemonic(Token t) {
        if (!(t instanceof MnemonicToken)) {
            Error e1=new Error();
            e1.generatemsg(Error.err_type.MNEMONIC, _lexer.getPosition(), t);
            errorReporter.record(e1);
            throw new RuntimeException();
        }
    }

    void assertOperand(Token t) {
        if (!(t instanceof OperandToken)) {
            Error e1=new Error();
            e1.generatemsg(Error.err_type.OPERAND, _lexer.getPosition(), t);
            errorReporter.record(e1);
            throw new RuntimeException();
        }
    }

    void assertTerminator(Token t) {
        if (!(t instanceof EOLToken || t instanceof EOFToken || t instanceof CommentToken)) {
            Error e1=new Error();
            e1.generatemsg(Error.err_type.TERMINATOR, _lexer.getPosition(), t);
            errorReporter.record(e1);
            throw new RuntimeException();
        }
    }

    /**
     * Creates a LineStatement object depending on the type of token being parsed.
     * @return A new LineStatement object.
     */
    private LineStatement parseLineStmt() {
        LabelToken   label = null;
        Instruction inst = null;
        CommentToken comment = null;

        Logger.getLogger("").fine("Parsing a Line Statement...");

        while (_token.getType() != TokenType.EOL && _token.getType() != TokenType.EOF) {
            switch (_token.getType()) {
                case IDENT:
                    label = (LabelToken) _token;
                    nextToken();
                    break;
                case MNEMONIC:
                    MnemonicToken mnemToken;
                    mnemToken = (MnemonicToken) _token;
                    if (mnemToken.getOpsize() > 0) {
                        nextToken();
                        assertOperand(_token);
                        inst = new Instruction(mnemToken, (OperandToken) _token);
                    } else {
                        inst = new Instruction(mnemToken, null);
                    }
                    nextToken();
                    assertTerminator(_token);
                    break;
                case COMMENT:
                    comment = (CommentToken) _token;
                    nextToken();
                    assertTerminator(_token);
                    break;
                case DIRECTIVE:
                    DirectiveToken directiveToken;
                    directiveToken = (DirectiveToken) _token;

                    nextToken();

                    assertOperand(_token);
                    inst = new Instruction(directiveToken, (OperandToken) _token);

                    nextToken();
                    assertTerminator(_token);
                    break;
            }
        }
        return new LineStatement(label, inst, comment);
    }

    /**
     * Loads the next _token to parse into the _token member
     */
    protected void nextToken() {
        _token = _lexer.getToken();
    }
}
