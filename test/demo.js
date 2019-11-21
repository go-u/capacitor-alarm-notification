// javascript

const wdio = require("webdriverio");
const assert = require("assert");

const opts = {
  port: 4723,
  capabilities: {
    platformName: "Android",
    platformVersion: "9",
    deviceName: "emu",
    app: "demo/dist/capacitor/android/app-debug.apk",
    appPackage: "com.demo.capacitor.alarm.notification",
    appActivity: "com.demo.capacitor.alarm.notification.MainActivity",
    automationName: "UiAutomator2"
  }
};

async function main () {
  const client = await wdio.remote(opts);
  // client.init()
  //   .saveScreenshot('/Users/runner/runners/2.160.2/work/capacitor-alarm-notification/capacitor-alarm-notification/screenshot.png');
  // const field = await client.$("android.widget.EditText");
  // await field.setValue("Hello World!");
  // const value = await field.getText();
  // assert.equal(value,"Hello World!");

  await client.deleteSession();
}

main();
