# Bedrock Forms

This library automatically converts menus to Bedrock Forms when a Floodgate player opens them.

## Supported Forms

- **Simple Form**: Mapped from standard Chest GUIs. Buttons become list items.
- **Modal Form**: (Planned/Partial support)
- **Custom Form**: (Planned/Partial support)

## Form Specifics

You can customize the Bedrock representation:

```kotlin
button {
    text("This shows on Bedrock")
    image("url_to_image") // Optional image for Bedrock
    
    onlyJava(false) // Set to true to hide on Bedrock
}
```

