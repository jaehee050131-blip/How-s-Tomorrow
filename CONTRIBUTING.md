# Contributing

## Project Setup

Install JDK 21 and clone the repository.

```bash
git clone https://github.com/jaehee050131-blip/OSS-Project.git
cd OSS-Project
```

## Reporting Issues

Report bugs through GitHub Issues. Include a description, reproduction steps, expected behavior, and actual behavior.

## Contribution Workflow

1. Create or select a GitHub issue.
2. Assign the issue to yourself.
3. Create a new branch from main.
4. Implement changes and run tests.
5. Open a pull request linked to the issue.
6. Request a review from another team member.
7. Merge only after approval and successful CI checks.

## Running Tests

Windows (PowerShell):

```powershell
.\gradlew.bat test
```

Linux/macOS:

```bash
./gradlew test
```

## Rules

- Do not push directly to main.
- Obtain at least one approving review.
- Never commit API keys or passwords.
- Disclose AI-generated code in pull requests.
- Follow the Code of Conduct.
