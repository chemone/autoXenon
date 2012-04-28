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

/**
 *
 * @author chemone
 */
public class AutoXenon {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Controlador elModelo=new Controlador();
        Ventana elPanel= new Ventana(elModelo);
        elPanel.setVisible(true);
    }
}
