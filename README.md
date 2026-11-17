# Weather Dragon

A weather-based message sharing web application that combines weather forecasts, personalized messages, and dragon characters.

## Purpose and Target Users

This project helps users share creative weather-based messages with friends, family, and acquaintances.

## Main Features

- Select a city in South Korea.
- Write a personalized message.
- Generate a shareable link.
- Display tomorrow's weather, date, city, and temperature.
- Display a dragon character based on the weather.
- Keep the generated link valid until the target date.

## Example Usage

1. Select Seoul as the target city.
2. Write a message such as "Have a wonderful day!"
3. Generate and share the link.
4. The recipient opens the link and sees the message, weather forecast, and corresponding dragon character.

## Tech Stack

- Java 21
- Spring Boot
- Gradle
- GitHub Actions

## Setup

```bash
git clone https://github.com/jaehee050131-blip/OSS-Project.git
cd OSS-Project
```

JDK 21 is required.

## Run

Windows (PowerShell):

```powershell
.\gradlew.bat bootRun
```

Linux/macOS:

```bash
./gradlew bootRun
```

The application runs on http://localhost:8080.

## Test

Windows:

```powershell
.\gradlew.bat test
```

Linux/macOS:

```bash
./gradlew test
```

## Current Limitations

- The project is currently in its initial development stage.
- Weather API integration, message sharing, and character rendering are not yet implemented.
- The planned MVP supports cities in South Korea only.

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for contribution and review guidelines.

## License

This project uses the MIT License because it allows others to freely use, modify, and distribute the software while retaining the original copyright and license notice.
