<script setup lang="ts">
import { ref } from 'vue'

const apiBase = import.meta.env.VITE_BASE_URL;

const email = ref("")
const password = ref("")

const responseRef = ref("")

async function submitLogin() {

    const body = {
        email: email.value,
        password: password.value,
    }

    try {
        const response = await fetch(`${apiBase}/api/v1/login`, {
            method: "POST",
            body: JSON.stringify(body),
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
        <h1>Log In</h1>

        <form class="sign-up-form">
            <input v-model="email" class="text-input email" type="email" placeholder="Meiliaadress">
            <input v-model="password" class="text-input password" type="password" placeholder="Password">
            <button class="text-input" type="button" @click="submitLogin">Logi sisse</button>
        </form>

        <p> {{ responseRef }} </p>

    </div>
</template>
