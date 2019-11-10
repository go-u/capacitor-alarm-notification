<template>
  <q-page class="flex flex-center column q-gutter-y-md">
    <img alt="Quasar logo" src="~assets/quasar-logo-full.svg">
    <q-btn :label="alarmTestTime + 'sec: vibrate'" @click="setAlarm(alarmTestTime, false)"/>
    <q-btn :label="alarmTestTime + 'sec: sound'" @click="setAlarm(alarmTestTime, true)"/>
  </q-page>
</template>

<script>
import { Plugins } from '@capacitor/core'
const { CapacitorAlarmNotification } = Plugins

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
    }
  }
}
</script>
