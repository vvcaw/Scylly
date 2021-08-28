<template>
  <div class="flex flex-col items-center w-full h-screen justify-center z-10 bg-spotify">
    <div class="absolute flex flex-col items-center w-full h-full justify-center z-20">
      <span class="font-bold text-5xl">Spotinder</span>
      <p class="font-medium text-gray-600 mt-5">Discover new songs based on what you like listening to at the moment.</p>
      <p class="font-medium text-gray-600 mt-1">Just log into your spotify account to get started!</p>
      <button class="font-medium text-gray-600 mt-4 border-1 bg-white border-spotify p-2 rounded-2xl">Get started</button>
    </div>
  </div>
</template>

<script>
import '../assets/particles.js'

export default {
  name: "Index",
  mounted() {
    window.particlesJS('particles-js', {
      "particles": {
        "number": {
          "value": 106,
          "density": {
            "enable": true,
            "value_area": 1042.21783956259
          }
        },
        "color": {
          "value": "#48e816"
        },
        "shape": {
          "type": "circle",
          "stroke": {
            "width": 0,
            "color": "#ff0000"
          },
          "polygon": {
            "nb_sides": 6
          },
          "image": {
            "src": "img/github.svg",
            "width": 70,
            "height": 100
          }
        },
        "opacity": {
          "value": 0.5,
          "random": false,
          "anim": {
            "enable": true,
            "speed": 1,
            "opacity_min": 0.1,
            "sync": false
          }
        },
        "size": {
          "value": 3,
          "random": true,
          "anim": {
            "enable": true,
            "speed": 12.181158184520175,
            "size_min": 0.1,
            "sync": false
          }
        },
        "line_linked": {
          "enable": true,
          "distance": 150,
          "color": "#ffffff",
          "opacity": 0.4,
          "width": 1
        },
        "move": {
          "enable": true,
          "speed": 6,
          "direction": "none",
          "random": false,
          "straight": false,
          "out_mode": "out",
          "bounce": false,
          "attract": {
            "enable": false,
            "rotateX": 600,
            "rotateY": 1200
          }
        }
      },
      "interactivity": {
        "detect_on": "window",
        "events": {
          "onhover": {
            "enable": true,
            "mode": "grab"
          },
          "onclick": {
            "enable": true,
            "mode": "push"
          },
          "resize": true
        },
        "modes": {
          "grab": {
            "distance": 158.35505639876231,
            "line_linked": {
              "opacity": 1
            }
          },
          "bubble": {
            "distance": 400,
            "size": 40,
            "duration": 2,
            "opacity": 8,
            "speed": 3
          },
          "repulse": {
            "distance": 200,
            "duration": 0.4
          },
          "push": {
            "particles_nb": 4
          },
          "remove": {
            "particles_nb": 2
          }
        }
      },
      "retina_detect": true
    })
  }
}
</script>