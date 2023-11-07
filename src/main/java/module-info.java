module com.foodroulette2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.foodroulette2 to javafx.fxml;
    exports com.foodroulette2;
}