import java.math.BigDecimal;
import java.util.UUID;

public class ConcurrentBankExample {
    public static void main(String[] args) {
        ConcurrentBank bank = new ConcurrentBank();

        // Создание счетов
        UUID account1 = bank.createAccount(BigDecimal.valueOf(1000));
        UUID account2 = bank.createAccount(BigDecimal.valueOf(500));

        // Перевод между счетами
        Thread transferThread1 = new Thread(() -> bank.transfer(account1, account2, BigDecimal.valueOf(200)));
        Thread transferThread2 = new Thread(() -> bank.transfer(account2, account1, BigDecimal.valueOf(100)));

        transferThread1.start();
        transferThread2.start();

        try {
            transferThread1.join();
            transferThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(bank.findAccount(account1).getBalance());
        System.out.println(bank.findAccount(account2).getBalance());

        // Вывод общего баланса
        System.out.println("Total balance: " + bank.getTotalBalance());
    }
}
