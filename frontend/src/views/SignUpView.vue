<script setup lang="ts">
import { ref } from 'vue'

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
    const response = await fetch("https://localhost:8080/api/v1/registration", {
      method: "POST",
      body: body,
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json"
      }
    });
    
    responseRef.value = await response.json()


  } catch (error) {
    console.error(error);
  }
}

</script>

<template>
  <div class="about">
    <h1>Sign Up</h1>

    <form class="sign-up-form">
      <input v-model="email" class="text-input email" type="email" placeholder="Meiliaadress">
      <input v-model="username" class="text-input username" placeholder="Kasutajanimi">
      <input v-model="password" class="text-input password" type="password" placeholder="Password">
      <button class="text-input" type="button" @click="submitSignUp">Registreeru</button>
    </form>

    <p> {{ responseRef }} </p>

  </div>
</template>


<style scoped>

.sign-up-form {

}

</style>