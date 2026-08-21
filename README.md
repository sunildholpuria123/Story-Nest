# 📚 StoryNest

**StoryNest** is an AI-powered Android storytelling application designed to create personalized stories for children.

Users can create stories based on a child's **name, age, category, topic, mood, and story length**, then read or listen to the generated story with text-to-speech and background music.

---

# Application Screenshots

<p align="center"> <a href="screenshots/image1.jpeg"></a> </p>

## ✨ Features

### 🤖 AI Story Generation

Generate personalized stories using Google's Gemini API.

Story creation supports:

- Character name
- Child's age
- Story category
- Specific story topic
- Story mood
- Story length
- Story language support

The application validates the child's age between **3 and 12 years** before sending a generation request.

---

### 📖 Topic-Based Stories

Users can select a specific topic instead of generating a completely random story.

Examples include:

- 👑 Akbar Birbal
- 🧠 Tenali Raman
- 🏛️ Indian History
- 👑 Indian Kings
- 🇮🇳 Freedom Fighters
- 🙏 Indian Gods
- 🦚 Lord Krishna
- 🏹 Lord Rama
- 🚩 Lord Hanuman
- 🏹 Ramayana
- ⚔️ Mahabharata
- 🐾 Panchatantra
- 👻 Vikram Betal

The topic selector is integrated into the story creation flow.

---

### 🎭 Story Categories & Mood

Stories can be customized using categories and moods to create different storytelling experiences.

The generated story is also associated with its category, which is used by the Reader experience for category-based presentation.

---

### 🔊 Text-to-Speech

StoryNest includes an Android Text-to-Speech engine for narrated stories.

Features include:

- Play
- Pause
- Resume
- Stop
- Sentence-level playback
- Reading position restoration
- Language configuration
- TTS readiness handling

Because Android's Text-to-Speech API does not provide a true pause/resume mechanism, StoryNest implements pause and resume at the sentence level.

---

### 🎵 Background Music

The Reader supports background story music.

Music can be:

- Enabled/disabled by the user
- Started automatically according to the Reader flow
- Controlled independently from narration
- Stopped when leaving the Reader

The Reader integrates `StoryAudioPlayer` for playback.

---

### 📚 Library

The Library provides access to saved stories with:

- Story listing
- Tab-based filtering
- Story opening
- Story deletion
- Empty states
- Loading states
- Error handling

---

### ❤️ Favorites

Stories can be marked as favorites and accessed from the Favorites section.

Favorite state is persisted through the story/domain layer.

---

### 🕘 Reading Progress

StoryNest remembers the user's reading position.

When the user pauses, stops, or leaves a story, the current sentence position can be persisted and restored when the story is opened again.

The Reader restores the saved sentence position when loading a story.

---

### 🎨 Category-Based Reader Experience

The Reader uses the story category to determine the appropriate visual theme.

This allows different story categories to have different backgrounds and presentation styles.

---

### ⚙️ Settings

The application includes a Settings section for configuring application preferences.

Settings are integrated into the application rather than requiring a separate settings activity.

---

## 🏗️ Architecture

StoryNest follows a feature-oriented Android architecture with separation between UI, ViewModels, domain use cases, and infrastructure components.

High-level structure:

```text
app
│
├── core
│   ├── audio
│   ├── designsystem
│   ├── navigation
│   ├── tts
│   └── util
│
├── domain
│   ├── model
│   ├── repository
│   └── usecase
│
├── feature
│   ├── home
│   ├── create
│   ├── reader
│   ├── library
│   ├── favorites
│   ├── category
│   ├── search
│   ├── settings
│   └── splash
│
└── ...
```

The Reader ViewModel, for example, coordinates story loading, favorites, reading-position persistence, Text-to-Speech, and audio playback.

---

## 🛠️ Technology Stack

