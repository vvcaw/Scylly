<template>
  <div class="min-h-full w-full bg-black flex justify-center p-4">
    <div class="w-2/3 flex flex-col justify-start items-center gap-2">
      <div class="w-full flex flex-row items-start justify-between">
        <div class="w-full h-auto flex flex-row items-center justify-between pb-2 border-b border-gray-700">
          <div class="flex flex-row items-center">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-7 text-spotify" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <span class="ml-1 text-2xl font-medium text-spotify">PROFILE</span>
            <span class="ml-1 text-2xl text-white mr-1">·</span>
            <span class="text-white text-2xl">{{ user.displayName }}</span>
          </div>
          <a class="group hover:bg-black flex flex-row cursor-pointer bg-spotify p-1 rounded-lg items-center">
            <span class="text-black group-hover:text-spotify text-sm mr-1">Logout</span>
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-black group-hover:text-spotify" fill="none"
                 viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                    d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"/>
            </svg>
          </a>
        </div>
      </div>
      <div class="flex mb-2 flex-row w-full text-2xl items-center">
        <span class="text-white">Your top tracks this month</span>
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 ml-1 hover:text-spotify cursor-pointer mt-1 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
      </div>
      <div class="flex gap-4 w-full flex-col">
        <Song v-for="song in topSongs"
              :duration="song.duration"
              :open-url="song.openUrl"
              :is-explicit="song.explicit"
              :song-image="song.images[2]"
              :song-artists="song.artists"
              :song-name="song.name">
        </Song>
      </div>
    </div>
  </div>
</template>

<script>
import Song from "./Song.vue";

export default {
  name: "Redirect",
  components: {Song},
  data() {
    return {
      topSongs: $javalin.state.topSongs,
      user: $javalin.state.user
    }
  }
}
</script>