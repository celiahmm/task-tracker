<div align="center">

# 📋 Task Tracker CLI

#### A simple command-line tool to track and manage your tasks.

</div>

---

## ✨ Features

- Add, update, and delete tasks
- Mark tasks as `todo`, `in-progress`, or `done`
- List all tasks or filter by status
- Tasks stored in JSON format
- Zero external dependencies

---

## ⚙️ Technologies

- **Java 17+**
- **Maven** - Build automation
- **Native JSON** - Custom JSON parser (no external libraries)

---

## 🧩 Installation

**1. Clone the repository:**

```bash
git clone https://github.com/celiahmm/task-tracker.git
cd task-tracker
```

**2. Build the project:**

```bash
make build
```

---

## 💻 Usage

```bash
# Add a task
./task-cli add "Buy groceries"

# Update a task
./task-cli update 1 "Buy groceries and cook dinner"

# Delete a task
./task-cli delete 1

# Mark task status
./task-cli mark-in-progress 1
./task-cli mark-done 1

# List tasks
./task-cli list
./task-cli list done
./task-cli list todo
./task-cli list in-progress
```

---

🔗 _Part of the [roadmap.sh Task Tracker](https://roadmap.sh/projects/task-tracker) challenge._

