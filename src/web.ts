import { WebPlugin } from '@capacitor/core';
import { CapacitorAlarmNotificationPlugin } from './definitions';

export class CapacitorAlarmNotificationWeb extends WebPlugin implements CapacitorAlarmNotificationPlugin {
  constructor() {
    super({
      name: 'CapacitorAlarmNotification',
      platforms: ['web']
    });
  }

  async echo(options: { value: string }): Promise<{value: string}> {
    console.log('ECHO', options);
    return options;
  }
}

const CapacitorAlarmNotification = new CapacitorAlarmNotificationWeb();

export { CapacitorAlarmNotification };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(CapacitorAlarmNotification);
