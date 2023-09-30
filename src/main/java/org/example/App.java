package org.example;

import Dao.ProductDAO;
import model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import database.MySQLConnUtils;



/**
 * Hello world!
 *
 */
public class App 
{
    private static Scanner sc=new Scanner(System.in);
    private static boolean isDB, isTableProduct;
    private static ArrayList<Product> listProduct= new ArrayList<>();
    public static void main( String[] args ) throws SQLException, ClassNotFoundException {
        isDB = ProductDAO.getInstance().createDB("products");
        isTableProduct =ProductDAO.getInstance().createTable("product");
        if(isDB){
            if(isTableProduct){
                System.out.println("----------------- MENU -------------");
                System.out.println("1. Read all products");
                System.out.println("2. Read detail of a product by ID");
                System.out.println("3. Add a new product");
                System.out.println("4. Update a product");
                System.out.println("5. Delete a product by ID");
                System.out.println("6. Exit");
                run();
            }
        }

    }
    public static void getAllProduct() throws SQLException{
        listProduct.clear();
        listProduct =ProductDAO.getInstance().selectAll();
        show(listProduct);

    }
    public static void getProductByID(int ID) throws SQLException {
        Product product = (Product) ProductDAO.getInstance().selectByID(ID);
        show(product);
    }
    public static void insertProduct(Product product) throws SQLException {
        if (ProductDAO.getInstance().insert(product))
            System.out.println("Add product success.");
        else
            System.out.println("Add product failed.");
    }
    public static void deleteProduct(int ID) throws SQLException {
        listProduct = ProductDAO.getInstance().selectAll();
        if (!listProduct.isEmpty()){
            if (ProductDAO.getInstance().delete(ID))
                System.out.println("Delete product success.");
            else
                System.out.println("Delete product failed.");
//            for (Product p : listProduct) {
//                if (p.getID() == ID)
//                    show(p);
//            }
        }else
            System.out.println("List product empty.");
    }
    public static void deleteProduct(Product product) throws SQLException {
        listProduct = ProductDAO.getInstance().selectAll();
        if (!listProduct.isEmpty()){
            if (ProductDAO.getInstance().delete(product))
                System.out.println("Delete product success.");
            else
                System.out.println("Delete product failed.");
//            show(p);
        }else
            System.out.println("List Products empty");
    }
    public static void updateProduct(Product product) throws SQLException {
        if (ProductDAO.getInstance().update(product)){
            System.out.println("Update product success.");
            show(product);
        }
        else
            System.out.println("Update product failed.");
    }
    public static void exit() throws SQLException {
        System.out.println(ProductDAO.getInstance().exit());
    }
    private static void show(Product product) {
        if(product !=null){
            System.out.println("-----------------INFORMATION PRODUCT-----------------");
            System.out.println(product);
            System.out.println("-----------------------------------------------------");
        }else
            System.out.println("Error: Product don't exists.");
    }
    public static void show(ArrayList<Product> listProduct){
        if(!listProduct.isEmpty()){
            System.out.println("-----------------LIST PRODUCT-----------------");
            for (Product product : listProduct)
                System.out.println(product.toString());
            System.out.println("------------------------------------------------------");
        }else
            System.out.println("Error: List products empty.");
    }
    public static void run() throws SQLException, ClassNotFoundException{
        String name, color;
        int ID;
        float price;
        Product product;
        do{
            System.out.println();
            System.out.println("Your choice: ");
            switch (sc.nextInt()){
                case 1:
                    System.out.println("Read all products");
                    System.out.println("------------------------------------------------");
                    getAllProduct();
                    break;
                case 2:
                    System.out.println("Read detail of a product by ID");
                    System.out.println("------------------------------------------------");
                    System.out.print("Enter ID: ");
                    getProductByID(sc.nextInt());
                    break;
                case 3:
                    System.out.println("Add a new product");
                    System.out.println("------------------------------------------------");
                    sc.nextLine();
                    System.out.print("Enter name: ");
                    name = sc.nextLine();
                    System.out.print("Enter price: ");
                    price = sc.nextFloat();
                    sc.nextLine();
                    System.out.print("Enter color: ");
                    color = sc.nextLine();
                    product = new Product(name , price, color);
                    insertProduct(product);
                    break;
                case 4:
                    System.out.println("Update a product");
                    System.out.println("------------------------------------------------");
                    System.out.print("Enter ID: ");
                    ID = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter name: ");
                    name = sc.nextLine();
                    System.out.print("Enter price: ");
                    price = sc.nextFloat();
                    System.out.print("Enter color: ");
                    sc.nextLine();
                    color = sc.nextLine();
                    product = new Product(ID,name , price, color);
                    updateProduct(product);
                    break;
                case 5:
                    System.out.println("Delete a product by ID");
                    System.out.println("------------------------------------------------");
                    System.out.print("Enter ID: ");
                    ID = sc.nextInt();
                    deleteProduct(ID);
                    break;
                case 6:
                    System.out.println("Exit");
                    System.out.println("------------------------------------------------");
                    exit();
                    return;
                default:
                    System.out.println("Invalid function number. Please re-enter:.");
                    break;
            }

        }while (true);
    }
}
