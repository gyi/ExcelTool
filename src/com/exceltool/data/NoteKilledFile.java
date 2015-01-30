package com.exceltool.data;

import java.util.HashSet;
import java.util.Set;

public class NoteKilledFile {
	private static Set<String> killedFile = new HashSet<String>();

	public static void toInit() {
		killedFile = new HashSet<String>();
	}
	
	public static void addKilledFile(String fileName){
		killedFile.add(fileName);
	}

	public static Set<String> getKilledFile() {
		return killedFile;
	}
	
	
	
	
}
