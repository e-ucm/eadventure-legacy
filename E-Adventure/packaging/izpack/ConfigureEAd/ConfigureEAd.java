import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import com.izforge.izpack.util.AbstractUIProcessHandler;

public class ConfigureEAd {

    public static void run( AbstractUIProcessHandler handler, String[] args) {
    
		String installFolder = args[0];
		String installLang = args[1];

        handler.startProcess("Configuring eAdventure");

		String userFolder = System.getProperty("user.home");
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			try {
				userFolder = getWinDocumentsPath();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (System.getProperty("os.name").toLowerCase( ).contains( "mac" )) {
            try {
                Runtime.getRuntime().exec("chmod 775 " + installFolder + "/eAdventure-engine.app/Contents/MacOS/eAdventure-engine");
                Runtime.getRuntime().exec("chmod 775 " + installFolder + "/eAdventure-editor.app/Contents/MacOS/eAdventure-editor");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (System.getProperty("os.name").toLowerCase( ).contains( "unix" ) ||
                System.getProperty("os.name").toLowerCase( ).contains( "linux" )) {
            try {
                Runtime.getRuntime().exec("chmod 775 " + installFolder + "/run-eAdventure-editor.sh");
                Runtime.getRuntime().exec("chmod 775 " + installFolder + "/run-eAdventure-engine.sh");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		String lang = "en_EN";
		if (installLang.toLowerCase().equals("spa"))
			lang = "es_ES";
		else if (installLang.toLowerCase().equals("por"))
			lang = "pt_PT";
		else if (installLang.toLowerCase().equals("deu"))
			lang = "de_DE";
		else if (installLang.toLowerCase().equals("chn"))
            lang = "zh_CN";
		else if (installLang.toLowerCase().equals("ita"))
            lang = "it_IT";
		else if (installLang.toLowerCase().equals("rom"))
            lang = "ro_RO";
		else if (installLang.toLowerCase().equals("glg"))
            lang = "gl_ES";
		else if (installLang.toLowerCase().equals("rus"))
            lang = "tu_RU";
		else if (installLang.toLowerCase().equals("bra"))
            lang = "pt_BR";
		
	
		String exports = "My eAdventure games";
		String projects = "My eAdventure projects";
		String reports = "My eAdventure reports";
        if (lang.equals("es_ES") || lang.equals("es_LA")) {
            exports = "Mis juegos de eAdventure";
            projects = "Mis proyectos de eAdventure";
            reports = "Mis informes de eAdventure";
        }
		
		try {
			FileOutputStream fos = new FileOutputStream(installFolder + File.separator + "eadventure" + File.separator + "config_editor.xml");
			OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
					"<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">\n" +
					"<properties>\n" +
					"<comment>eAdventure editor configuration</comment>\n");
			out.write("<entry key=\"AboutFile\">about-" + lang + ".html</entry>\n");
			out.write("<entry key=\"ShowAtrezzoReferences\">true</entry>\n");
			out.write("<entry key=\"ShowItemReferences\">true</entry>\n");
			out.write("<entry key=\"ShowNPCReferences\">true</entry>\n");
			out.write("<entry key=\"LanguageFile\">"+ lang + ".xml</entry>\n");
			out.write("<entry key=\"ShowStartDialog\">true</entry>\n");
			out.write("<entry key=\"RecentFiles\">0</entry>\n");
			out.write("<entry key=\"LoadingImage\">img/loading/"+ lang + "/Editor2D-Loading.png</entry>\n");
			
			out.write("<entry key=\"ExportsDirectory\">" + userFolder + File.separator + exports + "</entry>\n");
			out.write("<entry key=\"ReportsDirectory\">" + userFolder + File.separator + reports + "</entry>\n");
			out.write("<entry key=\"ProjectsDirectory\">" + userFolder + File.separator + projects + "</entry>\n");
			
			out.write("</properties>");
			
			out.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		try {
			FileOutputStream fos = new FileOutputStream(installFolder + File.separator + "eadventure" + File.separator + "config_engine.xml");
			OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
					"<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">\n" +
					"<properties>\n" +
					"<comment>eAdventure engine configuration</comment>\n");
			out.write("<entry key=\"AboutFile\">about-" + lang + ".html</entry>\n");
			out.write("<entry key=\"LanguageFile\">"+ lang + ".xml</entry>\n");
			out.write("<entry key=\"ExportsDirectory\">"+ userFolder + File.separator + exports + "</entry>\n");
			out.write("<entry key=\"ReportsDirectory\">"+ userFolder + File.separator + reports + "</entry>\n");			
			out.write("</properties>");
			
			out.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		handler.finishProcess();
	}

	public static String getWinDocumentsPath() throws Exception {
		javax.swing.JFileChooser fr = new javax.swing.JFileChooser();
		javax.swing.filechooser.FileSystemView fw = fr.getFileSystemView();
		return fw.getDefaultDirectory().getAbsolutePath();
	}
}
