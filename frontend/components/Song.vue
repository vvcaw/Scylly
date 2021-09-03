<template>
  <div ref="song"
       v-bind:class="zIndex"
       class="absolute pointer-events-none z-10 mr-0 ml-0 p-4 bg-black border-2 border-solid h-80 w-64 md:h-96 md:w-72 rounded-md flex flex-col">
    <img class="select-none rounded-md" :src="dataSongs[activeIndex].images[1]" alt="Song cover">
    <div class="h-full w-full flex flex-col items-start justify-center">
      <div class="flex flex-row-reverse md:flex-col h-full w-full justify-end gap-1 md:gap-0 md:justify-center items-center md:items-start">
        <div v-if="dataSongs[activeIndex].explicit" class="mt-2 md:mt-1 rounded-md pl-1 pr-1 bg-gray-900">
          <span class="select-none text-gray-300 text-sm">EXPLICIT</span>
        </div>
        <div v-if="!dataSongs[activeIndex].explicit" class="w-1"></div>
        <span class="mt-2 md:mt-0 select-none text-white font-medium text-md md:text-2xl overflow-ellipsis truncate">{{ dataSongs[activeIndex].name }}</span>
      </div>
      <div class="w-full flex-row justify-between">
        <span class="select-none overflow-ellipsis truncate text-gray-600 text-sm md:text-xl">{{ dataSongs[activeIndex].artists[0].name }}</span>
        <div class="w-1"></div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from "jquery";
import axios from "axios";

