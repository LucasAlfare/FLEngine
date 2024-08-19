# FLEngine

This is my own attempt on working on an engine project. In this project we attempt to introduce various implementations
related to the most various programming concepts and, in this project, related to graphics rendering.

The main propose for that is to study then deeply and implement then most possible from scratch.

Also, is important to see that this project is designed only to study, but it can work as a helper to render some, at
least, simple game/applications projects.

# Download

ou can grab this project directly from its [GitHub page](https://github.com/LucasAlfare/FLEngine)
with [Source Dependencies](https://blog.gradle.org/introducing-source-dependencies), from Gradle tool. First, add this
to your `settings.gradle.kts`:

```kotlin
sourceControl {
  gitRepository(java.net.URI("https://github.com/LucasAlfare/FLEngine")) {
    producesModule("com.lucasalfare.flengine:FLEngine")
  }
}
```

After, add this to your `build.gradle.kts` to target the `master` branch of this repository:

```kotlin
implementation("com.lucasalfare.flengine:FLEngine") {
  version {
    branch = "master"
  }
}
```
