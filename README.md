# 내일 어때용? (How's Tomorrow?)

A web application that lets you send tomorrow's weather to someone special through a link, delivered by four cute dragon brothers.

## Purpose and Target Users

**Target users:** People who want to share tomorrow's weather with someone special,
such as a partner, a crush, or a friend they want to get closer to.

**Problem:** Sometimes you want to say, "It's going to rain tomorrow, don't forget your umbrella."
But reaching out first can feel like a burden, and a weather app screenshot or a short text
can seem low-effort and rarely starts a conversation.

**Our solution:** Pick a city and create a link. When the other person opens it,
one of four dragon brothers (sunny, cloudy, rainy, snowy) delivers tomorrow's weather,
so you can show you care without the pressure.

## Main Features

- Select a major city in South Korea (Seoul and the metropolitan cities).
- Generate a shareable link that contains tomorrow's weather at the time the link is created.
- Show the date, city, weather, and temperature when the recipient opens the link.
- Show one of four dragon brothers based on the weather (sunny, cloudy, rainy, snowy).
- Show an "expired link" page after the forecast date has passed.

### Planned After the MVP

- Write a personalized message to include in the link.

## Example Usage

1. On November 20, select Seoul as the target city.
2. Generate the link and share it through a messenger app.
3. The recipient opens the link and sees the weather for Seoul on November 21:
   the date, weather, temperature, and the matching dragon brother.
4. If the recipient opens the link on November 22 or later,
   an "expired link" page is shown.

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
- Weather API integration, link generation, and the dragon character page are not yet implemented.
- The MVP supports only Seoul and the metropolitan cities in South Korea.
- Personalized messages are not supported in the MVP.
- Weather data is stored in the link itself, so someone could edit the link by hand to show incorrect weather.

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for contribution and review guidelines.

## License

This project uses the MIT License because it allows others to freely use, modify, and distribute the software while retaining the original copyright and license notice.
