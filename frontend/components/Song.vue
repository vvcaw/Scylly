<template>
  <a @click="toggled = !toggled"
     class="cursor-pointer select-none transform group w-full min-h-24 bg-black border border-white text-white rounded-md opacity-100 flex-col flex justify-start p-4 hover:border-spotify">
    <div class="flex items-center">
      <img :src="songImage">
      <span class="font-medium ml-4 text-white text-3xl group-hover:text-spotify">{{ songName }}</span>
    </div>
    <div class="overflow-hidden">
      <TransitionRoot
          :show="toggled"
          enter="transition-height duration-150"
          enter-from="h-0"
          enter-to="h-16"
          leave="transition-height duration-75"
          leave-from="h-16"
          leave-to="h-0"
      >
        <div class="mt-4 h-16 flex flex-col">
          <div class="flex flex-row justify-between items-center">
            <div class="flex flex-col justify-start items-start">
              <div class="flex flex-row justify-between items-center">
                <div>
                  <span v-for="(artist, index) in songArtists" class="font-medium text-2xl">{{ artist.name }}
                    <span class="font-medium text-2xl" v-if="songArtists.length - index >= 2">· </span>
                  </span>
                </div>
                <div class="flex flex-col items-center">
                  <span v-if="explicit" class="p-1 rounded-md ml-2 font-light text-gray-400 text-sm bg-gray-900">EXPLICIT</span>
                </div>
              </div>
              <div class="flex flex-row justify-start items-center mt-2">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24"
                     stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                        d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/>
                </svg>
                <span class="font-medium ml-2">{{ duration }} min</span>
              </div>
            </div>
            <div class="flex flex-row justify-between items-start w-16 h-16">
              <a :href="openUrl" target="_blank"
                      class="flex items-center justify-center h-16 w-16 bg-white focus:outline-none text-black text-sm font-medium hover:bg-spotify hover:text-white p-3 rounded-full">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-8 w-8" viewBox="0 0 20 20" fill="currentColor">
                  <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM9.555 7.168A1 1 0 008 8v4a1 1 0 001.555.832l3-2a1 1 0 000-1.664l-3-2z" clip-rule="evenodd" />
                </svg>
              </a>
            </div>
          </div>
        </div>
      </TransitionRoot>
    </div>
  </a>
</template>

<script>
import {TransitionRoot} from '@headlessui/vue';

export default {
  name: "Song",
  components: {
    TransitionRoot
  },
  props: {
    songName: String,
    songImage: String,
    explicit: Boolean,
    openUrl: String,
    duration: Number,
    preview: String,
    songArtists: {
      type: Array,
      default: []
    }
  },
  data() {
    return {
      toggled: false
    }
  }
}
</script>