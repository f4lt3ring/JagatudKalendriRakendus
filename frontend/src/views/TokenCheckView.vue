<script setup lang="ts">
import { ref } from 'vue'

const apiBase = import.meta.env.VITE_BASE_URL;

const token = ref("")

const responseRef = ref("")

async function submitToken() {

    const body = token.value;

    try {
        const response = await fetch(`${apiBase}/api/v1/login/token-check`, {
            method: "POST",
            body: body,
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            }
        });

        responseRef.value = await response.text();

    } catch (error) {
        console.error(error);
    }
}

</script>

<template>
    <div class="about">
        <h1>Token check</h1>

        <form class="sign-up-form">
            <input v-model="token" class="text-input token" type="token" placeholder="Tooken">
            <button class="text-input" type="button" @click="submitToken">Sisesta tooken</button>
        </form>

        <p> {{ responseRef }} </p>

    </div>
</template>
