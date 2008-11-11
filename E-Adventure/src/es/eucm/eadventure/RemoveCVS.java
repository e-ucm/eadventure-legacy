package es.eucm.eadventure;

import java.io.File;

import javax.swing.JFileChooser;

public class RemoveCVS {

	public static void main (String[]args){
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			deleteCVS(chooser.getSelectedFile());
		}
		
	}
	
	private static void deleteCVS(File file){
		if (file.isDirectory() && file.getName().equals("CVS")){
			deleteDir(file);
		} else if (file.isDirectory()){
			for (File child:file.listFiles()){
				deleteCVS(child);
			}
		}
	}
	
	private static void deleteDir (File dir){
		if (dir.isDirectory() && dir.exists()){
			for (File child: dir.listFiles()){
				if (child.isDirectory())
					deleteDir(child);
				else
					child.delete();
			}
			dir.delete();
		}
	}
}
