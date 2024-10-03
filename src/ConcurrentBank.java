import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;

public class ConcurrentBank {
    private final Map<UUID, BankAccount> accounts = Collections.synchronizedMap(new HashMap<>());

    public void transfer(BankAccount to, BankAccount from, BigDecimal amount) {
        Lock fromLock = from.getLock();
        Lock toLock = to.getLock();

        fromLock.lock();
        try {
            toLock.lock();
            try {
                if (from.getBalance().compareTo(amount) >= 0) {
                    from.withdraw(amount);
                    to.deposit(amount);
                } else {
                    System.out.println("Low balance");
                }
            } finally {
                toLock.unlock();
            }
        } finally {
            fromLock.unlock();
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
