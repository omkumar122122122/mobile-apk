# Build Cinematic Browser APK with GitHub Actions (No Android Studio)

1. Create an empty GitHub repository.
2. Upload **all contents of this folder** to the repository root. Make sure `.github/workflows/build-apk.yml` is included.
3. Commit to the `main` branch.
4. Open the repository's **Actions** tab.
5. Select **Build Cinematic Browser APK**.
6. Wait for the workflow to finish with a green check.
7. Open the successful run and scroll to **Artifacts**.
8. Download `CinematicBrowser-debug-apk`.
9. Extract the downloaded artifact ZIP. Inside is `app-debug.apk`.
10. Copy `app-debug.apk` to your Android phone and install it.

The workflow installs Android SDK Platform 35 and Build Tools 35.0.0, uses Java 17 and Gradle 8.7, and builds the debug APK.

If the workflow fails, open the failed job and send the red error section. The warnings about GitHub Actions runtime versions are not the same thing as a Gradle build failure.


## V2 changes
- Uses a mobile Chrome-style user agent for web compatibility.
- Keeps normal in-session WebView navigation instead of clearing cache after every page.
- Uses Google as the default search engine with Chrome-style search parameters.
- If Google returns its `/sorry/` unusual-traffic page, the app automatically falls back to DuckDuckGo for that query.
- App version is 2.0 (versionCode 2).
- Local cookies, cache, Web Storage, form data, and WebView history are cleared at session boundaries; no app database is used for browsing history.

Note: Google anti-abuse challenges are server/IP dependent, so no client-side change can guarantee that Google will never show a challenge. The automatic fallback keeps the search function usable when it does.
