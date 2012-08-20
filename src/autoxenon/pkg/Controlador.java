/*  AutoXenon is a noob-friendly libxenon installer
    Copyright (C) 2011-2012  chemone

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package autoxenon.pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author chemone
 */
public class Controlador {

    /*Éste método ejecuta un terminal donde se instalarán los paquetes básicos
     * para la toolchain de libxenon
     */
        private Process terminal;
        private String usuario="";
        private void instalarPaquetes(){
        try {
            terminal = Runtime.getRuntime().exec("gnome-terminal -x sudo apt-get install libgmp3-dev libmpfr-dev libmpc-dev texinfo git-core gettext build-essential");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Se ha producido un error el programa terminará", "Error", 
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    /*Éste método ejecuta un terminal donde se ejecutará el script con 
     * la instalación de la toolchain
     */
        private void llamarScript(){
            try {
                terminal.waitFor();
                terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git/toolchain -x sudo ./build-xenon-toolchain toolchain");
                terminal.waitFor();
                JOptionPane.showMessageDialog(null, "¡¡¡¡Enhorabuena!!!!\nSe ha terminado de instalar libxenon.\n"
                        + "Por favor, añade las dos últimas líneas de éste mensaje al fichero .bashrc,\n"
                        + "tanto del usuario root como de tu usuario normal\n"
                        + "(/home/nombredeususario/.bashrc, /root/.bashrc):\n"
                        + "export DEVKITXENON=\"/usr/local/xenon\"\n"
                        + "export PATH=\"$PATH:$DEVKITXENON/bin:$DEVKITXENON/usr/bin\"", "¡¡¡Enhorabuena!!!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception ex) {
                System.out.println(ex.toString());
                JOptionPane.showMessageDialog(null, "Se ha producido un error el programa terminará", "Error", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        /*Éste método comprueba si existen los directorios y si no los crea,
         * además comprueba que exista el fichero opt y, si existe, pide al usuario
         * su nombre y le otorga derechos para leer y escribir en ellos.
         */
        private void crearDirectorios(){
            File opt= new File("/opt");
            File losFicheros=new File("/opt/free60-git");
            if (opt.exists()){
                do
                usuario = JOptionPane.showInputDialog("Escribe tu nombre de usuario habitual:");
                while (usuario.isEmpty());
                try {
                        terminal = Runtime.getRuntime().exec("gnome-terminal -x sudo chown -R "+usuario+":"+usuario+
                            " /opt");
                    } 
                    catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                  }
            }
            if(!opt.exists() || !losFicheros.exists()){
                losFicheros.mkdirs();
            }
            else if (losFicheros.exists()){
            try {
                terminal.waitFor();
                terminal = Runtime.getRuntime().exec("gnome-terminal -x rm -R /opt/free60-git");
                losFicheros.mkdirs();
            } catch (Exception ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            }
        }
        /*Éste método, espera un String que representa la toolchain que se va a 
         * descargar e instalar,luego, abre un terminal y descarga los repositorios correspondientes*/
        private void descargarRepositorios(String toolchain){
            String xenon=new String();
            try {
                if (toolchain.equalsIgnoreCase("gligli")){
                    toolchain="gligli";
                    xenon="libxenon";
                }
//                else if (toolchain.equalsIgnoreCase("ced2911_Experimental")){
//                    toolchain="Ced2911";
//                    xenon="libxenon-experimental";
//                }
                else if (toolchain.equalsIgnoreCase("Free60")){
                    toolchain="Free60Project";
                    xenon="libxenon";
                }
                terminal.waitFor();
                crearDirectorios();
                terminal.waitFor();
                terminal = Runtime.getRuntime().exec("gnome-terminal -x git clone git://github.com/"+toolchain+"/"+xenon+" /opt/free60-git");
                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Se ha producido un error el programa terminará", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                }
        }
        /*Éste método espera un String que representa la toolchain
         * que se va a instalar y ejecuta todo el proceso de instalación de la toolchain, 
         * llamando a los métodos creados anteriormente
         */
        public void instalarToolchain(String toolchain){
            instalarPaquetes();
            descargarRepositorios(toolchain);
            llamarScript();
        }
        /*
         * Este método instala todas las librerías necesarias para libxenon.
         */
        public void instalarLibrerías(){
        if (usuario.isEmpty()){
            do
            usuario= JOptionPane.showInputDialog("Escribe tu nombre de usuario habitual:");
            while (usuario.isEmpty());
        }
            try {
            //terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git/toolchain -x sudo ./build-xenon-toolchain libs");
            //terminal.waitFor();
            instalaFat();
            instalaNtfs();
            instalaExt2();
            instalaSDL();
            instalaSdl_Image();
            instalaSdl_Mixer();
            instalaSdl_ttf();
            instalaXtaf();
            instalaZLX();
        } catch (Exception ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                
}
        /*Instala SDL*/
        public void instalaSDL(){
            File dir=new File("/opt/free60-git/libSDLXenon");
            if (dir.exists()){
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x rm -R "+dir.getAbsolutePath());
                    terminal.waitFor();
                } catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                try {
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git -x git clone git://github.com/LibXenonProject/libSDLXenon.git");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x sudo chown -R "+usuario+":"+usuario+" /usr/local/xenon");
                    terminal.waitFor();
                    
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git/libSDLXenon -x make -f Makefile.xenon install");
                    InputStream str= terminal.getErrorStream();
                    BufferedReader br = new BufferedReader (new InputStreamReader (str)); 
                    String error=br.readLine();
                    
                    while(error!=null){
                        
                        System.out.println(error);
                        br.readLine();
                    }
                    terminal.waitFor();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        public void instalaZLX(){
            File dir=new File("/opt/free60-git/ZLX-Library");
            if (dir.exists()){
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x rm -R "+dir.getAbsolutePath());
                    terminal.waitFor();
                } catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git -x git clone git://github.com/LibXenonProject/ZLX-Library.git");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x sudo chown -R "+usuario+":"+usuario+" /usr/local/xenon");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git/ZLX-Library -x make -f Makefile_lib install");
                    terminal.waitFor();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        public void instalaFat(){
            File dir=new File("/opt/free60-git/fat-xenon");
            if (dir.exists()){
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x rm -R "+dir.getAbsolutePath());
                    terminal.waitFor();
                } catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git -x git clone git://github.com/LibXenonProject/fat-xenon.git");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x sudo chown -R "+usuario+":"+usuario+" /usr/local/xenon");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make install");
                    terminal.waitFor();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
         public void instalaNtfs(){
            File dir=new File("/opt/free60-git/ntfs-xenon");
            if (dir.exists()){
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x rm -R "+dir.getAbsolutePath());
                    terminal.waitFor();
                } catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git -x git clone git://github.com/LibXenonProject/ntfs-xenon.git");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x sudo chown -R "+usuario+":"+usuario+" /usr/local/xenon");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make install");
                    terminal.waitFor();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
          public void instalaExt2(){
            File dir=new File("/opt/free60-git/ext2fs-xenon");
            if (dir.exists()){
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x rm -R "+dir.getAbsolutePath());
                    terminal.waitFor();
                } catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git -x git clone git://github.com/LibXenonProject/ext2fs-xenon.git");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x sudo chown -R "+usuario+":"+usuario+" /usr/local/xenon");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make install");
                    terminal.waitFor();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
           public void instalaXtaf(){
            File dir=new File("/opt/free60-git/xtaflib");
            if (dir.exists()){
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x rm -R "+dir.getAbsolutePath());
                    terminal.waitFor();
                } catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git -x git clone git://github.com/LibXenonProject/xtaflib.git");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x sudo chown -R "+usuario+":"+usuario+" /usr/local/xenon");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make install");
                    terminal.waitFor();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
            public void instalaSdl_ttf(){
            File dir=new File("/opt/free60-git/SDL_ttf");
            if (dir.exists()){
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x rm -R "+dir.getAbsolutePath());
                    terminal.waitFor();
                } catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git -x git clone git://github.com/LibXenonProject/SDL_ttf.git");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x sudo chown -R "+usuario+":"+usuario+" /usr/local/xenon");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make install");
                    terminal.waitFor();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
                  public void instalaSdl_Image(){
            File dir=new File("/opt/free60-git/SDL_Image");
            if (dir.exists()){
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x rm -R "+dir.getAbsolutePath());
                    terminal.waitFor();
                } catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git -x git clone git://github.com/LibXenonProject/SDL_Image.git");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x sudo chown -R "+usuario+":"+usuario+" /usr/local/xenon");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make -f Makefile.xenon install");
                    terminal.waitFor();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
               public void instalaSdl_Mixer(){
            File dir=new File("/opt/free60-git/SDL_Mixer");
            if (dir.exists()){
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x rm -R "+dir.getAbsolutePath());
                    terminal.waitFor();
                } catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
                try {
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory=/opt/free60-git -x git clone git://github.com/LibXenonProject/SDL_Mixer.git");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal -x sudo chown -R "+usuario+":"+usuario+" /usr/local/xenon");
                    terminal.waitFor();
                    terminal = Runtime.getRuntime().exec("gnome-terminal --working-directory="+dir.getAbsolutePath()+" -x make -f Makefile.xenon install");
                    terminal.waitFor();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
