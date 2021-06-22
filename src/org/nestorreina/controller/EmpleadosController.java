
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


import org.nestorreina.bean.Empleados;
import org.nestorreina.bean.TipoEmpleado;
import org.nestorreina.db.Conexion;
import org.nestorreina.system.Principal;


public class EmpleadosController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Empleados>listaEmpleados;
    private ObservableList<TipoEmpleado>listaTipoEmpleado;
    
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNumeroEmpleado;
    @FXML private TextField txtApellidoEmpleado;
    @FXML private TextField txtNombreEmpleado;
    @FXML private TextField txtDireccionEmpleado;
    @FXML private TextField txtTelefonoContacto;
    @FXML private TextField txtGradoCocinero;
    @FXML private ComboBox cmbCodigoTipoEmpleado;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colNumeroEmpleado;
    @FXML private TableColumn colApellidoEmpleado;
    @FXML private TableColumn colNombreEmpleado;
    @FXML private TableColumn colDireccionEmpleado;
    @FXML private TableColumn colTelefonoContacto;  
    @FXML private TableColumn colGradoCocinero;
    @FXML private TableColumn colCodigoTipoEmpleado;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
            
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoTipoEmpleado.setItems(getTipoEmpleado());

    }
    
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleados());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("numeroEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidoEmpleado"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
        colDireccionEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccionEmpleado"));
        colTelefonoContacto.setCellValueFactory(new PropertyValueFactory<Empleados, String>("telefonoContacto"));
        colGradoCocinero.setCellValueFactory(new PropertyValueFactory<Empleados, String>("gradoCocinero"));
        colCodigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoTipoEmpleado"));

        
    }
    
    public ObservableList<Empleados> getEmpleados(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleados}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                resultado.getInt("numeroEmpleado"),
                resultado.getString("apellidoEmpleado"),
                resultado.getString("nombreEmpleado"),
                resultado.getString("direccionEmpleado"),
                resultado.getString("telefonoContacto"),
                resultado.getString("gradoCocinero"),
                resultado.getInt("codigoTipoEmpleado")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    
    return listaEmpleados = FXCollections.observableArrayList(lista);
}
    
    public ObservableList<TipoEmpleado> getTipoEmpleado(){
        ArrayList <TipoEmpleado> lista = new ArrayList<TipoEmpleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoEmpleado}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoEmpleado(resultado.getInt("codigoTipoEmpleado"),
                                            resultado.getString("descripcion")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
    return listaTipoEmpleado = FXCollections.observableArrayList(lista);
    
    }
    
    public void nuevo(){
        switch (tipoDeOperacion){
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
        Empleados registro = new Empleados();
             registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
             registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
             registro.setNombreEmpleado(txtNombreEmpleado.getText());
             registro.setDireccionEmpleado(txtDireccionEmpleado.getText());
             registro.setTelefonoContacto(txtTelefonoContacto.getText());
             registro.setGradoCocinero(txtGradoCocinero.getText());
             registro.setCodigoTipoEmpleado(((TipoEmpleado)cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleados(?,?,?,?,?,?,?)}");
             procedimiento.setInt(1, registro.getNumeroEmpleado());
             procedimiento.setString(2, registro.getApellidoEmpleado());
             procedimiento.setString(3, registro.getNombreEmpleado());
             procedimiento.setString(4, registro.getDireccionEmpleado());
             procedimiento.setString(5, registro.getTelefonoContacto());
             procedimiento.setString(6, registro.getGradoCocinero());
             procedimiento.setInt(7, registro.getCodigoTipoEmpleado());
             procedimiento.execute();
             listaEmpleados.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void seleccionarElemento(){
            if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
            txtCodigoEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
            txtNumeroEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNumeroEmpleado()));
            txtApellidoEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
            txtNombreEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado());
            txtDireccionEmpleado.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccionEmpleado());
            txtTelefonoContacto.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoContacto());
            txtGradoCocinero.setText(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getGradoCocinero());
            cmbCodigoTipoEmpleado.getSelectionModel().select(buscarTipoEmpleado(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
        }
    
    }
    
    
    public Empleados buscarEmpleados(int codigoEmpleado){
        Empleados resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleados(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                                registro.getInt("numeroEmpleado"),
                                registro.getString("apellidoEmpleado"),
                                registro.getString("nombreEmpleado"),
                                registro.getString("direccionEmpleado"),
                                registro.getString("telefonoContacto"),
                                registro.getString("gradoCocinero"),
                                registro.getInt("codigoTipoEmpleado"));
            }  
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado; 
    }
    
    
   
    
   public TipoEmpleado buscarTipoEmpleado(int codigoTipoEmpleado){
        TipoEmpleado resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoEmpleado(?)}");
            procedimiento.setInt(1, codigoTipoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new TipoEmpleado(registro.getInt("codigoTipoEmpleado"),
                        registro.getString("descripcion"));
                     
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
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
                if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleados(?)}");
                            procedimiento.setInt(1,((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
                            limpiarControles();
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
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                    cmbCodigoTipoEmpleado.setDisable(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Por favor seleccione un elemento");
                }break;   
            case ACTUALIZAR:
                    actualizar();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    cargarDatos();
                    tipoDeOperacion = operaciones.NINGUNO;
                    break;
 
        } 

    }
    
    public void actualizar(){
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpleados(?,?,?,?,?,?,?)}");
             Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
             registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
             registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
             registro.setNombreEmpleado(txtNombreEmpleado.getText());
             registro.setDireccionEmpleado(txtDireccionEmpleado.getText());
             registro.setTelefonoContacto(txtTelefonoContacto.getText());
             registro.setGradoCocinero(txtGradoCocinero.getText());
             TipoEmpleado tipoEmpleado = (TipoEmpleado)cmbCodigoTipoEmpleado.getSelectionModel().getSelectedItem();
             registro.setCodigoTipoEmpleado(tipoEmpleado.getCodigoTipoEmpleado());
             procedimiento.setInt(1, registro.getCodigoEmpleado());
             procedimiento.setInt(2, registro.getNumeroEmpleado());
             procedimiento.setString(3, registro.getApellidoEmpleado());
             procedimiento.setString(4, registro.getNombreEmpleado());
             procedimiento.setString(5, registro.getDireccionEmpleado());
             procedimiento.setString(6, registro.getTelefonoContacto());
             procedimiento.setString(7, registro.getGradoCocinero());
             procedimiento.execute();
             desactivarControles();
             limpiarControles();
         }catch(Exception e){
             e.printStackTrace();
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
        txtCodigoEmpleado.setEditable(false);
        txtNumeroEmpleado.setEditable(false);
        txtApellidoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(false);
        txtDireccionEmpleado.setEditable(false);
        txtTelefonoContacto.setEditable(false);
        txtGradoCocinero.setEditable(false);
        cmbCodigoTipoEmpleado.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNumeroEmpleado.setEditable(true);
        txtApellidoEmpleado.setEditable(true);
        txtNombreEmpleado.setEditable(true);
        txtDireccionEmpleado.setEditable(true);
        txtTelefonoContacto.setEditable(true);
        txtGradoCocinero.setEditable(true);
        cmbCodigoTipoEmpleado.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoEmpleado.setText("");
        txtNumeroEmpleado.setText("");
        txtApellidoEmpleado.setText("");
        txtNombreEmpleado.setText("");
        txtDireccionEmpleado.setText("");
        txtTelefonoContacto.setText("");
        txtGradoCocinero.setText("");
        cmbCodigoTipoEmpleado.getSelectionModel().select(null);
    }
    
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaTipoEmpleado(){
        escenarioPrincipal.ventanaTipoEmpleado();
    }
}