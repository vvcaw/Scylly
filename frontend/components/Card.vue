<template>
  <div class="relative h-full w-full overflow-hidden">
    <div ref="lower" class="absolute h-full w-full flex items-center justify-start p-4"
         v-bind:class="colors[activeColorLower]">
      <p ref="lowerText" class="text-white text-7xl w-full"></p>
    </div>
    <div ref="upper" class="absolute h-full w-full flex items-center justify-start p-4"
         v-bind:class="colors[activeColorUpper]">
      <p ref="upperText" class="text-white text-7xl w-full"></p>
    </div>
  </div>
</template>

<script>
import $ from "jquery";
import TypeIt from "typeit";
import axios from "axios";

export default {
  name: "Card",
  data() {
    return {
      switchState: 0,
      direction: "right",
      activeColorLower: this.getRandomNumber(5),
      activeColorUpper: this.getRandomNumber(5),
      colors: [
        "bg-blue-400",
        "bg-green-400",
        "bg-indigo-400",
        "bg-purple-400",
        "bg-pink-400"
      ],
      activeSong: {}
    }
  },
  mounted() {
    switch (this.direction) {
      case "right":
        $(this.$refs.upper).css({right: '100%'});
        break;
      case "left":
        $(this.$refs.upper).css({left: '100%'});
        break;
      case "top":
        $(this.$refs.upper).css({top: '100%'});
        break;
      case "bottom":
        $(this.$refs.upper).css({bottom: '100%'});
        break;
    }

    this.startTyping(this.$refs.lowerText)
  },
  methods: {
    getRandomNumber(range, forbidden) {
      let random = Math.floor(Math.random() * range)

      while (random === forbidden) {
        random = Math.floor(Math.random() * range)
      }

      return random
    },
    startTyping(text) {
      // Get new track
      let track = this.getNewRandomTrack().then((songObject) => {
            let instance = new TypeIt(text, {
              strings: `${songObject.data.name}`,
              speed: this.getRandomNumber(70) + 20,
              waitUntilVisible: true,
              afterComplete: async (step, instance) => {
                instance.reset()
                this.switchCard()
              }
            })
                .break()
                .pause(this.getRandomNumber(800) + 800)
                .type(`<em>${songObject.data.artists[0].name}</em>`)
                .pause(this.getRandomNumber(500) + 500)
                .delete()
                .go()
          }
      )
    },
    getNewRandomTrack: async () => {
      try {
        return await axios.get('/api/trending');
      } catch (error) {
        console.error(error);
      }
    },
    switchCard() {

      if (this.switchState === 0) {
        switch (this.direction) {
          case "right":
            $(this.$refs.upper).animate({right: '0%'}, 700, "swing", () => {
              this.startTyping(this.$refs.upperText)
              this.activeColorLower = this.getRandomNumber(5, this.activeColorUpper)
            });
            break;
          case "left":
            $(this.$refs.upper).animate({left: '0%'}, 700, "swing", () => {
              this.startTyping(this.$refs.upperText)
              this.activeColorLower = this.getRandomNumber(5, this.activeColorUpper)
            });
            break;
          case "top":
            $(this.$refs.upper).animate({top: '0%'}, 700, "swing", () => {
              this.startTyping(this.$refs.upperText)
              this.activeColorLower = this.getRandomNumber(5, this.activeColorUpper)
            });
            break;
          case "bottom":
            $(this.$refs.upper).animate({bottom: '0%'}, 700, "swing", () => {
              this.startTyping(this.$refs.upperText)
              this.activeColorLower = this.getRandomNumber(5, this.activeColorUpper)
            });
            break;
        }

        this.direction = this.getRandomDirection()

        this.switchState = 1
      } else {
        $(this.$refs.upper).css({bottom: "", top: "", right: "", left: ""})
        switch (this.direction) {
          case "right":
            $(this.$refs.upper).animate({right: '100%'}, 700, "swing", () => {
              this.startTyping(this.$refs.lowerText)
              this.activeColorUpper = this.getRandomNumber(5, this.activeColorLower)
            });
            break;
          case "left":
            $(this.$refs.upper).animate({left: '100%'}, 700, "swing", () => {
              this.startTyping(this.$refs.lowerText)
              this.activeColorUpper = this.getRandomNumber(5, this.activeColorLower)
            });
            break;
          case "top":
            $(this.$refs.upper).animate({top: '100%'}, 700, "swing", () => {
              this.startTyping(this.$refs.lowerText)
              this.activeColorUpper = this.getRandomNumber(5, this.activeColorLower)
            });
            break;
          case "bottom":
            $(this.$refs.upper).animate({bottom: '100%'}, 700, "swing", () => {
              this.startTyping(this.$refs.lowerText)
              this.activeColorUpper = this.getRandomNumber(5, this.activeColorLower)
            });
            break;
        }

        this.switchState = 0
      }
    },
    getRandomDirection() {

      let random = this.getRandomNumber(4);

      switch (random) {
        case 0:
          return "right"
        case 1:
          return "top"
        case 2:
          return "bottom"
        case 3:
          return "left"
      }
    }
  }
}
</script>

<style scoped>

</style>