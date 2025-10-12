module mis.mygamejavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens mis.mygamejavafx to javafx.fxml;
    exports mis.mygamejavafx;
}
