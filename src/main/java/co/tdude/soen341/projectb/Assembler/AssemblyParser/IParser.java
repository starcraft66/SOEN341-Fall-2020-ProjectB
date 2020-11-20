package co.tdude.soen341.projectb.Assembler.AssemblyParser;

import co.tdude.soen341.projectb.Assembler.AssemblyUnit;

/**
 * Parses the assembly file and extracts a list of LineStatements.
 */
public interface IParser {
    /**
     * Parses the assembly file for tokens, creates LineStatements, and adds them to a list.
     * @return The assembly unit that is comprised of all the LineStatements.
     */
    AssemblyUnit parse();
}