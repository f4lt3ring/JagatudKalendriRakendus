<script setup lang="ts">
import Calendar from '../components/Calendar.vue'
import { useRoute } from 'vue-router';

const route = useRoute();
const id = route.params.id;

</script>

<template>
    <Calendar :id=id />
</template>
