declare module "@capacitor/core" {
  interface PluginRegistry {
    CapacitorAlarmNotification: CapacitorAlarmNotificationPlugin;
  }
}

export interface CapacitorAlarmNotificationPlugin {
  echo(options: { value: string }): Promise<{value: string}>;
}
