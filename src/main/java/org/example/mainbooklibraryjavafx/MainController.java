package org.example.mainbooklibraryjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.models.Book;
import org.example.utils.Library;

import java.io.IOException;

public class MainController {
    public TableView<Book> bookTable;
    public TableColumn<Book, String> isbnColumn;
    public TableColumn<Book, String> titleColumn;
    public TableColumn<Book, String> authorColumn;
    public TableColumn<Book, Integer> yearColumn;
    public TextField isbnField;
    public TextField titleField;
    public TextField authorField;
    public TextField yearField;

    private final Library library= new Library();
    private final ObservableList<Book> bookList = FXCollections.observableArrayList();
    public TableColumn<Book, Void> editColumn;
    public TableColumn<Book, Void> deleteColumn;

    public void initialize() {
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        //yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        addButtonsToTable();
        refreshTable();
    }


    @FXML
    protected void onExitButtonClick() {
        System.exit(0);
    }


    private void addButtonsToTable() {
        //кнопка редактирования
        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {

            @Override
            public TableCell<Book, Void> call(TableColumn<Book, Void> bookVoidTableColumn) {
                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {
                    private final Button btn = new Button("Edit");
                    {
                        btn.getStyleClass().add("button-edit");
                        btn.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            showBookForm(book);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        editColumn.setCellFactory(cellFactory);

        //кнопка удаление
        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> deleteCellFactory = new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {

            @Override
            public TableCell<Book, Void> call(TableColumn<Book, Void> bookVoidTableColumn) {
                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {
                    private final Button btn = new Button("Delete");
                    {
                        btn.getStyleClass().add("button-delete");
                        btn.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            library.removeBook(book.getIsbnBook());
                            refreshTable();
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        deleteColumn.setCellFactory(deleteCellFactory);
    }

    private void refreshTable() {
        bookList.clear();
        bookList.addAll(library.listBooks() );
        bookTable.setItems(bookList);
    }

    public void addBook(ActionEvent actionEvent) {
        showBookForm(null);
    }

    private void showBookForm(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle(book == null? "Добавление книги" : "Редактирование книги");
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(loader.load(), 400, 200);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            stage.setResizable(false);
            stage.setScene(scene);
            BookFormController controller = loader.getController();
            controller.setBook(book);
            stage.showAndWait();
            refreshTable();
        }catch (IOException e) { e.printStackTrace();}

    }
}