<template>
  <div v-if="visible" class="absolute z-50 h-full w-full flex items-center justify-center">
    <div class="bg-gray-900 h-1/2 z-50 rounded-md p-4 flex flex-col space-y-7 justify-center items-center">
      <div class="w-full h-auto flex justify-center items-center flex-row-reverse gap-x-3">
        <span class="text-gray-200 font-bold text-5xl">What is this?</span>

          <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 mt-1 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>

      </div>
      <p class="text-gray-300 text-xl font-medium">This websites shows recommendations based on your favourite songs this month</p>
      <div class="w-full flex items-center justify-center">
        <div class="flex w-full items-center justify-center">
          <div class="flex flex-col w-5/6 items-center justify-start gap-y-3 mb-5 mt-5">
            <div class="rounded-full p-3 bg-gray-300">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-7 w-7" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
              </svg>
            </div>
            <p class="text-gray-500 h-full w-full mt-2 text-xl">Liking a song automatically adds it to your <i>Liked Songs</i> section in Spotify</p>
          </div>
        </div>
        <div class="bg-gray-400 h-full w-2 ml-5 mr-5"></div>
        <div class="flex w-full items-center justify-center">
          <div class="flex flex-col w-5/6 items-center justify-start gap-y-3 mb-5 mt-5">
            <div class="rounded-full p-3 bg-gray-300">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-7 w-7" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </div>
            <p class="text-gray-500 h-full w-full mt-2 text-xl">Disliking a song just discards it, nothing else happens with it.</p>
          </div>
        </div>
      </div>
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