package brainacad.org;

import lombok.*;
import java.time.LocalDate;  
import java.util.*;
import java.util.stream.Collectors;

interface LibraryItem {
    String getTitle();
    String getAuthor();
    String displayInfo();
}

@Data
@AllArgsConstructor
class Book implements LibraryItem {
    private String author;
    private String title;
    private String genre;
    private int pages;

    @Override
    public String displayInfo() {
        return String.format("Книга: '%s' (автор: %s, жанр: %s, %d стор.)", title, author, genre, pages);
    }
}

@Data
@AllArgsConstructor
class Newspaper implements LibraryItem {
    private String title;
    private LocalDate publicationDate; 
    private List<String> headlines;

    @Override
    public String getAuthor() { return null; }

    @Override
    public String displayInfo() {
        return String.format("Газета: '%s' (дата: %s, заголовки: %s)", title, publicationDate, String.join(", ", headlines));
    }
}

@Data
@AllArgsConstructor
class Almanac implements LibraryItem {
    private String title;
    private List<Book> works;

    @Override
    public String getAuthor() { return null; }

    @Override
    public String displayInfo() {
        return String.format("Альманах: '%s' (твори: %s)", title, works.stream().map(Book::getTitle).collect(Collectors.joining(", ")));
    }
}

class Catalog {
    private final List<LibraryItem> items = new ArrayList<>();
    private final Random random = new Random();

    public void testInitialization() {
        items.add(new Book("Лев Толстой", "Війна і мир", "Історичний роман", 1225));
        items.add(new Newspaper("The Times", LocalDate.of(2025, 2, 1), Arrays.asList("Головні новини", "Оновлення спорту")));
        items.add(new Almanac("Класична література", Arrays.asList(
            new Book("Федір Достоєвський", "Злочин і кара", "Психологічний", 671),
            new Book("Френсіс Скотт Фіцджеральд", "Великий Гетсбі", "Трагедія", 180)
        )));
    }

    public void addItem(LibraryItem item) {
        items.add(item);
        System.out.println("--- Додано " + getItemType(item) + " ---");
        System.out.println(item.displayInfo());
        System.out.println();
    }

    public void addRandomItem() {
        LibraryItem item = switch (random.nextInt(3)) {
            case 0 -> new Book("Джек Лондон", "Мартін Іден", "Роман", 320);
            case 1 -> new Newspaper("The Times", LocalDate.of(2023, 6, 15), Arrays.asList("Новини", "Економіка"));
            default -> new Almanac("Сучасна проза", Arrays.asList(
                new Book("Джейн Остін", "Гордість і упередження", "Роман", 400),
                new Book("Емілі Бронте", "Грозовий перевал", "Роман", 350)
            ));
        };
        items.add(item);
        System.out.println("--- Додано рандомний тип ---");
        System.out.println(item.displayInfo());
        System.out.println();
    }

    public void deleteByTitle(String title) {
        items.removeIf(item -> item.getTitle().equalsIgnoreCase(title));
        System.out.println("--- Видалення '" + title + "' ---");
        displayAll();
    }

    public void displayAll() {
        System.out.println("--- Каталог бібліотеки ---");
        if (items.isEmpty()) {
            System.out.println("Каталог порожній.");
        } else {
            items.forEach(item -> System.out.println(item.displayInfo()));
        }
        System.out.println();
    }

    public void searchByTitle(String title) {
        System.out.println("--- Пошук за назвою '" + title + "' ---");
        items.stream()
            .filter(item -> item.getTitle().equalsIgnoreCase(title))
            .forEach(item -> System.out.println(item.displayInfo()));
        System.out.println();
    }

    public void searchByAuthor(String author) {
        System.out.println("--- Пошук за автором '" + author + "' ---");
        items.stream()
            .filter(item -> item instanceof Book)
            .filter(book -> ((Book) book).getAuthor().equalsIgnoreCase(author))
            .forEach(item -> System.out.println(item.displayInfo()));
        System.out.println();
    }

    private String getItemType(LibraryItem item) {
        if (item instanceof Book) return "книгу";
        if (item instanceof Newspaper) return "газету";
        return "альманах";
    }
}

public class Main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        catalog.testInitialization();
        catalog.displayAll();

        catalog.addItem(new Book("Джек Лондон", "Мартін Іден", "Роман", 320));
        catalog.addRandomItem();
        catalog.searchByTitle("Війна і мир");
        catalog.searchByAuthor("Лев Толстой");
        catalog.deleteByTitle("The Times");
    }
}
