package com.mascas.renameFiles;

import java.io.File;
import java.util.Arrays;

public class Main {

	private static final String COSA = "] [";
	private static final String FLECHA = " -> ";
	private static final String KBPS = " (320 kbps)";
	private static final String KBPS2 = "(320 kbps)";
	private static final String GUION = "-";
	private static final String DOT_MP3 = "mp3";

	public static void main(String[] args) {
		File[] files = new File(args[0]).listFiles();

		boolean isSmashDLC = Arrays.stream(args).anyMatch(x -> x.equalsIgnoreCase("\\smash") || x.equalsIgnoreCase("\\dlc"));
		boolean isMp3 = Arrays.stream(args).anyMatch(x -> x.equalsIgnoreCase("\\m") || x.equalsIgnoreCase("\\mp3"));
		boolean rename = Arrays.stream(args).anyMatch(x -> x.equalsIgnoreCase("\\r") || x.equalsIgnoreCase("\\rename"));

		if (rename)
			System.out.println("Procediendo al renombrado de los ficheros ubicados en \"" + args[0] + "\"\n");
		else {
			System.out.println("Procediendo al listado sin renombrar de los ficheros ubicados en \"" + args[0] + "\"");
			System.out.println("Si desea proceder la renombrado, incluya el argumento \\r o \\rename\n");
		}

		if (isSmashDLC)
			renameSmashDLC(files, rename);
		else if (isMp3)
			renameMp3(files, rename);
		else {
			System.out.println("Falta argumento para indicar le tipo de archivo:");
			System.out.println("    \\smash - DLC's de smash brosh.");
			System.out.println("    \\dlc - DLC's de smash brosh.");
			System.out.println("    \\m - Musica en formato MP3.");
			System.out.println("    \\mp3 - Musica en formato MP3.");
		}
	}

	public static void renameMp3(File[] files, boolean rename) {
		String name;
		String[] asd;
		File tempFile;
		for (File file : files) {
			if (file.isHidden() || file.isDirectory())
				continue;

			name = file.getName();
			name = name.replace(KBPS, "");
			name = name.replace(KBPS2, "");
			name = name.replaceAll("\\(.*\\)", "");
			name = name.replaceAll("\\[.*\\]", "");
			name = name.replace("HD", "");
			name = name.replace("¿", "");
			name = name.replace("?", "");
			name = name.replace("_", "");
			if (name.contains(GUION)) {
				asd = name.split(GUION);
				name = asd[1];
				for (int i = 2; i < asd.length; i++) { 
					name += asd[i];
				}
			}
			asd = name.split(DOT_MP3);
			name = asd[0];
			for (int i = 1; i < asd.length - 1; i++) {
				name += asd[i];
			}
			name = name.strip();
			name = name.replaceAll("^\\d\\d? ", "");
			name = name.strip();
			name += DOT_MP3;
			tempFile = new File(file.getParent() + File.separator + name);
			System.out.println(file.getName() + FLECHA + tempFile.getName());
			if (rename) file.renameTo(tempFile);
		}
	}

	public static void renameSmashDLC(File[] files, boolean rename) {
		int index;
		String name;
		File tempFile;
		for (File file : files) {
			name = file.getName();
			while ((index = name.indexOf(COSA)) != -1) {
				name = name.substring(index + 3);
			}
			tempFile = new File(file.getParent() + File.separator + name);
			System.out.println(file.getName() + FLECHA + tempFile.getPath());
			if (rename) file.renameTo(tempFile);
		}
	}

}
