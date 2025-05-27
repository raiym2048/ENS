# Email Notification Scheduler
HOW TO START

`docker-compose up --build -d`


---

## Getting Started: Usage Rules

Before using the application, follow these basic steps to ensure a smooth start:

### 1. Make sure required ports are free

Ensure that these ports are **not in use** by other apps on your system:

| Port  | Purpose              |
|-------|----------------------|
| 5432  | PostgreSQL database  |
| 8081  | Spring Boot backend  |
| 8025  | MailHog web UI       |
| 1025  | MailHog SMTP server  |

> You can check used ports with `lsof -i :PORT` or `netstat -an | grep PORT`.

---

### 2. Register at least 2 users

You need:
- **Sender** — the one who schedules and sends notifications
- **Recipient** — the one who receives them

Use Swagger UI to register users.
After registration and login, enter the received token into swagger - there is a column with a picture of the key where you can enter the token
![Screen Shot 2025-05-27 at 17 36 20](https://github.com/user-attachments/assets/3b56a431-1b8f-4cc0-b474-ed7fa54f3b25)

## ⚙️ API Endpoints

| Method | Endpoint                   | Description                                  |
|--------|----------------------------|----------------------------------------------|
| POST   | `/api/auth/register`       | Register a new user                          |
| POST   | `/api/auth/login`          | Authenticate user, return JWT token          |
| POST   | `/notifications`           | Schedule a notification                      |
| GET    | `/notifications`           | Get all created (outgoing) notifications     |
| GET    | `/notifications/inbox`     | View received (delivered) notifications      |
| DELETE | `/notifications/{id}`      | Delete a notification and cancel schedule    |
| POST   | `/notifications/{id}/send` | Manually trigger immediate delivery          |

 _Screenshot: Scheduled + Incoming Notifications_

![Screen Shot 2025-05-27 at 17 37 59](https://github.com/user-attachments/assets/70282144-6a25-4fb3-9ff5-f43f2df761f7)
![Screen Shot 2025-05-27 at 17 38 19](https://github.com/user-attachments/assets/17544504-6e83-4a51-b9ba-5734ef4c25b7)
1. CRON:
```bash

| Expression      | Meaning             |
| --------------- | ------------------- |
| `*/5 * * * * *` | Every 5 seconds     |
| `0 0 * * * *`   | Every hour          |
| `0 0 9 * * MON` | Every Monday at 9AM |
