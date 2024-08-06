module org.example.mainbooklibraryjavafx {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.microsoft.sqlserver.jdbc;


    opens org.example.mainbooklibraryjavafx to javafx.fxml;
    //opens org.example.mainbooklibraryjavafx.models to  javafx.base;
    exports org.example.mainbooklibraryjavafx;
}