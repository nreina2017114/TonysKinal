
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
import org.nestorreina.bean.Producto;
import org.nestorreina.bean.ProductoshasPlatos;
import org.nestorreina.bean.ServicioshasPlatos;
import org.nestorreina.db.Conexion;
import org.nestorreina.system.Principal;


public class ProductoshasPlatosController implements Initializable{

    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO, GUARDAR};
    private ObservableList<ProductoshasPlatos> listaProductoshasPlatos;
    private ObservableList<Plato> listaPlato;
    private ObservableList<Producto> listaProducto;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    
    @FXML private TextField txtProductos_codigoProducto;
    @FXML private ComboBox cmbCodigoPlato;
    @FXML private ComboBox cmbCodigoProducto;
    @FXML private TableView tblProductos_has_Platos;
    @FXML private TableColumn colProductos_codigoProducto;
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colCodigoProducto;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;


    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoPlato.setItems(getPlatos());
        cmbCodigoProducto.setItems(getProductos());
    }
    
    public void cargarDatos(){
        tblProductos_has_Platos.setItems(getProductoshasPlatos());
        colProductos_codigoProducto.setCellValueFactory(new PropertyValueFactory<ProductoshasPlatos, Integer>("Productos_codigoProducto"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<ProductoshasPlatos, Integer>("codigoPlato"));
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<ProductoshasPlatos, Integer>("codigoProducto"));

    }
    
    public void seleccionarElemento(){
        if(tblProductos_has_Platos.getSelectionModel().getSelectedItem() !=null){
           txtProductos_codigoProducto.setText(String.valueOf(((ProductoshasPlatos)tblProductos_has_Platos.getSelectionModel().getSelectedItem()).getProductos_codigoProducto()));
           cmbCodigoPlato.getSelectionModel().select(buscarPlatos(((ProductoshasPlatos)tblProductos_has_Platos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
           cmbCodigoProducto.getSelectionModel().select(buscarProducto(((ProductoshasPlatos)tblProductos_has_Platos.getSelectionModel().getSelectedItem()).getCodigoProducto()));

        }
    }
    
    public ObservableList<ProductoshasPlatos>getProductoshasPlatos(){
        ArrayList<ProductoshasPlatos> lista = new ArrayList<ProductoshasPlatos>();
        try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos_has_Platos}");
           ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new ProductoshasPlatos(resultado.getInt("Productos_codigoProducto"),
                                                 resultado.getInt("codigoPlato"),
                                                 resultado.getInt("codigoProducto")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProductoshasPlatos = FXCollections.observableArrayList(lista);
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
    
    
    
    public ProductoshasPlatos buscarProductoshasPlatos(int Productos_codigoProducto){
        ProductoshasPlatos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos_has_Platos(?)}");
            procedimiento.setInt(1, Productos_codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new ProductoshasPlatos(registro.getInt("Productos_codigoProducto"),
                                registro.getInt("codigoPlato"),
                                registro.getInt("codigoProducto"));
            
            }
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return resultado;
        
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
        ProductoshasPlatos registro = new ProductoshasPlatos();
        registro.setCodigoPlato(((Plato)cmbCodigoPlato.getSelectionModel().getSelectedItem()).getCodigoPlato());
        registro.setCodigoProducto(((Producto) cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());

        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProductos_has_Platos(?,?)}");
            procedimiento.setInt(1, registro.getCodigoPlato());
            procedimiento.setInt(2, registro.getCodigoProducto());
            procedimiento.execute();
            listaProductoshasPlatos.add(registro);
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
                if(tblProductos_has_Platos.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro", "Eliminar ProductoshasPlatos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProductos_has_Platos(?)}");
                            procedimiento.setInt(1, ((ProductoshasPlatos)tblProductos_has_Platos.getSelectionModel().getSelectedItem()).getProductos_codigoProducto());
                            procedimiento.execute();
                            listaProductoshasPlatos.remove(tblProductos_has_Platos.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                            tblProductos_has_Platos.getSelectionModel().clearSelection();
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
    
    
    
    public void desactivarControles(){
        txtProductos_codigoProducto.setEditable(false);
        cmbCodigoPlato.setDisable(true);
        cmbCodigoProducto.setDisable(true);
    }
    
    public void activarControles(){
        txtProductos_codigoProducto.setEditable(false);
        cmbCodigoPlato.setDisable(false);
        cmbCodigoProducto.setDisable(false);
    }
    
    public void limpiarControles(){
        txtProductos_codigoProducto.setText("");
        cmbCodigoPlato.getSelectionModel().select(null);
        cmbCodigoProducto.getSelectionModel().select(null);
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
