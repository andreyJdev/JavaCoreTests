import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private BigDecimal balance;
    private final Lock lock = new ReentrantLock();

    public BankAccount(BigDecimal balance) {
        this.balance = balance;
    }

    public void deposit(BigDecimal amount) {
        this.lock.lock();
        try {
            this.balance = this.balance.add(amount);
        } finally {
            this.lock.unlock();
        }
    }

    public void withdraw(BigDecimal amount) {
        this.lock.lock();
        try {
            this.balance = this.balance.subtract(amount);
        } finally {
            this.lock.unlock();
        }
    }

    public BigDecimal getBalance() {
        this.lock.lock();
        try {
            return balance;
        } finally {
            this.lock.unlock();
        }
    }

    public void setBalance(BigDecimal balance) {
        this.lock.lock();
        try {
            this.balance = balance;
        } finally {
            this.lock.unlock();
        }
    }

    public Lock getLock() {
        return lock;
    }
}
