# Java Banking System

A production-quality banking system built step by step in Java.

## Phases
-  Phase 1 — Core OOP: Account hierarchy, BigDecimal, Polymorphism
-  Phase 2 — Custom Exceptions: InsufficientFundsException, PrematureWithdrawalException
-  Phase 3 — Collections: Bank class, Transaction history
-  Phase 4 — File I/O: CSV persistence
-  Phase 5 — Interest Calculation
-  Phase 6 — CLI menu & JUnit Testing

## Account Types
| Account | Min Balance | Interest | Overdraft |
|---------|------------|----------|-----------|
| Savings | ₹500 | 4% simple | No |
| Current | None | None | ₹10,000 |
| Fixed Deposit | ₹1,000 | 7% compound | No |

## Tech Stack
- Java 21
- Maven
- JUnit 5 

## How to Run
```cmd
mvn compile
mvn exec:java "-Dexec.mainClass=com.banking.Main"
```

## Author
Shreya Upadhyay