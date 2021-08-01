<template>


    <TransitionRoot
      :show="switching"
      enter="transition-opacity duration-1000"
      enter-from="opacity-0"
      enter-to="opacity-100"

  >

      <a :class="{ 'bg-blue-900' : customColor, 'bg-white' : !customColor }"
          class="h-full w-full flex items-center p-4 font-medium text-6xl justify-start">
        <p :id="'songName' + id" class="text-black w-full">{{id}}</p>
      </a>

  </TransitionRoot>

</template>

<script>
import TypeIt from "typeit";
import axios from "axios";
import {TransitionRoot} from '@headlessui/vue';

export default {
  name: "SongInfo",
  components: {
    TransitionRoot
  },
  data() {
    return {
      artist: "Hans",
      songName: "Some Stuff"
    }
  },
  props: {
    id: Number,
    visible: Boolean,
    customColor: Boolean
  },
  methods: {
    switchIt(){
      this.switching = !this.switching
    },
    newCard() {
      this.switchIt()
      this.getTrending()
      this.startTyping()
    },
    startTyping() {
      this.switchIt()
      new TypeIt(`#songName${this.id}`, {
        strings: this.songName,
        speed: 75,
        waitUntilVisible: true,
        afterComplete: async (step, instance) => {
          this.newCard()
        }
      })
          .break()
          .pause(1000)
          .type(`<em>- ${this.artist} </em>`)
          .pause(500)
          .go()
    },
    getTrending() {
      this.artist = "Eric"
      this.songName = " lame"
      /*
      axios.get('/api/trending')
          .then(function (response) {
            console.log(response)
          })
          .catch(function (error) {
            // handle error
            console.log(error);
          })

       */
    }
  }
}
</script>

<style scoped>

</style>