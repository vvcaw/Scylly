<template>
  <div id="particles-js" class="flex flex-col items-center w-full h-screen justify-center z-10 bg-spotify">
    <div class="absolute flex flex-col items-center w-full h-full justify-center z-20">
      <span class="select-none font-bold text-gray-900 text-4xl md:text-5xl">Scylly</span>
      <p class="select-none	text-center text-md md:text-lg font-medium text-gray-700 mt-2 md:mt-4">Discover new songs based on what you like listening to at the moment.</p>
      <p class="select-none text-center	text-md md:text-lg hidden md:inline-block font-medium text-gray-700 mt-1">Just log into your spotify account to get started!</p>
      <form method="get" action="https://accounts.spotify.com:443/authorize">
        <input type="hidden" name="client_id" value="379ef201c377495cb09a99464e0e49df">
        <input type="hidden" name="response_type" value="code">
        <input type="hidden" name="scope" value="user-top-read user-library-modify">
        <input type="hidden" name="redirect_uri" value="https://scylly.eric.familie-elflein.de/redirect">
        <input type="hidden" name="show_dialog" value="true">
        <button class="select-none font-light mt-3 md:mt-5 bg-gray-800 p-2 pl-5 pr-5 rounded-md text-white text-sm md:text-lg shadow-md border border-transparent border-0 hover:bg-transparent hover:border-2 hover:shadow-none hover:border-white">Get started</button>
      </form>
    </div>
    <div class="absolute flex flex-row items-center justify-center gap-2 right-2 bottom-2 z-20">
      <form method="get" action="/imprint">
        <button type="submit" class="opacity-80 select-none right-2 bottom-2 bg-gray-800 p-2 pl-5 pr-5 rounded-md text-white">Imprint</button>
      </form>
      <form method="get" action="/privacy">
        <button type="submit" class="opacity-80 select-none right-2 bottom-2 bg-gray-800 p-2 pl-5 pr-5 rounded-md text-white">Privacy Policy</button>
      </form>
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