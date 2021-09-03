<template>
  <div class="flex items-center w-full h-screen justify-center z-10">

    <SoundInformation v-on:done="handleUserValidated"/>

    <div class="bg-black h-full grid grid-cols-2 w-full absolute items-center justify-center">
      <!-- User is on PC -->
      <div v-if="!userIsOnMobile" v-on:mouseenter="handleMouseOver(0)" v-on:click="yeet(0)" ref="no"
           class="group bg-black border-r-2 border-solid border-opacity-20 cursor-pointer h-full w-full flex items-end md:items-center justify-center hover:bg-gray-900">
        <svg xmlns="http://www.w3.org/2000/svg" class="md:mb-0 mb-32 text-spotifyRed group-hover:animate-pulse h-24 w-24 md:h-48 md:w-48"
             viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd"
                d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                clip-rule="evenodd"/>
        </svg>
      </div>
      <div v-if="!userIsOnMobile" v-on:mouseenter="handleMouseOver(1)" v-on:click="yeet(1)" ref="yes"
           class="group bg-black border-l-1 border-solid border-opacity-20 cursor-pointer h-full w-full flex items-end md:items-center justify-center  hover:bg-gray-900">
        <svg xmlns="http://www.w3.org/2000/svg" class="md:mb-0 mb-32 text-spotify group-hover:animate-pulse h-24 w-24 md:h-48 md:w-48"
             viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd"
                d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z"
                clip-rule="evenodd"/>
        </svg>
      </div>

      <!-- User is on mobile -->
      <div v-if="userIsOnMobile" ref="no"
           class="bg-black border-r-2 border-solid border-opacity-20 cursor-pointer h-full w-full flex items-end md:items-center justify-center">
        <svg xmlns="http://www.w3.org/2000/svg" class="md:mb-0 mb-32 text-spotifyRed h-24 w-24 md:h-48 md:w-48"
             viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd"
                d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                clip-rule="evenodd"/>
        </svg>
      </div>
      <div v-if="userIsOnMobile" ref="yes"
           class="bg-black border-l-1 border-solid border-opacity-20 cursor-pointer h-full w-full flex items-end md:items-center justify-center">
        <svg xmlns="http://www.w3.org/2000/svg" class="md:mb-0 mb-32 text-spotify h-24 w-24 md:h-48 md:w-48"
             viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd"
                d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z"
                clip-rule="evenodd"/>
        </svg>
      </div>
    </div>
    <!-- the v-show here is not necessary and could be removed -- Number of cards is variable -->
    <Song ref="song" class="mb-20 md:bm-0" v-show="this.activeCard === 0" v-on:done="cycle(this.$refs.song, this.activeCard)" :card-num-prop="3" :songs="cardArrays[0]"/>
    <Song ref="song2" class="mb-20 md:bm-0" v-show="this.activeCard === 1" v-on:done="cycle(this.$refs.song2, this.activeCard)" :card-num-prop="2" :songs="cardArrays[1]"/>
    <Song ref="song3" class="mb-20 md:bm-0" v-show="this.activeCard === 2" v-on:done="cycle(this.$refs.song3, this.activeCard)" :card-num-prop="1" :songs="cardArrays[2]"/>
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
      userIsOnMobile: (window.innerWidth < 768),
      xDown: null,
      yDown: null
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

    if (this.userIsOnMobile) {
      document.addEventListener('touchstart', this.handleSwipeStart, false)
      document.addEventListener('touchmove', this.handleSwipeMove, false)
    }
  },
  methods: {
    handleMouseOver(direction) {
      this.toggleActiveState(direction)

      this.lastDirection = direction
    },
    getTouches(evt) {
      return evt.touches || evt.originalEvent.touches
    },
    handleSwipeStart(evt) {
      const firstTouch = this.getTouches(evt)[0]

      this.xDown = firstTouch.clientX
      this.yDown = firstTouch.clientY
    },
    handleSwipeMove(evt) {
      if ( ! this.xDown || ! this.yDown ) {
        return;
      }

      const xUp = evt.touches[0].clientX
      const yUp = evt.touches[0].clientY

      const xDiff = this.xDown - xUp
      const yDiff = this.yDown - yUp

      if ( Math.abs( xDiff ) > Math.abs( yDiff ) ) {
        if ( xDiff > 0 ) {
          this.yeetMobile(0)
        } else {
          this.yeetMobile(1)
        }
      } else {
        if ( yDiff > 0 ) {
          console.log("swipe down")
        } else {
          console.log("swipe up")
        }
      }

      this.xDown = null
      this.yDown = null
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
    yeetMobile(direction) {
      if (!this.canClick)
        return

      this.canClick = false

      let card = this.cards[this.activeCard]

      card.yeetMobile(direction)
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

      if (!this.userIsOnMobile){
        // change direction of new active card to last registered direction
        this.toggleActiveState(this.lastDirection)
      }
      else {
        const card = this.cards[this.activeCard]

        card.play()
        card.playing = true
      }
    },
    handleUserValidated() {
      if (this.userIsOnMobile) {
        const card = this.cards[this.activeCard]

        card.play()
        card.playing = true
      }

    }
  }
}


// JQUERYUI EASING
$.easing['jswing'] = jQuery.easing['swing'];

jQuery.extend(jQuery.easing, easings);
</script>