
package org.nestorreina.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.nestorreina.controller.EmpleadosController;
import org.nestorreina.controller.EmpresasController;
import org.nestorreina.controller.MenuPrincipalController;
import org.nestorreina.controller.PlatosController;
import org.nestorreina.controller.PresupuestoController;
import org.nestorreina.controller.ProductosController;
import org.nestorreina.controller.ProductoshasPlatosController;
import org.nestorreina.controller.ProgramadorController;
import org.nestorreina.controller.ServiciosController;
import org.nestorreina.controller.ServicioshasEmpleadosController;
import org.nestorreina.controller.ServicioshasPlatosController;
import org.nestorreina.controller.TipoEmpleadoController;
import org.nestorreina.controller.TipoPlatoController;


public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/nestorreina/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Tony's Kinal App");
        escenarioPrincipal.getIcons().add(new Image("/org/nestorreina/image/iconoGeneral.png"));
        //Parent root = FXMLLoader.load(getClass().getResource("/org/nestorreina/view/MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
        menuPrincipal();
        escenarioPrincipal.show();
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal =(MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",402,367);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpresas(){
        try{
            EmpresasController Empresas = (EmpresasController) cambiarEscena("EmpresasView.fxml",664,484);
            Empresas.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController Programador = (ProgramadorController) cambiarEscena("ProgramadorView.fxml",600,400);
            Programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPresupuesto(){
        try{
            PresupuestoController Presupuesto = (PresupuestoController)cambiarEscena("PresupuestoView.fxml",651,571);
            Presupuesto.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void ventanaTipoEmpleado(){
        try{
            TipoEmpleadoController TipoEmpleado = (TipoEmpleadoController)cambiarEscena("TipoEmpleadoView.fxml",600,488);
            TipoEmpleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void ventanaEmpleados(){
        try{
            EmpleadosController Empleados = (EmpleadosController)cambiarEscena("EmpleadosView.fxml",1091,582);
            Empleados.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void ventanaServicios(){
        try{
            ServiciosController servicios = (ServiciosController)cambiarEscena("ServiciosView.fxml",912,644);
            servicios.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void ventanaProductos(){
        try{
            ProductosController productos = (ProductosController)cambiarEscena("ProductosView.fxml",571,540);
            productos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void ventanaTipoPlato(){
        try{
            TipoPlatoController tipoPlato= (TipoPlatoController)cambiarEscena("TipoPlatoView.fxml",655,421);
            tipoPlato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void ventanaPlatos(){
        try{
            PlatosController platos= (PlatosController)cambiarEscena("PlatosView.fxml",786,506);
            platos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }

    public void ventanaServicioshasPlatos(){
        try{
            ServicioshasPlatosController servicioshasplatos= (ServicioshasPlatosController)cambiarEscena("Servicios_has_PlatosView.fxml",600,529);
            servicioshasplatos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }

    public void ventanaServicioshasEmpleados(){
        try{
            ServicioshasEmpleadosController servicioshasempleados= (ServicioshasEmpleadosController)cambiarEscena("Servicios_has_EmpleadosView.fxml",773,598);
            servicioshasempleados.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void ventanaProductoshasPlatos(){
        try{
            ProductoshasPlatosController productoshasplatos= (ProductoshasPlatosController)cambiarEscena("Productos_has_PlatosView.fxml",813,426);
            productoshasplatos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena (String fxml,int ancho, int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader  cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
}
