package co.tdude.soen341.projectb.Assembler;

import co.tdude.soen341.projectb.Assembler.AssemblyParser.Parser;
import co.tdude.soen341.projectb.Environment.Environment;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.File;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Assembles an assembly file and generates corresponding binary and listing files.
 */
public class Assembler {
    public static void main(String[] args) throws IOException {
        ArgumentParser parser = ArgumentParsers.newFor("assembler").build()
                .defaultHelp(true)
                .description("Parse an assembly file, optionally output a listing and assemble it to a binary.");
        parser.addArgument("asmfile").nargs("?")
                .help("Path to the source assembly file")
                .setDefault("asmTestFile.asm")
                .type(String.class);
        parser.addArgument("-l", "--listing")
                .help("Output a listing file")
                .action(Arguments.storeTrue())
                .required(false);
        parser.addArgument("-v", "--verbose")
                .help("Print verbose information")
                .action(Arguments.storeTrue())
                .required(false);
        parser.addArgument("-s", "--skip-errors")
                .help("Skip errors and continue parsing")
                .action(Arguments.storeTrue())
                .required(false);
        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            // We should return 1 here ideally but it causes IDEA not to show the
            // output and complain about the exit code
            System.exit(0);
        }
        if (ns.getBoolean("verbose")) {
            Logger.getLogger("").setLevel(Level.FINE);
            for (Handler h : Logger.getLogger("").getHandlers()) {
                h.setLevel(Level.FINE);
            }
        }
        Logger.getLogger("").info("Starting assembly parsing");
        String asmFilePath = ns.getString("asmfile");
        File asmFile = new File(asmFilePath);
        SourceFile.StoreAssemblyFile(asmFile);
        Environment environment = new Environment(asmFile);
        Parser assemblyParser = new Parser(environment);
        AssemblyUnit assemblyUnit = assemblyParser.parse();

        Logger.getLogger("").info("Generating executable file");
        if (ns.getBoolean("listing")) {
            Logger.getLogger("").info("Generating listing file");
            assemblyUnit.Assemble(true);
        } else {
            // Otherwise, just assemble it to exe with no listing
            assemblyUnit.Assemble(false);
        }
        Logger.getLogger("").info("Assembly parsing done");
        System.out.println(environment.getErrorReporter().getErrorLst().toString());
    }

}
