<script setup lang="ts">

import ICAL from "ical.js";
import { VueCal } from 'vue-cal';
import 'vue-cal/style.css';
import { onMounted, ref, type Ref } from "vue";

const apiBase = import.meta.env.VITE_BASE_URL;
const props = defineProps({
    id: String
});

const calendarEvents: Ref<any[]>= ref([]);
const feedback:Ref<string| null>= ref("Loading...");

function dateToLdtString(date: Date) {
    const pad = n => n.toString().padStart(2, '0');

    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T` +
        `${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
}

function getDurationString(startDate: Date, endDate: Date) {
    const millis = endDate - startDate;
    let seconds = Math.floor(millis / 1000);

    let minutes = Math.floor(seconds / 60);
    seconds %= 60;

    let hours = Math.floor(minutes / 60);
    minutes %= 60;

    return `PT${hours}H${minutes}M${seconds}S`
}

onMounted(() => {
    displayCalendar();
});

async function displayCalendar() {

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
                end: event.endDate.toJSDate(),
                id: 1,
            }
        })

        console.log(JSON.stringify(vevents));
        feedback.value = null;

    } catch (error) {
        feedback.value = "Could not load calendar";
        console.error(error);
    }

}

const createEvent = async ({ event, resolve }) => {

    const title = "Uus sündmus";

    const eventDTO = {
        eventName: title,
        eventStart: dateToLdtString(event.start),
        duration: getDurationString(event.start, event.end)
    }

    console.log(eventDTO.eventStart)
    console.log(eventDTO.duration)

    const response = await fetch(`${apiBase}/calendar/${props.id}/events`, {
        method: "POST",
        body: JSON.stringify(eventDTO),
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
    });

    console.log(response);

    if (!response.ok) return;

    resolve({
        ...event,
        title: title
    });
}

const tryDelete = async ({ event }) => {

    console.log(event)

    
}

</script>

<template>

    <VueCal
        :events="calendarEvents"

        editable-events
        @event-create="createEvent"
        @event-dblclick="tryDelete"

        :snap-to-interval="30"
    />


    <div v-if="feedback != null">{{ feedback }}</div>

</template>
