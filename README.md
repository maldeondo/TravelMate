# 🌍 TravelMate

**A CLI-based travel planner built with Java.** 
Developed by [@maldeondo](https://github.com/maldeondo) and [@mantaimpermeable](https://github.com/mantaimpermeable).  
<br>

> _A Java project developed at the **ETSISI** (UPM), **Madrid**._

> _More info can be found inside `docs/` directory._

---

## ✨ Overview

**TravelMate** is a Command-Line Interface (CLI) application designed to help users **plan, organize, and manage trips** efficiently.  

Although this project is currently in development, its goal is to become a robust travel assistant capable of handling multiple aspects of trip organization.

---

## ⚙️ Features (Planned)

- 🧳 Create and manage travel itineraries 
- 🕓 Schedule activities and transportation 
- 🏨 Manage accommodation details 
- 💰 Track expenses and budgets 
- 🌐 Export and view summaries in different formats 

> _Features will change as development progresses._

---

## 🧩 Tech Stack

- **Language:** Java ♨️
- **Dependency Management:** Maven 🪶
- **Interface:** Command-Line (CLI) 
- **IDEs:** IntelliJ IDEA / VSCodium

> _Apache Maven handles dependencies, info like required JDK can be found in `pom.xml`._

---

## 🚀 (Not) Getting Started

This repository is intended for internal use and academic purposes.
 
No installation or public distribution is available or planned at this stage.

---

## 🧠 Authors

- [**@maldeondo**](https://github.com/maldeondo) (Mario)
- [**@mantaimpermeable**](https://github.com/mantaimpermeable) (Robert)

---

## 🏫 Academic Context

This project is part of **coursework** at the 
**ETSISI** (Escuela Técnica Superior de Ingeniería de Sistemas Informáticos), 
**UPM** (Universidad Politécnica de **Madrid**) 🇪🇸

> _A GitLab repo is provided by the school as a **base** for the project, which can be found [here](https://gitlab.etsisi.upm.es/tallerdeprogramacion/2526/enunciado-ordinaria)._

---

## 📅 Project Status

🧩 **Status:** Early development phase  
💡 **Goal:** Functional CLI prototype

---

## 💬 Notes

This repository is **private** and maintained solely for academic and collaborative purposes.  

External contributions are not expected at this time.

---

## 🧭 Git Workflow

To maintain a clean and organized development process, **TravelMate** follows a lightweight branching strategy based on the *Git Feature Branch Workflow*.

```text
main
└── develop
    ├── feature/cli-setup
    ├── feature/trip-planner
    ├── feature/data-storage
    └── feature/...
```

- `main` -> Stable branch, always containing the latest reviewed and approved version.
- `develop` -> Integration branch where new features are merged and tested
- `feature/` -> Temporary branches for new functionalities or fixes (won't be deleted after use)


Rules:

1. The `main` branch is **only** used for production code. Devs are **NOT** intended to work on `main` branch.
2. Therefore `main` branch will **only** change through merges coming from pull requests. (`main` <- `develop`)
3. Using `--amend` or changing any existing commit is **forbidden**.
4. No branch should ever be removed, even `feature/` ones after being finished.


Example Workflow:

1. Create a new branch **always** from `develop`:
```text
$ git checkout develop (changes active branch to develop)
$ git checkout -b feature/[new-functionality] (creates a new branch)
```
2. Implement and commit your changes until the branch has reached its goal.
3. Open a Pull Request to merge into `develop`.
4. Once tested and stable, merge `develop` into `main`.

> _Note: The remote origin path needs to be set after creating a new branch._
> _Git will ask for it when trying to push._

---

## 🔐 SSH Setup

The preferred method to handle SSH with git is as follows:
1. Create a new key, give it a descriptive name and a secure passphrase:
```text
$ ssh-keygen -t ed25519
```
2. Upload the public key (.pub file content) to GitHub:
```text
GitHub -> Settings -> SSH and GPG keys -> New SSH key
```
3. Use the `ssh-agent` once to clone the repo:
```text
$ eval "$(ssh-agent)"
$ ssh-add ~/.ssh/[private-key-name]
$ git clone git@github.com:[user]/TravelMate.git
$ cd TravelMate
```
4. Modify local repo settings to automatically use the key in the future, in order to avoid using the `ssh-agent` anymore:
```text
$ git config core.sshCommand "ssh -i ~/.ssh/[private-key-name]"
```
> _Note: Step 4 only affects the cloned repo, it's **not** a global git config._

Using a `GPG` key to sign commits is also recommended, but not necessary.

---

## 🧾 License

*No public license — for academic use only.*

*The base code provided to start the project is licensed, and its license can be found in the main PDF inside the `docs/` directory.*

---

> _“Not all those who wander are lost.”_ 
> — *J.R.R. Tolkien*
