<template>
  <div ref="song"
       class="pointer-events-none z-10 mr-0 ml-0 p-4 bg-white border border-opacity-25 h-96 w-72 rounded-md">
  </div>
</template>

<script>
export default {
  name: "Song",
  mounted() {

  },
  data() {
    return {
      resetting: false,
      degrees: 0,
      margin: [0, 0],
      activeDirection: 0
    }
  },
  methods: {
    toggleActiveState(direction) {
      let card = $(this.$refs.song)
      let vue = this;

      this.activeDirection = direction

      if(this.resetting)
        return

      if (direction === 0){
        $({deg: this.degrees, marginRight: this.margin[1], marginLeft: this.margin[0]}).animate({deg: -10, marginRight: 15, marginLeft: 0}, {
          duration: 800,
          easing: "easeOutExpo",
          step: function (now, x) {
            switch (x.prop){
              case "deg": card.css("transform", `rotate(${now}deg)`); vue.degrees = now; break;
              case "marginLeft": card.css(x.prop, `${now}rem`); vue.margin[0] = now; break;
              case "marginRight": card.css(x.prop, `${now}rem`); vue.margin[1] = now; break;
            }
          },
        });

        // Update values for next iteration
        this.degrees = -10
        this.margin[1] = 15
        this.margin[0] = 0
      } else if (direction === 1){
        $({deg: this.degrees, marginLeft: this.margin[0], marginRight: this.margin[1]}).animate({deg: 10, marginLeft: 15, marginRight: 0}, {
          duration: 800,
          easing: "easeOutExpo",
          step: function (now, x) {
            switch (x.prop){
              case "deg": card.css("transform", `rotate(${now}deg)`); vue.degrees = now; break;
              case "marginLeft": card.css(x.prop, `${now}rem`); vue.margin[0] = now; break;
              case "marginRight": card.css(x.prop, `${now}rem`); vue.margin[1] = now; break;
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

      if(this.resetting)
        return

      if (direction === 0){

        // Hinder user from canceling animations
        this.resetting = true

        $({deg: this.degrees, marginRight: this.margin[1]}).animate({deg: 0, marginRight: (window.innerWidth + card.width())}, {
          duration: 300,
          easing: "easeOutCirc",
          complete: () => {
            this.reset()
          },
          step: function (now, x) {
            switch (x.prop){
              case "deg": card.css("transform", `rotate(${now}deg)`); break;
              case "marginRight": card.css(x.prop, `${now}px`); break;
            }
          },
        });
      } else if (direction === 1){

        // Hinder user from canceling animations
        this.resetting = true

        $({deg: this.degrees, marginLeft: this.margin[0]}).animate({deg: 0, marginLeft: (window.innerWidth + card.width())}, {
          duration: 300,
          easing: "easeOutCirc",
          complete: () => {
            this.reset()
          },
          step: function (now, x) {
            switch (x.prop){
              case "deg": card.css("transform", `rotate(${now}deg)`); break;
              case "marginLeft": card.css(x.prop, `${now}px`); break;
            }
          },
        });
      }
    },
    reset() {
      let card = $(this.$refs.song)

      card.removeAttr('style')

      console.log("-------------------------------------")
      console.log(card.css("margin-left") + "LEFT BEFORE")
      console.log(card.css("margin-right") + "RIGHT BEFORE")

      // Nice animation missing here fix it pls

      this.degrees = 0
      this.margin[1] = 0
      this.margin[0] = 0

      this.resetting = false

      // Update new direction for card
      this.toggleActiveState(this.activeDirection)

      /*
      $({scale: 0}).animate({scale: 1}, {
        duration: 300,
        easing: "easeOutBounce",
        complete: () => {
          // Update values for next iteration
          this.degrees = 0
          this.margin[1] = 0
          this.margin[0] = 0

          this.resetting = false
          card.removeAttr('style')

          // Update new direction for card
          this.toggleActiveState(this.activeDirection)
        },
        step: function (now, x) {
          console.log(card.css("margin-left") + "LEFT")
          console.log(card.css("margin-right") + "RIGHT")
          switch (x.prop){
            case "scale": card.css("transform", `scale(${now})`); break;
          }
        },
      });
      */
    }
  }
}

// JQUERYUI EASING
$.easing['jswing'] = jQuery.easing['swing'];

jQuery.extend( jQuery.easing,
    {
      def: 'easeOutQuad',
      swing: function (x, t, b, c, d) {
        return jQuery.easing[jQuery.easing.def](x, t, b, c, d);
      },
      export: function easeInQuad(x, t, b, c,d) {
        return c*(t/=d)*t + b;
      },
      easeOutQuad: function (x, t, b, c, d) {
        return -c *(t/=d)*(t-2) + b;
      },
      easeInOutQuad: function (x, t, b, c, d) {
        if ((t/=d/2) < 1) return c/2*t*t + b;
        return -c/2 * ((--t)*(t-2) - 1) + b;
      },
      easeInCubic: function (x, t, b, c, d) {
        return c*(t/=d)*t*t + b;
      },
      easeOutCubic: function (x, t, b, c, d) {
        return c*((t=t/d-1)*t*t + 1) + b;
      },
      easeInOutCubic: function (x, t, b, c, d) {
        if ((t/=d/2) < 1) return c/2*t*t*t + b;
        return c/2*((t-=2)*t*t + 2) + b;
      },
      easeInQuart: function (x, t, b, c, d) {
        return c*(t/=d)*t*t*t + b;
      },
      easeOutQuart: function (x, t, b, c, d) {
        return -c * ((t=t/d-1)*t*t*t - 1) + b;
      },
      easeInOutQuart: function (x, t, b, c, d) {
        if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
        return -c/2 * ((t-=2)*t*t*t - 2) + b;
      },
      easeInQuint: function (x, t, b, c, d) {
        return c*(t/=d)*t*t*t*t + b;
      },
      easeOutQuint: function (x, t, b, c, d) {
        return c*((t=t/d-1)*t*t*t*t + 1) + b;
      },
      easeInOutQuint: function (x, t, b, c, d) {
        if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
        return c/2*((t-=2)*t*t*t*t + 2) + b;
      },
      easeInSine: function (x, t, b, c, d) {
        return -c * Math.cos(t/d * (Math.PI/2)) + c + b;
      },
      easeOutSine: function (x, t, b, c, d) {
        return c * Math.sin(t/d * (Math.PI/2)) + b;
      },
      easeInOutSine: function (x, t, b, c, d) {
        return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b;
      },
      easeInExpo: function (x, t, b, c, d) {
        return (t==0) ? b : c * Math.pow(2, 10 * (t/d - 1)) + b;
      },
      easeOutExpo: function (x, t, b, c, d) {
        return (t==d) ? b+c : c * (-Math.pow(2, -10 * t/d) + 1) + b;
      },
      easeInOutExpo: function (x, t, b, c, d) {
        if (t==0) return b;
        if (t==d) return b+c;
        if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b;
        return c/2 * (-Math.pow(2, -10 * --t) + 2) + b;
      },
      easeInCirc: function (x, t, b, c, d) {
        return -c * (Math.sqrt(1 - (t/=d)*t) - 1) + b;
      },
      easeOutCirc: function (x, t, b, c, d) {
        return c * Math.sqrt(1 - (t=t/d-1)*t) + b;
      },
      easeInOutCirc: function (x, t, b, c, d) {
        if ((t/=d/2) < 1) return -c/2 * (Math.sqrt(1 - t*t) - 1) + b;
        return c/2 * (Math.sqrt(1 - (t-=2)*t) + 1) + b;
      },
      easeInElastic: function (x, t, b, c, d) {
        var s=1.70158;var p=0;var a=c;
        if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
        if (a < Math.abs(c)) { a=c; var s=p/4; }
        else var s = p/(2*Math.PI) * Math.asin (c/a);
        return -(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
      },
      easeOutElastic: function (x, t, b, c, d) {
        var s=1.70158;var p=0;var a=c;
        if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
        if (a < Math.abs(c)) { a=c; var s=p/4; }
        else var s = p/(2*Math.PI) * Math.asin (c/a);
        return a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b;
      },
      easeInOutElastic: function (x, t, b, c, d) {
        var s=1.70158;var p=0;var a=c;
        if (t==0) return b;  if ((t/=d/2)==2) return b+c;  if (!p) p=d*(.3*1.5);
        if (a < Math.abs(c)) { a=c; var s=p/4; }
        else var s = p/(2*Math.PI) * Math.asin (c/a);
        if (t < 1) return -.5*(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
        return a*Math.pow(2,-10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )*.5 + c + b;
      },
      easeInBack: function (x, t, b, c, d, s) {
        if (s == undefined) s = 1.70158;
        return c*(t/=d)*t*((s+1)*t - s) + b;
      },
      easeOutBack: function (x, t, b, c, d, s) {
        if (s == undefined) s = 1.70158;
        return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
      },
      easeInOutBack: function (x, t, b, c, d, s) {
        if (s == undefined) s = 1.70158;
        if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b;
        return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b;
      },
      easeInBounce: function (x, t, b, c, d) {
        return c - jQuery.easing.easeOutBounce (x, d-t, 0, c, d) + b;
      },
      easeOutBounce: function (x, t, b, c, d) {
        if ((t/=d) < (1/2.75)) {
          return c*(7.5625*t*t) + b;
        } else if (t < (2/2.75)) {
          return c*(7.5625*(t-=(1.5/2.75))*t + .75) + b;
        } else if (t < (2.5/2.75)) {
          return c*(7.5625*(t-=(2.25/2.75))*t + .9375) + b;
        } else {
          return c*(7.5625*(t-=(2.625/2.75))*t + .984375) + b;
        }
      },
      easeInOutBounce: function (x, t, b, c, d) {
        if (t < d/2) return jQuery.easing.easeInBounce (x, t*2, 0, c, d) * .5 + b;
        return jQuery.easing.easeOutBounce (x, t*2-d, 0, c, d) * .5 + c*.5 + b;
      }
    });

</script>