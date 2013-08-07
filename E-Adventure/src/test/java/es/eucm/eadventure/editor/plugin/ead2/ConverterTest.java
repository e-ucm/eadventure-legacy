package es.eucm.eadventure.editor.plugin.ead2;

import es.eucm.eadventure.editor.control.Controller;

import java.io.File;

public class ConverterTest {

    public static final String TEST_FOLDER = "/home/eva/repositories/eadventure-legacy/Projects/Dama Boba";
    //public static final String TEST_FOLDER = "/home/eva/repositories/eadventure-legacy/Projects/TestBugs";

    public static void main(String args[]){
        Converter conv = new Converter(new Controller(){
            @Override
            public String getProjectFolder() {
                return TEST_FOLDER;
            }
        });
        File f = new File(TEST_FOLDER);
        if ( f.exists() ){
            conv.setSimplifications(false);
            conv.run();
        } else {
            System.out.println(f.getAbsolutePath() + " doesn't exist");
        }
    }
}
