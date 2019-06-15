<template>
    <v-layout column ma-4>
        <v-data-table
            :headers="headers"
            :items="invitations"
            v-bind:pagination.sync="pagination">

            <template v-slot:items="props">
                <td>{{ props.item.departure }}</td>
                <td>{{ props.item.destination }}</td>
                <td>{{ props.item.date }}</td>
                <td class="text-xs-right disable-events">              
                    <v-btn depressed :color="computeButtonColor(props.item.status)">
                        <span>{{ props.item.status }}</span>
                    </v-btn>
                </td>
                <td class="text-xs-right">              
                    <v-btn dark depressed color="green" v-if="isOnWait(props.item.status)" @click="acceptInvitation(props.item)">
                        <span>Accept</span>
                    </v-btn>
                    <v-btn dark depressed color="red" v-if="isOnWait(props.item.status)" @click="declineInvitation(props.item)">
                        <span>Decline</span>
                    </v-btn>
                </td>
            </template>
            <v-alert v-slot:no-results :value="true" color="error" icon="warning">
                No results.
            </v-alert>
        </v-data-table>
    </v-layout>
</template>

<script>
export default {
    name: 'invitations',
    data() {
        return {
            headers: [
                {
                    text: "From",
                    align: 'left',
                    sortable: true,
                    value: 'from'
                },
                {
                    text: "To",
                    align: 'left',
                    sortable: true,
                    value: 'to'
                },
                {
                    text: "Date",
                    align: 'left',
                    sortable: true,
                    value: 'date'
                },
                {
                    text: "Status",
                    align: 'left',
                    sortable: true,
                    value: 'status',
                    width: 130
                },
                {
                    text: "",
                    align: 'right',
                    sortable: false,
                    value: 'actions',
                    width: 300
                }
            ],
            pagination: {'sortBy': 'status', 'descending': false, 'rowsPerPage': 5},
            invitations: []
        };
    },
    methods: {
        computeButtonColor(status) {
            if(status.toLowerCase() == "waiting")
                return "yellow";
            else if (status.toLowerCase() == "accepted")
                return "green";
            else
                return "red";
        },
        isOnWait(status) {
            if(status.toLowerCase() == 'waiting')
                return true;
            else
                return false;
        },
        acceptInvitation: function(item) {

            var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};
            
            this.$axios.put('http://localhost:8080/api/reservations/acceptInvitation/' + item.id, {}, yourConfig).then((response) => {
                this.$swal('Accepted', 'Invitation accepted.', 'success');
                item.status = "Accepted";
            })
            .catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });
        },
        declineInvitation: function(item) {
            
            var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};
            
            this.$axios.put('http://localhost:8080/api/reservations/declineInvitation/' + item.id, {}, yourConfig).then((response) => {
                this.$swal('Declined', 'Invitation declined.', 'success');
                item.status = "Denied";
            })
            .catch((error) => {
                this.$swal("Error", error.response.data.message, 'error');
            });
        }
    },
    created() {
        var yourConfig = { headers: { Authorization: "Bearer " + localStorage.getItem("token") }};

        this.$axios.get('http://localhost:8080/api/reservations/getAllInvitations/', yourConfig).then((response) => {
            this.invitations = response.data;
        }).catch((error) => {
            this.$swal("Error", error.response.data.message, 'error');
        });
    }
}
</script>

<style>
.disable-events {
  pointer-events: none
}
</style>