- **Kotlin**
- **Android**
- **Jetpack Compose**
- **Material 3**
- **Android ViewModel**
- **StateFlow**
- **Hilt / Dependency Injection**
- **Kotlin Coroutines**
- **Room / local persistence**
- **DataStore / application settings**
- **Retrofit**
- **Gemini API**
- **Android Text-to-Speech**
- **Jetpack Navigation**

---

## 📱 Main Screens

### Home

The Home screen provides access to:

- Story search
- Welcome section
- Create Story
- Story categories
- Recent stories

The Home screen uses a Compose `Scaffold` with a StoryNest toolbar.

### Create Story

The Create Story screen allows the user to configure:

```text
Character Name
      ↓
Age
      ↓
Story Category
      ↓
Story Topic
      ↓
Story Length
      ↓
Story Mood
      ↓
Generate Story
```

The topic selector is integrated directly after category selection.

### Reader

The Reader provides:

```text
Story
 │
 ├── Read
 ├── Listen
 ├── Pause
 ├── Resume
 ├── Favorite
 ├── Background Music
 └── Reading Progress
```

---

## 🔐 Gemini API

StoryNest uses the Gemini API for AI story generation.

For local development, configure your Gemini API credentials according to the project's configuration.

### ⚠️ Production Security

**Do not ship a production Gemini API key directly inside the Android APK.**

For a production release, the recommended architecture is:

```text
Android App
     │
     │ HTTPS
     ▼
Your Backend
     │
     ▼
Gemini API
```

This allows API keys, quotas, rate limiting, and abuse protection to remain server-side.

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/StoryNest.git
```

### 2. Open the project

Open the project using **Android Studio**.

### 3. Configure Gemini

Add your Gemini API configuration using the project's existing configuration mechanism.

> Never commit private API keys to GitHub.

### 4. Sync Gradle

Allow Android Studio to download and synchronize the project dependencies.

### 5. Run

Connect an Android device or start an Android emulator and run the application.

---

## 📋 Story Creation Flow

```text
User
 │
 ▼
Create Story
 │
 ├── Character
 ├── Age
 ├── Category
 ├── Topic
 ├── Length
 └── Mood
 │
 ▼
Validation
 │
 ▼
StoryRequest
 │
 ▼
GenerateStoryUseCase
 │
 ▼
Gemini API
 │
 ▼
Generated Story
 │
 ▼
Local Storage
 │
 ▼
Reader
```

The Create Story ViewModel validates the character name and age before starting generation.

---

## 🔊 Story Narration Flow

```text
Story Content
     │
     ▼
StoryTextToSpeech
     │
     ▼
Sentence Parser
     │
     ├── Sentence 1
     ├── Sentence 2
     ├── Sentence 3
     └── ...
     │
     ▼
Android TextToSpeech
```

Sentence callbacks are used to track the current reading position and continue narration through the story.

---

## 🎯 Project Goals

StoryNest is designed to provide children with:

- Personalized storytelling
- Educational stories
- Indian mythology and cultural stories
- Historical stories
- Moral stories
- Interactive listening
- A personalized reading experience

---

## 🔮 Future Improvements

Potential future enhancements include:

- More Indian story topics
- Category-specific voice emotions
- More narration voices
- More background music
- Offline story narration
- User accounts and cloud synchronization
- Backend-based AI quota management
- Subscription / premium stories
- Parental controls
- Story sharing
- Improved AI safety filtering
- More languages
- Personalized story recommendations

---

## 🤝 Contributing

Contributions, suggestions, and improvements are welcome.

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature/my-feature
```

3. Commit your changes

```bash
git commit -m "Add my feature"
```

4. Push the branch

```bash
git push origin feature/my-feature
```

5. Open a Pull Request

---

## 📄 License

Add your preferred open-source license here.

For example:

```text
Copyright © 2026 StoryNest

All rights reserved.
```

---

## 👨‍💻 Developer

**Sunil Dholpuria**

Android / Mobile Application Developer

Built with ❤️ using Kotlin, Jetpack Compose, and AI.
