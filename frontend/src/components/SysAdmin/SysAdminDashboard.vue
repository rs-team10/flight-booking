<template>
    <div>
        <v-layout row>
            <define-discount></define-discount>
            <add-service></add-service>
        </v-layout>
    </div>
</template>

<script>
import AddService from "./AddService.vue"
import DefineDiscount from "./DefineDiscount.vue"

export default{
    components: {
        "add-service" : AddService,
        "define-discount" : DefineDiscount
    }
}
</script>