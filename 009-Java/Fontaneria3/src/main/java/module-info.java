module com.jovian.fontaneria.app {
    requires transitive javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires jasperreports;
	requires java.sql;
	requires ecj;
	requires itext;

    opens com.jovian.fontaneria.app to javafx.fxml;
    exports com.jovian.fontaneria.app;

}
