<div align="center">

# Task Tracker CLI

#### A simple command-line tool to track and manage your tasks.

</div>

---

## Features

- Add, update, and delete tasks
- Mark tasks as `todo`, `in-progress`, or `done`
- List all tasks or filter by status
- Tasks stored in JSON format
- **Zero external dependencies** - uses only native Java

---

## Technologies

- **Java 17+**
- **Maven** - Build automation
- **Native JSON** - Custom JSON parser (no external libraries)

---

## Requirements

- Java 17+ or higher
- Maven 3.6+

---

## Installation

**1. Clone the repository:**

```bash
git clone https://github.com/celiahmm/Task-Tracker2.git
cd Task-Tracker2
```

**2. Build the project:**

```bash
make build
```

---

## Global Installation (Optional)

To use `task-cli` from anywhere without `./`:

```bash
make install
```

Then simply use `task-cli` instead of `./task-cli`.

To uninstall:

```bash
make uninstall
```

---

## Usage

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

## Commands

- `make build` - Build the project
- `make install` - Install globally
- `make clean` - Clean build files
- `make help` - Show help

---

_Part of the [roadmap.sh Task Tracker](https://roadmap.sh/projects/task-tracker) challenge._
