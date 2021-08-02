<template>
  <div class="bg-black h-screen grid grid-cols-10 items-center justify-center">
    <div v-on:mouseenter="toggleActiveState(0)" ref="no" class="bg-black border-r-2 border-dashed col-span-2 h-full w-full">

    </div>
    <div class="flex w-full h-full items-center col-span-6 justify-center">
      <div ref="draggable" class="transition-all duration-700 ease-out cursor-move p-4 bg-white border border-opacity-25 h-72 w-64 rounded-md">
        <h1 class="text-white">Some head</h1>
      </div>
    </div>
    <div v-on:mouseenter="toggleActiveState(1)" ref="yes" class="bg-black border-l-2 border-dashed col-span-2 h-full w-full">

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