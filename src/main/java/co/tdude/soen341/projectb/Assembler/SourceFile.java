package co.tdude.soen341.projectb.Assembler;

import java.io.File;

/**
 * Stores the .asm source file being compiled.
 */
public final class SourceFile {

	/**
	 * The File representation of the .asm file.
	 */
	private static File _asmFile;

	/**
	 * The String representation of the .asm file.
	 */
	private static String _asmFileStr;

	/**
	 * Stores the .asm file locally.
	 * @param asmFile The .asm file received fromt he command line.
	 */
	public static void StoreAssemblyFile(File asmFile) {
		_asmFile = asmFile;
		ConvertToString();
	}

	/**
	 * Gets the name of the assembly file.
	 * @return The assembly file name.
	 */
	public static String getName() {
		return _asmFileStr;
	}

	/**
	 * Converts the .asm file to a String.
	 */
	public static void ConvertToString() {
		_asmFileStr = _asmFile.toString();
		_asmFileStr = _asmFileStr.substring(0, _asmFileStr.lastIndexOf('.'));
	}
}
