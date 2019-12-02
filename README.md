[![Android](https://img.shields.io/badge/Capacitor-Android-green.svg?style=flat)](https://capacitor.ionicframework.com/)
[![Javascript](https://img.shields.io/badge/Capacitor-Javascript-gold.svg?style=flat)](https://capacitor.ionicframework.com/)
[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)

<p align="center"><img src="docs/logo.png" alt="Capacitor Alarm Notification logo"></p>
<h1 align="center">capacitor-alarm-notification</h1>

# What's this?
Capacitor plugin for alarm notification.

# Current State
Android : minSdkVersion = 26(Using Notification Channel)  
iOS : developing 

# Implementations
## Android
`SET_ALARM` and `Notification Channel` is used.    
So Min Android SDK Version = 26 

## iOS
Developing  

# Set up
## Install Plugin
```
npm install capacitor-alarm-notification
```
## Sync to Capacitor project
```
npx cap sync android|ios
```

## Add code (iOS)
The code is automatically added with npx cap sync.

## Add code (Android)
Add the following to `MainActivity.java`
(Receiver is in MainActivity for the convenience of the plugin)

```
// import
import com.go.capacitor.alarm.notification.CapacitorAlarmNotification;

// register plugin
public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    ...
    ...
    // Initializes the Bridge
    this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{
      // Additional plugins you've installed go here
      // Ex: add(TotallyAwesomePlugin.class);
       add(CapacitorAlarmNotification.class);
    }});
  }
}
```

# Usage

# usage
## set Alarm
10 min alarm
```
setAlarm(60 * 10)
```

## clear Alarm
```
setAlarm(0)
```


```
import { Plugins } from '@capacitor/core'
const { CapacitorAlarmNotification } = Plugins

# set Alarm
CapacitorAlarmNotification.setAlarm({ 
    sec: 60 * 10, 
    sound: false, // false: vibration only, true: ring system alarm sound 
    title: 'Boost',  // notification title
    text: 'time is up'  // notification text
})

// clear Alarm
CapacitorAlarmNotification.setAlarm(sec: 0)
```
