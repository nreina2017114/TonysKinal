
package org.nestorreina.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.nestorreina.bean.Empleados;
import org.nestorreina.bean.Empresa;
import org.nestorreina.bean.Servicios;
import org.nestorreina.bean.ServicioshasEmpleados;
import org.nestorreina.db.Conexion;
import org.nestorreina.system.Principal;


public class ServicioshasEmpleadosController implements Initializable{

    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, ELIMINAR, GUARDAR, ACTUALIZAR, EDITAR, CANCELAR, NINGUNO};
    private ObservableList<ServicioshasEmpleados> listaServicioshasEmpleados;
    private ObservableList<Servicios> listaServicio;
    private ObservableList<Empleados>listaEmpleados;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private DatePicker fecha;
    
    @FXML private TextField txtServicios_codigoServicio;
    @FXML private ComboBox cmbCodigoServicio;
    @FXML private ComboBox cmbCodigoEmpleado;
    @FXML private GridPane grpFechaEvento;
    @FXML private TextField txtHoraEvento;
    @FXML private TextField txtLugarEvento;
    @FXML private TableView tblServicios_has_Empleados;
    @FXML private TableColumn colServicios_codigoServicio;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colFechaEvento;
    @FXML private TableColumn colHoraEvento;
    @FXML private TableColumn colLugarEvento;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(false);
        grpFechaEvento.add(fecha, 0, 0);
        fecha.getStylesheets().add("/org/nestorreina/resource/DatePicker.css");
        cmbCodigoServicio.setItems(getServicio());
        cmbCodigoEmpleado.setItems(getEmpleados());
        
    }
    
    public void cargarDatos(){
        tblServicios_has_Empleados.setItems(getServicioshasEmpleados());
        colServicios_codigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioshasEmpleados, Integer>("Servicios_codigoServicio"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServicioshasEmpleados, Integer>("codigoServicio"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<ServicioshasEmpleados, Integer>("codigoEmpleado"));
        colFechaEvento.setCellValueFactory(new PropertyValueFactory<ServicioshasEmpleados, Date>("fechaEvento"));
        colHoraEvento.setCellValueFactory(new PropertyValueFactory<ServicioshasEmpleados, String>("horaEvento"));
        colLugarEvento.setCellValueFactory(new PropertyValueFactory<ServicioshasEmpleados, String>("lugarEvento"));
    }
    
    public void seleccionarElemento(){
        if(tblServicios_has_Empleados.getSelectionModel().getSelectedItem() !=null){
        txtServicios_codigoServicio.setText(String.valueOf(((ServicioshasEmpleados)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getServicios_codigoServicio()));
        cmbCodigoServicio.getSelectionModel().select(buscarServicio(((ServicioshasEmpleados)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleados(((ServicioshasEmpleados)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        fecha.selectedDateProperty().set(((ServicioshasEmpleados)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getFechaEvento());
        txtHoraEvento.setText(((ServicioshasEmpleados)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getHoraEvento());
        txtLugarEvento.setText(((ServicioshasEmpleados)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getLugarEvento());
        
            }
    }
    
    public ObservableList<ServicioshasEmpleados> getServicioshasEmpleados(){
              ArrayList<ServicioshasEmpleados> lista = new ArrayList<ServicioshasEmpleados>();
              try{
                  PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios_has_Empleados}");
                  ResultSet resultado = procedimiento.executeQuery();
                  while (resultado.next()) {
                      lista.add(new ServicioshasEmpleados(resultado.getInt("Servicios_codigoServicio"),
                                                          resultado.getInt("codigoServicio"),
                                                          resultado.getInt("codigoEmpleado"),
                                                          resultado.getDate("fechaEvento"),
                                                          resultado.getString("horaEvento"),
                                                          resultado.getString("lugarEvento")));
                      
                  }
              }catch(Exception e){
                  e.printStackTrace();
                  
              }
        return listaServicioshasEmpleados = FXCollections.observableArrayList(lista);

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
    public ServicioshasEmpleados buscarServicioshasEmpleados(int ServicioshasEmpleados){
        ServicioshasEmpleados resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{sp_BuscarServicios_has_Empleados(?)}");
            procedimiento.setInt(1, ServicioshasEmpleados);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new ServicioshasEmpleados(
                                                          registro.getInt("Servicios_codigoServicio"),
                                                          registro.getInt("codigoServicio"),
                                                          registro.getInt("codigoEmpleado"),
                                                          registro.getDate("fechaEvento"),
                                                          registro.getString("horaEvento"),
                                                          registro.getString("lugarEvento"));
                        
                
                
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
        ServicioshasEmpleados registro = new ServicioshasEmpleados();
        registro.setCodigoServicio(((Servicios)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
        registro.setCodigoEmpleado(((Empleados)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        registro.setFechaEvento(fecha.getSelectedDate());
        registro.setHoraEvento(txtHoraEvento.getText());
        registro.setLugarEvento(txtLugarEvento.getText());
                try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServicios_has_Empleados(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoServicio());
            procedimiento.setInt(2, registro.getCodigoEmpleado());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaEvento().getTime()));
            procedimiento.setString(4, registro.getHoraEvento());
            procedimiento.setString(5, registro.getLugarEvento());
            procedimiento.execute();
            listaServicioshasEmpleados.add(registro);
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
                if(tblServicios_has_Empleados.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro", "Eliminar ServiciohasEmpleados", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarServicios_has_Empleados(?)}");
                            procedimiento.setInt(1, ((ServicioshasEmpleados)tblServicios_has_Empleados.getSelectionModel().getSelectedItem()).getServicios_codigoServicio());
                            procedimiento.execute();
                            listaServicioshasEmpleados.remove(tblServicios_has_Empleados.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                            tblServicios_has_Empleados.getSelectionModel().clearSelection();
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
                if(tblServicios_has_Empleados.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                    cmbCodigoServicio.setDisable(true);
                    cmbCodigoEmpleado.setDisable(true);

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
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarServicios_has_Empleados(?,?,?,?)}");
             ServicioshasEmpleados registro = (ServicioshasEmpleados)tblServicios_has_Empleados.getSelectionModel().getSelectedItem();
             registro.setCodigoServicio(((Servicios)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
             registro.setCodigoEmpleado(((Empleados)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
             registro.setFechaEvento(fecha.getSelectedDate());
             registro.setHoraEvento(txtHoraEvento.getText());
             registro.setLugarEvento(txtLugarEvento.getText());
             
             procedimiento.setInt(1, registro.getServicios_codigoServicio());
             procedimiento.setDate(2, new java.sql.Date(registro.getFechaEvento().getTime()));
             procedimiento.setString(3, registro.getHoraEvento());
             procedimiento.setString(4, registro.getLugarEvento());
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
        txtServicios_codigoServicio.setEditable(false);
        cmbCodigoServicio.setDisable(true);
        cmbCodigoEmpleado.setDisable(true);
        grpFechaEvento.setDisable(true);
        txtHoraEvento.setEditable(false);
        txtLugarEvento.setEditable(false);
    }
    
    public void activarControles(){
        txtServicios_codigoServicio.setEditable(false);
        cmbCodigoServicio.setDisable(false);
        cmbCodigoEmpleado.setDisable(false);
        grpFechaEvento.setDisable(false);
        txtHoraEvento.setEditable(true);
        txtLugarEvento.setEditable(true);
    }
    
    public void limpiarControles(){
        txtServicios_codigoServicio.setText("");
        cmbCodigoServicio.getSelectionModel().select(null);
        cmbCodigoEmpleado.getSelectionModel().select(null);
        fecha.selectedDateProperty().set(null);
        txtHoraEvento.setText("");
        txtLugarEvento.setText("");
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
