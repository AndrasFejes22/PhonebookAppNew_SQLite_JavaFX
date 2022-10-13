package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Phonebook App 2022");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) throws SQLException {
        /*
        update(10, "Jim Doe");

        String sql = "SELECT * FROM users;";

        try {
            //Connection connection = DriverManager.getConnection("jdbc:sqlite:myShop.db");
            //a fenti sor is jó ha nincs a Connectiont intéző osztály (de legyen)

            Statement st = DbConnection.connect().createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String phone_number = rs.getString(3);
                String email = rs.getString(4);

                //System.out.println(id+ "  | "+name + " | " + phone_number + " | " + email);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */

        launch(args);
    }


    public static void update(int a, String newName) throws SQLException {
        String st = "update users set name = ? where id = ?;";

        PreparedStatement ps = DbConnection.connect().prepareStatement(st);

        ps.setString(1, newName);
        ps.setInt(2, a);


        int i=ps.executeUpdate();
        System.out.println(i+" records updated");
    }
}
