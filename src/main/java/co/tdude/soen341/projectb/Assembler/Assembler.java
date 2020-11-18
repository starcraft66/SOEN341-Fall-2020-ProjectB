package co.tdude.soen341.projectb.Assembler;

import co.tdude.soen341.projectb.Assembler.AssemblyParser.Parser;
import co.tdude.soen341.projectb.Environment.Environment;

import java.io.File;
import java.io.IOException;

public class Assembler {
    public static void main(String[] args) throws IOException {
        File asmFile = new File(args[0]);
        SourceFile.StoreAssemblyFile(asmFile);
        Environment environment = new Environment(asmFile);
        Parser assemblyParser = new Parser(environment);
        AssemblyUnit assemblyUnit = assemblyParser.parse();
        assemblyUnit.GenerateListing();
    }
}
