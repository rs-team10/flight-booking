<template>
<div>
    <GmapMap v-if="this.selectedHotelLocation.lat"
        :center="currentMapCenter"
        :zoom="14"
        map-type-id="terrain"
        class="custom-map"
        :options="{
            zoomControl: true,
            mapTypeControl: true,
            scaleControl: false,
            streetViewControl: false,
            rotateControl: false,
            fullscreenControl: false,
            disableDefaultUi: false
        }">
        <GmapMarker
            :position="currentMapCenter"
            :clickable="false"
            :draggable="false"/>
    </GmapMap>
</div>
</template>

<script>
export default {
    props: [ 'selectedHotelLocation' ],

    data(){
        return {

        }
    },
    computed: {
        currentMapCenter(){
            return this.selectedHotelLocation;
        }
    }
}
</script>

<style scoped>
.custom-map {
    position: relative;
    padding: 5px 0;
    width: 100%;
    height: 590px;
}
</style>
