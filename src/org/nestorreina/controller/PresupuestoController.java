
package org.nestorreina.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
import org.nestorreina.bean.Empresa;
import org.nestorreina.bean.Presupuesto;
import org.nestorreina.db.Conexion;
import org.nestorreina.report.GenerarReporte;
import org.nestorreina.system.Principal;


public class PresupuestoController implements Initializable {

    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Empresa> listaEmpresa;
    private ObservableList <Presupuesto> listaPresupuesto;
    private DatePicker fechaSoli;
    
    @FXML private TextField txtCodigoPresupuesto;
    @FXML private TextField txtCantidadPresupuesto;
    @FXML private GridPane grpFechaSolicitd;
    @FXML private ComboBox cmbCodigoEmpresa;
    @FXML private TableView tblPresupuesto;
    @FXML private TableColumn colCodigoPresupuesto;
    @FXML private TableColumn colFechaSolicitud;
    @FXML private TableColumn colCantidadPresupuesto;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fechaSoli = new DatePicker(Locale.ENGLISH);
        fechaSoli.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fechaSoli.getCalendarView().todayButtonTextProperty().set("Today");
        fechaSoli.getCalendarView().setShowWeeks(false);
        fechaSoli.getStylesheets().add("/org/nestorreina/resource/DatePicker.css");
        grpFechaSolicitd.add(fechaSoli, 0, 0);
        cmbCodigoEmpresa.setItems(getEmpresa());
    }
    
    
    
    
    public void cargarDatos(){
        tblPresupuesto.setItems(getPresupuesto());
        colCodigoPresupuesto.setCellValueFactory(new PropertyValueFactory <Presupuesto, Integer>("codigoPresupuesto"));
        colFechaSolicitud.setCellValueFactory(new PropertyValueFactory<Presupuesto, Date>("fechaSolicitud"));
        colCantidadPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuesto ,Double>("cantidadPresupuesto"));
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Presupuesto , Integer>("codigoEmpresa"));
        
    }
    
    public void seleccionarElemento(){
        if(tblPresupuesto.getSelectionModel().getSelectedItem() != null) {
        txtCodigoPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCodigoPresupuesto()));
        fechaSoli.selectedDateProperty().set(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getFechaSolicitud());
        txtCantidadPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCantidadPresupuesto()));
        cmbCodigoEmpresa.getSelectionModel().select(buscarEmpresa(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
    }
  }
    
    
    public ObservableList<Presupuesto> getPresupuesto(){
        
        ArrayList<Presupuesto> lista = new ArrayList<Presupuesto>();
        try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPresupuesto}");
        ResultSet resultado = procedimiento.executeQuery();
        while (resultado.next()){
            lista.add(new Presupuesto(resultado.getInt("codigoPresupuesto"),
                resultado.getDate("fechaSolicitud"),
                resultado.getDouble("cantidadPresupuesto"),
                resultado.getInt("codigoEmpresa")));            
        }
        }catch(Exception e){
            e.printStackTrace();
            
        }
                return listaPresupuesto = FXCollections.observableArrayList(lista);

    }
   
    
    public ObservableList<Empresa> getEmpresa(){
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpresas}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empresa(resultado.getInt("codigoEmpresa"),
                resultado.getString("nombreEmpresa"),
                resultado.getString("direccion"),
                resultado.getString("telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return listaEmpresa = FXCollections.observableArrayList(lista);
    }
    
    public Empresa buscarEmpresa(int codigoEmpresa){
        Empresa resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpresas(?)}");
            procedimiento.setInt(1, codigoEmpresa);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Empresa(registro.getInt("codigoEmpresa"),
                        registro.getString("nombreEmpresa"),
                        registro.getString("direccion"),
                        registro.getString("telefono"));
            
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return resultado;
        
    }
    
    public Presupuesto buscarPresupuesto(int codigoPresupuesto){
        Presupuesto resultado = null;
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPresupuesto(?)}");
           procedimiento.setInt(1, codigoPresupuesto);
           ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Presupuesto(registro.getInt("codigoPresupuesto"),
                                            registro.getDate("fechaSolicitud"),
                                            registro.getDouble("cantidadPresupuesto"),
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
        Presupuesto registro = new Presupuesto();
        registro.setFechaSolicitud(fechaSoli.getSelectedDate());
        registro.setCantidadPresupuesto(Double.parseDouble(txtCantidadPresupuesto.getText()));
        registro.setCodigoEmpresa(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPresupuesto(?,?,?)}");
            procedimiento.setDate(1, new java.sql.Date(registro.getFechaSolicitud().getTime()));
            procedimiento.setDouble(2, registro.getCantidadPresupuesto());
            procedimiento.setInt(3, registro.getCodigoEmpresa());
            procedimiento.execute();
            listaPresupuesto.add(registro);
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
                if(tblPresupuesto.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro", "Eliminar Presupuesto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPresupuesto(?)}");
                            procedimiento.setInt(1, ((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCodigoPresupuesto());
                            procedimiento.execute();
                            listaPresupuesto.remove(tblPresupuesto.getSelectionModel().getSelectedIndex());
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
                if(tblPresupuesto.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                    cmbCodigoEmpresa.setDisable(true);
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
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPresupuesto(?,?,?)}");
             Presupuesto registro = (Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem();
             registro.setFechaSolicitud(fechaSoli.getSelectedDate());
             registro.setCantidadPresupuesto(Double.parseDouble(txtCantidadPresupuesto.getText()));
             Empresa empresa = (Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem();
             registro.setCodigoEmpresa(empresa.getCodigoEmpresa());
             procedimiento.setInt(1, registro.getCodigoPresupuesto());
             procedimiento.setDate(2, new java.sql.Date(registro.getFechaSolicitud().getTime()));
             procedimiento.setDouble(3, registro.getCantidadPresupuesto());
             procedimiento.execute();
             desactivarControles();
             limpiarControles();
         }catch(Exception e){
             e.printStackTrace();
         }
    }
    
    public void generarReporte(){
            switch (tipoDeOperacion) {
                case NINGUNO:
                    imprimirReporte();
                    break;
            }
        }
        
        public void imprimirReporte(){
            Map parametros =  new HashMap();
            int codEmp = Integer.valueOf(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
            parametros.put("codEmp",codEmp);
            GenerarReporte.mostrarReporte("ReportePresupuesto.jasper", "Reporte de Presupuestos", parametros);
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
        txtCodigoPresupuesto.setEditable(false);
        txtCantidadPresupuesto.setEditable(false);
        grpFechaSolicitd.setDisable(true);
        cmbCodigoEmpresa.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoPresupuesto.setEditable(false);
        txtCantidadPresupuesto.setEditable(true);
        grpFechaSolicitd.setDisable(false);
        cmbCodigoEmpresa.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoPresupuesto.setText("");
        fechaSoli.selectedDateProperty().set(null);
        txtCantidadPresupuesto.setText("");
        cmbCodigoEmpresa.getSelectionModel().select(null);
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
        
    }
    public void ventanaEmpresas(){
        escenarioPrincipal.ventanaEmpresas();
    }
    
}
