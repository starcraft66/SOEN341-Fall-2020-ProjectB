package Assembler;

import java.io.File;

public final class SourceFile {

	private static File _asmFile;
	private static String _asmFileStr;
	
	public static void StoreAssemblyFile(File asmFile) {
		
		_asmFile = asmFile;
	
	}
	
	public static void ConvertToString() {
		
		_asmFileStr = _asmFile.toString();
		
	}

}
