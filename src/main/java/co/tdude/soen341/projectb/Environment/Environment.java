package co.tdude.soen341.projectb.Environment;

import co.tdude.soen341.projectb.Lexer.Lexer;
import co.tdude.soen341.projectb.Reader.Reader;
import co.tdude.soen341.projectb.SymbolTable.SymbolTable;
import co.tdude.soen341.projectb.ErrorReporter.ErrorReporter;


import java.io.*;

public class Environment {
   private File asmFile;
   private Lexer lexer;
   private Reader reader;
   private SymbolTable symTable;
   private ErrorReporter eReporter;
   /**
    * constructor of Environment
    * @param asmFile: Assembly source file
    */
   public Environment(File asmFile){
      this.asmFile = asmFile;
      this.reader = new Reader(this.asmFile);
//      this.symTable = new SymbolTable();
      this.lexer = new Lexer(reader/*, symTable*/);
      this.eReporter=new ErrorReporter();
   }

   public Lexer getLexer() {
      return lexer;
   }

   public SymbolTable getSymbolTable() {
      return symTable;
   }

   public File getSourceFile (){
      return asmFile;
   }

   public ErrorReporter getErrorReporter (){
      return eReporter;
   }

}
