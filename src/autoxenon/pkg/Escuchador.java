
 /* AutoXenon is a noob-friendly libxenon installer
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author chemone
 */
public class Escuchador implements ActionListener{
    private Ventana laVentana;
    public Escuchador(Ventana v){
        this.laVentana=v;
    }
    public void actionPerformed(ActionEvent e) {
        Object Fuente = e.getSource();
        Controlador elModelo = laVentana.getElcontrolador();
        if (Fuente==laVentana.getBToolchain()){
            elModelo.instalarToolchain(laVentana.getElCombo().getSelectedItem().toString());
        }
        else if (Fuente==laVentana.getBInstalarLibrerias()){
            elModelo.instalarLibrerías();
        }
    }
    
}
