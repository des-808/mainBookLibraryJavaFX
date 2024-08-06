package org.example.mainbooklibraryjavafx;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.models.Author;
import org.example.utils.Library;
import org.example.models.Book;

public class BookFormController {
    public TextField isbnField;
    public TextField titleField;
    public TextField yearField;
    public TextField authorFirstField;
    public TextField authorLastField;

    private Library library = new Library();
    private Book book;

    public void setBook(Book book) {
        this.book = book;
        if(book != null) {
            isbnField.setText(book.getIsbn());
            titleField.setText(book.getTitle());
            authorFirstField.setText(book.getAuthorFirstName());
            authorLastField.setText(book.getAuthorLastName());
            //yearField.setText(String.valueOf(book.getYear()));
        }
    }

    public void saveBook() {
        if(book == null) {
            book = new Book();
        }
        book.setIsbn(isbnField.getText());
        book.setTitle(titleField.getText());
        Author author = new Author(authorFirstField.getText(),authorLastField.getText());
        book.setAuthor(author);
       // book.setYear(Integer.parseInt(yearField.getText()));

       /* if(book.getId() == null) {
            library.addBook(book);
        } else {
            library.updateBook(book);
        }*/
        //закрытие окна после добавления/изменения
        Stage stage = (Stage)isbnField.getScene().getWindow();
        stage.close();
    }
}
