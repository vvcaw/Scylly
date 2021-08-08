<template>
  <div v-if="visible" class="absolute z-50 h-full w-full flex items-center justify-center">
    <div class="bg-gray-900 h-1/2 z-50 rounded-md p-4 flex flex-col space-y-7 justify-center items-center">
      <div class="w-full h-auto flex justify-center items-center flex-row-reverse gap-x-3">
        <span class="text-gray-200 font-bold text-5xl">Warning</span>
        <div class="mt-1 rounded-full p-2 bg-white">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
          </svg>
        </div>
      </div>
      <p class="text-gray-200 w-2/3 text-xl">This website uses audio to showcase songs you might like, thus audio on this site will play automatically!</p>
      <button v-on:click="visible = false" class="shadow-md bg-spotify p-3 text-white font-medium rounded-md">Understood</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "SoundInformation",
  data() {
    return {
      visible: true
    }
  }
}
</script>

<style scoped>

</style>