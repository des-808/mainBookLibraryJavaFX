module org.example.mainbooklibraryjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.mainbooklibraryjavafx to javafx.fxml;
    exports org.example.mainbooklibraryjavafx;
}