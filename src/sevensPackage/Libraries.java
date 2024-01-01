package sevensPackage;
import java.io.*;

public class Libraries {
    public static void outputLibrary(ILibrary library, OutputStream out) throws IOException {
        DataOutputStream data = new DataOutputStream(out);
        if(library.getClass() == ScientificLibrary.class) {
            data.writeUTF("Научная");
        } else if(library.getClass() == ChildrenLibrary.class) {
            data.writeUTF("Детская");
        }
        data.writeInt(library.getHallsQty());
        for(int i = 0; i < library.getHallsQty(); ++i) {
            IHall hall = library.getHall(i).get();
            if(hall.getClass() == ScientificLibraryHall.class) {
                data.writeUTF("Научный");
            } else if(hall.getClass() == ChildrenLibraryHall.class) {
                data.writeUTF("Детский");
            }
            data.writeInt(hall.getBooksQty());
            for(int j = 0; j < hall.getBooksQty(); ++j) {
                IBook book = hall.getBook(j).get();
                if(book.getClass() == ScientificBook.class) {
                    data.writeUTF("Научная");
                } else if(book.getClass() == ChildrenBook.class) {
                    data.writeUTF("Детская");
                }
                data.writeUTF(book.getAuthor());
                data.writeUTF(book.getName());
                data.writeDouble(book.getCost());
                data.writeInt(book.getYear());
                if(book.getClass() == ScientificBook.class) {
                    data.writeDouble(((ScientificBook) book).getIndex());
                } else if(book.getClass() == ChildrenBook.class) {
                    data.writeInt(((ChildrenBook) book).getAge());
                }
            }
        }
        out.close();
        data.close();
    }

    public static ILibrary inputLibrary(InputStream in) throws IOException {
        DataInputStream data = new DataInputStream(in);
        String libraryType = data.readUTF();
        int hallsQty = data.readInt();
        IHall[] halls = new IHall[hallsQty];
        for (int i = 0; i < halls.length; ++i) {
            String hallType = data.readUTF();
            int booksQty = data.readInt();
            IBook[] books = new IBook[booksQty];
            for (int j = 0; j < books.length; ++j) {
                String bookType = data.readUTF();
                String author = data.readUTF();
                String name = data.readUTF();
                double cost = data.readDouble();
                int year = data.readInt();

                if (bookType.equals("Научная")) {
                    ScientificBook book = new ScientificBook(author, name, cost, year, data.readDouble());
                    books[j] = book;
                } else if( bookType.equals("Детская")) {
                    ChildrenBook book = new ChildrenBook(author, name, cost, year, data.readInt());
                    books[j] = book;
                }
            }
            IHall hall = hallType.equals("Научный")
                    ? new ScientificLibraryHall(hallType, books)
                    : new ChildrenLibraryHall(hallType, books);
            halls[i] = hall;
        }
        ILibrary library = libraryType.equals("Научная")
                ? new ScientificLibrary(halls)
                : new ChildrenLibrary(halls);
        in.close();
        data.close();
        return library;
    }

    public static void writeLibrary(ILibrary library, Writer out) throws IOException {
        if (library.getClass() == ScientificLibrary.class) out.write("Научная ");
        else if (library.getClass() == ChildrenLibrary.class) out.write("Детская ");
        out.write(library.getHallsQty() + " ");
        for (int i = 0; i < library.getHallsQty(); ++i) {
            IHall hall = library.getHall(i).get();
            if (hall.getClass() == ScientificLibraryHall.class) {
                out.write("Научный ");
            } else if (hall.getClass() == ChildrenLibraryHall.class) {
                out.write("Детский ");
            }
            out.write(hall.getBooksQty() + " ");
            for (int j = 0; j < hall.getBooksQty(); ++j) {
                IBook book = hall.getBook(j).get();
                if (book.getClass() == ScientificBook.class) {
                    out.write("Научная ");
                } else if (book.getClass() == ChildrenBook.class) {
                    out.write("Детская ");
                }
                out.write("\"" + book.getAuthor() + "\" ");
                out.write("\"" + book.getName() + "\" ");
                out.write(Double.toString(book.getCost()) + " ");
                out.write(book.getYear() + " ");
                if (book.getClass() == ScientificBook.class) {
                    out.write(Double.toString(((ScientificBook) book).getIndex()) + " ");
                } else if (book.getClass() == ChildrenBook.class) {
                    out.write(((ChildrenBook) book).getAge() + " ");
                }
            }
        }
        out.close();
    }

    public static ILibrary readLibrary(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.wordChars('\"', '\"');
        tokenizer.nextToken();
        if (!tokenizer.sval.equals("Научная") && !tokenizer.sval.equals("Детская")) {
            throw new IOException("Ошибка чтения из файла типа библиотеки книг!");
        }
        String libraryType = tokenizer.sval;
        tokenizer.nextToken();
        IHall[] halls = new IHall[(int) tokenizer.nval];
        for (int i = 0; i < halls.length; ++i) {
            tokenizer.nextToken();
            if (!tokenizer.sval.equals("Научный") && !tokenizer.sval.equals("Детский")) {
                throw new IOException("Ошибка чтения из файла типа зала библиотеки книг!");
            }
            String hallType = tokenizer.sval;
            tokenizer.nextToken();
            IBook[] books = new IBook[(int) tokenizer.nval];
            for (int j = 0; j < books.length; ++j) {
                tokenizer.nextToken();
                if (!tokenizer.sval.equals("Научная") && !tokenizer.sval.equals("Детская")) {
                    throw new IOException("Ошибка чтения из файла типа книги!");
                }
                String bookType = tokenizer.sval;
                tokenizer.nextToken();
                String author = tokenizer.sval;
                tokenizer.nextToken();
                while (author.charAt(author.length() - 1) != '\"') {
                    author += ' ' + tokenizer.sval;
                    tokenizer.nextToken();
                }
                author = author.replace("\"", "");
                String name = tokenizer.sval;
                tokenizer.nextToken();
                while (name.charAt(name.length() - 1) != '\"') {
                    name += ' ' + tokenizer.sval;
                    tokenizer.nextToken();
                }
                name = name.replace("\"", "");
                double cost = tokenizer.nval;
                tokenizer.nextToken();
                int year = (int) tokenizer.nval;
                tokenizer.nextToken();
                if (bookType.equals("Научная")) {
                    double index = tokenizer.nval;
                    ScientificBook book = new ScientificBook(author, name, cost, year, index);
                    books[j] = book;
                } else if (bookType.equals("Детская")) {
                    int age = (int) tokenizer.nval;
                    ChildrenBook book = new ChildrenBook(author, name, cost, year, age);
                    books[j] = book;
                }
            }
            IHall hall = hallType.equals("Научный")
                    ? new ScientificLibraryHall(hallType, books)
                    : new ChildrenLibraryHall(hallType, books);
            halls[i] = hall;
        }
        ILibrary library = libraryType.equals("Научная")
                ? new ScientificLibrary(halls)
                : new ChildrenLibrary(halls);
        in.close();
        return library;
    }
}

