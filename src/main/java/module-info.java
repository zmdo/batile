module com.outbina.sgengine {
    requires javafx.controls;
    requires javafx.fxml;
	requires org.yaml.snakeyaml;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.web;
	requires java.desktop;
	requires com.almasb.fxgl.gameplay;
	requires com.almasb.fxgl.all;

    opens com.outbina.sgengine to javafx.fxml;
    exports com.outbina.sgengine;
}