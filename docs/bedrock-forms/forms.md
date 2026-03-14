# Bedrock Forms

The library detects Floodgate players by their UUID (most significant bits are 0) and automatically sends Bedrock forms
instead of opening inventory menus if configured.

## Form Types

- **Simple Form**: List of buttons.
- **Modal Form**: Yes/No dialogs.
- **Custom Form**: Complex input forms (Text input, Dropdowns, Toggles, Sliders).

## Using Forms Directly

You can also use the low-level API to send forms directly.

```kotlin
import codes.bed.minestom.menus.bedrock.form.SimpleForm

val form = SimpleForm.Builder()
    .title("Select Game Mode")
    .content("Choose your game mode")
    .button("Survival") {
        // ...
    }
    .button("Creative") {
        // ...
    }
    .build()

form.send(player)
```

## Custom Forms

```kotlin
import codes.bed.minestom.menus.bedrock.form.CustomForm

val customForm = CustomForm.Builder()
    .title("Settings")
    .input("Enter your name", "Name here")
    .dropdown("Difficulty", listOf("Easy", "Normal", "Hard"), 1)
    .toggle("Enable PvP", true)

    .onClose {
        // Handle close
    }
    // Responses are handled in specific component callbacks or via global listener if needed
    .build()

customForm.send(player)
```

