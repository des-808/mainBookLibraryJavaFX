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

            // Update book
            String query = "UPDATE Book SET title = ?,price =?,pages =?,author_id = ?,publisher_id =?,genre_id=? WHERE isbn = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getTitle());
            statement.setDouble(2, book.getPrice());
            statement.setInt(3, book.getPages());
            statement.setInt(4, addedAuthorId);
            statement.setInt(5, addedPublisherId);
            statement.setInt(6, addedGenreId);
            statement.setString(7, book.getIsbn());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
            return false;
        }
    }

    public boolean addBook(Book book) {
        try (Connection connection = DatabaseManager.connect()) {
            String query = "";
            PreparedStatement statement;
            int addedAuthorId = 0;
            int addedPublisherId = 0;
            int addedGenreId = 0;

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

            // Add book
            statement = connection.prepareStatement("INSERT INTO Book (title,price,pages, isbn, author_id, publisher_id, genre_id) VALUES (?,?,?,?,?,?,?)");
            statement.setString(1, book.getTitle());
            statement.setDouble(2, book.getPrice());
            statement.setInt(3, book.getPages());
            statement.setString(4, book.getIsbn());
            statement.setInt(5, addedAuthorId);
            statement.setInt(6, addedPublisherId);
            statement.setInt(7, addedGenreId);
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


    public List<Book> findBookByGenre(Genre genr) {
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseManager.connect()) {
            String query = "SELECT Book.id, Book.title,Book.price, Book.pages, Book.isbn, Author.Author_id, Author.FirstName, Author.LastName, Publisher.Publisher_id, Publisher.PublisherName, Genre.Genre_id ,Genre.GenreName FROM Book " +
                    "JOIN Author ON Book.author_id = Author.Author_id " +
                    "JOIN Publisher ON Book.publisher_id = Publisher.Publisher_id " +
                    "JOIN Genre ON Book.genre_id = Genre.Genre_id " +
                    "WHERE Genre.GenreName = ?";
            PreparedStatement statement = connection.prepareStatement(query);//"SELECT * FROM Book WHERE Book.Genre_id =(SELECT Genre_id FROM Genre WHERE GenreName = ?)"
            statement.setString(1, genr.getName());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Author author = new Author(resultSet.getInt("Author_id"), resultSet.getString("FirstName"), resultSet.getString("LastName"));
                Publisher publisher = new Publisher(resultSet.getInt("Publisher_id"), resultSet.getString("PublisherName"));
                Genre genre = new Genre(resultSet.getInt("Genre_id"), resultSet.getString("GenreName"));
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("pages"),
                        resultSet.getString("isbn"),
                        author,
                        publisher,
                        genre
                );// Добавляем автора и книгу в коллекцию или сохраняем в базе данных
                books.add(book);
            }
            return books;
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
            while(resultSet.next()) {
                Author author = new Author(resultSet.getInt("Author_id"), resultSet.getString("FirstName"), resultSet.getString("LastName"));
                Publisher publisher = new Publisher(resultSet.getInt("Publisher_id"), resultSet.getString("PublisherName"));
                Genre genre = new Genre(resultSet.getInt("Genre_id"), resultSet.getString("GenreName"));
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("pages"),
                        resultSet.getString("isbn"),
                        author,
                        publisher,
                        genre
                );// Добавляем автора и книгу в коллекцию или сохраняем в базе данных
                books.add(book);
            }
            return books;
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
           String query ="SELECT Book.id, Book.title,Book.price, Book.pages, Book.isbn, Author.Author_id, Author.FirstName, Author.LastName, Publisher.Publisher_id, Publisher.PublisherName, Genre.Genre_id ,Genre.GenreName FROM Book " +
                    "JOIN Author ON Book.author_id = Author.Author_id " +
                    "JOIN Publisher ON Book.publisher_id = Publisher.Publisher_id " +
                    "JOIN Genre ON Book.genre_id = Genre.Genre_id " +
                    "WHERE Book.isbn =?";
            PreparedStatement statement = connection.prepareStatement(query);//"SELECT * FROM Book WHERE isbn = ?"
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Author author = new Author(resultSet.getInt("Author_id"), resultSet.getString("FirstName"), resultSet.getString("LastName"));
                Publisher publisher = new Publisher(resultSet.getInt("Publisher_id"), resultSet.getString("PublisherName"));
                Genre genre = new Genre(resultSet.getInt("Genre_id"), resultSet.getString("GenreName"));
                return new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("pages"),
                        resultSet.getString("isbn"),
                        author,
                        publisher,
                        genre
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
            String query ="SELECT Book.id, Book.title,Book.price, Book.pages, Book.isbn, Author.Author_id, Author.FirstName, Author.LastName, Publisher.Publisher_id, Publisher.PublisherName, Genre.Genre_id ,Genre.GenreName FROM Book " +
                    "JOIN Author ON Book.author_id = Author.Author_id " +
                    "JOIN Publisher ON Book.publisher_id = Publisher.Publisher_id " +
                    "JOIN Genre ON Book.genre_id = Genre.Genre_id " +
                    "WHERE FirstName = ? AND LastName = ?";
            PreparedStatement statement = connection.prepareStatement(query);//"SELECT * FROM Book WHERE Book.author_id =(SELECT Author_id FROM Author WHERE FirstName = ? AND LastName = ?)"
            statement.setString(1, auth.getFirstName());
            statement.setString(2, auth.getLastName());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Author author = new Author(resultSet.getInt("Author_id"), resultSet.getString("FirstName"), resultSet.getString("LastName"));
                Publisher publisher = new Publisher(resultSet.getInt("Publisher_id"), resultSet.getString("PublisherName"));
                Genre genre = new Genre(resultSet.getInt("Genre_id"), resultSet.getString("GenreName"));
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("pages"),
                        resultSet.getString("isbn"),
                        author,
                        publisher,
                        genre
                );// Добавляем автора и книгу в коллекцию или сохраняем в базе данных
                books.add(book);
            }
            return books;

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
           // PreparedStatement statement = connection.prepareStatement(
                        //"SELECT * FROM Book b INNER JOIN Author a ON b.author_id = a.id INNER JOIN Publisher p ON b.publisher_id = p.id INNER JOIN Genre g ON b.genre_id = g.id");
String query = "SELECT Book.id, Book.title,Book.price, Book.pages, Book.isbn, Author.Author_id, Author.FirstName, Author.LastName, Publisher.Publisher_id, Publisher.PublisherName, Genre.Genre_id ,Genre.GenreName FROM Book " +
        "JOIN Author ON Book.author_id = Author.Author_id " +
        "JOIN Publisher ON Book.publisher_id = Publisher.Publisher_id " +
        "JOIN Genre ON Book.genre_id = Genre.Genre_id ";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Author author = new Author(
                        resultSet.getInt("Author_id"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName")
                );
                Publisher publisher = new Publisher(
                        resultSet.getInt("Publisher_id"),
                        resultSet.getString("PublisherName")
                );
                Genre genre = new Genre(
                        resultSet.getInt("Genre_id"),
                        resultSet.getString("genreName")
                );
                Book book = new Book(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("pages"),
                        resultSet.getString("isbn"),
                        author,
                        publisher,
                        genre
                );// Добавляем автора и книгу в коллекцию или сохраняем в базе данных
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e);
        }
        return null;
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

