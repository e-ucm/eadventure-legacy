package es.eucm.eadventure.editor.auxiliar.filefilters;

import es.eucm.eadventure.common.auxiliar.FileFilter;

import java.io.File;

public class GenericFileFilter extends FileFilter {

    private String extension;

    public GenericFileFilter(String extension){
        this.extension = extension;
    }

    @Override
    public boolean accept(File file) {
        return file.getAbsolutePath( ).toLowerCase( ).endsWith( extension ) || file.isDirectory( );
    }

    @Override
    public String getDescription() {
        return "(" + extension + ")";
    }
}
