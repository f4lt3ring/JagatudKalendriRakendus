<script setup lang="ts">

import ICAL from "ical.js";
import VueCal from 'vue-cal';
import 'vue-cal/dist/vuecal.css';
import { onMounted, ref } from "vue";

const calendarEvents = ref([]);

onMounted(() => {
    createCalendar();
});

function createCalendar() {
    fetch("/test-calendar.ics").then(res => res.text()).then(icsData => {
        const calendar = new ICAL.Component(ICAL.parse(icsData));
        const vevents = calendar.getAllSubcomponents("vevent");

        calendarEvents.value = vevents.map(vevent => {
            const event = new ICAL.Event(vevent);
            return {
                title: event.summary,
                start: event.startDate.toJSDate(),
                end: event.endDate.toJSDate()
            }
        })
    })
}

</script>

<template>

    <VueCal :events="calendarEvents"/>

</template>
