package Dao;

import database.MySQLConnUtils;
import model.Product;

import java.sql.*;
import java.util.ArrayList;

public class ProductDAO implements DAOInterface {
    private Product product =new Product();
    private ArrayList<Product> listProduct= new ArrayList<>();
    private static boolean isExistProduct=false;
    private static Connection conn;

    static {
        try {
            conn= MySQLConnUtils.getMySQLConnection();
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }
     private volatile static ProductDAO productDAO;
    private ProductDAO(){}
    public static ProductDAO getInstance() throws SQLException{
        if (productDAO == null){
            synchronized (ProductDAO.class){
                if(productDAO== null)
                    productDAO= new ProductDAO();
            }
        }
        if(conn.isClosed()){
            synchronized (ProductDAO.class){
                if(conn.isClosed())
                    conn=MySQLConnUtils.getMySQLConnection();
            }
        }
        return productDAO;
    }
    public boolean createDB(String name) throws SQLException{
        Statement st= conn.createStatement();
        String sql = "SHOW DATABASES LIKE '" + name + "'";
        ResultSet rs= st.executeQuery(sql);
        if(!rs.next()){
            sql = "CREATE DATABASE '" + name + "'";
            st.executeUpdate(sql);
        }
        return true;
    }
    public boolean createTable(String nameTable) throws SQLException {
        Statement st = conn.createStatement();
//        String sql = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = N'" + name + "'";     // Lay tat ca cac tables trong mysql có ten la name
        String sql = "SHOW TABLES LIKE '" + nameTable + "'";
        ResultSet rs = st.executeQuery(sql);
        if (!rs.next()) {
            sql = "CREATE TABLE " + nameTable +
                    "(ID INT not NULL AUTO_INCREMENT, " +
                    "name CHAR(20), " +
                    "price FLOAT, " +
                    "color CHAR(20), " +
                    "PRIMARY KEY (ID))";
            st.executeUpdate(sql);
        }
        return true;
    }

    @Override
    public boolean insert(Object o) throws SQLException {
        if(o instanceof Product)
            product =(Product) o;
        listProduct =getInstance().selectAll();
        for(Product p: listProduct){
            if(p.getName().equals(product.getName())&& p.getPrice()== product.getPrice() && p.getColor().equals(product.getColor())){
               isExistProduct=true;
               System.out.println("This Product: "+ product.getName() + "already exists! ");
               break;
            }
        }
        if (product.getName().isEmpty() || (product.getPrice()==0.0&& product.getColor().isEmpty()))
            isExistProduct=true;
        if(!isExistProduct){
            String sql= "INSERT INTO product(name, price,color)" + "VALUES(?, ?, ?)";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1, product.getName());
            pst.setFloat(2, product.getPrice());
            pst.setString(3, product.getColor());

            int rowCount= pst.executeUpdate();
            return rowCount !=0;
        }
        return false;
    }

    @Override
    public boolean update(Object o) throws SQLException {
        if (o instanceof Product)
            product = (Product) o;

        String sql = "UPDATE product SET name=?, price=?, color=? WHERE ID=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, product.getName());
        pst.setFloat(2, product.getPrice());
        pst.setString(3, product.getColor());
        pst.setInt(4, product.getID());
        return pst.executeUpdate() != 0;
    }

    @Override
    public boolean delete(Object o) throws SQLException {
        if (o instanceof Product)
            product = (Product) o;
        String sql = "DELETE FROM product WHERE ID = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, product.getID());

        return pst.executeUpdate() != 0;
    }

    @Override
    public boolean delete(int ID) throws SQLException {
        String sql = "DELETE FROM product WHERE ID = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1,ID);

        return pst.executeUpdate() != 0;
    }

    @Override
    public ArrayList selectAll() throws SQLException {
        listProduct.clone();
        Statement st = conn.createStatement();
        String sql = "SELECT * FROM product";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            int ID = rs.getInt(1);
            String name = rs.getString(2);
            float price = rs.getFloat(3);
            String color = rs.getString(4);
            Product p = new Product(ID, name, price, color);
            listProduct.add(p);
        }
        return listProduct;
    }

    @Override
    public Object selectByID(int ID) throws SQLException {
        Statement st= conn.createStatement();
        String sql="SELECT * FROM product WHERE ID= " +ID;
        ResultSet rs=st.executeQuery(sql);
        while (rs.next()){
            int _ID=rs.getInt(1);
            String name = rs.getString(2);
            float price = rs.getFloat(3);
            String color = rs.getString(4);
            return new Product(_ID,name,price,color);
        }
        return null;
    }
    public String exit() throws SQLException {
        MySQLConnUtils.CloseMySQLConnection(conn);
        if (conn.isClosed()) {
            return "Exit success";
        }else
            return "Exit failed";
    }
}

