---

🃏 Deck CLI

Deck CLI is a lightweight, blazing-fast command-line tool for running Deck automation scripts - whether globally or per project. Powered by GraalVM native image and built with Kotlin, it’s optimized for simplicity, speed, and cross-platform consistency.


---

✨ Features

- Fast: Native image means instant startup, even for complex tasks

- Flexible: Run global scripts or project-specific automation

- Composable: Backed by deck-api, making it modular and extensible

- Asynchronous: Built with kotlinx.coroutines for smooth parallel execution



---

🛠️ Usage

# Run a script defined in a project-local .deckfile file inside project/.deck/scripts/
deck project <script-name>

# Run an independent .deckfile
deck <script-name>

Example:

deck project ci         # Run the 'ci' task from .deck in current directory
deck setup              # Run setup.deckfile script


---

📦 Tech Stack

Deck API – modular DSL for defining tasks

Kotlinx Coroutines – async power under the hood

GraalVM Native Image – instant startup, tiny binary



---

💬 Usage

To simplify developer tool management, and CI pipelines. CLI primarily is targeted towards CI pipelines.

For a GUI, use [DevDeck](https://github.com/JustINCodingUK/DevDeck)

---