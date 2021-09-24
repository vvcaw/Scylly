<template id="#app">
  <div class="h-screen w-full flex flex-col justify-center bg-spotify">
    <div id="particles-js" class="h-60 w-full items-end justify-start flex">
      <div class="absolute border-2 bg-transparent z-20 border-dashed rounded-md pl-5 pr-5 pb-3 p-2 ml-12 mb-12">
        <span class="text-white text-4xl font-medium select-none">Imprint</span>
      </div>
    </div>
    <div class="w-full h-full bg-gray-800">
      <div class="ml-12 mt-12 flex flex-col items-start justify-start text-white gap-y-3">
        <h2 class="text-xl font-medium">Angaben gem&auml;&szlig; &sect; 5 TMG</h2>
        <p>Eric Elflein<br />
          Treinfeld 47<br />
          96184 Rentweinsdorf</p>

        <h2 class="text-xl font-medium">Kontakt</h2>
        <p>E-Mail: eric.elflein@googlemail.com</p>

        <p>Quelle: <a href="https://www.e-recht24.de">e-recht24.de</a></p>
      </div>
    </div>
  </div>
</template>

<script>
import '../assets/particles.js'

export default {
  name: "Imprint",
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

<style scoped>

</style>