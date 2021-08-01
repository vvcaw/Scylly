<template>
  <div class="bg-black h-screen flex flex-row items-center justify-center">
    <div ref="draggable" class="cursor-move p-4 bg-white border border-opacity-25 h-2/3 w-1/4 rounded-md">
      <h1 class="text-white">Some head</h1>
    </div>
  </div>
</template>

<script>
export default {
  name: "Discover",
  methods: {

  },
  mounted() {
    let card = this.$refs.draggable

    // Make card draggable
    $(card).draggable({
      refreshPositions: true,
      revert: true,
      scroll: false,
      start: (event, ui) => {
        console.log("dragging started!")
      },
      stop: (event, ui) => {
        console.log("dragging stopped!")
      },
      drag: (event, ui) => {
        console.log(ui.position.left)
      }
    });
  }
}
</script>