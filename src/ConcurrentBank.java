import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;

public class ConcurrentBank {
    private final Map<UUID, BankAccount> accounts = Collections.synchronizedMap(new HashMap<>());

    public void transfer(UUID to, UUID from, BigDecimal amount) {
        BankAccount fromAccount = accounts.get(from);
        BankAccount toAccount = accounts.get(to);

        // блокировка всегда в одном порядке
        BankAccount first = from.compareTo(to) < 0 ? fromAccount : toAccount;
        BankAccount second = from.compareTo(to) < 0 ? toAccount : fromAccount;

        first.getLock().lock();
        try {
            second.getLock().lock();
            try {
                if (fromAccount.getBalance().compareTo(amount) >= 0) {
                    fromAccount.withdraw(amount);
                    toAccount.deposit(amount);
                } else {
                    System.out.println("Low balance");
                }
            } finally {
                second.getLock().unlock();
            }
        } finally {
            first.getLock().unlock();
        }
    }

    public UUID createAccount(BigDecimal amount) {
        UUID walletId;
        // генерируем точно уникальный ключ
        do {
            walletId = UUID.randomUUID();
        } while (accounts.containsKey(walletId));

        accounts.put(walletId, new BankAccount(amount));
        return walletId;
    }

    public BankAccount findAccount(UUID accountId) {
        return accounts.get(accountId);
    }

    public BigDecimal getTotalBalance() {
        BigDecimal total = BigDecimal.ZERO;
        synchronized (accounts) {
            for (Map.Entry<UUID, BankAccount> entry : accounts.entrySet()) {
                BankAccount account = entry.getValue();
                total = total.add(account.getBalance());
            }
        }
        return total;
    }
}
