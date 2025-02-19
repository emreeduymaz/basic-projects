// Task 1: Book class
class Book {
    constructor(ISBN, author, title, pageNumbers, genre) {
      this.ISBN = ISBN;
      this.author = author;
      this.title = title;
      this.pageNumbers = pageNumbers;
      this.genre = genre;
      this.borrowed = false;
    }
  }
  
  // Task 2: Shelf class
  class Shelf {
    constructor() {
      this.books = [];
    }
  
    addBook(book) {
      this.books.push(book);
    }
  
    // Task 3: removeBook method
    removeBook(criteriaFunction) {
      this.books = this.books.filter(book => criteriaFunction(book));
    }
  
    // Task 4: getAllBy method
    getAllBy(criteriaFunction) {
      return this.books.filter(criteriaFunction);
    }
  }
  
  // Task 5: SortedShelf class
  class SortedShelf extends Shelf {
    constructor(sortingFunction) {
      super();
      this.sortingFunction = sortingFunction;
    }
  
    addBook(book) {
      super.addBook(book);
      this.books.sort(this.sortingFunction);
    }
  }
  
  // Task 6 and 7: Library class
  class Library {
    constructor() {
      this.shelfs = [];
      this.users = new Set();
    }
  
    addShelf(shelf) {
      this.shelfs.push(shelf);
    }
  
    getBooksBy(criteriaFunction) {
      return this.shelfs.flatMap(shelf => shelf.getAllBy(criteriaFunction));
    }
  
    getBookByAuthor(author) {
      return this.getBooksBy(book => book.author === author);
    }
  
    getBookByISBN(ISBN) {
      return this.getBooksBy(book => book.ISBN === ISBN);
    }
  
    getBookByGenre(genre) {
      return this.getBooksBy(book => book.genre === genre);
    }
  
    getBookByTitle(title) {
      return this.getBooksBy(book => book.title === title);
    }
  
    getBookByAuthorRegex(regex) {
      return this.getBooksBy(book => regex.test(book.author));
    }
  
    getBookByGenreRegex(regex) {
      return this.getBooksBy(book => regex.test(book.genre));
    }
  
    getBookByTitleRegex(regex) {
      return this.getBooksBy(book => regex.test(book.title));
    }
  
    addUser(user) {
      this.users.add(user);
    }
  
    borrowABook(user, book) {
      if (!this.users.has(user)) throw new Error('UndefinedUser');
      const foundBook = this.getBooksBy(b => b === book && !b.borrowed)[0];
      if (!foundBook) throw new Error('NoSuchBookOnShelf');
      if (foundBook.borrowed) throw new Error('AlreadyBorrowed');
  
      foundBook.borrowed = true;
      user.borrowBook(foundBook);
    }
  }
  
  // Task 8: User class
  let userIdCounter = 0;
  class User {
    constructor() {
      this.id = userIdCounter++;
      this.books = [];
    }
  
    borrowBook(book) {
      this.books.push(book);
    }
  }
  
  // Exporting the modules
  module.exports = {
    Book,
    Shelf,
    SortedShelf,
    Library,
    User
  };
  