# 💸 Finance Tracker

A simple Spring Boot-based personal finance tracking application. Manage your incomes and expenses, categorize them, and view your balance with filtering and pagination support.

---

## 🚀 Features

- ✅ Add, update, delete **transactions** and **categories**
- ✅ Track **income** and **expenses**
- ✅ Filter transactions by:
    - Type (`INCOME` / `EXPENSE`)
    - Category
    - Date range
    - Amount range
- ✅ Paginated and sortable transaction list
- ✅ Input validation (e.g. non-empty fields, unique category names)
- ✅ Database seeding on app startup
- ✅ Global error handling for clean API responses

---

## ⚙️ Tech Stack

- Java 19
- Spring Boot 3.5
- Spring Data JPA (Hibernate)
- MySQL (XAMPP or Docker)
- Maven
- JSR-303 Bean Validation

---

## 🧪 API Endpoints (Examples)

### 🔹 Transactions

- `GET /api/transactions`  
  List with pagination & filtering:
- `GET /api/transactions?page=0&size=5&sort=amount,desc&type=EXPENSE&category=Food`  
- `POST /api/transactions`  
    Create a transaction:
```json
{
  "amount": 120.50,
  "type": "EXPENSE",
  "category": "Food",
  "date": "2025-07-02T14:00:00"
}
```
- `PUT /api/transactions/{id}`
Update a transaction

- `DELETE /api/transactions/{id}`
Delete a transaction

- `GET /api/transactions/balance`
Returns current balance (income - expense)

🔹 Categories
- `GET /api/categories`

- `POST /api/categories`

- `PUT /api/categories/{id}`

- `DELETE /api/categories/{id}`

## Getting Started
### Prerequisites
- Java 19+
- Maven
- MySQL (via XAMPP or Docker)

### Setup
Clone the project:

```
git clone https://github.com/your-username/finance-tracker.git
cd finance-tracker
```
Configure DB in application.yml or application.properties

Run the app:
```
./mvnw spring-boot:run
```
Visit: http://localhost:8080

## TODO (Next Steps)
- Add user authentication (JWT)
- Multi-user support (user-specific transactions)
- Export data to CSV
- Monthly summaries / charts
- Add integration tests