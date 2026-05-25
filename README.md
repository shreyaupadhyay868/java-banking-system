# java-banking-system

A banking system I built from scratch in Java — not a tutorial clone, but designed 
from first principles to practice writing production-quality OOP code. Every design 
decision has a reason: why BigDecimal instead of double, why abstract classes instead 
of interfaces, why custom exceptions instead of generic ones.

## The idea
I wanted a project that forces every core Java concept at once. Banking fits perfectly:
- Accounts need inheritance (savings vs current vs FD have different rules)
- Balance must be encapsulated (no one should modify it directly)
- Withdrawals need custom exceptions (not just "error" — *which* error, and why)
- Transaction history needs collections
- Data needs to survive app restarts — file I/O
- Money must be exact — BigDecimal, not double (0.1 + 0.2 = 0.30000000000000004 in double)

## Account types
| Type | Min Balance | Interest | Overdraft | Special rule |
|------|------------|----------|-----------|--------------|
| Savings | ₹500 | 4% p.a. simple | No | Cannot withdraw below ₹500 |
| Current | None | None | ₹10,000 | Can go negative up to limit |
| Fixed Deposit | ₹1,000 | 7% compound monthly | No | Locked until maturity date |

## Project structure

src/main/java/com/banking/
├── model/
│   ├── Account.java                  — abstract base, private balance, BigDecimal
│   ├── SavingsAccount.java           — min balance enforcement
│   ├── CurrentAccount.java           — overdraft logic
│   ├── FixedDepositAccount.java      — compound interest, maturity lock
│   └── Transaction.java              — immutable transaction record
├── exception/
│   ├── BankingException.java         — base custom exception
│   ├── InsufficientFundsException.java
│   ├── InvalidAmountException.java
│   ├── AccountNotFoundException.java
│   └── PrematureWithdrawalException.java
├── filehandler/
│   └── FileHandler.java              — CSV read/write with BufferedReader/Writer
├── Bank.java                         — manages accounts via HashMap
├── InterestCalculator.java           — monthly interest, projections, simple vs compound
└── Main.java                         — demo runner, becomes CLI in Phase 6


## Build phases
- Phase 1 — Abstract Account class, SavingsAccount, CurrentAccount, FixedDepositAccount, BigDecimal
-  Phase 2 — Custom exception hierarchy, defensive coding, try/catch
- Phase 3 — Bank class with HashMap, Transaction history with ArrayList
-  Phase 4 — File I/O, CSV persistence with BufferedReader/Writer, try-with-resources
-  Phase 5 — Monthly interest, simple vs compound comparison, balance projections
-  Phase 6 — Interactive CLI menu, JUnit 5 tests

## Key design decisions
**Why abstract Account and not an interface?**  
Account has shared state (balance) and shared behaviour (deposit, withdraw). 
Interfaces can't hold state. Abstract class was the right choice.

**Why BigDecimal?**  
Double arithmetic is imprecise. ₹0.01 errors compound over millions of transactions. 
BigDecimal gives exact decimal arithmetic with controlled rounding.

**Why custom exceptions instead of IllegalStateException?**  
`InsufficientFundsException` carries the actual balance and requested amount. 
The caller can display a meaningful message. `IllegalStateException` carries nothing.

**Why HashMap for accounts?**  
O(1) lookup by account number. A List would be O(n). Banks have millions of accounts.

## How to run
```cmd
mvn compile
mvn exec:java "-Dexec.mainClass=com.banking.Main"
```

## Stack
Java 21 · Maven · JUnit 5

## Author
Shreya Upadhyay