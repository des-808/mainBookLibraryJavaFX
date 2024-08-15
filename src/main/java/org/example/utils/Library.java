package org.example.utils;
import org.example.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//наделал фигни
public class Library {

    public Library() {
        DatabaseManager.DatabaseManagerInit(); // Initialize the database connection
    }

    public boolean updateBook(Book book) {
        try (Connection connection = DatabaseManager.connect()) {
            int addedAuthorId = 0;
            int addedPublisherId = 0;
            int addedGenreId = 0;
            int addedSeriesId = 0;
            int addedCoverId = 0;
            // Find or add author
            Author foundAuthor = findAuthor(book.getAuthorObject());
            if (foundAuthor == null) {
                addedAuthorId = addAuthor(book.getAuthorObject());
            } else {
                addedAuthorId = foundAuthor.getId();
            }

            // Find or add publisher
            Publisher foundPublisher = findPublisher(book.getPublisher());
            if (foundPublisher == null) {
                addedPublisherId = addPublisher(book.getPublisherObject());
            } else {
                addedPublisherId = foundPublisher.getId();
            }

            // Find or add genre
            Genre foundGenre = findGenre(book.getGenre());
            if (foundGenre == null) {
                addedGenreId = addGenre(book.getGenreObject());
            } else {
                addedGenreId = foundGenre.getId();
            }

            Series foundSeries = findSeries(book.getSeries());
            if (foundSeries == null) {
                addedSeriesId = addSeries(book.getSeriesObject());
            }
            else {
                addedSeriesId = foundSeries.getSeries_id();
            }

            Cover foundCover = findCover(book.getCover());
            if (foundCover == null) {
                addedCoverId = addCover(book.getCoverObject());
            }
            else {
                addedCoverId = foundCover.getCover_id();
            }

            // Update book
            String query = "UPDATE Book SET title = ?,price =?,pages =?,year =?,author_id = ?,publisher_id =?,genre_id=?,series_id =?,cover_id =?WHERE isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setDouble(2, book.getPrice());
            statement.setInt(3, book.getPages());
            statement.setInt(4, book.getYear());
            statement.setInt(5, addedAuthorId);
            statement.setInt(6, addedPublisherId);
            statement.setInt(7, addedGenreId);
            statement.setInt(8, addedSeriesId);
            statement.setInt(9, addedCoverId);
            statement.setString(10, book.getIsbn());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return false;
        }
    }

