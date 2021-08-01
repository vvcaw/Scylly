<template>
  <div class="bg-black h-screen grid grid-cols-10 items-center justify-center">
    <div ref="no" class="bg-black border-r-2 border-dashed col-span-2 h-full w-full">

    </div>
    <div class="flex items-center col-span-6 justify-center">
      <div ref="draggable" class="cursor-move p-4 bg-white border border-opacity-25 h-64 w-64 rounded-md">
        <h1 class="text-white">Some head</h1>
      </div>
    </div>
    <div ref="yes" class="bg-black border-l-2 border-dashed col-span-2 h-full w-full">

    </div>
  </div>
</template>

<script>

export default {
  name: "Discover",
  methods: {},
  data() {
    return {
      direction: 0
    }
  },
  mounted() {
    let card = this.$refs.draggable
    let yes = this.$refs.yes
    let no = this.$refs.no

    $(yes).droppable({
      drop: (event, ui) => {
        console.log("Yes!")
      }
    })

    $(no).droppable({
      drop: (event, ui) => {
        console.log("No!")

        $(event)
      }
    })

    // Make card draggable
    $(card).draggable({
      refreshPositions: true,
      revert: true,
      scroll: false,
      start: (event, ui) => {
        console.log(ui)
      },
      stop: (event, ui) => {
        $(card).animate(
            {
              deg: 0
            },
            {
              duration: 100,
              step: function (now) {
                $(this).css({
                  transform: 'rotate(' + now + 'deg)'
                })
              }
            }
        )

        this.direction = 0
      },
      drag: (event, ui) => {
        if (ui.position.left < 0 && this.direction !== -1) {
          $(card).animate(
              {
                deg: -10
              },
              {
                duration: 200,
                step: function (now) {
                  $(this).css({
                    transform: 'rotate(' + now + 'deg)'
                  })
                }
              }
          )

          this.direction = -1
        }

        else if (ui.position.left === 0 && this.direction !== 0) {
          $(card).animate(
              {
                deg: 0
              },
              {
                duration: 100,
                step: function (now) {
                  $(this).css({
                    transform: 'rotate(' + now + 'deg)'
                  })
                }
              }
          )

          this.direction = 0
        }

        else if (ui.position.left > 0 && this.direction !== 1) {
          $(card).animate(
              {
                deg: 10
              },
              {
                duration: 200,
                step: function (now) {
                  $(this).css({
                    transform: 'rotate(' + now + 'deg)'
                  })
                }
              }
          )

          this.direction = 1
        }
      }
    });
  }
}
</script>