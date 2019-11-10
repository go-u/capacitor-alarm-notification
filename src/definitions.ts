declare module "@capacitor/core" {
  interface PluginRegistry {
    CapacitorAlarmNotification: CapacitorAlarmNotificationPlugin;
  }
}

export interface CapacitorAlarmNotificationPlugin {
  setAlarm(options: { sec: number, sound: boolean, title: string, text: string }): Promise<AlarmSetResult>;
}

export class AlarmSetResultBool{
  // return input value as it is
  sec: number;
  // return result
  constructor(public result: boolean) {
  }
}

export type AlarmSetResult = AlarmSetResultBool

