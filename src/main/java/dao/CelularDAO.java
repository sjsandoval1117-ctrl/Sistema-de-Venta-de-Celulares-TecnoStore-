package dao;
import modelo.Celular;
import persistencia.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.CelularFactory;

public class CelularDAO {
    private final Connection con;
    public CelularDAO() { this.con = Conexion.getConexion(); }
    public Celular listar(Celular Celular) {
        List<Celular> lista = new ArrayList<>();
        String sql = "SELECT * FROM celulares";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return Celular var c = new Celular(CelularFactory.rs.getInt("id"), CelularFactory.rs.getString("nombre"), CelularFactory.rs.getString("fecha_venta"), CelularFactory.rs.getInt("Modelo"),CelularFactory.rs.getInt("Precio"),CelularFactory.rs.getInt("Stock"),CelularFactory.rs.getInt("Vendedor(a)"); 
            }
        } catch(SQLException e) { System.out.println("Error: " + e.getMessage()); }
        return (Celular) lista;
    }
}