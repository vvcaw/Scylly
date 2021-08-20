<template>
  <div class="flex items-center w-full h-screen justify-center z-10">

    <SoundInformation/>

    <div class="bg-black h-full grid grid-cols-2 w-full absolute items-center justify-center">
      <div v-on:mouseenter="handleMouseOver(0)" v-on:click="yeet(0)" ref="no"
           class="group bg-black border-r-2 border-solid border-opacity-20 cursor-pointer h-full w-full flex items-center justify-center hover:bg-gray-900">
        <svg xmlns="http://www.w3.org/2000/svg" class="text-spotifyRed group-hover:animate-pulse h-48 w-48"
             viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd"
                d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                clip-rule="evenodd"/>
        </svg>
      </div>
      <div v-on:mouseenter="handleMouseOver(1)" v-on:click="yeet(1)" ref="yes"
           class="group bg-black border-l-1 border-solid border-opacity-20 cursor-pointer h-full w-full flex items-center justify-center  hover:bg-gray-900">
        <svg xmlns="http://www.w3.org/2000/svg" class="text-spotify group-hover:animate-pulse h-48 w-48"
             viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd"
                d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z"
                clip-rule="evenodd"/>
        </svg>
      </div>
    </div>
    <!-- the v-show here is not necessary and could be removed -- Number of cards is variable -->
    <Song ref="song" v-show="this.activeCard === 0" v-on:done="cycle(this.$refs.song, this.activeCard)" v-on:done-fetching="cardActiveState[0] = 1" :card-num-prop="3" :songs="cardArrays[0]"/>
    <Song ref="song2" v-show="this.activeCard === 1" v-on:done="cycle(this.$refs.song2, this.activeCard)" v-on:done-fetching="cardActiveState[1] = 1" :card-num-prop="2" :songs="cardArrays[1]"/>
    <Song ref="song3" v-show="this.activeCard === 2" v-on:done="cycle(this.$refs.song3, this.activeCard)" v-on:done-fetching="cardActiveState[2] = 1" :card-num-prop="1" :songs="cardArrays[2]"/>
  </div>
</template>

<script>
import Song from "./Song.vue";
import $ from "jquery";
import jQuery from "jquery";
import SoundInformation from "./SoundInformation.vue";
import { easings } from "../assets/easings";

export default {
  name: "Discover",
  components: {SoundInformation, Song},
  data() {
    return {
      canClick: true,
      lastDirection: 0,
      recommendations: $javalin.state.recommendations,
      numberOfCards: 3,
      activeCard: 0,
      cards: [],
      cardActiveState: [1, 1, 1]
    }
  },
  computed: {
    cardArrays: function () {
      return new Array(Math.ceil(this.recommendations.length / this.numberOfCards))
        .fill()
        .map(_ => this.recommendations.splice(0, this.numberOfCards))
    }
  },
  mounted() {
    // Add all cards to cards array
    this.cards.push(this.$refs.song)
    this.cards.push(this.$refs.song2)
    this.cards.push(this.$refs.song3)
  },
  methods: {
    handleMouseOver(direction) {
      this.toggleActiveState(direction)

      this.lastDirection = direction
    },
    toggleActiveState(direction) {
      if (!this.canClick)
        return

      let card = this.cards[this.activeCard]

      card.toggleActiveState(direction)
    },
    yeet(direction) {
      if (!this.canClick)
        return

      this.canClick = false

      let card = this.cards[this.activeCard]

      card.yeet(direction)
    },
    cycle(card, cardIndex) {
      // card is the card to be reset
      card.setCardNum(1)

      // increment the position of all other cards by one
      this.cards.forEach((value, index) => {
        if (index !== cardIndex) {
          value.incrementCardNum()
        }
      });

      // change active card number
      if (this.activeCard + 1 < this.cards.length) {
        this.activeCard++
      } else {
        this.activeCard = 0
      }

      // Allow user to click
      this.canClick = true

      // change direction of new active card to last registered direction
      this.toggleActiveState(this.lastDirection)
    }
  }
}


// JQUERYUI EASING
$.easing['jswing'] = jQuery.easing['swing'];

jQuery.extend(jQuery.easing, easings);
</script>