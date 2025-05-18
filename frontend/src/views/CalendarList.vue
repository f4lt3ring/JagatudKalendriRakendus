<script setup lang="ts">
import { onMounted, ref } from "vue";

const apiBase = import.meta.env.VITE_BASE_URL;

const calendarList = ref([]);

onMounted(() => {
    listCalendars();
});

async function listCalendars() {
    try {
        const response = await fetch(`${apiBase}/calendar/list-calendars`);
        calendarList.value = await response.json();

    } catch (error) {
        console.error(error);
    }
}

async function createCalendar() {
    try {
        const response = await fetch(`${apiBase}/calendar/create`, {
            method: "POST",
        });

        listCalendars();

    } catch (error) {
        console.error(error);
    }
}


</script>

<template>

    <div>

        <li v-for="calendarId in calendarList">
            <RouterLink :to="`/calendar/${calendarId}`">Kalender {{ calendarId }}</RouterLink>
        </li>

        <button v-on:click="createCalendar">Loo kalender</button>
    </div>

</template>
