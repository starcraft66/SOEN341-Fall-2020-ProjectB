package co.tdude.soen341.projectb.Environment;

import co.tdude.soen341.projectb.Lexer.Lexer;
import co.tdude.soen341.projectb.Reader.Reader;
import co.tdude.soen341.projectb.SymbolTable.SymbolTable;

import java.io.*;

public class Environment {
   private File asmFile;
   private Lexer lexer;
   private Reader reader;
   private SymbolTable symTable;
   //TODO : Add field for ErrorReporter

   /**
    * constructor of Environment
    * @param asmFile: Assembly source file
    */
   public Environment(File asmFile){
      this.asmFile = asmFile;
      this.reader = new Reader(this.asmFile);
//      this.symTable = new SymbolTable();
      this.lexer = new Lexer(reader/*, symTable*/);
      //TODO : Add instantiation of ErrorReporter
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

   /* TODO: Introduce getErrorReporter()
   public ErrorReporter getErrorReporter (){
      return eReporter;
   }
    */
}
