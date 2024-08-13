package org.example.mainbooklibraryjavafx;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.models.Author;
import org.example.models.Genre;
import org.example.models.Publisher;
import org.example.utils.Library;
import org.example.models.Book;
import org.example.models.Series;
import org.example.models.Cover;

public class BookFormController {
    public TextField titleField;
    public TextField priceField;
    public TextField pagesField;
    public TextField isbnField;
    public TextField authorFirstField;
    public TextField authorLastField;
    public TextField publisherField;
    public TextField yearField;
    public TextField genreField;
    public TextField seriesField;
    public TextField coverField;

    private Library library = new Library();
    private Book book;

    public void setBook(Book book) {
        this.book = book;
        if(book != null) {
            titleField.setText(book.getTitle());
            priceField.setText(String.valueOf(book.getPrice()));
            pagesField.setText(String.valueOf(book.getPages()));
            isbnField.setText(book.getIsbn());
            authorFirstField.setText(book.getAuthorFirstName());
            authorLastField.setText(book.getAuthorLastName());
            publisherField.setText(book.getPublisher());
            yearField.setText(String.valueOf(book.getYear()));
            genreField.setText(book.getGenre());
            seriesField.setText(book.getSeries());
            coverField.setText(book.getCover());
        }
    }

    public void saveBook() {
        boolean isNewBook = false;
        if(book == null) {
            book = new Book();
            isNewBook = true;
        }
        book.setTitle(titleField.getText());
        book.setPrice(Double.parseDouble(priceField.getText()));
        book.setPages(Integer.parseInt(pagesField.getText()));
        book.setIsbn(isbnField.getText());
        Author author = new Author(authorFirstField.getText(),authorLastField.getText());
        book.setAuthor(author);
        Publisher publisher = new Publisher(publisherField.getText());
        book.setPublisher(publisher);
         book.setYear(Integer.parseInt(yearField.getText()));
        Genre genre = new Genre(genreField.getText());
        book.setGenre(genre);
        Series series = new Series(seriesField.getText());
        book.setSeries(series);
        Cover cover = new Cover(coverField.getText());
        book.setCover(cover);
        if(isNewBook) {
            library.addBook(book);
        } else {
            library.updateBook(book);
        }
        //закрытие окна после добавления/изменения
        Stage stage = (Stage)isbnField.getScene().getWindow();
        stage.close();
    }

    public void Cancel(ActionEvent actionEvent) {
        Stage stage = (Stage)isbnField.getScene().getWindow();
        stage.close();
    }
}
