<template>
  <a onclick="location.href = '/spotify-redirect'" class="absolute bottom-0 right-0 rounded-full cursor-pointer hover:bg-gray-200 mb-20 mr-2 bg-white flex items-center justify-center z-10 absolute h-14 w-14">
    <svg xmlns="http://www.w3.org/2000/svg" class="h-7 w-7 relative text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
    </svg>
  </a>
</template>

<script>
export default {
  name: "AccountIcon"
}
</script>

<style scoped>

</style>