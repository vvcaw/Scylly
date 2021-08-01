<template>
  <div class="absolute top-0 w-full h-auto flex items-start justify-center">
    <div ref="banner" v-if="visible" class="relative z-10 w-full h-auto bg-white flex flex-row justify-between items-center p-3">
      <div class="flex flex-row space-x-2 items-center">
        <div class="bg-gray-100 rounded-full p-2">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 19V6l12-3v13M9 19c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zm12-3c0 1.105-1.343 2-3 2s-3-.895-3-2 1.343-2 3-2 3 .895 3 2zM9 10l12-3" />
          </svg>
        </div>
        <span class="font-medium text-lg text-gray-600">Want to register and add your own songs?</span>
      </div>
      <div class="flex flex-row-reverse items-center">
        <a v-on:click.stop="changeVisibility()" class="cursor-pointer hover:bg-gray-200 rounded-xl p-2">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
          </svg>
        </a>
        <button v-on:click="addSongs()" class="mr-3 focus:outline-none bg-gray-100 hover:bg-gray-200 rounded-md text-gray-600 p-2">
          Add songs
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import $ from "jquery";

export default {
  name: "InfoBanner",
  methods:{
    addSongs(){
      location.href = "https://accounts.spotify.com:443/authorize?client_id=ca135148aceb494cb629d07939b71f7c&response_type=code&scope=user-read-recently-played+user-top-read&redirect_uri=http%3A%2F%2Flocalhost%3A7000%2Fspotify-redirect&show_dialog=true"
    },
    changeVisibility(){
      $(this.$refs.banner).animate({bottom: '5rem'}, 500, "swing", () => {
        this.visible = false
      })
    }
  },
  mounted() {
    $(this.$refs.banner).css({bottom: '5rem'})
    setTimeout(() => {
      $(this.$refs.banner).animate({bottom: '0'}, 500, "swing")
    }, 10000)
  },
  data(){
    return{
      visible: true
    }
  }
}
</script>