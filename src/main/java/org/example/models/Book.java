package org.example.models;

import java.util.Objects;
//наделал фигни
public class Book {


    public org.example.models.Author Author;
    public org.example.models.Author Publisher;
    public org.example.models.Author Genre;
    //public BookLib.Author getAuthor;

    public void setId(int id) {
        this.id = id;
    }

    public void setGenre(Author genre) {
        Genre = genre;
    }

    public void setPublisher(Author publisher) {
        Publisher = publisher;
    }

    private int id;
    private String title;
    private double price;
    private int pages;
    private String isbn;
    private Author author;
    private Publisher publisher;
    private Genre genre;

    public Book(String title, double price, int pages, String isbn, Author author, Publisher publisher, Genre genre) {
        this.title = title;
        this.price = price;
        this.pages = pages;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
    }

    public Book(String title, double price, int pages, String isbn, String author, String publisher, String genre) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.pages = pages;
        this.author = new Author(author," ");
        this.publisher = new Publisher(publisher);
        this.genre = new Genre(genre);
    }

    public Book(int id, String title, double price, int pages, String isbn, Author author, Publisher publisher, Genre genre) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.pages = pages;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
    }

    public Book(String isbn) {
        this.isbn = isbn;
    }

    public Book(int bookId, String bookTitle) {
        this.id = bookId;
        this.title = bookTitle;
    }
    /*public Book(String title, double price, int pages,  String isbn){
        this.title = title;
        this.price = price;
        this.pages = pages;
        this.isbn = isbn;
    }*/


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }
    public Book getIsbnBook() {
        Book book = new Book(isbn);
        return book;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author.getName();
    }
    public Author getAuthorObject() { return author;}

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getAuthorId() {
        return author.getId();
    }

    public void setAuthorId(int id) {
        this.author.setId(id);
    }

    public String getPublisher() {
        return publisher.getName();
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre.getName();
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    /*@Override
    public String toString() {
        return "ISBN: " + isbn + "   Title: " + title + "   BookLib.Author: " + author.getName();
    }*/

    @Override
    public String toString() {
        //String book = "ISBN: " + isbn + "   Title: " + title + "   BookLib.Book: " + book.getName();
       //return "| Title: " + title +"| Price: "+price+ "| Pages: "+pages+"| ISBN: " + isbn+"| Author: "+author.getName()+"| Publisher: "+publisher.getName()+"| Genre: "+genre.getName();
        return id +"| Title: " + title +"| Price: "+price+ "| Pages: "+pages+"| ISBN: " + isbn+
                "|Author.Id "   + author.getId()  +"| Author: "+author.getName()+
                "|Publisher.Id "+ publisher.getId()  +"| Publisher: "+publisher.getName()+
                "|Genre.Id "    + genre.getId()  +"| Genre: "+genre.getName();
        //return "| Title: " + title +"| Price: "+price+ "| Pages: "+pages+"| ISBN: " + isbn+"| Author: "+author.getId()+"| Publisher: "+publisher.getId()+"| Genre: "+genre.getId();
    }


    public void setPublisherId(int id) {
        publisher.setId(id);
    }

    public int getPublisherId() {
        return publisher.getId();
    }

    public int getGenreId() {
        return genre.getId();
    }
    public void setGenreId(int id) { genre.setId(id); }

    public String getAuthorLastName() {
        return author.getLastName();
    }

    public void setAuthorLastName(String authorLastName) {
        author.setLastName(authorLastName) ;
    }

    public String getAuthorFirstName() {
        return author.getFirstName();
    }

    public void setAuthorFirstName(String authorFirstName) {
        author.setFirstName(authorFirstName) ;
    }


    public org.example.models.Publisher getPublisherObject() {
        return publisher;
    }

    public org.example.models.Genre getGenreObject() {
        return genre;
    }
}