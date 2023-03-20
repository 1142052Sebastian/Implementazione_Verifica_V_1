public class Book {
    private String title;
    private String author;
    private String code;
    private int availableCopies;

    
    public Book(String title, String author, int availableCopies, String code) {
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
        this.code = code;
    }
    
    public String getTitle() {
        return title;
    }
    public String getCode() {
        return code;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public int getAvailableCopies() {
        return availableCopies;
    }
    
    public void reserveCopy() {
        availableCopies--;
    }
}
