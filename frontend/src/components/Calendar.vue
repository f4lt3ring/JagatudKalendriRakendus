<script setup lang="ts">

import ICAL from "ical.js";
import VueCal from 'vue-cal';
import 'vue-cal/dist/vuecal.css';
import { onMounted, ref, type Ref } from "vue";

const apiBase = import.meta.env.VITE_BASE_URL;
const props = defineProps({
    id: String
});

const calendarEvents: Ref<any[]> = ref([]);

onMounted(() => {
    createCalendar();
});

async function createCalendar() {

    try {
        const response = await fetch(`${apiBase}/calendar/${props.id}/download`);
        const ics = await response.text();

        const calendar = new ICAL.Component(ICAL.parse(ics));
        const vevents = calendar.getAllSubcomponents("vevent");

        calendarEvents.value = vevents.map(vevent => {
            const event = new ICAL.Event(vevent);
            return {
                title: event.summary,
                start: event.startDate.toJSDate(),
                end: event.endDate.toJSDate()
            }
        })

        console.log(JSON.stringify(vevents));

    } catch (error) {
        console.error(error);
    }

}

</script>

<template>

    <VueCal :events="calendarEvents"/>

</template>
