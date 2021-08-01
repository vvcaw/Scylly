<template>
  <div>
    <InfoBanner/>
    <Help/>
    <AccountIcon/>
    <div class="min-h-screen w-full bg-black grid justify-start items-center" v-bind:class="{ 'grid-cols-1 grid-rows-1' : view === 0, 'grid-cols-2 grid-rows-2' : view === 1, 'grid-cols-3 grid-rows-3' : view === 2 }">
      <Card v-if="view === 0"/>
      <Card v-if="view === 1" v-for="i in 4"/>
      <Card v-if="view === 2" v-for="i in 9"/>
    </div>
  </div>
</template>

<script>
import Card from "./Card.vue";
import InfoBanner from "./InfoBanner.vue";
import Help from "./Help.vue";
import AccountIcon from "./AccountIcon.vue";

export default {
  name: "Overview",
  components: {
    AccountIcon,
    Help,
    InfoBanner,
    Card
  },
  data(){
    return {
      view: 1
    }
  }
}
</script>