export default {
  name: "Song",
  props: {
    songs: Array,
    cardNumProp: Number,
  },
  computed: {
    zIndex: function () {
      return `z-${this.cardNum}0`;
    }
  },
  data() {
    return {
      cardNum: this.cardNumProp,
      activeIndex: 0,
      resetting: false,
      degrees: 0,
      margin: [0, 0],
      dataSongs: this.songs,
      audio: null,
      playing: false,
      startedPlaying: false
    }
  },
  methods: {
    // get/set
    incrementCardNum() {
      this.cardNum++
    },
    setCardNum(newNum) {
      this.cardNum = newNum
    },

    // http calls
    async updateRecommendations() {
      let res = await this.getRecommendations()
      this.dataSongs = res.data

      console.log("Recommendations were updated for card with title" + ` ${this.dataSongs[this.activeIndex].name} RESETTING: ${this.resetting}`)
    },
    getRecommendations() {
      return axios.get('/api/recommendations')
          .then(function (response) {
            return response;
          })
          .catch(function (error) {
            console.log(error);
          })
    },

    play() {
      // Check if playUrl is valid
      if (this.dataSongs[this.activeIndex].playUrl === "")
        return

      let audio = new Audio(this.dataSongs[this.activeIndex].playUrl)
      audio.volume = 0.4;

      this.audio = audio

      audio.play().then(() => {
        this.startedPlaying = true

        if (!this.playing) {
          this.audio.pause()
          this.startedPlaying = false
        }
      })
    },
    toggleActiveState(direction) {
      if (this.resetting)
        return

      let card = $(this.$refs.song)
      let vue = this

      if (!this.playing) {
        this.play()
        this.playing = true
      }

      let deg = 0;
      let marginRight = 0;
      let marginLeft = 0;

      if (direction === 0) {
        deg = -10;
        marginLeft = 0;
        marginRight = 15;
      } else if (direction === 1) {
        deg = 10;
        marginLeft = 15;
        marginRight = 0;
      }

      $({deg: this.degrees, marginRight: this.margin[1], marginLeft: this.margin[0]}).animate({
        deg: deg,
        marginRight: marginRight,
        marginLeft: marginLeft
      }, {
        duration: 800,
        easing: "easeOutExpo",
        step: function (now, x) {
          if (vue.resetting) {
            $(this).stop()
          }

          switch (x.prop) {
            case "deg":
              card.css("transform", `rotate(${now}deg)`);
              vue.degrees = now;
              break;
            case "marginLeft":
              card.css(x.prop, `${now}rem`);
              vue.margin[0] = now;
              break;
            case "marginRight":
              card.css(x.prop, `${now}rem`);
              vue.margin[1] = now;
              break;
          }
        },
      });

      // Update values for next iteration
      this.degrees = deg
      this.margin[1] = marginRight
      this.margin[0] = marginLeft
    },
    yeet(direction) {
      if (this.resetting)
        return

      if (this.startedPlaying) {
        this.startedPlaying = false
        this.audio.pause()
      }
      this.playing = false

      let card = $(this.$refs.song)

      let toAnimate = {}
      let animationValues = {}

      toAnimate.deg = this.degrees

      animationValues.deg = 0

      if (direction === 0) {
        toAnimate.marginRight = this.margin[1]
        animationValues.marginRight = (window.innerWidth + card.width())
      } else if (direction === 1) {
        toAnimate.marginLeft = this.margin[0]
        animationValues.marginLeft = (window.innerWidth + card.width())
      }

      // Hinder user from canceling animations
      this.resetting = true

      $(toAnimate).animate(animationValues, {
        duration: 300,
        easing: "easeOutCirc",
        complete: async () => {
          let song = $(this.$refs.song)

          // Stop animation here! Otherwise animation renders after resetting style causing glitches!
          // Not working because .animate() is not called directly on 'song' selector but rather on javascript object
          // song instance doesn't know it's currently animating
          song.stop()
          song.removeAttr('style')

          this.$emit("done")

          // Increment song index and request new songs if index reaches end of array
          if (this.activeIndex + 1 >= this.dataSongs.length) {
            this.updateRecommendations().then(() => {
              this.degrees = 0
              this.margin[1] = 0
              this.margin[0] = 0

              // Stop resetting here and wait for new recommendations if needed
              // This needs to be called in a .then() callback, otherwise this assignment gets lost and unnoticed
              this.resetting = false
            })

            this.activeIndex = 0
          } else {
            this.activeIndex++
          }

          this.degrees = 0
          this.margin[1] = 0
          this.margin[0] = 0

          // Stop resetting here and wait for new recommendations if needed
          this.resetting = false
        },
        step: function (now, x) {
          switch (x.prop) {
            case "deg":
              card.css("transform", `rotate(${now}deg)`);
              break;
            default:
              card.css(x.prop, `${now}px`);
              break;
          }
        },
      });
    },
    yeetMobile(direction) {
      if (this.resetting)
        return

      if (this.startedPlaying) {
        this.startedPlaying = false
        this.audio.pause()
      }
      this.playing = false

      let card = $(this.$refs.song)

      let toAnimate = {}
      let animationValues = {}

      toAnimate.deg = this.degrees

      animationValues.deg = 0

      if (direction === 0) {
        toAnimate.marginRight = this.margin[1]
        animationValues.marginRight = (window.innerWidth * 2 + card.width())
      } else if (direction === 1) {
        toAnimate.marginLeft = this.margin[0]
        animationValues.marginLeft = (window.innerWidth * 2 + card.width())
      }

      // Hinder user from canceling animations
      this.resetting = true

      $(toAnimate).animate(animationValues, {
        duration: 300,
        easing: "easeOutCirc",
        complete: async () => {
          let song = $(this.$refs.song)

          // Stop animation here! Otherwise animation renders after resetting style causing glitches!
          // Not working because .animate() is not called directly on 'song' selector but rather on javascript object
          // song instance doesn't know it's currently animating
          song.stop()
          song.removeAttr('style')

          this.$emit("done")

          // Increment song index and request new songs if index reaches end of array
          if (this.activeIndex + 1 >= this.dataSongs.length) {
            this.updateRecommendations().then(() => {
              this.degrees = 0
              this.margin[1] = 0
              this.margin[0] = 0

              // Stop resetting here and wait for new recommendations if needed
              // This needs to be called in a .then() callback, otherwise this assignment gets lost and unnoticed
              this.resetting = false
            })

            this.activeIndex = 0
          } else {
            this.activeIndex++
          }

          this.degrees = 0
          this.margin[1] = 0
          this.margin[0] = 0

          // Stop resetting here and wait for new recommendations if needed
          this.resetting = false
        },
        step: function (now, x) {
          switch (x.prop) {
            case "deg":
              card.css("transform", `rotate(${now}deg)`);
              break;
            default:
              card.css(x.prop, `${now}px`);
              break;
          }
        },
      });
    },
  }
}
</script>