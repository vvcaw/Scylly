<template>
  <div class="absolute top-5 right-5 z-10 flex flex-row justify-evenly items-center gap-0.5">
    <div v-on:click="$emit('minus')" class="hover:bg-gray-900 rounded-xl cursor-pointer h-12 w-12 flex items-center justify-center">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-2/3 w-2/3 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
      </svg>
    </div>

    <div class="flex items-center justify-center h-12 w-12">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-3/4 w-3/4 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15.536 8.464a5 5 0 010 7.072m2.828-9.9a9 9 0 010 12.728M5.586 15H4a1 1 0 01-1-1v-4a1 1 0 011-1h1.586l4.707-4.707C10.923 3.663 12 4.109 12 5v14c0 .891-1.077 1.337-1.707.707L5.586 15z" />
      </svg>
    </div>

    <div v-on:click="$emit('plus')" class="hover:bg-gray-900 rounded-xl cursor-pointer h-12 w-12 flex items-center justify-center">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-2/3 w-2/3 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
      </svg>
    </div>
  </div>
</template>

<script>
export default {
  name: "VolumeRocker",
}
</script>

<style scoped>

</style>