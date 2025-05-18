<script setup lang="ts">
import { ref } from 'vue'

const apiBase = import.meta.env.VITE_BASE_URL;

const email = ref("")
const username = ref("")
const password = ref("")

const responseRef = ref("")

async function submitSignUp() {

    const body = {
        email: email.value,
        username: username.value,
        password: password.value,
    }

    try {
        const response = await fetch(`${apiBase}/api/v1/registration`, {
            method: "POST",
            body: JSON.stringify(body),
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            }
        });

        await response.text();
        responseRef.value = "Aktiveeri konto meili kaudu."


    } catch (error) {
        console.error(error);
    }
}

</script>

<template>
    <div class="about">
        <h1>Loo konto</h1>

        <form class="sign-up-form">
            <input v-model="email" class="text-input email" type="email" placeholder="Meiliaadress">
            <input v-model="username" class="text-input username" placeholder="Kasutajanimi">
            <input v-model="password" class="text-input password" type="password" placeholder="Parool">
            <button class="text-input" type="button" @click="submitSignUp">Loo Konto</button>
        </form>

        <p> {{ responseRef }} </p>

    </div>
</template>
