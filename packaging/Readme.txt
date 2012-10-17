 ***********************************************************************************
 * eAdventure (formerly <e-Adventure>, <e-Game>) is a research project of the      *
 *    e-UCM research group.                                                        *
 *                                                                                 *
 *   Copyright 2005-2012 e-UCM research group.                                     *
 *                                                                                 *
 *   You can access a list of all the contributors to eAdventure at:               *
 *         http://e-adventure.e-ucm.es/contributors                                *
 *                                                                                 *
 *   e-UCM is a research group of the Department of Software Engineering           *
 *         and Artificial Intelligence at the Complutense University of Madrid     *
 *         (School of Computer Science).                                           *
 *                                                                                 *
 *         C Profesor Jose Garcia Santesmases sn,                                  *
 *         28040 Madrid (Madrid), Spain.                                           *
 *                                                                                 *
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or           * 
 *         <http://www.e-ucm.es>                                                   *
 *                                                                                 *
 ***********************************************************************************

 ***********************************************************************************
 *                                                                                 *
 *     eAdventure is free software: you can redistribute it and/or modify          *
 *     it under the terms of the GNU Lesser General Public License as published by *
 *     the Free Software Foundation, either version 3 of the License, or           *
 *     any later version.                                                          *
 *                                                                                 *
 *     eAdventure is distributed in the hope that it will be useful,               *
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of              *
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               *
 *     GNU Lesser General Public License for more details.                         *
 *                                                                                 *
 *     You should have received a copy of the GNU Lesser General Public License    *
 *     along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.         *
 *                                                                                 *
 ***********************************************************************************


Thanks for using the eAdventure platform. eAdventure is an authoring tool for the development of educational 
adventure games (like Myst or Monkey Island) compounded by two applications: the adventure editor, which is used to
develop the games, and the game engine, which is used to play the games once their development is complete. 

eAdventure is completely free software, distributed under the terms of the LGPLv3 license. 
You can find all the information about the license under the 'licenses' folder. 
Moreover you can visit the http:/e-adventure.e-ucm.es/license for updated information. 

In this document you will find some installation guidelines for the present version: 1.5 RC4 Multiplatform.

1) INSTALLATION
---------------

This version of the platform requires you to have the Java Runtime Environment (version 1.6) installed in your system. 
We strongly recommend java 1.6.0_31. To get it you can get to the java official web site:

http://www.java.com

If you want to check if Java is installed properly on your system, type the following line in a console window:

java -version

If the command reports an error, or the java version is outdated and you think it is really installed check that
the JAVA_HOME directory (directory where java was installed) is in the $PATH enviroment variable.


2) EXECUTION
------------

To execute the application, you need to type the following lines on a console in the folder of the eAdventure 
release (for either editor or engine respectively). Please ensure the folder where java is installed in your 
system is in the $PATH environment var.

editor:
-------
cd eadventure
java -Xms512m -Xmx512m -jar eadventure-editor.jar


engine:
-------
cd eadventure
java -Xms512m -Xmx512m -jar eadventure-engine.jar



3) WINDOWS SHORT-CUT
--------------------

For your convenience you will find two .bat files to get the adventure editor and game engine running on windows systems. 
You just need to double-click on those files to execute both applications. 

editor:
-------

Run eAdventure editor.bat


engine:
-------

Run eAdventure engine.bat



4) LINUX SHORT-CUT
------------------

For your convenience you will find two .sh files to get the adventure editor and game engine running on linux systems. 
You just need to type the next console lines. 

NOTE: Please, ensure all the files are given the right permissions. Otherwise the execution of both editor or engine would crash.
The typical problem you will get in such case is a Java URLNotFoundException at the initialization phase.
Besides, we recommend to execute the platform with root privileges. The GUI of the game editor is not rendered properly in some
Linux systems if the user is not root.

editor:
-------

./run-eAdventure-editor.sh


engine:
-------

./run-eAdventure-engine.sh




5) Exports, Reports and Projects folders
-------------------------------
On the other hand, there will be three more folders in this path which deserve your attention. Those are the "Projects" folder, 
where by default your new Adventure projects will be generated, the "Exports" folder, where by default the games you 
export (those which are runnable by the game engine) will be stored, and the "Reports" folder, where the assessment reports
generated during play sessions will be kept.



6) Contact us!
---------------------------
We really encourage you to contact us. We would like to hear from you, either for suggestions or bug reports. 
With your help the eAdventure platform may become even better! 

You can leave a message for us using the following vias:
- By e-mail: e-adventure@e-ucm.es
- The adventure editor. Run the application, get to the main window, press on the "About" menu and then 
on "Send comments and suggestions". Then fill the form that will appear and press on send.

Finally, do not forget to visit the eAdventure web site (http://e-adventure.e-ucm.es) and our research group's web site:
(http://www.e-ucm.es).

Thank you!
