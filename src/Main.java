import account.Account;
import account.service.AccountService;
import category.Category;
import category.services.CategoryService;
import core.constants.Options;
import core.enums.*;
import history.History;
import history.HistoryFilter;
import history.services.HistoryService;
import spot.services.SpotService;
import transaction.Transaction;

import java.util.*;
import java.util.function.BiFunction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Options OPTIONS = new Options();
    private static final AccountService accountService = new AccountService();
    private static final CategoryService categoryService = new CategoryService();
    private static final SpotService spotService = new SpotService();
    private static final HistoryService historyService = new HistoryService();

    public static void main (String[] args){
//        categoryService.create(new Category("Compras"));
//        accountService.create(new Account("Ahorros"));
//        System.out.println((char)27+"[31m****[EXPENSAPP]****");
//        showMenuOptions();
        connectToDB();
    }

    public static void connectToDB(){
        String url = "jdbc:h2:~/test"; // URL de conexión a la base de datos H2
        String username = "user"; // Nombre de usuario de la base de datos
        String password = "1234"; // Contraseña de la base de datos

        try {
            // Establecer la conexión
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS usuarios (id INT PRIMARY KEY, nombre VARCHAR(50))");
            statement.executeUpdate("INSERT INTO usuarios VALUES(1,'Karlo')");
            System.out.println(connection);
            // Realizar operaciones en la base de datos

            // Cerrar la conexión
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showMenuOptions(){
        System.out.println((char)27 + "[34m\n___MENU___"+(char)27+"[32m");
        int option = launchOptions(OPTIONS.MENU.values()) -1;
        if(option < 0) {
            showMenuOptions();
            return;
        }
        MenuOpt value = MenuOpt.values()[option];
        switch (value){
            case ACCOUNTS -> {
                showAccountOptions();
                break;
            }
            case CATEGORIES -> {
                showCategoryOptions();
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + value);
        }
    }

    public static void showAccountOptions(){
        System.out.println((char)27 + "[34m\n___CUENTAS___"+(char)27+"[33m");
        Collection<String> options = new ArrayList<>(OPTIONS.ACCOUNT.values());
        if(accountService.getAll().isEmpty()) {
            System.out.println("No hay cuentas disponibles");
            options.removeIf(opt -> !opt.equals(OPTIONS.ACCOUNT.get(AccountOpt.CREATE)));
        }
        int option = launchOptions(options) -1;
        if(option < 0) {
            showMenuOptions();
            return;
        } else if(option > options.size()-1) {
            showMenuOptions();
            return;
        }
        AccountOpt value = AccountOpt.values()[option];
        switch (value){
            case SELECT -> {
                Account account = selectAccounts();
                showSpotOptions(account);
                break;
            }
            case CREATE -> {
                System.out.print("Ingresa el nombre de la cuenta: ");
                String accountName = scanner.nextLine();
                System.out.print("Ingresa una descripción (opcional): ");
                String accountDescription = scanner.nextLine();
                Account newAccount = new Account(accountName,accountDescription);
                accountService.create(newAccount);
                showAccountOptions();
                break;
            }
            case DELETE -> {
                Account account = selectAccounts();
                accountService.delete(account);
                showAccountOptions();
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + value);
        }
    }
    private static void showCategoryOptions(){
        System.out.println((char)27 + "[34m\n___CATEGORIAS___"+(char)27+"[33m");
        Collection<String> options = new ArrayList<>(OPTIONS.CATEGORY.values());
        if(categoryService.getAll().isEmpty()) {
            System.out.println("No hay categorias disponibles");
            options.removeIf(opt -> !opt.equals(OPTIONS.CATEGORY.get(CategoryOpt.CREATE)));
        }
        int option = launchOptions(options)-1;
        if(option < 0) {
            showMenuOptions();
            return;
        }else if(option > options.size()-1) {
            showMenuOptions();
            return;
        }
        CategoryOpt value = CategoryOpt.values()[option];
        switch (value){
            case CREATE -> {
                System.out.print("Ingresa el nombre de la categoria: ");
                String categoryName = scanner.nextLine();
                System.out.print("Ingresa una descripción (opcional): ");
                String categoryDescription = scanner.nextLine();
                Category newCategory = new Category(categoryName,categoryDescription);
                categoryService.create(newCategory);
                showCategoryOptions();
                break;
            }
            case DELETE -> {
                Category category = selectCategories();
                categoryService.delete(category);
                showCategoryOptions();
            }
            default -> throw new IllegalStateException("Unexpected value: " + value);
        }
    }
    private static void showSpotOptions(Account account){
        System.out.printf((char)27 + "[34m\n___%s___"+(char)27+"[33m\n",account.getName());
        System.out.printf("ID: %s Saldo: %s\n",account.getId(),account.getSpot().getAmount());
        int option = launchOptions(OPTIONS.SPOT.values())-1;
        if(option < 0) {
            showMenuOptions();
            return;
        }else if(option > OPTIONS.SPOT.values().size()-1) {
            showMenuOptions();
            return;
        }
        SpotOpt value = SpotOpt.values()[option];
        switch (value){
            case DEPOSIT -> {
                System.out.print("Ingresa el monto a depositar: ");
                double amount = (double) scanner.nextInt();
                spotService.deposit(account.getSpot(),amount);
                historyService.newDeposit(account.getHistory(),amount);
                break;
            }
            case DRAW -> {
                System.out.print("Ingresa el monto a retirar: ");
                double amount = (double) scanner.nextInt();
                System.out.println("Selecciona una categoría:");
                if(categoryService.getAll().isEmpty()) {
                    System.out.println("No se encontraron categorías");
                    System.out.print("¿Deseas crear una categoría? [s]Sí/[n]No");
                    String confirm = scanner.nextLine();
                    if(confirm.equalsIgnoreCase("s"))
                    showCategoryOptions();
                    else break;
                }
                else {
                Category category = selectCategories();
                spotService.draw(account.getSpot(),amount);
                historyService.newExpense(account.getHistory(),amount, category);
                }
                break;
            }
            case HISTORY -> {
                showHistoryOptions(account);
            }
            default -> throw new IllegalStateException("Unexpected value: " + value);
        }
        showSpotOptions(account);
    }
    private static void showHistoryOptions(Account account){
        History history = account.getHistory();
        System.out.println((char)27 + "[34m\n___HISTORIAL___"+(char)27+"[33m");
        int option = launchOptions(OPTIONS.HISTORY.values())-1;
        if(option < 0) {
            showSpotOptions(account);
            return;
        }else if(option > OPTIONS.HISTORY.values().size()-1) {
            showMenuOptions();
            return;
        }
        HistoryOpt value = HistoryOpt.values()[option];
        switch (value){
            case GET_ALL -> {
                List<Transaction> transactions = historyService.getAll(history);
                transactions.forEach(t -> System.out.printf(t.toString()));
                break;
            }
            case GET_BY_DAY -> {
                System.out.println((char)27 + "[34m\n___HOY___"+(char)27+"[32m");
                List<Transaction> transactions = historyService.getBy(history, HistoryFilter.BY_DAY);
                transactions.forEach(t -> System.out.println(t.toString()));
                break;
            }
            case GET_BY_WEEK -> {
                System.out.println((char)27 + "[34m\n___ESTA SEMANA___"+(char)27+"[32m");
                List<Transaction> transactions = historyService.getBy(history, HistoryFilter.BY_WEEK);
                transactions.forEach(t -> System.out.println(t.toString()));
                break;
            }
            case GET_BY_MONTH -> {
                System.out.println((char)27 + "[34m\n___ESTE MES___"+(char)27+"[32m");
                List<Transaction> transactions = historyService.getBy(history, HistoryFilter.BY_MONTH);
                transactions.forEach(t -> System.out.println(t.toString()));
                break;
            }
            case GET_TRANSACTION -> {
                System.out.print("Ingresa el id de la transacción a buscar: ");
                String id = scanner.nextLine();
                System.out.println((char)27 + "[34m\n___RESULTADOS___"+(char)27+"[32m");
                List<Transaction> transactions = historyService.get(history, id);
                transactions.forEach(t -> System.out.println(t.toString()));
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + value);
        }
    }

    private static int launchOptions(Collection<String> options){
        int currIndex = 0;
        BiFunction<Integer,String,String> newMsg = (index,msg) -> String.format("[%s] %s",index,msg);
        for(String option : options){
            String msg;
            if(currIndex == 0) {
                msg = newMsg.apply(currIndex, "Volver atrás");
                System.out.println(msg);
            }
            currIndex++;
            msg = newMsg.apply(currIndex,option);
            System.out.println(msg);
        }
        System.out.println(newMsg.apply(++currIndex,"Volver al inicio"));
        System.out.print((char)27+ "[0mIngresa una opcion: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    private static Category selectCategories(){
        List<String> list = new ArrayList<>();
        categoryService.getAll().forEach(cat -> list.add(cat.getName()));
        int option = launchOptions(list)-1;
        return categoryService.getAll().get(option);
    }

    private static Account selectAccounts(){
        List<String> list = new ArrayList<>();
        accountService.getAll().forEach(acc -> list.add(acc.getId() + " - " + acc.getName()));
        int option = launchOptions(list) -1;
        return accountService.getAll().get(option);
    }
}
