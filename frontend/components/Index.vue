<template>
  <div class="min-h-full w-full bg-black flex flex-col justify-start items-center">
    <div class="w-3/5 h-1/4 flex flex-col">
      <label for="search" class="font-medium mt-14 text-4xl h-12 text-spotify">Search for your favourite song!</label>
      <input v-model="songInput"
             v-on:keyup="fetchSongsDebounce"
             id="search"
             class="bg-black text-5xl h-28 border-b placeholder-white border-white text-white focus:border-white outline-none"
             placeholder="Some good song..."
             spellcheck="false"
             autocomplete="off"/>
    </div>
    <div class="mt-4 w-3/5 mb-3 h-3/4 flex flex-col gap-4">
      <Song v-if="songsLoaded" v-for="song in currentSongs" :song-name="song.name"
            :song-image="song.album.images[2].url"/>
    </div>
  </div>
</template>

<script>
import Song from "./Song.vue";
import "../assets/style.css"

export default {
  name: "Index",
  components: {Song},
  data() {
    return {
      currentSongs: [
        {
          name: "",
          album: {
            images: [
              {
                url: ""
              }
            ]
          }
        }
      ],
      songsLoaded: false,
      songInput: "",
      debounce: null
    }
  },
  methods: {
    fetchSongsDebounce() {
      clearTimeout(this.debounce)
      this.debounce = setTimeout(this.fetchSongs, 300)
    },
    fetchSongs() {

      if (this.songInput === "")
        return

      fetch('/api/search', {
        method: "POST",
        body: JSON.stringify({name: this.songInput})
      })
          .then(response => response.json())
          .then(data => {
            this.currentSongs = data
            this.songsLoaded = true
          });
    }
  }
}
</script>