    private int addCover(Cover coverObject) {
        try (Connection connection = DatabaseManager.connect()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Cover (CoverName,CoverPath) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, coverObject.getCover_name());
            statement.setString(2, coverObject.getCover_path());
            if (statement.executeUpdate() > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return 0;
    }

    private Cover findCover(String cover) {
        try (Connection connection = DatabaseManager.connect()) {
            String query = "SELECT * FROM Cover WHERE CoverName = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cover);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Cover(resultSet.getInt("Cover_id"), resultSet.getString("CoverName"), resultSet.getString("CoverPath"));
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }

    private int addSeries(Series seriesObject) {
        try (Connection connection = DatabaseManager.connect()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Series (SeriesName) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, seriesObject.getSeries_name());
            if (statement.executeUpdate() > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return 0;
    }


    private Series findSeries(String series) {
        try (Connection connection = DatabaseManager.connect()) {
                String query = "SELECT * FROM Series WHERE SeriesName = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, series);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new Series(resultSet.getInt("Series_id"), resultSet.getString("SeriesName"));
                }
            } catch (SQLException e) {
                System.out.println("Error executing query: " + e);
            }
            return null;

    }

    public boolean addBook(Book book) {
        try (Connection connection = DatabaseManager.connect()) {
            String query = "";
            PreparedStatement statement;
            int addedAuthorId = 0;
            int addedPublisherId = 0;
            int addedGenreId = 0;
            int addedSeriesId = 0;
            int addedCoverId = 0;

            // Find or add author
            Author author = findAuthor(book.getAuthorObject());
            if (author == null) {
                author = new Author(book.getAuthorFirstName(), book.getAuthorLastName());
                addedAuthorId = addAuthor(author);
            } else {
                addedAuthorId = author.getId();
            }

            // Find or add publisher
            Publisher publisher = findPublisher(book.getPublisher());
            if (publisher == null) {
                publisher = new Publisher(book.getPublisher());
                addedPublisherId = addPublisher(publisher);
            } else {
                addedPublisherId = publisher.getId();
            }

            // Find or add genre
            Genre genre = findGenre(book.getGenre());
            if (genre == null) {
                genre = new Genre(book.getGenre());
                addedGenreId = addGenre(genre);
            } else {
                addedGenreId = genre.getId();
            }

            Series series = findSeries(book.getSeries());
            if (series == null) {
                series = new Series(book.getSeries());
                addedSeriesId = addSeries(series);
            }
            else {
                addedSeriesId = series.getSeries_id();
            }

            Cover cover = findCover(book.getCover());
            if (cover == null) {
                cover = new Cover(book.getCover());
                addedCoverId = addCover(cover);
            }
            else {
                addedCoverId = cover.getCover_id();
            }

            // Add book
            statement = connection.prepareStatement("INSERT INTO Book (title,price,pages,year, isbn, author_id, publisher_id, genre_id, series_id, cover_id) VALUES (?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, book.getTitle());
            statement.setDouble(2, book.getPrice());
            statement.setInt(3, book.getPages());
            statement.setInt(4, book.getYear());
            statement.setString(5, book.getIsbn());
            statement.setInt(6, addedAuthorId);
            statement.setInt(7, addedPublisherId);
            statement.setInt(8, addedGenreId);
            statement.setInt(9, addedSeriesId);
            statement.setInt(10, addedCoverId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return false;
        }
    }

    public int addNewGenre(Genre newToNewGenre) {
        int addedGenreId = 0;
        // Find or add genre
        Genre genre = findGenre(newToNewGenre.getName());
        if (genre == null) {
            genre = new Genre(newToNewGenre.getName());
            addedGenreId = addGenre(genre);
        } else {
            //addedGenreId = genre.getId();
        }
        return addedGenreId;
    }

    private int addGenre(Genre genre) {
        try (Connection connection = DatabaseManager.connect()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Genre (GenreName) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, genre.getName());
            if (statement.executeUpdate() > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return 0;
    }

    public int addNewPublisher(Publisher newToNewPublisher) {
        int addedPublisherId = 0;
        // Find or add publisher
        Publisher publisher = findPublisher(newToNewPublisher.getName());
        if (publisher == null) {
            publisher = new Publisher(newToNewPublisher.getName());
            addedPublisherId = addPublisher(publisher);
        } else {
            //addedPublisherId = publisher.getId();
        }
        return addedPublisherId;
    }
    private int addPublisher(Publisher publisher) {
        try (Connection connection = DatabaseManager.connect()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Publisher (PublisherName) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, publisher.getName());
            if (statement.executeUpdate() > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return 0;
    }

    public int addNewAuthor(Author authorNew) {
        int addedAuthorId = 0;
        // Find or add author
        Author author = findAuthor(authorNew);
        if (author == null) {
            author = new Author(authorNew.getFirstName(), authorNew.getLastName());
            addedAuthorId = addAuthor(author);
        } else {
            //addedAuthorId = author.getId();
        }
        return addedAuthorId;
    }




    private int addAuthor(Author author) {
        try (Connection connection = DatabaseManager.connect()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Author (FirstName, LastName) VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, author.getFirstName());
            statement.setString(2, author.getLastName());
            if (statement.executeUpdate() > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return 0;
    }


    public List<Book> findBookByGenre(Genre findGenre) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseManager.connect()) {
            String query = "SELECT Book.id, Book.title,Book.price, Book.pages,Book.year, Book.isbn, Author.Author_id, Author.FirstName, Author.LastName, Publisher.Publisher_id, Publisher.PublisherName, Genre.Genre_id ,Genre.GenreName FROM Book " +
                    "JOIN Author ON Book.author_id = Author.Author_id " +
                    "JOIN Publisher ON Book.publisher_id = Publisher.Publisher_id " +
                    "JOIN Genre ON Book.genre_id = Genre.Genre_id " +
                    "WHERE Genre.GenreName = ?";
            PreparedStatement statement = connection.prepareStatement(query);//"SELECT * FROM Book WHERE Book.Genre_id =(SELECT Genre_id FROM Genre WHERE GenreName = ?)"
            statement.setString(1, findGenre.getName());
            ResultSet resultSet = statement.executeQuery();
            return getBooks(books, resultSet);
        }catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }

    private Genre findGenre(String genr) {
        try (Connection connection = DatabaseManager.connect()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Genre WHERE GenreName = ?");
            statement.setString(1, genr);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                return new Genre(resultSet.getInt("Genre_id"), resultSet.getString("GenreName"));
            }
        }catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }
    public Genre findGenreId(int idGenre) {
        try (Connection connection = DatabaseManager.connect()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Genre WHERE Genre_id= ?");
            statement.setInt(1, idGenre);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return new Genre(resultSet.getInt("Genre_id"), resultSet.getString("GenreName"));
            }
        }catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }

    public List<Book> findBookByPublisher(Publisher publish) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseManager.connect()) {
            String query = "SELECT Book.id, Book.title,Book.price, Book.pages, Book.isbn, Author.Author_id, Author.FirstName, Author.LastName, Publisher.Publisher_id, Publisher.PublisherName, Genre.Genre_id ,Genre.GenreName FROM Book " +
                    "JOIN Author ON Book.author_id = Author.Author_id " +
                    "JOIN Publisher ON Book.publisher_id = Publisher.Publisher_id " +
                    "JOIN Genre ON Book.genre_id = Genre.Genre_id " +
                    "WHERE Publisher.PublisherName =?";
            PreparedStatement statement = connection.prepareStatement(query);//"SELECT * FROM Book WHERE Book.Publisher_id =(SELECT Publisher_id FROM Publisher WHERE PublisherName = ?)"
            statement.setString(1, publish.getName());
            ResultSet resultSet = statement.executeQuery();
            return getBooks(books, resultSet);
        }catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }

    private Publisher findPublisher(String publish) {
        try (Connection connection = DatabaseManager.connect()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Publisher WHERE PublisherName = ?");
            statement.setString(1, publish);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return new Publisher(resultSet.getInt("Publisher_id"),resultSet.getString("PublisherName"));
            }
        }catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }
    public Publisher findPublisherId(int idPublisher) {
        try (Connection connection = DatabaseManager.connect()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Publisher WHERE Publisher_id= ?");
            statement.setInt(1, idPublisher);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return new Publisher(resultSet.getInt("Publisher_id"),resultSet.getString("PublisherName"));
            }
        }catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }


    public boolean removeBook(Book book) {
        try (Connection connection = DatabaseManager.connect()) {
            String query = "DELETE FROM Book WHERE isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getIsbn());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return false;
        }
    }




    // добавляем новую книгу в базу данных со связанными таблицами
    public Book findBookByIsbn(String isbn) {
        try (Connection connection = DatabaseManager.connect()) {
           String query ="SELECT Book.id, Book.title,Book.price, Book.pages,Book.year,Book.isbn, Author.Author_id, Author.FirstName, Author.LastName, Publisher.Publisher_id, Publisher.PublisherName, Genre.Genre_id ,Genre.GenreName FROM Book " +
                    "JOIN Author ON Book.author_id = Author.Author_id " +
                    "JOIN Publisher ON Book.publisher_id = Publisher.Publisher_id " +
                    "JOIN Genre ON Book.genre_id = Genre.Genre_id " +
                    "JOIN Series ON Book.series_id = Series.Series_id " +// Добавляем серию книги в коллекцию или сохраняем в базе данных
                    "JOIN Cover ON Book.cover_id = Cover.Cover_id " +// Добавляем обложку книги в коллекцию или сохраняем в базе данных
                    "WHERE Book.isbn =?";
            PreparedStatement statement = connection.prepareStatement(query);//"SELECT * FROM Book WHERE isbn = ?"
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               return new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("pages"),
                        resultSet.getInt("year"),
                        resultSet.getString("isbn"),
                        new Author(resultSet.getInt("Author_id"), resultSet.getString("FirstName"), resultSet.getString("LastName")),
                        new Publisher(resultSet.getInt("Publisher_id"), resultSet.getString("PublisherName")),
                        new Genre(resultSet.getInt("Genre_id"), resultSet.getString("GenreName")),
                        new Series(resultSet.getInt("Series_id"), resultSet.getString("SeriesName")),
                        new Cover(resultSet.getInt("Cover_id"),resultSet.getString("CoverName"), resultSet.getString("CoverPath"))
                );
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }

    public List<Book> findBookByAuthor(Author auth) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseManager.connect()) {
            String query ="SELECT Book.id, Book.title,Book.price, Book.pages,Book.year,Book.isbn, Author.Author_id, Author.FirstName, Author.LastName, Publisher.Publisher_id, Publisher.PublisherName, Genre.Genre_id ,Genre.GenreName FROM Book " +
                    "JOIN Author ON Book.author_id = Author.Author_id " +
                    "JOIN Publisher ON Book.publisher_id = Publisher.Publisher_id " +
                    "JOIN Genre ON Book.genre_id = Genre.Genre_id " +
                    "JOIN Series ON Book.series_id = Series.Series_id " +// Добавляем серию книги в коллекцию или сохраняем в базе данных
                    "JOIN Cover ON Book.cover_id = Cover.Cover_id " +// Добавляем обложку книги в коллекцию или сохраняем в базе данных
                    "WHERE FirstName = ? AND LastName = ?";
            PreparedStatement statement = connection.prepareStatement(query);//"SELECT * FROM Book WHERE Book.author_id =(SELECT Author_id FROM Author WHERE FirstName = ? AND LastName = ?)"
            statement.setString(1, auth.getFirstName());
            statement.setString(2, auth.getLastName());
            ResultSet resultSet = statement.executeQuery();
            return getBooks(books, resultSet);

        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }

    public Author findAuthor(Author auth) {
        try (Connection connection = DatabaseManager.connect()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Author WHERE FirstName = ? AND LastName = ?");
            statement.setString(1, auth.getFirstName());
            statement.setString(2, auth.getLastName());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return new Author(resultSet.getInt("Author_id"),resultSet.getString("FirstName"),resultSet.getString("LastName"));
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }
    public Author findAuthorId(int idAuthor) {
        try (Connection connection = DatabaseManager.connect()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Author WHERE Author_id=?");
            statement.setInt(1, idAuthor);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return new Author(resultSet.getInt("Author_id"),resultSet.getString("FirstName"),resultSet.getString("LastName"));
            }
        }catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }


    public List<Book> listBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseManager.connect()) {
            Statement statement = connection.createStatement();
        String query = "SELECT Book.id, Book.title,Book.price, Book.pages,Book.year,Book.isbn, Author.Author_id, Author.FirstName, Author.LastName, Publisher.Publisher_id, Publisher.PublisherName, Genre.Genre_id ,Genre.GenreName,Series.Series_id ,Series.SeriesName,Cover.Cover_id, Cover.CoverName, Cover.Cover_id,Cover.CoverName ,Cover.CoverPath FROM Book " +
        "JOIN Author ON Book.author_id = Author.Author_id " +
        "JOIN Publisher ON Book.publisher_id = Publisher.Publisher_id " +
        "JOIN Genre ON Book.genre_id = Genre.Genre_id "+
        "JOIN Series ON Book.series_id = Series.Series_id " +// Добавляем серию книги в коллекцию или сохраняем в базе данных
        "JOIN Cover ON Book.cover_id = Cover.Cover_id ";// Добавляем обложку книги в коллекцию или сохраняем в базе данных

            ResultSet resultSet = statement.executeQuery(query);
            return getBooks(books, resultSet);
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }

    public List<Book> findBooks(String text) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseManager.connect()) {
            String query = "SELECT Book.id, Book.title,Book.price, Book.pages,Book.year,Book.isbn, Author.Author_id, Author.FirstName, Author.LastName, Publisher.Publisher_id, Publisher.PublisherName, Genre.Genre_id ,Genre.GenreName,Series.Series_id ,Series.SeriesName,Cover.Cover_id, Cover.CoverName, Cover.Cover_id,Cover.CoverName ,Cover.CoverPath FROM Book " +
                    "JOIN Author ON Book.author_id = Author.Author_id " +
                    "JOIN Publisher ON Book.publisher_id = Publisher.Publisher_id " +
                    "JOIN Genre ON Book.genre_id = Genre.Genre_id "+
                    "JOIN Series ON Book.series_id = Series.Series_id " +// Добавляем серию книги в коллекцию или сохраняем в базе данных
                    "JOIN Cover ON Book.cover_id = Cover.Cover_id " + // Добавляем обложку книги в коллекцию или сохраняем в базе данных
                    "WHERE Book.title LIKE? OR Book.price LIKE? OR Book.pages LIKE? OR Book.year LIKE? OR Book.isbn LIKE? OR Author.FirstName LIKE? OR Author.LastName LIKE? OR Publisher.PublisherName LIKE? OR Genre.GenreName LIKE? OR Series.SeriesName LIKE? OR Cover.CoverName LIKE?";
            PreparedStatement statement = connection.prepareStatement(query);//"SELECT * FROM Book WHERE Book.title LIKE ?"
            statement.setString(1, "%" + text + "%");
            statement.setString(2, "%" + text + "%");
            statement.setString(3, "%" + text + "%");
            statement.setString(4, "%" + text + "%");
            statement.setString(5, "%" + text + "%");
            statement.setString(6, "%" + text + "%");
            statement.setString(7, "%" + text + "%");
            statement.setString(8, "%" + text + "%");
            statement.setString(9, "%" + text + "%");
            statement.setString(10, "%" + text + "%");
            statement.setString(11, "%" + text + "%");
            ResultSet resultSet = statement.executeQuery();
            /*if(resultSet.next()) {
                return new Book(resultSet.getInt("id"),resultSet.getString("title"),resultSet.getDouble("price"),resultSet.getInt("pages"),resultSet.getInt("year"),resultSet.getString("isbn"), new Author(resultSet.getInt("Author_id"), resultSet.getString("FirstName"), resultSet.getString("LastName")), new Publisher(resultSet.getInt("Publisher_id"), resultSet.getString("PublisherName")), new Genre(resultSet.getInt("Genre_id"), resultSet.getString("GenreName")), new Series(resultSet.getInt("Series_id"), resultSet.getString("SeriesName")), new Cover(resultSet.getInt("Cover_id"),resultSet.getString("CoverName"), resultSet.getString("CoverPath")));
            }*/
            return getBooks(books, resultSet);

        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
    }

    private List<Book> getBooks(List<Book> books, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Book book = new Book(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("pages"),
                    resultSet.getInt("year"),
                    resultSet.getString("isbn"),
                    new Author(resultSet.getInt("Author_id"), resultSet.getString("FirstName"), resultSet.getString("LastName")),
                    new Publisher(resultSet.getInt("Publisher_id"), resultSet.getString("PublisherName")),
                    new Genre(resultSet.getInt("Genre_id"), resultSet.getString("GenreName")),
                    new Series(resultSet.getInt("Series_id"), resultSet.getString("SeriesName")),
                    new Cover(resultSet.getInt("Cover_id"),resultSet.getString("CoverName"), resultSet.getString("CoverPath"))
            );// Добавляем автора и книгу в коллекцию или сохраняем в базе данных
            books.add(book);
        }
        return books;
    }



}



/*
use LibraryDB2
SELECT [id],[FirstName],[LastName] FROM Author
SELECT [id],[PublisherName] FROM Publisher
SELECT [id],[GenreName] FROM Genre
*/
/*DELETE FROM Publisher WHERE id = 4;
INSERT INTO Publisher(PublisherName)
VALUES ('рабочая работа');
SELECT SCOPE_IDENTITY();
SELECT [id],[PublisherName] FROM Publisher
Insert into Book(title,price,pages,isbn,author_id,publisher_id,genre_id) values('Книга 2',199,184,75435435,1,2,1)
*/

