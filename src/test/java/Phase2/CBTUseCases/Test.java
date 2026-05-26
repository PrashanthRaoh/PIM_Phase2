package Phase2.CBTUseCases;

import java.io.IOException;

import common_functions.ExcelUtilities;

public class Test {

	public static void main(String[] args) throws IOException {
		ExcelUtilities.WriteEntietiestoExcel("TC_001_ABCD", "12345677");
	}

}
