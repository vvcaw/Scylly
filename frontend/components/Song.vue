<template>
  <div ref="song"
       class="pointer-events-none z-10 mr-0 ml-0 p-4 bg-black border-2 border-dashed h-96 w-72 rounded-md flex flex-col">
    <img class="select-none rounded-md" :src="dataSongs[activeIndex].images[1]">
    <div class="h-full w-full flex flex-col items-start justify-center">
      <span class="select-none text-white font-medium text-2xl">{{ dataSongs[activeIndex].name }}</span>
      <span class="select-none text-gray-600 text-xl">{{ dataSongs[activeIndex].artists[0].name }}</span>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import $ from "jquery";
import jQuery from "jquery";

export default {
  name: "Song",
  props: {
    songs: Array,
  },
  data() {
    return {
      activeIndex: 0,
      resetting: false,
      degrees: 0,
      margin: [0, 0],
      activeDirection: 0,
      dataSongs: this.songs
    }
  },
  methods: {
    getRecommendations() {
      return axios.get('/api/recommendations')
          .then(function (response) {
            return response;
          })
          .catch(function (error) {
            console.log(error);
          })
    },
    toggleActiveState(direction) {
      let card = $(this.$refs.song)
      let vue = this;

      this.activeDirection = direction

      if (this.resetting)
        return

      if (direction === 0) {
        $({deg: this.degrees, marginRight: this.margin[1], marginLeft: this.margin[0]}).animate({
          deg: -10,
          marginRight: 15,
          marginLeft: 0
        }, {
          duration: 800,
          easing: "easeOutExpo",
          step: function (now, x) {
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
        this.degrees = -10
        this.margin[1] = 15
        this.margin[0] = 0
      } else if (direction === 1) {
        $({deg: this.degrees, marginLeft: this.margin[0], marginRight: this.margin[1]}).animate({
          deg: 10,
          marginLeft: 15,
          marginRight: 0
        }, {
          duration: 800,
          easing: "easeOutExpo",
          step: function (now, x) {
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
          }
        });

        // Update values for next iteration
        this.degrees = 10
        this.margin[1] = 0
        this.margin[0] = 15
      }
    },
    yeet(direction) {
      let card = $(this.$refs.song)

      if (this.resetting)
        return

      if (direction === 0) {

        // Hinder user from canceling animations
        this.resetting = true

        $({deg: this.degrees, marginRight: this.margin[1]}).animate({
          deg: 0,
          marginRight: (window.innerWidth + card.width())
        }, {
          duration: 300,
          easing: "easeOutCirc",
          complete: () => {
            this.reset()
          },
          step: function (now, x) {
            switch (x.prop) {
              case "deg":
                card.css("transform", `rotate(${now}deg)`);
                break;
              case "marginRight":
                card.css(x.prop, `${now}px`);
                break;
            }
          },
        });
      } else if (direction === 1) {

        // Hinder user from canceling animations
        this.resetting = true

        $({deg: this.degrees, marginLeft: this.margin[0]}).animate({
          deg: 0,
          marginLeft: (window.innerWidth + card.width())
        }, {
          duration: 300,
          easing: "easeOutCirc",
          complete: () => {
            this.reset()
          },
          step: function (now, x) {
            switch (x.prop) {
              case "deg":
                card.css("transform", `rotate(${now}deg)`);
                break;
              case "marginLeft":
                card.css(x.prop, `${now}px`);
                break;
            }
          },
        });
      }
    },
    async reset() {
      let card = $(this.$refs.song)

      card.css("visibility", "hidden")

      // Change this to http request -> return promise an await it
      if (this.activeIndex + 1 >= this.dataSongs.length) {
        let res = await this.getRecommendations()
        this.dataSongs = res.data
        this.activeIndex = 0
      } else {
        this.activeIndex++
      }

      card.removeAttr('style')

      // Nice animation missing here fix it pls
      this.degrees = 0
      this.margin[1] = 0
      this.margin[0] = 0

      this.resetting = false

      // Update new direction for card
      this.toggleActiveState(this.activeDirection)
    }
  }
}
</script>