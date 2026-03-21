# Contributing to StomUI

Thanks for your interest in contributing.

## Development Setup

1. Install JDK 25.
2. Clone the repository.
3. Build the project:

```bash
./gradlew build
```

On Windows PowerShell use:

```powershell
.\gradlew.bat build
```

## Running the Local Test Server

Use the dedicated Gradle task:

```bash
./gradlew runTestServer
```

On Windows PowerShell:

```powershell
.\gradlew.bat runTestServer
```

## Branches and Commits

- Create a feature branch from `main`.
- Keep commits focused and descriptive.
- Reference issues in commit messages when relevant.

Recommended commit style:

- `feat: add ...`
- `fix: resolve ...`
- `docs: update ...`
- `refactor: improve ...`

## Pull Request Guidelines

Before opening a PR:

1. Rebase onto the latest `main`.
2. Run all checks locally.
3. Ensure formatting and style are consistent.
4. Update docs when behavior or APIs change.

PR descriptions should include:

- What changed
- Why it changed
- How it was tested
- Any follow-up work

## Code Style

- Follow Kotlin official style.
- Prefer clear, readable APIs over clever implementations.
- Keep functions focused and small.
- Add comments only when logic is non-obvious.

## Reporting Issues

Please include:

- Server and Java version
- Steps to reproduce
- Expected vs actual behavior
- Relevant logs or stack traces

## Security

Do not open public issues for security-sensitive bugs.
Contact maintainers privately when possible.

