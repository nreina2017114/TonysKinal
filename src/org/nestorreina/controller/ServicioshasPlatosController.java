
package org.nestorreina.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.nestorreina.bean.Plato;
import org.nestorreina.bean.Servicios;
import org.nestorreina.bean.ServicioshasPlatos;
import org.nestorreina.db.Conexion;
import org.nestorreina.system.Principal;

public class ServicioshasPlatosController implements Initializable{

    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, CANCELAR, EDITAR, ACTUALIZAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<ServicioshasPlatos>listaServicioshasPlatos;
    private ObservableList<Plato>listaPlato;
    private ObservableList<Servicios> listaServicio;
    
    @FXML private TextField txtServicios_codigoServicio;
    @FXML private ComboBox cmbCodigoPlato;
    @FXML private ComboBox cmbCodigoServicio;
    @FXML private TableView tblServicios_has_Platos;
    @FXML private TableColumn colServicios_codigoServicio;
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colCodigoServicio;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoPlato.setItems(getPlatos());
        cmbCodigoServicio.setItems(getServicio());
    }
    
    public void cargarDatos(){
        tblServicios_has_Platos.setItems(getServicioshasPlatos());
        colServicios_codigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioshasPlatos, Integer>("Servicios_codigoServicio"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<ServicioshasPlatos, Integer>("codigoPlato"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioshasPlatos, Integer>("codigoServicio"));

    }
    
    public void seleccionarElemento(){
        if(tblServicios_has_Platos.getSelectionModel().getSelectedItem() != null){
        txtServicios_codigoServicio.setText(String.valueOf(((ServicioshasPlatos)tblServicios_has_Platos.getSelectionModel().getSelectedItem()).getServicios_codigoServicio()));
        cmbCodigoPlato.getSelectionModel().select(buscarPlatos(((ServicioshasPlatos) tblServicios_has_Platos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        cmbCodigoServicio.getSelectionModel().select(buscarServicio(((ServicioshasPlatos) tblServicios_has_Platos.getSelectionModel().getSelectedItem()).getCodigoServicio()));

        }
    }
    
    public ObservableList<ServicioshasPlatos>getServicioshasPlatos(){
        ArrayList<ServicioshasPlatos> lista = new ArrayList<ServicioshasPlatos>();
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios_has_Platos}");
           ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new ServicioshasPlatos(resultado.getInt("Servicios_codigoServicio"),
                                                 resultado.getInt("codigoPlato"),
                                                 resultado.getInt("codigoServicio")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServicioshasPlatos = FXCollections.observableArrayList(lista);
    }

    
    public ObservableList<Plato> getPlatos(){
        ArrayList<Plato> lista = new ArrayList<Plato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPlatos}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Plato(resultado.getInt("codigoPlato"),
                                     resultado.getInt("cantidad"),
                                     resultado.getString("nombrePlato"),
                                     resultado.getString("descripcionPlato"),
                                     resultado.getDouble("precioPlato"),
                                     resultado.getInt("codigoTipoPlato")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return listaPlato = FXCollections.observableArrayList(lista);
    }
    
    
    public ObservableList<Servicios> getServicio(){
        ArrayList<Servicios> lista = new ArrayList<Servicios>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Servicios(resultado.getInt("codigoServicio"),
                                   resultado.getDate("fechaServicio"),
                                   resultado.getString("tipoServicio"),
                                   resultado.getString("horaServicio"),
                                   resultado.getString("lugarServicio"),
                                   resultado.getString("telefonoContacto"),
                                   resultado.getInt("codigoEmpresa")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServicio = FXCollections.observableArrayList(lista);
    }
    
    
    public ServicioshasPlatos buscarServicioshasPlatos(int Servicios_codigoServicio){
        ServicioshasPlatos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarServicios_has_Platos(?)}");
            procedimiento.setInt(1, Servicios_codigoServicio);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new ServicioshasPlatos(registro.getInt("Servicios_codigoServicio"),
                                registro.getInt("codigoPlato"),
                                registro.getInt("codigoServicio"));
            
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return resultado;
        
    }
    
    public Plato buscarPlatos(int codigoPlato){
        Plato resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPlatos(?)}");
            procedimiento.setInt(1, codigoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Plato(registro.getInt("codigoPlato"),
                                registro.getInt("cantidad"),
                                registro.getString("nombrePlato"),
                                registro.getString("descripcionPlato"),
                                registro.getDouble("precioPlato"),
                                registro.getInt("codigoTipoPlato"));
            
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return resultado;
        
    }
    
    
    public Servicios buscarServicio(int codigoServicio){
        Servicios resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarServicios(?)}");
            procedimiento.setInt(1, codigoServicio);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Servicios(
                        registro.getInt("codigoServicio"),
                        registro.getDate("fechaServicio"),
                        registro.getString("tipoServicio"),
                        registro.getString("horaServicio"),
                        registro.getString("lugarServicio"),
                        registro.getString("telefonoContacto"),
                        registro.getInt("codigoEmpresa"));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    public void nuevo(){
        switch (tipoDeOperacion) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        ServicioshasPlatos registro = new ServicioshasPlatos();
        registro.setCodigoPlato(((Plato)cmbCodigoPlato.getSelectionModel().getSelectedItem()).getCodigoPlato());
        registro.setCodigoServicio(((Servicios) cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());

        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServicios_has_Platos(?,?)}");
            procedimiento.setInt(1, registro.getCodigoPlato());
            procedimiento.setInt(2, registro.getCodigoServicio());
            procedimiento.execute();
            listaServicioshasPlatos.add(registro);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void eliminar(){
        switch (tipoDeOperacion) {
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblServicios_has_Platos.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro", "Eliminar ServicioshasPlatos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarServicios_has_Platos(?)}");
                            procedimiento.setInt(1, ((ServicioshasPlatos)tblServicios_has_Platos.getSelectionModel().getSelectedItem()).getServicios_codigoServicio());
                            procedimiento.execute();
                            listaServicioshasPlatos.remove(tblServicios_has_Platos.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                            tblServicios_has_Platos.getSelectionModel().clearSelection();
                        }catch(Exception e){
                            e.printStackTrace();
                            
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
        }
    }
    
    public void cancelar(){
        btnNuevo.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnEditar.setText("Editar");
        btnReporte.setText("Reporte");
        limpiarControles();
        btnNuevo.setDisable(false);
        btnEliminar.setDisable(false);
        btnEditar.setDisable(false);
        btnReporte.setDisable(false);
        desactivarControles();
        cargarDatos();
        tipoDeOperacion = operaciones.NINGUNO;
    }
    
    public void desactivarControles(){
        txtServicios_codigoServicio.setEditable(false);
        cmbCodigoPlato.setDisable(true);
        cmbCodigoServicio.setDisable(true); 
    }
    
    public void activarControles(){
        txtServicios_codigoServicio.setEditable(false);
        cmbCodigoPlato.setDisable(false);
        cmbCodigoServicio.setDisable(false);
    }
    
    public void limpiarControles(){
        txtServicios_codigoServicio.setText("");
        cmbCodigoPlato.getSelectionModel().select(null);
        cmbCodigoServicio.getSelectionModel().select(null);
    }
   
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
