# Contributing

Thanks for helping out. By taking part, you agree to follow our [Code of Conduct](CODE_OF_CONDUCT.md).

## Reporting issues

Open a GitHub issue. For a bug, describe what happened, what you expected, the steps to reproduce it, and your Java version and OS. Do not paste API keys.

## Setting up the project

1. Install a JDK (version listed in the README). Gradle is included through the `gradlew` wrapper, so you do not need to install it.
2. Clone the repository and run `./gradlew build` (on Windows: `gradlew.bat build`).
3. Set your weather API key as an environment variable (name to be added) if you want to call the real API. Tests do not need it.

## How we plan work

We use one GitHub Projects board with these columns: **Backlog → Ready → In progress → In review → Done**.

Every issue explains:

- **Goal**: what should be possible when it is done
- **Scope**: what is included and what is not
- **Responsible student**: one person who owns it
- **Acceptance criteria**: observable conditions that show it is complete

Keep issues small enough for one person to finish and for another to understand. Keep the board current: move your card when its state changes.

## Proposing and making changes

1. Agree on a bounded task, or clarify an existing issue.
2. Assign one student who is responsible for it.
3. Create a branch from `main`: `feature/<issue-number>-short-name` (or `fix/`, `docs/`) and implement the change. Verify it locally.
4. Open a pull request linked to the issue (`Closes #<issue-number>`). Explain the change and give verification evidence.
5. Another student reviews the changes and checks. Discuss concerns and resolve them before merging.
6. Merge the reviewed change, then update the issue and the board.

Nobody pushes directly to `main`. The initial repository setup may come before the first pull request. Keep pull requests small.

## Running checks

```bash
./gradlew test
```

- Tests must not call the real weather API. Use the sample JSON in `src/test/resources/`.
- Weather-to-dragon rules (sunny, cloudy, rain, snow) need boundary-value tests.
- GitHub Actions runs the same checks on every pull request, and CI must pass before merging.

## Requesting and giving review

- Ask for review by assigning a teammate other than the author. At least one must approve.
- Reviewers check: does it match the issue, is it tested, is it readable, and does the author understand it?
- Be kind and specific. Resolve each comment by changing the code or replying with a reason.

## Shared responsibility

Every student takes real responsibility for implementation and for review. Roles can rotate. No one is assigned only to slides or admin tasks.

## Using AI tools

AI tools are allowed. You are responsible for the code and must be able to explain it. Check the output before opening a PR (run tests, read the code), and fill in the "AI-use disclosure" section of the PR template: what you delegated, how you checked it, and what you accepted, changed, or rejected.

## Secrets

Never commit API keys or passwords. Use environment variables or a local file listed in `.gitignore`. If you commit one by accident, tell a teammate right away so the key can be replaced.

## Code style

Follow standard Java conventions. Write comments that explain why. If you choose a threshold (for example a temperature cutoff), explain the reason in the PR.

## License

Contributions to the source code are licensed under the project's [MIT License](LICENSE).
