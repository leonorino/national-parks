## Безопасность приложения
1. Использование HTTPS для взаимодействия с внешним API с данными о погоде.
2. Генерация подписи .apk файла приложения уникальным цифровым ключом.
3. Ограничение привелегий приложения - единственное запрашиваемое в AndroidManifest.xml разрешение - `android.permission.INTERNET`. Это не позволяет приложению обращаться к микрофону, камере, хранилищу устройства.

## План публикации
Публикация в Google Play Store требует создания аккаунта разработчика и прохождения проверки. Перед публикацией необходимо расширить список поддерживаемых языков приложения.

## Стратегия монетизации
Базовый функционал приложения (всё, что реализовано сейчас) - бесплатен.
Возможности монетизации:
- Наборы альтернативных дизайнов штампов, покупамые отдельно внутри приложения.
- При публикации в США, внедрение ссылок на покупку официальных сувениров или путеводителей для каждого из парков через партнёрские программы.

# National Parks

This is a mobile application for exploring US National Parks, featuring a map view, passport for stamps, and detailed park information.

## Как пользоваться

- **Исследование (Explore):** Главный экран содержит список всех национальных парков США. Строка поиска в верхней части позволяет фильтровать список по названию или штату. Нажатие на карточку открывает экран с подробной информацией о парке.
- **Карта (Map):** Интерактивная карта отображает географическое расположение парков. Нажатие на метку показывает краткое описание. Повторное нажатие на метку переводит на экран подробной информации (Details).
- **Паспорт (Passport):** Цифровой журнал посещений. Позволяет отмечать посещенные парки и собирать виртуальные штампы. Вкладки сортировки организуют список парков. В разделе доступна информация о прогрессе посещения.
- **Подробности (Details):** Страница содержит описание парка, актуальную информацию и виджет погоды с текущими условиями.
- **Настройки (Settings):** В разделе доступны следующие параметры:
    - **Единицы измерения:** Переключение между метрической и имперской системами.
    - **Язык:** Выбор языка интерфейса приложения.

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
