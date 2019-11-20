<template>
  <q-page class="flex flex-center column q-gutter-y-md">
    <img alt="Quasar logo" src="~assets/quasar-logo-full.svg">
    <q-btn :label="alarmTestTime + 'sec: vibrate'" @click="setAlarm(alarmTestTime, false)"/>
    <q-btn :label="alarmTestTime + 'sec: sound'" @click="setAlarm(alarmTestTime, true)"/>
    <q-btn label="notify(iOS)" @click="notify()"/>
  </q-page>
</template>

<script>
import { Plugins } from '@capacitor/core'
const { CapacitorAlarmNotification, LocalNotifications } = Plugins

const alarmTestTime = 3

export default {
  name: 'PageIndex',
  data () {
    return {
      alarmTestTime: alarmTestTime
    }
  },
  methods: {
    setAlarm: function (sec, soundMode) {
      CapacitorAlarmNotification.setAlarm({
        sec: sec,
        sound: soundMode,
        title: 'Boost',
        text: '時間です'
      })
      // Alarm.setAndUpdate({ 'min': min })
        .then((res) => {
          console.log(res)
          // alert(resApp['min'])
        }).catch((err) => {
          console.log(err)
        })
    },
    notify: function () {
      LocalNotifications.schedule({
        notifications: [
          {
            title: 'Title',
            body: 'Body',
            id: 1,
            schedule: { at: new Date(Date.now() + 1000 * 5) },
            sound: './public/assets/sounds/alarm.aiff',
            attachments: null,
            actionTypeId: '',
            extra: null
          }
        ]
      })
        .then((res) => {
          console.log(res)
        }).catch((err) => {
          console.log(err)
        })
    }
  }
}
</script>
