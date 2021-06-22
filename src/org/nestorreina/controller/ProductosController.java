
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.nestorreina.bean.Producto;
import org.nestorreina.db.Conexion;
import org.nestorreina.system.Principal;


public class ProductosController implements Initializable{
    
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, ELIMINAR, GUARDAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Producto> listaProducto;
    
    @FXML private TextField txtCodigoProducto;
    @FXML private TextField txtNombreProducto;
    @FXML private TextField txtCantidad;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodigoProducto;
    @FXML private TableColumn colNombreProducto;
    @FXML private TableColumn colCantidad;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte
            ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblProductos.setItems(getProductos());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("codigoProducto"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Producto, Integer>("cantidad"));
    }
    
    public void seleccionarElemento(){
        if(tblProductos.getSelectionModel().getSelectedItem() !=null){
        txtCodigoProducto.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtNombreProducto.setText(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getNombreProducto());
        txtCantidad.setText(String.valueOf(((Producto)tblProductos.getSelectionModel().getSelectedItem()).getCantidad()));

        }
    }
    
     public ObservableList<Producto> getProductos(){
        ArrayList<Producto> lista = new ArrayList<Producto>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Producto(resultado.getInt("codigoProducto"),
                resultado.getString("nombreProducto"),
                resultado.getInt("cantidad")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
                return listaProducto = FXCollections.observableArrayList(lista);

     }
     
     public Producto buscarProducto(int codigoProducto){
         Producto resultado = null;
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos(?)}");
             procedimiento.setInt(1, codigoProducto);
             ResultSet registro = procedimiento.executeQuery();
             while (registro.next()) {
                 resultado = new Producto(registro.getInt("codigoProducto"),
                                          registro.getString("nombreProducto"),
                                          registro.getInt("cantidad"));
                 
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
        Producto registro = new Producto();
        registro.setNombreProducto(txtNombreProducto.getText());
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProductos(?,?)}");
            procedimiento.setString(1, registro.getNombreProducto());
            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.execute();
            listaProducto.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        } 
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
                if (tblProductos.getSelectionModel().getSelectedItem() !=null ){
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION){
                  try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProductos(?)}");
                        procedimiento.setInt(1,((Producto) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                        procedimiento.execute();
                        listaProducto.remove(tblProductos.getSelectionModel().getSelectedIndex());
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
            switch (tipoDeOperacion) {
                case NINGUNO:
                    if(tblProductos.getSelectionModel().getSelectedItem() != null){
                        btnEditar.setText("Actualizar");
                        btnReporte.setText("Cancelar");
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
                        activarControles();
                        tipoDeOperacion = operaciones.ACTUALIZAR;
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                    }
                    break;
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
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProductos(?,?,?)}");
                Producto registro = (Producto)tblProductos.getSelectionModel().getSelectedItem();
                registro.setNombreProducto(txtNombreProducto.getText());
                registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
                procedimiento.setInt(1, registro.getCodigoProducto());
                procedimiento.setString(2, registro.getNombreProducto());
                procedimiento.setInt(3, registro.getCantidad());
                procedimiento.execute();
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
        btnEditar.setDisable(false);
        btnReporte.setDisable(false);
        btnNuevo.setDisable(false);
        btnEliminar.setDisable(false);
        desactivarControles();
        cargarDatos();
        tipoDeOperacion = operaciones.NINGUNO;
    }
    
    
    public void desactivarControles(){
        txtCodigoProducto.setEditable(false);
        txtNombreProducto.setEditable(false);
        txtCantidad.setEditable(false);
        
    }
    
    public void activarControles(){
        txtCodigoProducto.setEditable(false);
        txtNombreProducto.setEditable(true);
        txtCantidad.setEditable(true);
        
    }
    
    public void limpiarControles(){
        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtCantidad.setText("");
        
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
