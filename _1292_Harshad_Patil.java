import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

 interface BookServiceInterface {
    void addBook();

    void showAllBooks();

    void showAllAvailableBooks();

    void borrowBook();

    void returnBook();
}

class Book {
    private String id;
    private String title;
    private String author;
    private String publishYear;
    private String status;

    public Book() {
    }

    public Book(String id, String title, String author, String publishYear, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishYear='" + publishYear + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

class BookServiceImpl implements BookServiceInterface {
    public static final String RED="\u001B[31m";
    public static final String RESET="\u001B[0m";
    public static final String GREEN="\u001B[32m";
    public static final String CYAN="\u001B[36m";
    Scanner sc=new Scanner(System.in);
    Validator validator=new Validator();
    List<Book> books=new ArrayList<>();

    @Override
    public void addBook() {
       String bookid= validator.validateId();
       String Author=validator.validateAuthorTitle("Author");
       String Title=validator.validateAuthorTitle("Title");
       String year=validator.validatePublishYear();
        Book book=new Book(bookid,Author,Title,year,"Available");
        books.add(book);
        System.out.println(GREEN+"Book Added Successfully !!!"+RESET);
    }

    @Override
    public void showAllBooks() {
        boolean flag=false;
        System.out.println("\n----------------------------------------------------------------------------------------------");
        System.out.format(CYAN+"%s%15s%15s%15s%15s","ID","TITLE","AUTHOR","PUBLISH YEAR","STATUS"+RESET);
        System.out.println("\n----------------------------------------------------------------------------------------------");

        for (Book book:books){
            System.out.format("%s%15s%15s%15s%15s",book.getId(),book.getTitle(),book.getAuthor(),book.getPublishYear(),book.getStatus());
            System.out.println();
            flag=true;
        }
        System.out.println("\n----------------------------------------------------------------------------------------------");
        if(flag==false)
            System.out.println(RED+"There are no Books in Library"+RESET);
    }

    public void showAllAvailableBooks(){
        boolean flag=false;
        System.out.println("\n----------------------------------------------------------------------------------------------");
        System.out.format(CYAN+"%s%15s%15s%15s%15s","ID","TITLE","AUTHOR","PUBLISH YEAR","STATUS"+RESET);
        System.out.println("\n----------------------------------------------------------------------------------------------");

        if(books.size()>0){
            for (Book book:books){
                if(book.getStatus().equals("Available")){
                    System.out.format("%s%15s%15s%15s%15s",book.getId(),book.getTitle(),book.getAuthor(),book.getPublishYear(),book.getStatus());
                    System.out.println();
                    flag=true;
                }
            }
        }
        else{
            System.out.println(RED+"No Books Available in the library"+RESET);
        }
        System.out.println("\n----------------------------------------------------------------------------------------------");
        if(flag==false)
            System.out.println(RED+"There are no books with status Available"+RESET);
    }

    public void borrowBook(){
       String bookid= validator.validateId();
       boolean flag=false;
       for(Book book:books){
           if(book.getId().equals(bookid) && book.getStatus().equals("Available")){
               flag=true;
               System.out.println(GREEN+"Book Borrowed Successfully !!!"+RESET);
               book.setStatus("Not Available");
               System.out.println("Borrowed Book Details: "+book);
           }
       }
       if(flag==false)
           System.out.println(RED+"This book is not available to borrow"+RESET);
    }

    public void returnBook(){
        boolean flag=false;
        String bookid= validator.validateId();
        for(Book book:books){
            if(book.getId().equals(bookid) && book.getStatus().equals("Not Available")){
                flag=true;
                System.out.println(GREEN+"Book Returned Successfully !!!"+RESET);
                book.setStatus("Available");
                System.out.println("Returned Book Details: "+book);
            }
        }
        if(flag==false)
            System.out.println(RED+"We can not return this book"+RESET);
    }
}

class Validator {
    public static final String RED="\u001B[31m";
    public static final String RESET="\u001B[0m";
    private static Pattern ID_PATTERN = Pattern.compile("^\\d{1,4}$");
    private static Pattern AuthorTitle_Pattern=Pattern.compile("^[a-zA-Z ]+$");
    private static Pattern PublishYear_Pattern=Pattern.compile("^\\d{4}$");
    Scanner sc = new Scanner(System.in);

    public String validateId() {
        String bookid;
        while (true) {
            System.out.println("Enter Book ID ");
            bookid = sc.nextLine();
            if (!ID_PATTERN.matcher(bookid).matches()) {
                System.out.println(RED+"SORRY ! PLEASE ENTER VALID BOOK ID "+RESET);
            } else {
                break;
            }
        }
        return bookid;
    }

    public String validateAuthorTitle(String input){
        String result;
        while (true){
            if(input.equals("Title")){
                System.out.println("Enter Title");
            }else{
                System.out.println("Enter Author");
            }
            result=sc.nextLine();
            if(!AuthorTitle_Pattern.matcher(result).matches()){
                System.out.println(RED+"Please Enter Valid "+input+RESET);
            }
            else{
                break;
            }
        }
        return result;
    }

    public String validatePublishYear(){
        String year;
        while(true){
            System.out.println("Enter Publish Year of Book ");
            year=sc.nextLine();
            if(!PublishYear_Pattern.matcher(year).matches()){
                System.out.println(RED+"Enter valid Publish Year"+RESET);
            }
            else{
                break;
            }
        }
        return year;
    }
}

public class _1292_Harshad_Patil {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        BookServiceInterface service=new BookServiceImpl();

       do{
           System.out.println("Welcome to Book Management Application");
           System.out.println("1.Add Book\n"+
                   "2.Show All Books\n"+
                   "3.Show Available Books\n"+
                   "4.Borrow Book\n"+
                   "5.Return Book\n"+
                   "6.Exit\n");

               System.out.println("Enter Your Choice !");
               int ch = sc.nextInt();

           switch (ch){
               case 1:
                   service.addBook();
                   break;
               case 2:
                   service.showAllBooks();
                   break;
               case 3:
                   service.showAllAvailableBooks();
                   break;
               case 4:
                   service.borrowBook();
                   break;
               case 5:
                   service.returnBook();
                   break;
               case 6:
                   System.out.println("Thank you for Using Application !!");
                   System.exit(0);
                   break;
               default:
                   System.out.println("Please Enter Valid Choice !");
           }
       }
       while(true);
    }
}
