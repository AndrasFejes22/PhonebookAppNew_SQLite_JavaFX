package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Label mylabel;
    public Button listButton;
    public Button flushButton;
    public TextField myTextField;
    public Button newUser;
    public Pane newUserPane;
    public Button addUser;
    public Button delete;
    public Button deleteUser;
    public TextField textName;
    public TextField textPhone;
    public TextField textEmail;
    public Pane base_pane;
    public Pane edit_user;
    public TextField edit_name;
    public TextField edit_phone;
    public Button btn_edit_user;
    public TextField edit_email;
    public Button Btn_edit_user;
    public Button close_newUserPane;
    public TextField old_name;
    public Button Btn_cancel;
    public Button Btn_close_edit;
    public Button Btn_GO;
    public Button Btn_Edit;
    public Button Btn_Cancel;

    @FXML
    private ListView<String> myListView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        System.out.println("myTextField.getText(): "+myTextField.getText());
        

        //base_pane.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        newUserPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        edit_user.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        newUserPane.setVisible(false);
        edit_user.setVisible(false);


        DbConnection connectNow = new DbConnection();

        Connection connectDB = connectNow.connect();

    }




    public void click_lflushButton(ActionEvent actionEvent) {

        //myListView.setVisible(false);
        myListView.getItems().clear();
        myTextField.clear();
        all_Query(myListView);


    }

    //SEARCH
    public void click_BtnGO(ActionEvent actionEvent) throws SQLException {
        myListView.getItems().clear();

        String sql = "select * from users where name like ? or email like ?;";
        //String sql2 = "select * from users where name like 'bud';";

        String input = myTextField.getText();
        System.out.println("input: "+input);

        PreparedStatement ps = DbConnection.connect().prepareStatement(sql);

        //ps.setString(1, input + "%");//suffix - match: ps.setString(1, "%" + notes);
        ps.setString(1, "%"+ input + "%"); //or a global match: ps.setString(1, "%" + notes + "%");
        //ps.setString(2, input + "%");
        ps.setString(2, "%"+ input+ "%"); //or a global match: ps.setString(1, "%" + notes + "%");
        System.out.println("input2: "+input);

        //uj


        ResultSet rs = ps.executeQuery();
        if (!Objects.equals(myTextField.getText(), " ")){ // "" or " "?
            while (rs.next()) {
                String id = rs.getString(1);
                String name2 = rs.getString(2);
                String phone_number = rs.getString(3);
                String email = rs.getString(4);

                System.out.println(id + "  | " + name2 + " | " + phone_number + " | " + email);
                String listOut = id + "  |  " + name2 + "  |  " + phone_number + "  |  " + email;



                myListView.getItems().add(listOut);

            }
    }
        /*
        String sql2 = "select * from users where email like ?;";

        //String input = myTextField.getText();

        PreparedStatement ps2 = DbConnection.connect().prepareStatement(sql2);

        ps2.setString(1, input+"%");


        ResultSet rs2 = ps2.executeQuery();

        while(rs2.next()) {
            String id = rs2.getString(1);
            String name2 = rs2.getString(2);
            String phone_number = rs2.getString(3);
            String email = rs2.getString(4);

            System.out.println(id+ "  | "+name2 + " | " + phone_number + " | " + email);
            String listOut2 = id+ "    "+name2 + "  |  " + phone_number + "  |  " + email;

            myListView.getItems().add(listOut2);

        }
        */
    }

    public void click_newUser(ActionEvent actionEvent) {
        newUserPane.setVisible(true);
        delete.setVisible(false);
        delete.setDisable(true);
        addUser.setVisible(true);
        addUser.setDisable(false);
        edit_user.setVisible(false);
    }

    public void click_addUser(ActionEvent actionEvent) throws SQLException {


        String sql = "INSERT INTO users (name, phone_number, email) VALUES (:name, :phone_number, :email)";
        //egyszer hozzálehet adni csak nevet, de a következőnél így a két üres emeilt nem engedi a unique constraint miatt

        PreparedStatement ps = DbConnection.connect().prepareStatement(sql);

        String name = textName.getText();
        String number = textPhone.getText();
        String email = textEmail.getText();

        ps.setString(1, name);
        ps.setString(2, number);
        ps.setString(3, email);

        ps.executeUpdate();

        textName.clear();
        textPhone.clear();
        textEmail.clear();

    }

    public void click_delete(ActionEvent actionEvent) throws SQLException {

        String sql = "delete from users where name = ?;";

        String name = textName.getText();

        PreparedStatement ps = DbConnection.connect().prepareStatement(sql);

        ps.setString(1, name);

        ps.executeUpdate();

        //panelt nem zárja be de üresre rakja a mezőket a következő inputok számára
        textName.clear();
        textPhone.clear();
        textEmail.clear();

    }

    public void click_deleteUser(ActionEvent actionEvent) {
        newUserPane.setVisible(true);
        edit_user.setVisible(false);
        addUser.setVisible(false);
        addUser.setDisable(true);
        delete.setDisable(false);
        delete.setVisible(true);
    }

    public void click_labelName(ActionEvent actionEvent) {
    }

    public void click_labelPhone(ActionEvent actionEvent) {
    }

    public void click_labelEmail(ActionEvent actionEvent) {
    }

    public void all_Query(ListView<String> LV){
        String sql = "SELECT * FROM users ORDER BY name COLLATE NOCASE ASC ;";
        try {
            //Connection connection = DriverManager.getConnection("jdbc:sqlite:myShop.db");
            //a fenti sor is jó ha nincs a Connectiont intéző osztály (de legyen)

            Statement st = DbConnection.connect().createStatement();

            ResultSet rs = st.executeQuery(sql);
            int serialNum = 0;
            while(rs.next()) {//sorszám szerkesztendő itt
                serialNum++;
                String id = rs.getString(1);
                String name = rs.getString(2);
                String phone_number = rs.getString(3);
                String email = rs.getString(4);
                String slug = rs.getString(6);

                System.out.println(id+ "  | "+name + " | " + phone_number + " | " + email);
                String listOut = serialNum+ "  |  " + name + "  |  " + phone_number + "  |  " + email + "  |  "+ slug;

                if (slug == null){
                    //slug = "";
                    listOut = serialNum+ "  |  " + name + "  |  " + phone_number + "  |  " + email + "  |  "+ "";
                    LV.getItems().add(listOut);
                }else{
                    String listOut2 = serialNum+ "  |  " + name + "  |  " + phone_number + "  |  " + email + "  |  "+ slug;
                    LV.getItems().add(listOut2);
                }



            }
        } catch ( SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //myListView.setVisible(true);
    }

    public void click_edit_user(ActionEvent actionEvent) throws SQLException {
        //edit name
        String name = old_name.getText();
        String sql = "UPDATE users set name = ? where name = ?";
        PreparedStatement ps = DbConnection.connect().prepareStatement(sql);
        ps.setString(1, edit_name.getText());
        ps.setString(2, old_name.getText());
        ps.executeUpdate();
        //edit phone_number
        String sql2 = "UPDATE users set phone_number = ? where name = ?";
        PreparedStatement ps2 = DbConnection.connect().prepareStatement(sql2);
        ps2.setString(1, edit_phone.getText());
        ps2.setString(2, edit_name.getText());
        ps2.executeUpdate();
        //edit email
        String sql3 = "UPDATE users set email = ? where name = ?";
        PreparedStatement ps3 = DbConnection.connect().prepareStatement(sql3);
        ps3.setString(1, edit_email.getText());
        ps3.setString(2, edit_name.getText());
        ps3.executeUpdate();

        old_name.clear();
        edit_email.clear();
        edit_phone.clear();
        edit_name.clear();
    }


    public void click_editUser(ActionEvent actionEvent) {

        edit_user.setVisible(true);
        newUserPane.setVisible(false);
    }

    public void click_close_newUserPane(ActionEvent actionEvent) {
        newUserPane.setVisible(false);

    }
    //edit userhez a keresés, átírandó


    public void click_BtnCancel(ActionEvent actionEvent) {
        textName.clear();
        textPhone.clear();
        textEmail.clear();
    }

    public void click_BtnCloseEdit(ActionEvent actionEvent) {
        edit_user.setVisible(false);
    }


    public void click_BtnEdit(ActionEvent actionEvent) throws SQLException {
        String name = old_name.getText();

        String sql = "select * from users where name = ?;";

        PreparedStatement ps = DbConnection.connect().prepareStatement(sql);

        ps.setString(1, name);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            //String id = rs.getString(1);
            edit_name.setText(rs.getString(2)) ;
            edit_phone.setText(rs.getString(3)) ;
            edit_email.setText(rs.getString(4)) ;


        }
    }

    public void click_Btn_EditCancel(ActionEvent actionEvent) {
        old_name.clear();
        edit_email.clear();
        edit_phone.clear();
        edit_name.clear();
    }
}

