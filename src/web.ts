import { WebPlugin } from '@capacitor/core';
import { AlarmSetResult, CapacitorAlarmNotificationPlugin } from './definitions';

export class CapacitorAlarmNotificationWeb extends WebPlugin implements CapacitorAlarmNotificationPlugin {
  constructor() {
    super({
      name: 'CapacitorAlarmNotification',
      platforms: ['web']
    });
  }

  // 5 min alarm : set(5 * 60)
  // clear alarm : set(0)
  async setAlarm(options: { sec: number, sound: boolean, title: string, text: string }): Promise<AlarmSetResult> {
    console.log('setAlarm options: ', options);
    return Promise.resolve({
      sec: options.sec,
      result: undefined
    });
  }
}

const CapacitorAlarmNotification = new CapacitorAlarmNotificationWeb();

export { CapacitorAlarmNotification };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(CapacitorAlarmNotification);
