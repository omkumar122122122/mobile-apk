# Build Cinematic Browser APK in the cloud (no Android Studio)

## GitHub Actions — easiest method

1. Create/sign in to a GitHub account.
2. Create a **new repository** (Public or Private).
3. Upload **the contents of this project** to the repository root. `settings.gradle` and `app/` must be at the root level.
4. Commit to the `main` branch.
5. Open the repository's **Actions** tab.
6. Select **Build Cinematic Browser APK**.
7. Click **Run workflow** if it has not already run.
8. Wait for the green checkmark.
9. Open the completed workflow run.
10. Under **Artifacts**, download `CinematicBrowser-debug-apk`.
11. Extract it and copy `app-debug.apk` to your Android phone.
12. Tap the APK and install it.

The workflow installs Android SDK 35, Java 17 and Gradle 8.7 automatically. No Android Studio is required.

## If GitHub warns about workflow permissions

Go to repository **Settings → Actions → General** and ensure GitHub Actions are allowed to run. The workflow only needs to read repository contents and upload its build artifact.

## What is built

- Android application ID: `com.cinemabrowser.app`
- Version: `1.0` (versionCode 1)
- Portrait-first UI
- 37 bundled mobile wallpapers
- Cinematic dashboard
- WebView browser navigation
- Search / URL bar
- ChatGPT / Gemini / Claude shortcuts
- Google / YouTube / GitHub / WhatsApp / LinkedIn / Drive shortcuts
- Session-only navigation state
- Clears WebView cookies, cache, history, Web Storage and form data when the app session is closed/restarted

### Privacy limitation

The app is designed not to persist browsing history locally. Websites and online accounts can still receive and retain activity on their own servers.
