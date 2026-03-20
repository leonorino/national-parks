# National Parks

This is a mobile application for exploring US National Parks, featuring a map view, passport for stamps, and detailed park information.

## Assets

Link to assets, as they're too large to put in main repository: [here](https://drive.google.com/file/d/1jIzfuecwnfpHB5Gn3jZ0qxSrjxbbswqp/view?usp=sharing)

**Placement:** Extract the assets so they are placed under `app/src/main/assets`.

## Build Instructions (CLI)

You can build and run this project from the command line without using Android Studio.

### Prerequisites

- **Java Development Kit (JDK) 17 or higher:** Ensure your `JAVA_HOME` environment variable is set.
- **Android SDK:** Ensure `ANDROID_HOME` is set. You will need:
    - Android SDK Platforms for API 36
    - Android SDK Build-Tools (version 36.x.x)
    - Android Platform-Tools (for `adb`)

### Building

To clean the project and build the debug APK:

```bash
./gradlew clean assembleDebug
```

The generated APK will be available at:
`app/build/outputs/apk/debug/app-debug.apk`

### Running on a Connected Device

To install the debug version on a connected device or emulator:

```bash
./gradlew installDebug
```

You can verify your connected devices with:
```bash
adb devices
```
