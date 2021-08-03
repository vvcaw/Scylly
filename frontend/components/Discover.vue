<template>
  <div class="flex items-center w-full h-screen justify-center z-10">
    <div class="bg-black h-full grid grid-cols-2 w-full absolute items-center justify-center">
      <div v-on:mouseenter="toggleActiveState(0)" v-on:click="yeet(0)" ref="no"
           class="bg-black border-r-2 border-solid border-opacity-20 cursor-pointer h-full w-full flex items-center justify-center hover:bg-gray-900">
        <svg xmlns="http://www.w3.org/2000/svg" class="text-spotifyRed h-48 w-48" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd" />
        </svg>
      </div>
      <!-- TODO: Add wiggle motion and bounce motion on heart and x when hovering -->
      <div v-on:mouseenter="toggleActiveState(1)" v-on:click="yeet(1)" ref="yes"
           class="bg-black border-l-1 border-solid border-opacity-20 cursor-pointer h-full w-full flex items-center justify-center  hover:bg-gray-900">
        <svg xmlns="http://www.w3.org/2000/svg" class="text-spotify h-48 w-48" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z" clip-rule="evenodd" />
        </svg>
      </div>
    </div>
    <Song ref="song"/>
  </div>
</template>

<script>
import Song from "./Song.vue";

export default {
  name: "Discover",
  components: {Song},
  methods: {
    toggleActiveState(direction) {
      let card = this.$refs.song
      card.toggleActiveState(direction)
    },
    yeet(direction) {
      let card = this.$refs.song
      card.yeet(direction)
    }
  }
}
</script>