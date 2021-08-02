<template>
  <div class="flex items-center w-full h-screen justify-center z-10">
    <div class="bg-black h-full grid grid-cols-2 w-full absolute items-center justify-center">
      <div v-on:mouseenter="toggleActiveState(0)" ref="no"
           class="bg-black border-r-2 border-solid border-opacity-20 h-full w-full flex items-center justify-center hover:bg-gray-900">
        <svg xmlns="http://www.w3.org/2000/svg" class="text-spotifyRed h-48 w-48" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd" />
        </svg>
      </div>
      <div v-on:mouseenter="toggleActiveState(1)" ref="yes"
           class="bg-black border-l-1 border-solid border-opacity-20 h-full w-full flex items-center justify-center  hover:bg-gray-900">
        <svg xmlns="http://www.w3.org/2000/svg" class="text-spotify h-48 w-48" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z" clip-rule="evenodd" />
        </svg>
      </div>
    </div>
    <div ref="draggable"
         class="transition-all z-10 duration-700 ease-out cursor-move p-4 bg-white border border-opacity-25 h-72 w-64 rounded-md">
      <h1 class="text-white">Some head</h1>
    </div>
  </div>
</template>

<script>

export default {
  name: "Discover",
  data() {
    return {
      direction: 0
    }
  },
  methods: {
    toggleActiveState(direction) {
      let card = this.$refs.draggable

      if (direction === 0) {
        $(card).css({'transform': 'rotate(-10deg)', 'height': '22rem', 'margin-right': '15rem', 'margin-left': '0'})
      } else if (direction === 1) {
        $(card).css({'transform': 'rotate(10deg)', 'height': '22rem', 'margin-left': '15rem', 'margin-right': '0'})
      }
    }
  }
}
</script>