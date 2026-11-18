# MY RIDE AUTO – Android prototype

## What this prototype does
- Reads notification text after you explicitly enable Android Notification Access.
- Looks for notifications mentioning Rapido, Ola, or Uber.
- Extracts a displayed fare in formats such as `₹461`, `Rs. 461`, or `INR 461`.
- Shows a MY RIDE AUTO alert when the detected fare is **₹101 or more**.
- Does nothing for ₹100 or below.
- Never presses Accept or Reject and does not modify the original driver apps.

## Important limitations
- This is source code, not an installable APK.
- It is an unbuilt starter project and has not been tested on your exact phone or on live Rapido/Ola/Uber notifications.
- The current brand matching is a basic prototype. Android notifications may omit the brand name from their text, and each app may use different notification formats. The app may therefore need tuning after testing.
- Some ride requests appear as in-app floating cards rather than Android notifications. Notification access cannot read those cards.
- Automatic acceptance is not included. It requires a platform-supported and permitted integration; screen-click automation may violate platform terms and can risk your driver account.
- Notification access exposes notification contents to this app. Enable it only if you are comfortable with that permission.

## Build
Open this folder in Android Studio (a computer is normally required), allow Gradle sync, then build an APK using **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## Install and test
1. Install the debug APK on your Android phone.
2. Open MY RIDE AUTO.
3. Tap **Enable notification access** and enable MY RIDE AUTO in Android settings.
4. Send a test notification containing `Rapido ₹150` to verify the alert.
5. Then test with actual driver-app notifications while observing whether the fare is included in the notification text.

## Next development step
After you can provide a real expanded notification screenshot (not the in-app ride card), inspect the notification's actual text and package name, then refine app-specific detection for Rapido first.
