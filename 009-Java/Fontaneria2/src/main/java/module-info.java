module com.jovian.fontaneria.app {
    requires transitive javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires java.sql;

    opens com.jovian.fontaneria.app to javafx.fxml;
    exports com.jovian.fontaneria.app;

}
