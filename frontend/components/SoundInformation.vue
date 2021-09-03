<template>
  <div v-if="visible" class="absolute z-50 h-full md:bg-transparent bg-gray-900 w-full md:w-full flex items-center justify-center">
    <div class="bg-gray-900 h-auto w-11/12 md:h-1/2 z-50 rounded-md md:p-4 p-4 flex flex-col space-y-4 md:space-y-7 justify-center items-center">
      <div class="w-full h-auto flex justify-center items-center flex-row-reverse gap-x-3">
        <span class="text-gray-200 font-bold text-2xl md:text-5xl">What is this?</span>

          <svg xmlns="http://www.w3.org/2000/svg" class="md:h-10 md:w-10 h-6 w-6 mt-1 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>

      </div>
      <p class="text-gray-300 text-md md:text-xl text-center font-medium">This website shows recommendations based on your favourite songs this month</p>
      <div class="w-full flex md:flex-row-reverse flex-col items-center justify-center">
        <div class="flex w-full h-full md:w-10/12 md:items-start items-center justify-center">
          <div class="flex md:flex-col flex-row w-5/6 items-center justify-start gap-y-3 md:mb-5 md:mt-5">
            <div class="rounded-full mr-2 md:mr-0 md:p-3 p-2 bg-gray-300">
              <svg xmlns="http://www.w3.org/2000/svg" class="md:h-7 md:w-7 h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
              </svg>
            </div>
            <p class="text-gray-500 h-full w-full mt-2 text-lg text-center md:text-xl">Liking a song automatically adds it to your <i>Liked Songs</i> section in Spotify</p>
          </div>
        </div>
        <div class="bg-gray-400 md:block hidden h-full w-2 ml-5 mr-5"></div>
        <div class="bg-gray-400 md:hidden block w-10/12 h-1 mb-3 mt-3"></div>
        <div class="flex w-full h-full md:w-10/12 md:items-start items-center justify-center">
          <div class="flex md:flex-col flex-row w-5/6 items-center justify-start gap-y-3 md:mb-5 md:mt-5">
            <div class="rounded-full mr-2 md:mr-0 md:p-3 p-2 bg-gray-300">
              <svg xmlns="http://www.w3.org/2000/svg" class="md:h-7 md:w-7 h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </div>
            <p class="text-gray-500 h-full w-full mt-2 text-lg text-center md:text-xl">Disliking a song just discards it, nothing else happens with it.</p>
          </div>
        </div>
      </div>
      <button v-on:click="handleClick" class="shadow-md bg-spotify md:p-3 p-2 text-white font-medium rounded-md">Understood</button>
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
  },
  methods: {
    handleClick() {
      this.$emit("done")

      this.visible = false
    }
  }
}
</script>

<style scoped>

</style>