package Dao;
import java.sql.SQLException;
import java.util.ArrayList;
public interface DAOInterface<T> {
    public boolean insert(T t) throws SQLException;
    public boolean update(T t) throws SQLException;
    public boolean delete(T t) throws SQLException;
    public boolean delete(int ID) throws SQLException;
    public ArrayList<T> selectAll() throws SQLException;
    public T selectByID(int ID) throws SQLException;
}
