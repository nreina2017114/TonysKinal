package org.nestorreina.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.nestorreina.system.Principal;


public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaEmpresas(){
        escenarioPrincipal.ventanaEmpresas();
    }
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    }
    public void ventanaPresupuesto(){
        escenarioPrincipal.ventanaPresupuesto();
    }
    public void ventanaTipoEmpleado(){
        escenarioPrincipal.ventanaTipoEmpleado();
    }
    public void ventanaEmpleados(){
        escenarioPrincipal.ventanaEmpleados();
    }
    public void ventanaServicios(){
        escenarioPrincipal.ventanaServicios();
    }
    public void ventanaProductos(){
        escenarioPrincipal.ventanaProductos();
    }
    public void ventanaTipoPlato(){
        escenarioPrincipal.ventanaTipoPlato();
    }
    public void ventanaPlatos(){
        escenarioPrincipal.ventanaPlatos();
    }
    public void ventanaServicioshasPlatos(){
        escenarioPrincipal.ventanaServicioshasPlatos();
    }
    public void ventanaServicioshasEmpleados(){
        escenarioPrincipal.ventanaServicioshasEmpleados();
    }
    public void ventanaProductoshasPlatos(){
        escenarioPrincipal.ventanaProductoshasPlatos();
    }
}
