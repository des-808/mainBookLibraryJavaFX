package org.example.models;

import java.util.Objects;
//наделал фигни
public class Book {

    public org.example.models.Cover Cover; //картинка книги
    public org.example.models.Series Series; //серия книги
    public org.example.models.Author Author; //автор книги
    public org.example.models.Author Publisher; //издатель книги
    public org.example.models.Author Genre; //жанр книги

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String title;
    private double price;
    private int pages;
    private int year;
    private String isbn;
    private Author author;
    private Publisher publisher;
    private Genre genre;
    private Cover cover;
    private Series series;

    public Book(String title, double price, int pages,int year, String isbn, Author author, Publisher publisher, Genre genre, Series series, Cover cover) {
        this.title = title;
        this.price = price;
        this.pages = pages;
        this.year = year;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.cover = cover;
        this.series = series;
    }

    public Book(String title, double price, int pages,int year, String isbn, String author, String publisher, String genre, String series, String coverName,String coverPath) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.pages = pages;
        this.year = year;
        this.author = new Author(author," ");
        this.publisher = new Publisher(publisher);
        this.genre = new Genre(genre);
        this.series = new Series(series);
        this.cover = new Cover(coverName,coverPath);
    }

    public Book(int id, String title, double price, int pages, int year,String isbn, Author author, Publisher publisher, Genre genre, Series series, Cover cover) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.pages = pages;
        this.year = year;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.series = series;
        this.cover = cover;
    }

    public Book(String isbn) {
        this.isbn = isbn;
    }
    public Book() {}

    public Book(int bookId, String bookTitle) {
        this.id = bookId;
        this.title = bookTitle;
    }


    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public void setSeries(Series series) {
        this.series = series;
    }
    public String getSeries() { return series.getSeries_name();  }

    public String getCover() {
        return cover.getCover_name();
    }

    public void setCover(Cover cover) {
        this.cover = cover;
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

    public org.example.models.Series getSeriesObject() {
        return series;
    }
    public org.example.models.Cover getCoverObject() { return cover;}
}